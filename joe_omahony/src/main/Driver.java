package main;

import controllers.NoteAPI;
import models.Note;
import models.Item;

import static utils.CategoryUtility.categoryFormatter;
import static utils.ScannerInput.*;
import static utils.Utilities.YNtoBoolean;
import static utils.Utilities.tryAgain;

import utils.CategoryUtility;
import utils.Utilities;

/**
 * The responsibility of the {@code Driver} class is to run the application.
 *
 * <h4>Purpose:</h4>
 * <ul>
 *     <li>Handle user input {@link utils.ScannerInput}</li>
 *     <li>Display information to the console</li>
 *     <li>Call methods in the {@link NoteAPI} class to perform CRUD operations</li>
 * </ul>
 * This is the only class that handles System.out.print statements and direct user input calls.
 * <ol>
 *     <li>The {@code main} method creates a new {@code Driver} instance, which calls {@code runMenu()}.</li>
 *     <li>{@code runMenu()} continuously displays a main menu and handles user selections until the user chooses to
 *     exit. A pause is set so the user has time to think, with a keypress needed to re-display the menu text block.</li>
 *     <li>User choices correspond to methods that gather necessary input and call the appropriate
 *     {@link NoteAPI} methods through the switch statement.</li>
 * </ol>
 *
 * @author Joe O'Mahony, Dave Hearne (Adapted from Shop V5.0)
 * @version 2.0
 */
public class Driver {
    /*
    The responsibility of the Driver class is to run the app and perform I/O with the user.
    It is the only class that should have:
    - System.out.print statements
    - Scanner input method calls.
    */

    /*
     one private field in the Driver class:
    noteAPI: this holds and manages the collection (ArrayList) of notes and is
     instantiated at variable declaration time.
    */
    /**
     * Manages the collection of notes
     * The main access point for all note-related operations within the Driver class.
     */
    private NoteAPI noteAPI = new NoteAPI();

    /*
    The main method has one line of code:
    new Driver();
    */
    /**
     * Entry point of the application. Creates a new instance of the {@code Driver} class, triggering {@code runMenu()},
     * which in turn triggers the application to run.
     *
     * @param args Arguments passed to main()
     */
    public static void main(String[] args) {
        new Driver();
    }

    /*
    We will write a default constructor for this class.
    There is one line of code in this method:
    public Driver() {
        runMenu();
    }
    */
    /**
     * Constructs a new {@code Driver} instance and starts the application by calling {@code runMenu()}.
     */
    public Driver() {
        runMenu();
    }

    /**
     * Displays the main menu of options to the user and returns their selection.
     * <br /><b>This menu design was directly copy-and-pasted from the online assignment spec.</b>
     *
     * @return an integer representing the user's choice for the switch statement
     * @author Dave Hearne, Joe O'Mahony
     */
    private int mainMenu() {
        /*
        Menu Displayed
        Using the same approach adopted when developing the Shop projects, the
        following menu is continually displayed to the user:
        [Menu options as described in the specification]

        Note that the mainMenu() method displays the above menu and returns the
        user choice.
        The runMenu method contains the switch statement that processes the user choice.
        */
        System.out.println(
                "-------------------------------------------------------\n" +
                        "|                    NOTE KEEPER APP                  |\n" +
                        "|-----------------------------------------------------|\n" +
                        "| NOTE MENU                                           |\n" +
                        "|  1) Add a note                                      |\n" +
                        "|  2) List all notes (all, active, archived)          |\n" +
                        "|  3) Update a note                                   |\n" +
                        "|  4) Delete a note                                   |\n" +
                        "|  5) Archive a note                                  |\n" +
                        "|-----------------------------------------------------|\n" +
                        "| ITEM MENU                                           |\n" +
                        "|  6) Add an item to a note                           |\n" +
                        "|  7) Update item description on a note               |\n" +
                        "|  8) Delete an item from a note                      |\n" +
                        "|  9) Mark item as complete/todo                      |\n" +
                        "|-----------------------------------------------------|\n" +
                        "| REPORT MENU FOR NOTES                               |\n" +
                        "| 10) All notes and their items (active & archived)   |\n" +
                        "| 11) Archive notes whose items are all complete      |\n" +
                        "| 12) All notes within a selected Category            |\n" +
                        "| 13) All notes within a selected Priority            |\n" +
                        "| 14) Search for all notes (by note title)            |\n" +
                        "------------------------------------------------------|\n" +
                        "| REPORT MENU FOR ITEMS                               |\n" +
                        "| 15) All items that are todo (with note title)       |\n" +
                        "| 16) Overall number of items todo/complete           |\n" +
                        "| 17) Todo/complete items by specific Category        |\n" +
                        "| 18) Search for all items (by item description )     |\n" +
                        "------------------------------------------------------|\n" +
                        "| SETTINGS MENU                                       |\n" +
                        "| 20) Save                                            |\n" +
                        "| 21) Load                                            |\n" +
                        "|  0) Exit                                            |\n" +
                        "-------------------------------------------------------");

        return readNextInt("Enter an option => ");
    }

    /**
     * Runs the menu loop.
     * <h4>Menu Loop</h4>
     * <ul>
     * <li>Continuously displays the main menu,</li>
     * <li>Processes user input when received,</li>
     * <li>Does something(s) via sending the user input integer to the switch, which calls to the relevant method,</li>
     * <li>Stops when the user chooses to exit (option 0).</li>
     * </ul>
     */
    private void runMenu() {
        int option = mainMenu();
        while (option != 0) {
            switch (option){
                case 1 -> addNote();
                case 2 -> viewNotes();
                case 3 -> updateNote();
                case 4 -> deleteNote();
                case 5 -> archiveNote();

                case 6 -> addItemToNote();
                case 7 -> updateItemDescInNote();
                case 8 -> deleteItemFromNote();
                case 9 -> markCompletionOfItem();

                case 10 -> printActiveAndArchivedReport();
                case 11 -> archiveNotesWithAllItemsComplete();
                case 12 -> printNotesBySelectedCategory();
                case 13 -> printNotesByPriority();
                case 14 -> searchNotesByTitle();

                case 15 -> printAllTodoItems();
                case 16 -> printOverallItemsTodoComplete();
                case 17 -> printItemCompletionStatusByCategory();
                case 18 -> searchItemsByDescription();

                case 20 -> save();
                case 21 -> load();

                default -> System.out.println("Invalid option selected! [" + option + "]");
            }
            //pause the program so that the user can read what we just printed to the terminal window
            readNextLine("\nPress any key to continue...");  //this second read is required - bug in Scanner
            // class; a String read is ignored straight after reading an int.

            //display the main menu again
            option = mainMenu();
        }

        //the user chose option 0, so exit the program
        exitApplication();
    }

    /*
    Option 1 - Add a note
    Create an addNote() method in Driver. This method asks the user to enter the following:
    - note title,
    - note priority
    - note category
    The note is added to the ArrayList of notes (i.e., in noteAPI). If the add was successful,
        output a message to the console alerting the user of this. Likewise, if the add was not
        successful, alert the user to this via a message printed to the console.
    */

    /**
     * Prompts the user to add a new {@link Note}.
     * <ul>
     *   <li>Prompts user for the note's title, priority, and category.</li>
     *   <li>Validates the category via {@link utils.CategoryUtility}.</li>
     *   <li>If valid, attempts to add the note via {@link NoteAPI}.</li>
     *   <li>Tells the user the result</li>
     *   <ul>
     *   <li>If the add was unsuccessful, gives details and offers the option to try again by calling the method again.</li>
     *   </ul>
     * </ul>
     * Option 1 in switch.
     */
    private void addNote() {
        String title = readNextLine("Enter note title => ");
        int priority = readNextInt("Enter note priority [1-5] => ");
        String category = categoryFormatter(readNextLine("Enter note category [Home/Work/Hobby/Holiday/College] => "));
        if (!(CategoryUtility.isValidCategory(category))) {
            System.out.println("Invalid category! [" + category + "]");
            if (tryAgain()) {
                addNote();
            }
        }
        else {
            Note newNote = new Note(title, priority, category);
            if (noteAPI.add(newNote)) {
                System.out.println("Note added successfully.");
            } else {
                System.out.println("Unable to add note with attributes:\n" +
                        "[Title: " + title + ", Priority: " + priority + ", Category: " + category + "].");
                if (tryAgain()) {
                    addNote();
                }
            }
        }
    }

    /*
    Option 2 - List all notes
    Create a viewNotes() method in Driver. This method asks the user if they
        would like to see:
    - All notes (printAllNotes())
    - Active notes (printActiveNotes())
    - Archived notes (printArchivedNotes())
    If there are no notes stored, inform the user via a console message.
    */

    /**
     * Prompts the user to view notes in one of three statuses.
     * <h4>Three note statuses</h4>
     * <ol>
     *     <li>ALL notes [ACTIVE + ARCHIVED]</li>
     *     <li>ACTIVE notes only</li>
     *     <li>ARCHIVED notes only</li>
     * </ol>
     * If no notes exist, informs the user via a console message and exit.
     * Otherwise prompts for a choice [1-3] and calls the relevant helper method.
     * <br /><h4>Helper methods</h4>
     * <ul>
     *     <li>{@code printAllNotes()}</li>
     *     <li>{@code printActiveNotes()}</li>
     *     <li>{@code printArchivedNotes()}</li>
     * </ul>
     * Option 2 in switch.
     * <br /><br /><b>This menu design was directly copy-and-pasted from the online assignment spec.</b>
     * @author Dave Hearne, Joe O'Mahony
     */
    private void viewNotes() {
        if ((noteAPI == null) || (noteAPI.numberOfNotes() == 0)) { // added null
            System.out.println("No notes saved!");
        }
        else {
            int option = readNextInt("""
                    -------------------------------
                    |    1) View ALL Notes        |
                    |    2) View ACTIVE Notes     | 
                    |    3) View ARCHIVED Notes   |
                    -------------------------------
                    Enter an option => """);

            switch (option) {
                case 1 -> printAllNotes();
                case 2 -> printActiveNotes();
                case 3 -> printArchivedNotes();
                default -> System.out.println("Invalid option selected! [" + option + "]");
            }
        }
    }

    /*
    Option 3 - Update a note
    updateNote() method: lists notes, validates user index choice, updates selected note if valid.
    */

    /**
     * Prompts a user to update an existing note by:
     * <ul>
     *   <li>Checking that there are notes saved,</li>
     *   <li>Listing all notes,</li>
     *   <li>Prompting for a note index</li>
     *   <li>Validating user index choice,</li>
     *   <li>Asking for new title, priority, and category,</li>
     *   <li>Formatting the inputted category, then validating it,</li>
     *   <li>Attempting the update by {@link NoteAPI updateNote()},</li>
     *   <li>Informing the user of success/failure and offering to retry if needed</li>
     * </ul>
     * Option 3 in switch.
     */
    private void updateNote() {
        if (noteAPI.numberOfNotes() == 0) {
            System.out.println("No notes saved!");
        }
        else {
            System.out.println(noteAPI.listAllNotes());
            int index = readNextInt("Enter index of note to update => ");

            if ((noteAPI.findNote(index) == null) || !(noteAPI.isValidIndex(index))) {
                System.out.println("Invalid note index selected! [" + index + "]");
                if (tryAgain()) {
                    updateNote();
                }
            }
            else {
                String updatedTitle = readNextLine("Enter new note title => ");
                int updatedPriority = readNextInt("Enter new note priority [1-5] => ");
                String updatedCategory = categoryFormatter(readNextLine("Enter new note category [Home/Work/Hobby/Holiday/College] => "));

                if (!(CategoryUtility.isValidCategory(updatedCategory))) {
                    System.out.println("Invalid category selected! [" + updatedCategory + "]");
                    if (tryAgain()) {
                        updateNote();
                    }
                }
                else {
                    if (noteAPI.updateNote(index, updatedTitle, updatedPriority, updatedCategory)) {
                        System.out.println("Note updated successfully.");
                    } else {
                        System.out.println("Unable to update note at index " + index + " with options:\n" +
                                "[Title: " + updatedTitle + " / Priority: " + updatedPriority + " / Category: " + updatedCategory + "]");
                        if (tryAgain()) {
                            updateNote();
                        }
                    }
                }
            }
        }
    }

    /*
    Option 4 - Delete a note
    deleteNote() method: lists notes, validates user index, attempts to delete note, informs user of outcome.
    */

    /**
     * Prompts the user to delete a note by:
     * <ul>
     *     <li>Checking that there are notes saved,</li>
     *   <li>Listing all notes,</li>
     *   <li>Prompting for the note index,</li>
     *   <li>Validating the note index,</li>
     *   <li>Attempting deletion via {@link NoteAPI deleteNote()},</li>
     *   <li>Informing the user of success/failure and offering to retry,</li>
     *   <li>If successful, displays the deleted note</li>
     * </ul>
     * Option 4 in switch.
     */
    private void deleteNote() {
        if (noteAPI.numberOfNotes() == 0) {
            System.out.println("No notes saved!");
        }
        else {
            System.out.println(noteAPI.listAllNotes());
            int index = readNextInt("Enter index of note to delete => ");

            if ((noteAPI.findNote(index) == null) || !(noteAPI.isValidIndex(index))) {
                System.out.println("Unable to delete note! Invalid note index [" + index + "]");
                if (tryAgain()) {
                    deleteNote();
                }
            }
            else {
                Note deletedNote = noteAPI.deleteNote(index);
                if (deletedNote != null) {
                    System.out.println("Note deleted successfully");
                    System.out.println(deletedNote);
                }
                else {
                    System.out.println("Unable to delete note at index [" + index + "].");
                    if (tryAgain()) {
                        deleteNote();
                    }
                }
            }
        }
    }

    /*
    Option 5 - Archive a note
    archiveNote() method: lists active notes, prompts user for index, attempts to archive.
    */

    /**
     * Prompts the user to archive a note by:
     * <ul>
     *   <li>Checking that there are active notes saved,</li>
     *   <li>Listing all active notes</li>
     *   <li>Asking user for the index of the active note to archive</li>
     *   <li>Validating the provided index</li>
     *   <li>Attempts archiving via {@link NoteAPI#archiveNote(int)}</li>
     *   <li>Informs the user whether the note was successfully archived</li>
     *   <li>Explains why archiving failed (invalid index, incomplete items, already archived) where possible</li>
     *   <li>Selectively asks the user if they want to retry (if the note is already archived, no prompt to retry is
     *   deemed necessary.</li>
     * </ul>
     * Option 5 in switch.
     */
    private void archiveNote() {
        if (noteAPI.numberOfActiveNotes() == 0) {
            System.out.println("No active notes!");
        }
        else {
            System.out.println(noteAPI.listActiveNotes());
            int index = readNextInt("Enter active note index to archive => ");

            if (noteAPI.archiveNote(index)) {
                System.out.println("Note archived successfully.");
            }
            else {
                if (!(noteAPI.isValidIndex(index))) {
                    System.out.println("Unable to archive note! Invalid note index [" + index + "].");
                    if (tryAgain()) {
                        archiveNote();
                    }
                }
                else if (!(noteAPI.findNote(index).checkNoteCompletionStatus())) {
                    System.out.println("Unable to archive note! Incomplete note items.");
                }
                else if (noteAPI.findNote(index).isNoteArchived()) {
                    System.out.println("This note is already archived!");
                }
                else {
                    System.out.println("Unable to archive note!");
                    if (tryAgain()) {
                        archiveNote();
                    }
                }
            }
        }
    }

    /*
    ITEM MENU Options
    Option 6 - Add an item to a note
    addItemToNote() method: lists active notes, gets note index, then asks for item description.
    */

    /**
     * Prompts the user to add an {@link Item} to a note (which must be {@code active}:
     * <ul>
     *   <li>Checks that there are active notes saved,</li>
     *   <li>Lists all active notes</li>
     *   <li>Prompts user for a note index</li>
     *   <li>Validates the note index</li>
     *   <li>If valid and the note is active, asks for item description and adds it</li>
     *   <li>Informs user of success/failure and offers to retry if needed</li>
     * </ul>
     * Option 6 in switch.
     */
    private void addItemToNote() {
        if (noteAPI.numberOfActiveNotes() == 0) {
            System.out.println("No active notes!");
        }
        else {
            System.out.println(noteAPI.listActiveNotes());
            int index = readNextInt("Enter active note index to add item to => ");
            Note note = noteAPI.findNote(index);
            if ((note == null) || !(noteAPI.isValidIndex(index))) {
                System.out.println("Unable to access note! Invalid note index [" + index +"].");
                if (tryAgain()) {
                    addItemToNote();
                }
            }
            else if (note.isNoteArchived()) {
                System.out.println("This note is already archived!");
            }
            else {
                String itemDesc = readNextLine("Enter item description => ");
                Item newItem = new Item(itemDesc);
                if (note.addItem(newItem)) {
                    System.out.println("Item added successfully.");
                }
                else {
                    System.out.println("Unable to add item with description [" + itemDesc + "]!");
                    if (tryAgain()) {
                        addItemToNote();
                    }
                }
            }
        }
    }

    /*
    Option 7 - Update item description on a note
    updateItemDescInNote() method: lists active notes, validates chosen note and item, updates item description.
    */

    /**
     * Prompts the user to update an item's description on an active note:
     * <ul>
     *   <li>Checks that there are active notes saved</li>
     *   <li>Lists active notes</li>
     *   <li>Prompts user for a note index</li>
     *   <li>Validates the note index</li>
     *   <li>If valid and has items, prompts for item index and new description</li>
     *   <li>Validates the note index for {@code null}</li>
     *   <li>Uses {@link Note updateItem()} with the old item status to keep archived status unchanged</li>
     *   <li>Informs user of success/failure and allows retry when appropriate </li>
     * </ul>
     * Option 7 in switch.
     */
    private void updateItemDescInNote() {
        if (noteAPI.numberOfActiveNotes() == 0) {
            System.out.println("No active notes!");
        }
        else {
            System.out.println(noteAPI.listActiveNotes());
            int noteIndex = readNextInt("Enter active note index to update item description from => ");
            Note note = noteAPI.findNote(noteIndex);
            if ((note == null) || !(noteAPI.isValidIndex(noteIndex))) {
                System.out.println("Unable to access note! Invalid note index [" + noteIndex + "].");
                if (tryAgain()) {
                    updateItemDescInNote();
                }
            }
            else if (note.isNoteArchived()) {
                System.out.println("Unable to update item description! This note is archived [" +
                        Utilities.booleanToYN(note.isNoteArchived()) + "].");
            }
            else if (note.numberOfItems() == 0) {
                System.out.println("This note has no items!");
            }
            else {
                System.out.println(note.listItems());
                int itemIndex = readNextInt("Enter item index to update => ");
                Item itemToUpdate = note.findItem(itemIndex);
                if (itemToUpdate == null) {
                    System.out.println("Invalid item index! [" + itemIndex + "]");
                    if (tryAgain()) {
                        updateItemDescInNote();
                    }
                }
                else {
                    String newDesc = readNextLine("Enter new item description => ");
                    boolean oldStatus = itemToUpdate.isItemCompleted();
                    if (note.updateItem(itemIndex, newDesc, oldStatus)) {
                        System.out.println("Item updated successfully.");
                    }
                    else {
                        System.out.println("Unable to update item with old status: " +
                                Utilities.booleanToYN(oldStatus) + ", index: " + itemIndex + ", " +
                                "and new description [" + newDesc + "].");
                        if (tryAgain()) {
                            updateItemDescInNote();
                        }
                    }
                }
            }
        }
    }

    /*
    Option 8 - Delete an item from a note
    deleteItemFromNote() method: lists active notes, validates index, deletes chosen item.
    */

    /**
     * Prompts the user to delete an item from an active note:
     * <ul>
     *   <li>Checks that there are active notes saved</li>
     *   <li>Lists active notes</li>
     *   <li>Prompts for a note index</li>
     *   <li>Validates note index</li>
     *   <li>If valid and has items, prompts for item index and deletes it</li>
     *   <li>Informs user of success/failure and allows retry</li>
     * </ul>
     * Option 8 in switch.
     */
    private void deleteItemFromNote() {
        if (noteAPI.numberOfActiveNotes() == 0) {
            System.out.println("No active notes!");
        }
        else {
            System.out.println(noteAPI.listActiveNotes());
            int noteIndex = readNextInt("Enter active note index to delete item from => ");
            Note note = noteAPI.findNote(noteIndex);
            if ((note == null) || !(noteAPI.isValidIndex(noteIndex))) {
                System.out.println("Unable to access note! Invalid note index [" + noteIndex + "].");
                if (tryAgain()) {
                    deleteItemFromNote();
                }
            } else if (note.isNoteArchived()) {
                System.out.println("Unable to delete item from note! This note is archived [" + Utilities.booleanToYN(note.isNoteArchived()) + "].");
            } else if (note.numberOfItems() == 0) {
                System.out.println("No note items! [" + note.numberOfItems() + "]");
            } else {
                System.out.println(note.listItems());
                int itemIndex = readNextInt("Enter the index of the item to delete => ");
                Item deletedItem = note.deleteItem(itemIndex);
                if (deletedItem != null) {
                    System.out.println("Item deleted successfully: " + deletedItem);
                } else {
                    System.out.println("Unable to delete item! Invalid item index [" + itemIndex + "].");
                    if (tryAgain()) {
                        deleteItemFromNote();
                    }
                }
            }
        }
    }

    /*
    Option 9 - Mark item as complete/todo
    markCompletionOfItem() method: lists active notes, prompts user for note/item index, toggles completion.
    */

    /**
     * Marks an item as complete or TODO:
     * <ul>
     *   <li>Checks that there are active notes saved</li>
     *   <li>Lists active notes</li>
     *   <li>Prompts for a note index</li>
     *   <li>Validates note index</li>
     *   <li>If valid, prompts for an item index and a "Y"/"N" response to mark it completed or todo by calling
     *   {@link Utilities} method</li>
     *   <li>Updates the item and informs the user of the new status</li>
     *   <li>If it fails, offers retry where appropriate</li>
     * </ul>
     * Option 9 in switch.
     */
    private void markCompletionOfItem() {
        if (noteAPI.numberOfActiveNotes() == 0) {
            System.out.println("No active notes saved!");
        }
        else {
            System.out.println(noteAPI.listActiveNotes());
            int noteIndex = readNextInt("Enter active note index to mark item completion from  => ");

            if (!(noteAPI.isValidIndex(noteIndex))) {
                System.out.println("Unable to access note! Invalid note index [" + noteIndex + "].");
                if (tryAgain()) {
                    markCompletionOfItem();
                }
            }
            else {
                Note note = noteAPI.findNote(noteIndex);
                if (note == null) {
                    System.out.println("Nothing at that index! [" + noteIndex + "]");
                    if (tryAgain()) {
                        markCompletionOfItem();
                    }
                }
                else if (note.isNoteArchived()) {
                    System.out.println("Unable to mark items completed in note! This note is archived.");
                }
                else if (note.numberOfItems() == 0) {
                    System.out.println("No items in this note! [" + note.numberOfItems() + "]");
                }
                else {
                    System.out.println(note.listItems());
                    int itemIndex = readNextInt("Enter index of the item => ");
                    Item item = note.findItem(itemIndex);
                    if (item == null) {
                        System.out.println("Invalid item index! [" + itemIndex + "]");
                        if (tryAgain()) {
                            markCompletionOfItem();
                        }
                    }
                    else {
                        char itemCompletedChar = readNextChar("Mark item as completed? [Y/N] => ");
                        boolean completedBool = YNtoBoolean(itemCompletedChar);
                        item.setItemCompleted(completedBool);
                        if (completedBool) {
                            System.out.println("Item successfully marked as completed.");
                        }
                        else {
                            System.out.println("Item successfully marked as to-do.");
                        }
                    }
                }
            }
        }
    }

    /**
     * Prints all notes and the total number of notes.
     * Helper method for {@code viewNotes()}
     */
    private void printAllNotes() {
        // Helper method
        System.out.println("Number of active and archived notes: " + noteAPI.numberOfNotes());
        System.out.println(noteAPI.listAllNotes());
    }

    /**
     * Prints all archived notes and the number of archived notes.
     * Helper method for {@code viewNotes()}
     */
    private void printArchivedNotes() {
        // Helper method for printActiveAndArchivedReport()
        System.out.println("Number of archived notes: " + noteAPI.numberOfArchivedNotes());
        System.out.println(noteAPI.listArchivedNotes());
    }

    /**
     * Prints all active notes and the number of active notes.
     * Helper method for {@code viewNotes()}
     */
    private void printActiveNotes() {
        // Helper method for printActiveAndArchivedReport()
        System.out.println("Number of active notes: " + noteAPI.numberOfActiveNotes());
        System.out.println(noteAPI.listActiveNotes());
    }

    /*
    REPORT MENU FOR NOTES Options
    Option 10 - All notes and their items (active & archived)
    printActiveAndArchivedReport() method: prints active notes, then archived notes.
    */

    /**
     * Prints a report (String) showing all active notes with their items, then all archived notes with their items.
     * If no notes exist, informs the user.
     * Option 10 in switch.
     */
    private void printActiveAndArchivedReport() {
        if (noteAPI.numberOfNotes() == 0) {
            System.out.println("No notes saved!");
        }
        else {
            System.out.println("Active notes: ");
            System.out.println(noteAPI.listActiveNotes());
            System.out.println("Archived notes: ");
            System.out.println(noteAPI.listArchivedNotes());
        }
    }

    /*
    Option 11 - Archive notes whose items are all complete
    archiveNotesWithAllItemsComplete() method: archives all eligible active notes.
    */

    /**
     * Archives all active notes whose items are completed and prints the result.
     * Option 11 in switch.
     */
    private void archiveNotesWithAllItemsComplete() {
        System.out.println(noteAPI.archiveNotesWithAllItemsComplete());
    }

    /*
    Option 12 - All notes within a selected Category
    printNotesBySelectedCategory() method: if notes exist, prompts for category, then prints notes in that category.
    */

    /**
     * Prompts user for a category and prints all notes belonging to that category.
     * If no notes exist, informs the user. Prompts the user for a category, then validates category and prints.
     * If this fails, will prompt to try again.
     * Option 12 in switch.
     */
    private void printNotesBySelectedCategory() {
        if (noteAPI.numberOfNotes() == 0) {
            System.out.println("No notes saved!");
        }
        else {
            String category = categoryFormatter(readNextLine("Enter a category [Home, Work, Hobby, Holiday, College] => "));
            if ((category == null) || !(CategoryUtility.isValidCategory(category))) {
                System.out.println("Invalid category selected! [" + category + "]");
                if (tryAgain()) {
                    printNotesBySelectedCategory();
                }
            }
            else {
                System.out.println(noteAPI.listNotesBySelectedCategory(category));
            }
        }
    }

    /*
    Option 13 - All notes within a selected Priority
    printNotesByPriority() method: if notes exist, prompts for a priority, then prints notes in that priority.
    */

    /**
     * Prompts user for a priority and prints all notes belonging to that priority.
     * If no notes exist, informs the user. Prompts the user for a priority, then validates priority and prints.
     * If this fails, will prompt to try again.
     * Option 13 in switch.
     */
    private void printNotesByPriority() {
        if (noteAPI.numberOfNotes() == 0) {
            System.out.println("No notes saved!");
        }
        else {
            int priority = readNextInt("Enter a priority [1-5] => ");
            if ((priority >= 1) && (priority <= 5)) {
                System.out.println(noteAPI.listNotesBySelectedPriority(priority));
            }
            else {
                System.out.println("Invalid priority selected! [" + priority + "]");
                if (tryAgain()) {
                    printNotesByPriority();
                }
            }
        }
    }

    /*
    Option 14 - Search for all notes (by note title)
    searchNotesByTitle() method: if notes exist, asks user for a search string, prints matches.
    */

    /**
     * Prompts user for a string to search note titles for a relevant note.
     * If no notes exist, informs user and asks to try again. If found, searches and prints search results.
     * Option 14 in switch.
     */
    private void searchNotesByTitle() {
        if (noteAPI.numberOfNotes() == 0) {
            System.out.println("No notes saved!");
        }
        else {
            String searchTitle = readNextLine("Enter search string for note title => ");
            System.out.println(noteAPI.searchNotesByTitle(searchTitle));
        }
    }

    /*
    REPORT MENU FOR ITEMS Options
    Option 15 - All items that are todo (with note title)
    printAllTodoItems() method: prints all TODO items if notes exist, else informs user.
    */

    /**
     * Prints all to-do items in notes if any exist, otherwise informs user no notes saved.
     * Option 15 in switch.
     */
    private void printAllTodoItems() {
        if (noteAPI.numberOfNotes() == 0) {
            System.out.println("No notes saved!");
        }
        else {
            System.out.println(noteAPI.listTodoItems());
        }
    }

    /*
    Option 16 - Overall number of items todo/complete
    printOverallItemsTodoComplete() method: prints counts of completed and todo items if notes exist.
    */

    /**
     * Prints the overall number of completed and to-do items across all notes.
     * If no notes exist, informs the user.
     * Option 16 in switch.
     */
    private void printOverallItemsTodoComplete() {
        if (noteAPI.numberOfNotes() == 0) {
            System.out.println("No notes saved!");
        }
        else {
            System.out.println("Number of completed items: " + noteAPI.numberOfCompleteItems());
            System.out.println("Number of to-do items: " + noteAPI.numberOfTodoItems());
        }
    }

    /*
    Option 17 - Todo/complete items by specific Category
    printItemCompletionStatusByCategory() method: if notes exist, prompts category and prints item status by category.
    */

    /**
     * Prints the completion status of items filtered by a user-selected category.
     * If no notes exist or category is invalid, informs the user. Allows retry.
     * Option 17 in switch.
     */
    private void printItemCompletionStatusByCategory() {
        if (noteAPI.numberOfNotes() == 0) {
            System.out.println("No notes saved!");
        }
        else {
            String category = categoryFormatter(readNextLine("Enter a category [Home, Work, Hobby, Holiday, College] => "));
            if (!(CategoryUtility.isValidCategory(category))) {
                System.out.println("Invalid category selected! [" + category + "]");
                if (tryAgain()) {
                    printItemCompletionStatusByCategory();
                }
            }
            else {
                System.out.println(noteAPI.listItemStatusByCategory(category));
            }

        }
    }

    /*
    Option 18 - Search for all items (by item description)
    searchItemsByDescription() method: if notes exist, asks user for search text and prints matches.
    */

    /**
     * Searches items by a description string from user prompt.
     * If no notes exist, informs user. Otherwise, prints search results.
     * Option 18 in switch.
     */
    private void searchItemsByDescription() {
        if (noteAPI.numberOfNotes() == 0) {
            System.out.println("No notes saved.");
        }
        else {
            String searchDesc = readNextLine("Enter search text for item description: ");
            System.out.println(noteAPI.searchItemByDescription(searchDesc));
        }
    }

    // NO OPTION 19

    /*
    SETTINGS MENU Options
    Option 20 - Save
    save() method: saves notes to XML.
    */

    /**
     * Saves the current state of notes to an XML file using {@link NoteAPI save()}.
     * Informs the user of success/failure and allows retry if needed.
     * Option 20 in switch.
     */
    private void save() {
        try {
            noteAPI.save();
            System.out.println("Notes saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving notes! [" + e.getMessage() + "]");
            if (tryAgain()) {
                save();
            }
        }
    }

    /*
    Option 21 - Load
    load() method: loads notes from XML.
    */

    /**
     * Loads notes from the XML file using {@link NoteAPI load()}.
     * Informs the user of success/failure and allows retry if needed.
     * Option 21 in switch.
     */
    private void load() {
        try {
            noteAPI.load();
            System.out.println("Notes loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error loading notes! [" + e.getMessage() + "]");
            if (tryAgain()) {
                load();
            }
        }
    }

    /*
    Option 0 - Exit
    This option exits the application.
    */

    /**
     * Exits the application by printing a goodbye message and terminating the program.
     * Option 0 in switch.
     */
    private void exitApplication() {
        System.out.println("Exiting... goodbye");
        System.exit(0);
    }
}