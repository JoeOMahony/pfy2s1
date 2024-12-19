package controllers;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import models.Item;
import models.Note;

import java.util.ArrayList;

import static utils.CategoryUtility.isValidCategory;
import static utils.Utilities.validRange;

/**
 * The responsibility of the {@code NoteAPI} class is to manage a collection (ArrayList) of {@link Note} objects.
 * <p>
 * Each {@link Note} may contain zero or many {@link Item} objects. The {@code NoteAPI} provides:
 * <ul>
 *   <li>CRUD operations for managing notes (add, update, delete).</li>
 *   <li>Methods for archiving notes based on specific conditions (e.g., all items completed).</li>
 *   <li>Counting methods to retrieve information about how many notes/items meet various conditions
 *   (e.g. number of active or archived notes, number of items completed).</li>
 *   <li>Listing methods to display notes or items based on various conditions, such as category, priority, and item
 *   completion state.</li>
 *   <li>Searching methods to find notes by title or items by description.</li>
 *   <li>Persistence methods to save and load notes from an XML file using {@code XStream}.</li>
 * </ul>
 *
 * <h4>Purpose:</h4>
 * <ul>
 *   <li>Provide a central API for working with {@link Note} objects and their associated {@link Item} objects.</li>
 *   <li>Enforce validation rules with notes (e.g. index validation, category validation through
 *   {@link utils.CategoryUtility}).</li>
 *   <li>Support persistence.</li>
 * </ul>
 *
 * @author Joe O'Mahony, Dave Hearne
 * @version 2.0
 */
public class NoteAPI {
    // The responsibility for the NoteAPI class is to manage the ArrayList of notes.

    /* You will notice that we don’t have constructors, getters, setters and
    toStrings in the above class structure. You can decide to generate these
    boilerplate methods for your class, should you wish. */

// -------------- START OF CRUD METHODS --------------

    /**
     * Adds a {@link Note} object to the notes collection.
     *
     * @param note note to add
     * @return {@code true} if the {@link Note} was successfully added, {@code false} otherwise
     */
    public boolean add(Note note) {
        //This method adds a note object to the ArrayList notes and returns
        // the boolean result of the add.
        return notes.add(note);
    }

    /**
     * Updates an existing {@link Note} at the passed index with new details for title, priority, and category.
     * If the index is invalid, returns {@code false}. Otherwise, updates the note and returns {@code true}.
     *
     * @param indexToUpdate index of the note to update
     * @param noteTitle     new title for the note
     * @param notePriority  new priority for the note (1 to 5)
     * @param noteCategory  new category for the note (one of the valid categories)
     * @return {@code true} if the update was successful; {@code false} if the update fails
     */
    public boolean updateNote(int indexToUpdate, String noteTitle, int notePriority, String noteCategory) {
        /*
        This method should attempt to retrieve the note stored at the index number passed
        as a parameter. ==>> If the note:
        ==>> doesn’t exist, return false, indicating the update was not successful.
        ==>> exists, use the Note mutators to update the title, priority and category of
             the note with the details passed in the parameter list. Finally, return
             true to indicate a successful update.
         */
        if (isValidIndex(indexToUpdate)) {
            notes.get(indexToUpdate).setNoteTitle(noteTitle);
            notes.get(indexToUpdate).setNotePriority(notePriority);
            notes.get(indexToUpdate).setNoteCategory(noteCategory);
            return true;
        }
        return false;
    }

    /**
     * Deletes the {@link Note} at the specified index, if valid.
     * Also deletes all {@link Item} objects associated with that note (cascading delete needed for {@link NoteAPITest}).
     * If the index is valid, deletes the {@link Note} and returns the deleted {@link Note}.
     * Otherwise, returns {@code null}.
     *
     * @param indexToDelete index of the note to delete
     * @return the deleted {@link Note} if successful,{@code null} otherwise.
     */
    public Note deleteNote(int indexToDelete) {
        /* This method deletes a note at the index parameter (if that index exists)
           and returns the deleted note object. If the index does not exist in the
           notes list, then null should be returned. */
        if (isValidIndex(indexToDelete)) {
            Note noteToDelete = notes.get(indexToDelete);
            for (int i = 0; i < noteToDelete.getItems().size(); i++) {
                noteToDelete.deleteItem(i);
            }
            notes.remove(indexToDelete);
            return noteToDelete;
        }
        else {
            return null;
        }
    }

    /**
     * Tries to archive a {@link Note} at the given index.
     * <ul>
     *     <li>Index must be valid.</li>
     *     <li>Note must not be already archived.</li>
     *     <li>All items in the note must be completed.</li>
     * </ul>
     * If all above conditions are met, note is archived and {@code true} is returned, otherwise {@code false}.
     *
     * @param indexToArchive index of the note to archive
     * @return {@code true} if the note was successfully archived, {@code false} otherwise
     */
    public boolean archiveNote(int indexToArchive) {
        /*
        This method checks that the index passed as a parameter is valid. ==>> If it is:
        ==>> valid, the note is retrieved and assuming the note is NOT currently archived
        AND all the items on the note are complete, the note is then archived and true
        is returned. If the note exists, but is already archived or items on the note
        are still TODO, return false.
        ==>> invalid, return false.
         */
        if (isValidIndex(indexToArchive)) {
            Note noteToArchive = notes.get(indexToArchive);
            if (!(noteToArchive.isNoteArchived()) && (noteToArchive.checkNoteCompletionStatus())) {
                noteToArchive.setNoteArchived(true);
                return true;
            }
        }
        return false;
    }

    /**
     * Archives all active notes that have all items completed.
     * <ul>
     *   <li>If no active notes exist, returns "No active notes stored".</li>
     *   <li>If active notes exist and some are archived by this operation, returns a string of newly archived notes.</li>
     *   <li>If active notes exist but none are eligible for archiving, returns "No active notes eligible for archive".</li>
     * </ul>
     *
     * @return a message with the archiving result
     */
    public String archiveNotesWithAllItemsComplete() {
        /*
        This method first checks that active notes are in the ArrayList. ==>>If:
        ==>> no active notes exist, return “No active notes stored”.
        ==>> active notes exist, for every active note, if ALL items in the note are complete,
        archive the note. Also build a String of all newly archived notes and return it.

        Hint: remember we wrote a method in Item - checkNoteCompletionStatus()
         */
        if ((notes == null) || (notes.isEmpty()) || (numberOfActiveNotes() == 0)) {
            return "No active notes stored";
        }

        String archivedNotesString = "Archived Notes:\n";
        boolean archivedANoteFlag = false;

        for (Note note : notes) {
            if (!(note.isNoteArchived()) && (note.checkNoteCompletionStatus())) {
                archivedANoteFlag = true;
                note.setNoteArchived(true);
                archivedNotesString += note.toString();
            }
        }

        if (archivedANoteFlag) {
            return archivedNotesString;
        }
        else { // Runs only if there are active notes, but no active note is archived
            return "No active notes eligible for archive";
        }

    }

// -------------- END OF CRUD METHODS --------------

// -------------- START OF COUNTING METHODS --------------

    /**
     * Returns number of notes currently stored.
     *
     * @return total number of notes
     */
    public int numberOfNotes() {
        //returns the number of notes stored in the notes ArrayList.
        return notes.size();
    }

    /**
     * Returns number of archived notes
     *
     * @return number of archived notes
     */
    public int numberOfArchivedNotes() {
        // returns the number of ARCHIVED notes stored in the notes ArrayList
        // (i.e. where isNoteArchived is set to true).
        int archiveCtr = 0;
        if ((notes == null) || (notes.isEmpty())) {
            return archiveCtr;
        }
        else {
            for (Note note : notes) {
                if (note.isNoteArchived()) {
                    archiveCtr++;
                }
            }
            return archiveCtr;
        }
    }

    /**
     * Returns number of active notes
     *
     * @return number of active notes
     */
    public int numberOfActiveNotes() {
        // returns the number of ACTIVE notes stored in the notes ArrayList
        // (i.e. where isNoteArchived is set to false).
        int activeCtr = 0;
        if ((notes == null) || (notes.isEmpty())) {
            return activeCtr;
        }
        else {
            for (Note note : notes) {
                if (!(note.isNoteArchived())) {
                    activeCtr++;
                }
            }
            return activeCtr;
        }
    }

    /**
     * Returns number of notes belonging to the passed category.
     * Only counts the notes if the passed category is valid.
     *
     * @param category category to filter notes by
     * @return number of notes in the specified category
     */
    public int numberOfNotesByCategory(String category) {
        // returns the number of notes that are stored for the category passed as a parameter.
        int categoryCtr = 0;
        if ((notes == null) || (notes.isEmpty())) {
            return categoryCtr;
        }
        else {
            if (isValidCategory(category)) {
                for (Note note : notes) {
                    if (note.getNoteCategory().equals(category)) {
                        categoryCtr++;
                    }
                }
            }
            return categoryCtr;
        }
    }

    /**
     * Returns number of notes with specified priority.
     * Priority must be in range 1 to 5.
     *
     * @param priority priority to filter notes by
     * @return number of notes with specified priority
     */
    public int numberOfNotesByPriority(int priority) {
        // returns the number of notes that are stored for the priority passed
        // as a parameter.
        int priorityCtr = 0;
        if ((notes == null) || (notes.isEmpty())) {
            return priorityCtr;
        }
        else {
            if (validRange(priority, 1, 5)) {
                for (Note note : notes) {
                    if (note.getNotePriority() == priority) {
                        priorityCtr++;
                    }
                }
            }
            return priorityCtr;
        }
    }

    /**
     * Returns total number of items
     *
     * @return total number of items
     */
    public int numberOfItems() {
        // adds up the number of items stored on ALL the notes in the ArrayList
        // and returns it.
        int totalItemsCtr = 0;
        if ((notes == null) || (notes.isEmpty())) {
            return totalItemsCtr;
        }
        else {
            for (Note note : notes) {
                totalItemsCtr += note.getItems().size();
            }
            return totalItemsCtr;
        }
    }

    /**
     * Returns total number of completed items
     *
     * @return total number of completed items
     */
    public int numberOfCompleteItems() {
        // adds up the number of items stored on ALL the notes in the ArrayList where
        // isItemCompleted is set to true. The number is then returned.
        int totalItemsCompleteCtr = 0;
        if ((notes == null) || (notes.isEmpty())) {
            return totalItemsCompleteCtr;
        }
        else {
            for (Note note : notes) {
                if ((note.getItems() != null) && !(note.getItems().isEmpty())) {
                    for (Item item : note.getItems()) {
                        if (item.isItemCompleted()) {
                            totalItemsCompleteCtr++;
                        }
                    }
                }
            }
            return totalItemsCompleteCtr;
        }
    }

    /**
     * Returns total number of items that are not completed, i.e. to-do
     *
     * @return total number of items that are not completed, i.e. to-do
     */
    public int numberOfTodoItems() {
        // adds up the number of items stored on ALL the notes in the ArrayList where
        // isItemCompleted is set to false. The number is then returned.
        int totalItemsToDoCtr = 0;
        if ((notes == null) || (notes.isEmpty())) {
            return totalItemsToDoCtr;
        }
        else {
            for (Note note : notes) {
                if ((note.getItems() != null) && !(note.getItems().isEmpty())) {
                    for (Item item : note.getItems()) {
                        if (!(item.isItemCompleted())) {
                            totalItemsToDoCtr++;
                        }
                    }
                }
            }
            return totalItemsToDoCtr;
        }
    }

// -------------- END OF COUNTING METHODS --------------

// -------------- START OF LISTING METHODS --------------

    /**
     * Lists all notes in a friendly format, including index.
     * If no notes exist, returns "No notes stored".
     *
     * @return formatted string of all notes or "No notes stored"
     */
    public String listAllNotes() {
        /*
        ==>> If the notes list is:
            ==>> empty, the String “No notes stored” is returned.
            ==>> not empty, a String is returned that contains a list of all notes
                 (including their index number)
        */
        if ((notes == null) || (notes.isEmpty())) {
            return "No notes stored";
        }

        String allNotes = "";

        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            allNotes += i + ": " + note.toString();
        }

        return allNotes;
    }

    /**
     * Lists all active notes
     * If no active notes exist, returns "No active notes stored".
     *
     * @return a formatted string of active notes or "No active notes stored"
     */
    public String listActiveNotes() {
        /*
        If the notes ArrayList is checked to see if Active notes are stored.
        ==>>If active notes:
            ==>> exist, a String is returned that contains a list of all active notes
                (including their index number).
            ==>>don’t exist, the String “No active notes stored” is returned.
         */
        if ((notes == null) || (notes.isEmpty()) || (numberOfActiveNotes() == 0)) {
            return "No active notes stored";
        }

        String activeNotes = "";

        for (Note note : notes) {
            if (note.isNoteArchived() == false) {
                activeNotes += notes.indexOf(note) + ": " + note.toString();
            }
        }

        return activeNotes;
    }

    /**
     * Lists all archived notes
     * If no archived notes exist, returns "No archived notes stored".
     *
     * @return a formatted string of archived notes or "No archived notes stored"
     */
    public String listArchivedNotes() {
        /*
        The functionality of this method is the same as the listActiveNotes()
        functionality, except you are dealing with Archived notes, not active ones.
         */
        if ((notes == null) || (notes.isEmpty()) || (numberOfArchivedNotes() == 0)) {
            return "No archived notes stored";
        }

        String archivedNotes = "";

        for (Note note : notes) {
            if (note.isNoteArchived()) {
                archivedNotes += notes.indexOf(note) + ": " + note.toString();
            }
        }
        return archivedNotes;
    }

    /**
     * Lists all notes belonging to a category.
     * <ul>
     *   <li>If no notes exist, returns "No notes stored".</li>
     *   <li>If notes exist but none for the given category, returns "No notes with category [category]".</li>
     *   <li>If notes exist for the category, returns a formatted list of those notes, including the total.</li>
     * </ul>
     *
     * @param category the category to filter by
     * @return a formatted string of notes in that category or the appropriate message if none relevant
     */
    public String listNotesBySelectedCategory(String category) {
        /*
        ==>> If the notes list is:
            ==>> empty, the String “No notes stored” is returned.
            ==>> not empty, but has no notes for the selected category, the String “No notes with category ” is returned.
            ==>> not empty, but has notes for that category, then a String is returned that contains a list of all notes
                (including their index number). A nice touch would be to include the number of notes in that category.
        */
        if ((notes == null) || (notes.isEmpty())) {
            return "No notes stored";
        }
        if (!(isValidCategory(category)) || (numberOfNotesByCategory(category) == 0)) {
            return "No notes with category " + category;
        }

        String notesByCategory = "";

        int categoryNoteCount = numberOfNotesByCategory(category);
        notesByCategory += categoryNoteCount + " notes with category " + category+ ":\n";

        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getNoteCategory().equals(category)) {
                notesByCategory += notes.indexOf(note) + ": " + note.toString();
            }
        }
        return notesByCategory;
    }

    /**
     * Lists all notes with a given priority.
     * <ul>
     *   <li>If no notes exist, returns "No notes stored".</li>
     *   <li>If notes exist but none match the given priority, returns "No notes with priority [1-5]".</li>
     *   <li>If notes exist for the priority, returns a formatted list of those notes, including the count.</li>
     * </ul>
     *
     * @param priority the priority to filter by [1 to 5]
     * @return a formatted string of notes matching the priority or relevant message if none found
     */
    public String listNotesBySelectedPriority(int priority) {
        /*
        The functionality of this method is the same as the
        listNotesBySelectedCategory(String category) functionality, except you are
        dealing with Priority, not Category.
         */
        if ((notes == null) || (notes.isEmpty())) {
            return "No notes stored";
        }

        if (!(validRange(priority, 1, 5)) || (numberOfNotesByPriority(priority) == 0)) {
            return "No notes with priority " + priority;
        }

        String notesByPriority = "";

        int priorityNoteCount = numberOfNotesByPriority(priority);
        notesByPriority += priorityNoteCount + " notes with priority " + priority + ":\n";

        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getNotePriority() == priority) {
                notesByPriority += notes.indexOf(note) + ": " + note.toString();
            }
        }
        return notesByPriority;
    }

    /**
     * Lists all items that are not completed ([TODO]).
     * <ul>
     *   <li>If no notes or no to-do items exist, returns "No notes stored".</li>
     *   <li>Otherwise returns a list of to-do items, prefixed by note title.</li>
     * </ul>
     *
     * @return a formatted string of all to-do items or "No notes stored"
     */
    public String listTodoItems() {
        /*
        ==>> If the notes list is:
            ==>> empty, the String “No notes stored” is returned.
            ==>> not empty, for every note, for all TODO items, add the note title and
                 the TODO item to a String. This String is then returned.
        */
        if ((notes == null) || (notes.isEmpty()) || (numberOfTodoItems() == 0)) {
            return "No notes stored";
        }

        String todoItems = "";

        for (Note note : notes) {
            if ((note.getItems() != null) && !(note.getItems().isEmpty())) {
                for (Item item : note.getItems()) {
                    if (!(item.isItemCompleted())) {
                        todoItems += note.getNoteTitle() + " " + item.toString() + "\n";
                    }
                }
            }
        }
        return todoItems;
    }

    /**
     * Lists completion status of items for a given category
     * Shows both completed and TODO items, along with totals
     * <ul>
     *   <li>If no notes exist, returns "No notes stored".</li>
     *   <li>If the category is invalid or no items match, returns "No notes with category [category]".</li>
     *   <li>Otherwise returns a formatted string including totals and lists of completed and TODO items.</li>
     * </ul>
     *
     * @param category category to filter items by
     * @return a formatted string with completion status of items for the given category
     */
    public String listItemStatusByCategory(String category) {
        /*
        ==>> If the notes list is:
            ==>> empty, the String “No notes stored” is returned.
            ==>> not empty, create two strings that will eventually be returned; one for
                containing TODO items and the other for containing Completed items. Then,
                for every note of the chosen category, add each item to the
                appropriate string. Finally, return a formatted result including counts.
        */
        if ((notes == null) || (notes.isEmpty())) {
            return "No notes stored";
        }
        if (!(isValidCategory(category))) {
            return "No notes with category " + category;
        }
        int completedCtr = 0;
        String completedItems = "";
        int todoCtr = 0;
        String todoItems = "";

        String itemStatusByCategory = "";

        for (Note note : notes) {
            if ((note.getItems() != null) && !(note.getItems().isEmpty())) {
                if (note.getNoteCategory().equals(category)) {
                    for (Item item : note.getItems()) {
                        String itemDescription = item.getItemDescription() + " (Note: " + note.getNoteTitle() + " )\n";
                        if (item.isItemCompleted()) {
                            completedItems += itemDescription;
                            completedCtr++;
                        }
                        else {
                            todoItems += itemDescription;
                            todoCtr++;
                        }
                    }
                }
            }
        }

        itemStatusByCategory += "Number completed: " + completedCtr + "\n";
        itemStatusByCategory += completedItems;
        itemStatusByCategory += "Number TODO: " + todoCtr + "\n";
        itemStatusByCategory += todoItems;

        return itemStatusByCategory.trim();
    }

// -------------- END OF LISTING METHODS --------------

// -------------- START OF FINDING/SEARCHING METHODS --------------

    /**
     * Finds and returns a {@link Note} by its index if valid, otherwise returns {@code null}.
     *
     * @param index index of the note to find
     * @return {@link Note} at the given index or {@code null}
     * */
    public Note findNote(int index) {
        /*
        This method first checks that the index, passed as a parameter, is valid.
        ==>> If it is:
            ==>> valid, then the note at that index location in the notes ArrayList is returned.
            ==>> not valid, null is returned.
         */
        if (isValidIndex(index)) {
            return notes.get(index);
        }
        else {
            return null;
        }
    }

    /**
     * Searches for notes by title.
     * <ul>
     *   <li>If no notes stored, returns "No notes stored".</li>
     *   <li>Otherwise returns a list of notes whose title contains the given search string. </li>
     *   <ul>
     *   <li>If none found, returns "No notes found for: X".</li>
     *   </ul>
     * </ul>
     *
     * @param searchTitle title substring to search for
     * @return a formatted string of matching notes or an appropriate message if none found
     */
    public String searchNotesByTitle(String searchTitle) {
        /*
        ==>> If the notes list is:
            ==>> empty, the String “No notes stored” is returned.
            ==>> not empty, the title of each note is checked against the searchTitle.
                 If a match is found, add the note and its index to a String.
                 If no matches, return “No notes found for: ” + searchTitle.
        */
        if (notes == null || notes.isEmpty()) {
            return "No notes stored";
        }

        String searchResultsByTitle = "";

        for (Note note : notes) {
            String noteTitle = note.getNoteTitle();
            if (noteTitle.contains(searchTitle)) {
                searchResultsByTitle += "Note " + notes.indexOf(note) + ": " + note.toString() + "\n";
            }
        }

        if (searchResultsByTitle.isEmpty()) {
            return "No notes found for: " + searchTitle;
        }

        return searchResultsByTitle;
    }

    /**
     * Searches for items by description
     * <ul>
     *   <li>If no notes stored, returns "No notes stored".</li>
     *   <li>Otherwise, returns a list of items whose description contains the given search string. <li>
     *   <li>If none found, returns "No items found for: X".</li>
     * </ul>
     *
     * @param searchItemDescription the description substring to search for
     * @return a formatted string of matching items or an appropriate message if none found
     */
    public String searchItemByDescription(String searchItemDescription) {
        /*
        ==>> If the notes list is:
            ==>> empty, the String “No notes stored” is returned.
            ==>> not empty, for each note and each item in that note,
                 if the item’s description matches, add it to the results String.
                 If no matches found, return "No items found for: " + searchItemDescription.
        */
        if (notes == null || notes.isEmpty()) {
            return "No notes stored";
        }

        String searchResultsByDescription = "";

        for (Note note : notes) {
            if ((note.getItems() != null) && !(note.getItems().isEmpty())) {
                for (Item item : note.getItems()) {
                    if (item.getItemDescription().contains(searchItemDescription)) {
                        searchResultsByDescription += notes.indexOf(note) + ": " +
                                note.getNoteTitle() + "\n" + notes.indexOf(item) +": " + item.toString() + "\n";
                    }
                }
            }
        }
        if (searchResultsByDescription.isEmpty()) {
            return "No items found for: " + searchItemDescription;
        }
        return searchResultsByDescription;
    }

// -------------- END OF FINDING/SEARCHING METHODS --------------

    // -------------- SINGLE HELPER METHOD IMPORTANT --------------

    /**
     * Checks if provided index is valid for the notes collection.
     *
     * @param index index to validate
     * @return {@code true} if index is within the valid range; {@code false} otherwise
     */
    public boolean isValidIndex(int index) {
        /*
        This helper method will be used by several methods below,
        so it is a good idea to write it first!!!!
        isValidIndex(int) - This method checks that the index, passed as a parameter
        is a valid index in the notes ArrayList. If it is a valid index, return true.
         If invalid, return false.
         */
        return (validRange(index, 0, (this.notes.size()) - 1));
    }

    // -------------- TWO PERSISTENCE METHODS  --------------

    /**
     * Loads the notes from an XML file ("notes.xml") into the notes ArrayList using {@link XStream} and {@link DomDriver}.
     * Relevant classes added to avoid security warnings. <b>This method was taken from lecture notes.</b>
     * @throws IOException if there is an issue reading the file
     * @throws ClassNotFoundException if the classes are not found
     * @author Dave Hearne
     */
    public void load() throws IOException, ClassNotFoundException {
        // this method saves the notes ArrayList to an XML file on your hard disk.
        XStream xstream = new XStream(new DomDriver());
        // ------------------ PREVENT SECURITY WARNINGS-----------------------------
        // The Note class is what we are reading in.
        // Modify to include others if needed by modifying the next line,
        // add additional classes inside the braces, comma separated
        Class<?>[] classes = new Class[]{Note.class, Item.class, NoteAPI.class};

        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);
        // -------------------------------------------------------------------------

        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("notes.xml"));
        notes = (ArrayList<Note>) is.readObject();
        is.close();
    }

    /**
     * Saves the current notes ArrayList to an XML file ("notes.xml") using @link XStream} and {@link DomDriver}.
     * Relevant classes added to avoid security warnings. <b>This method was taken from lecture notes.</b>
     * @throws Exception if an error occurs during the save
     * @author Dave Hearne
     */
    public void save() throws Exception {
        // this method loads the XML file previously stored on your hard disk into the
        // notes ArrayList.
        XStream xstream = new XStream(new DomDriver());

        // ------------------ PREVENT SECURITY WARNINGS-----------------------------
        // The Note class is what we are reading in.
        // Modify to include others if needed by modifying the next line,
        // add additional classes inside the braces, comma separated
        Class<?>[] classes = new Class[]{Note.class, Item.class, NoteAPI.class};

        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);
        // -------------------------------------------------------------------------

        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("notes.xml"));
        out.writeObject(notes);
        out.close();
    }

    /**
     * The ArrayList of {@link Note} objects
     * Initially empty when the object is created, populated through CRUD operations.
     */
    private ArrayList<Note> notes = new ArrayList<Note>();
}