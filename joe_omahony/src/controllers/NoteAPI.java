package controllers;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.ClassNotFoundException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import models.Item;
import models.Note;

import java.util.ArrayList;

import static utils.CategoryUtility.isValidCategory;
import static utils.Utilities.validRange;

public class NoteAPI {
    // The responsibility for the NoteAPI class is to manage the ArrayList of notes.

    /* You will notice that we don’t have constructors, getters, setters and
    toStrings in the above class structure. You can decide to generate these
    boilerplate methods for your class, should you wish. */

// -------------- START OF CRUD METHODS --------------

    public boolean add(Note note) {
        //This method adds a note object to the ArrayList notes and returns
        // the boolean result of the add.
        return notes.add(note);
    }

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

    public Note deleteNote(int indexToDelete) {
        /* This method deletes an note at the index parameter (if that index exists)
           and returns the deleted note object. If the index does not exist in the
           notes list, then null should be returned. */
        if (isValidIndex(indexToDelete)) {
            return notes.remove(indexToDelete);
        }
        return null;
    }

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

    public int numberOfNotes() {
//returns the number of notes stored in the notes ArrayList.
        return notes.size();
    }

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

    public int numberOfNotesByCategory(String category) {
// returns the number of notes that are stored for the category passed as a
// parameter.
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
                    // getItems() doesn't check in Note class
                    for (Item item : note.getItems()) {
                        if (item.isItemCompleted()) {
                            totalItemsCompleteCtr++;
                        }
                    }
                }
                // else {} empty here
            }
            return totalItemsCompleteCtr;
        }
    }

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
                    // getItems() doesn't check in Note class
                    for (Item item : note.getItems()) {
                        if (!(item.isItemCompleted())) {
                            totalItemsToDoCtr++;
                        }
                    }
                }
                // else {} empty here
            }
            return totalItemsToDoCtr;
        }
    }

// -------------- END OF COUNTING METHODS --------------

// -------------- START OF LISTING METHODS --------------

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

    public String listNotesBySelectedCategory(String category) {
/*
==>> If the notes list is:
    ==>> empty, the String “No notes stored” is returned.
    ==>> not empty, but has no notes for the selected category, the String “No notes with category ” is returned.
    ==>> not empty, but has notes for that category, then a String is returned that contains a list of all notes
        (including their index number). A nice touch would be to include the number of notes in that category.
        - Sample output could be:
            4 notes with category HOME:
            1: School Run, Priority=5, Category=Home, Archived=N
              0: Secondary School 3:30. [Completed]
              1: Primary School 2:30. [TODO]
            2: Hoover House, Priority=1, Category=Home, Archived=N
              0: Hoover Upstairs. [TODO]
            3: Polish Furniture, Priority=2, Category=Home, Archived=N
              No items added
            5: Book Holiday, Priority=5, Category=Home, Archived=N
              0: Book flights. [Completed]
              1: Book hotel. [Completed]
 */
        if ((notes == null) || (notes.isEmpty())) {
            return "No notes stored";
        }// had to change below else if to if because of this
        if (!(isValidCategory(category)) || (numberOfNotesByCategory(category) == 0)) {
            return "No notes with category " + category;
        }

        String notesByCategory = "";

        int categoryNoteCount = numberOfNotesByCategory(category);
        notesByCategory += categoryNoteCount + " notes with category " + category.toUpperCase() + ":\n";

        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getNoteCategory().equals(category)) {
                notesByCategory += notes.indexOf(note) + ": " + note.toString();
            }
        }
        return notesByCategory;
    }

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

    public String listTodoItems() {
/*
==>> If the notes list is:
    ==>> empty, the String “No notes stored” is returned.
    ==>> not empty, for every note, for all TODO items, add the note title and
         the TODO item to a String. This String is then returned.
              Sample output could be:
         School Run: Primary School 2:30. [TODO]
         Hoover House: Hoover Upstairs. [TODO]
 */
        if ((notes == null) || (notes.isEmpty()) || (numberOfTodoItems() == 0)) {
            return "No notes stored";
        }

        String todoItems = "";

        for (Note note : notes) {
                    if ((note.getItems() != null) && !(note.getItems().isEmpty())) {
                        // getItems() doesn't check in Note class
                        for (Item item : note.getItems()) {
                            if (!(item.isItemCompleted())) {
                                todoItems += note.getNoteTitle() + " " + item.toString() + "\n";
                            }
                        }
                    }
                    // else {} empty here
                }
        return todoItems;
    }

    public String listItemStatusByCategory(String category) {
/*
==>> If the notes list is:
    ==>> empty, the String “No notes stored” is returned.
    ==>> not empty, create two strings that will eventually be returned; one for
        containing TODO items and the other for containing Compelted items. Then,
        for every note of the chosen category, add each item to the
        appropriate string (also keep a tally of how many have been added
        to each String). When you have finished looking at all notes, build
        a new return string so it looks something like this:
        Enter the category [Home, Work, Hobby, Holiday, Collegel: home
        Number Completed: 3
        Secondary School 3:30 (Note: School Run)
        Book flights (Note: Book Holiday)
        Book hotel (Note: Book Holiday)
        Number TODO: 2
        Primary School 2:30 (Note: School Run)
        Hoover Upstairs (Note: Hoover House)
 */
        if (notes == null || notes.isEmpty()) {
            return "No notes stored";
        }

        if (!(isValidCategory(category)) || (numberOfNotesByCategory(category) == 0)) {
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
                        String itemDescription = "";
                        itemDescription += item.getItemDescription() + " (Note: " + note.getNoteTitle() + ")\n";
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

        itemStatusByCategory += "Number Completed: " + completedCtr + "\n";
        itemStatusByCategory += completedItems;
        itemStatusByCategory += "Number TODO: " + todoCtr + "\n";
        itemStatusByCategory += todoItems;

        return itemStatusByCategory;
    }

// -------------- END OF LISTING METHODS --------------

// -------------- START OF FINDING/SEARCHING METHODS --------------

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

    public String searchNotesByTitle(String searchTitle) {
/*
==>> If the notes list is:
    ==>> empty, the String “No notes stored” is returned.
    ==>> not empty, the title of each note in the notes ArrayList is checked against the
         searchString passed as a parameter. If they match, the note is added, along with
         the index of the note, to a String.
         ==>> When all notes have been checked, if the String:
            ==>> is still empty, the text “No notes found for: ” is returned.
            ==>> is not empty, the String containing all notes that matched is returned.
 */
        if (notes == null || notes.isEmpty()) {
            return "No notes stored";
        }

        String searchResultsByTitle = "";

        for (Note note : notes) {
            String noteTitle = note.getNoteTitle();
            // Moved lowercase change to below
            if (noteTitle.contains(searchTitle)) {
                searchResultsByTitle += "Note " + notes.indexOf(note) + " : " + note.toString() + "\n";
            }
        }

        if (searchResultsByTitle.isEmpty()) {
            return "No notes found for: " + searchTitle;
        }

        return searchResultsByTitle;
    }

    public String searchItemByDescription(String searchItemDescription) {
/*
==>> If the notes list is:
    ==>> empty, the String “No notes stored” is returned.
    ==>> not empty, for each note, the description of each item is checked
         against the searchString passed as a parameter. If they match, the index of
         the note, the noteTitle and the item is added to a String. When all notes
         have been checked,
         ==>> if the String:
            ==>> is still empty, the text “No items found for: ” is returned.
            ==>> is not empty, the String containing all items that matched is returned.
 */
        if (notes == null || notes.isEmpty()) {
            return "No notes stored";
        }

        String searchResultsByDescription = "";

        for (Note note : notes) {
            if ((note.getItems() != null) && !(note.getItems().isEmpty())) {
                for (Item item : note.getItems()) {
                    if (item.getItemDescription().contains(searchItemDescription)) {
                        searchResultsByDescription += notes.indexOf(note) + " : " +
                                note.getNoteTitle() + "\n" + item.toString() + "\n";
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
     * The load method uses the XStream component to read all the product objects from the products.xml
     * file stored on the hard disk.  The read products are loaded into the products ArrayList
     *
     * @throws Exception  An exception is thrown if an error occurred during the load e.g. a missing file.
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
     * The save method uses the XStream component to write all the product objects in the products ArrayList
     * to the products.xml file stored on the hard disk.
     *
     * @throws Exception  An exception is thrown if an error occurred during the save e.g. drive is full.
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

    // notes: This is the ArrayList of Note in the app. It is initialised at
    // field declaration time.
    private ArrayList<Note> notes = new ArrayList<Note>();
}
