package main;

import controllers.NoteAPI;
import models.Note;
import models.Item;

import static utils.ScannerInput.*;
import static utils.Utilities.YNtoBoolean;

import utils.CategoryUtility;
import utils.Utilities;


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
    private NoteAPI noteAPI = new NoteAPI();

    /*
    The main method has one line of code:
    new Driver();
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
    public Driver() {
        runMenu();
    }

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

        return readNextInt("Please enter an option => ");
    }

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

// ---------- SWITCH OPTIONS -------------
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
    private void addNote() {
        String title = readNextLine("Enter note title => ");
        int priority = readNextInt("Enter note priority [1-5] => ");
        String category = readNextLine("Enter note category [Home/Work/Hobby/Holiday/College] => ");
        Note newNote = new Note(title, priority, category);
        if (noteAPI.add(newNote)) {
            System.out.println("Note added successfully.");
        }
        else {
            System.out.println("Unable to add note with attributes:\n" + "[Title: " + title + ", Priority: "
                    + priority + ", Category: " + category + "].");
        }
    }

    /*
    Option 2 - List all notes
    Create a viewNotes() method in Driver. This method asks the user if they
        would like to see:
    - All notes (call the Driver method printAllNotes() that prints the number
        of notes along with all notes and their associated items)
    - Active notes (call the Driver method printActiveNotes() that prints the
        number of active notes along with all active notes and their associated
        items)
    - Archived notes (call the Driver method printArchivedNotes() that prints
        the number of archived notes along with all archived notes and their
        associated items)
    If there are no notes stored, inform the user via a console message.
    */
    private void viewNotes() {
        if (noteAPI.numberOfNotes() == 0) {
            System.out.println("No notes saved!");
        }
        else {
            int option = readNextInt("""
                    -------------------------------
                    |    1) View ALL Notes        |
                    |    2) View ACTIVE Notes     | 
                    |    3) View ARCHIVED Notes   |
                    -------------------------------
                    Enter option => """);

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
    Create an updateNote() method in Driver. This method lists all the notes. If notes:
    - haven’t been added yet, inform the user via a console message.
    - have been added, prompt the user to enter the index number of the note they wish to update.
        - If it is a valid index, prompt the user to enter new values for note
            title, note priority, and note category.
            Pass the new values along with the selected index number to noteAPI
            for updating.
            Inform the user whether the update was successful or not.
        - If not a valid index, inform the user via a console message.
    */
    private void updateNote() {
        // Implementation for updating a note
        if (noteAPI.numberOfNotes() == 0) {
            System.out.println("No notes saved!");
        }
        else {
            System.out.println(noteAPI.listAllNotes());
            int index = readNextInt("Enter index of note to update => ");

            if ((noteAPI.findNote(index) == null) || !(noteAPI.isValidIndex(index))) {
                System.out.println("Invalid note index selected! [" + index + "]");
            }
            else {
                String updatedTitle = readNextLine("Enter new note title => ");
                int updatedPriority = readNextInt("Enter new note priority [1-5] => ");
                String updatedCategory = readNextLine("Enter new note category [Home/Work/Hobby/Holiday/College] => ");

                if (!(CategoryUtility.isValidCategory(updatedCategory))) {
                    System.out.println("Invalid category selected! [" + updatedCategory + "]");
                }
                else {
                    if (noteAPI.updateNote(index, updatedTitle, updatedPriority, updatedCategory)) {
                        System.out.println("Note updated successfully.");
                    } else {
                        System.out.println("Unable to update note at index " + index + " with options:\n" + "[Title: "
                                + updatedTitle + " / Priority: " + updatedPriority + " / Category: " + updatedCategory + "]");
                    }
                }
            }
        }
    }

    /*
    Option 4 - Delete a note
    Create a deleteNote() method in Driver. This method lists all the notes. If notes:
    - haven’t been added yet, inform the user via a console message.
    - have been added, prompt the user to enter the index number of the note they wish to delete.
      Pass the selected index number to noteAPI for deleting.
      Inform the user whether the delete was successful or not.
      If the delete was successful, also print the deleted note to the console.
    */
    private void deleteNote() {
        // Implementation for deleting a note
        if (noteAPI.numberOfNotes() == 0) {
            System.out.println("No notes saved!");
        }
        else {
            System.out.println(noteAPI.listAllNotes());
            int index = readNextInt("Enter index of note to delete => ");

            if ((noteAPI.findNote(index) == null) || !(noteAPI.isValidIndex(index))) {
                System.out.println("Unable to delete note! Invalid note index [" + index + "]");
            }
            else {
                Note deletedNote = noteAPI.deleteNote(index);
                if (deletedNote != null) {
                    System.out.println("Note deleted successfully");
                    System.out.println(deletedNote);
                }
                else {
                    System.out.println("Unable to delete note at index [" + index + "].");
                }
            }
        }
    }

    /*
    Option 5 - Archive a note
    Create an archiveNote() method in Driver.
    This method lists all the ACTIVE notes. If there are ACTIVE notes,
        ask the user to enter the index of the active note they wish to archive.
    Pass the selected index number to noteAPI for archiving.
    Inform the user whether the archive was successful or not.
    */
    private void archiveNote() {
        // Implementation for archiving a note
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

                }
                else if (!(noteAPI.findNote(index).checkNoteCompletionStatus())) {
                    System.out.println("Unable to archive note! Incomplete note items.");
                }
                else if (noteAPI.findNote(index).isNoteArchived()) {
                    System.out.println("This note is already archived!");
                }
                else {
                    System.out.println("Unable to archive note!");
                }
            }
        }
    }

    /*
    ITEM MENU Options
    Option 6 - Add an item to a note
    Create an addItemToNote() method in Driver.

    This method lists all the ACTIVE notes and asks the user to select the index
     number of an ACTIVE note.

    If the index number is valid, ask the user to enter the item description.
    Pass the item description along with the selected index number to noteAPI for adding.
    Inform the user whether the add was successful or not.
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
                }
            }
        }
        // Implementation for adding an item to a note
    }

    /*
    Option 7 - Update item description on a note
    Create an updateItemDescInNote() method in Driver.

    This method lists all the ACTIVE notes and asks the user to select the index
     number of an ACTIVE note.
    If the selected note:
    - is valid and has items, ask the user to choose the item index number.
      Then ask the user to enter the new item description.
      Pass the item description, along with the selected item index number and
       the existing note status (todo/completed) to the note for updating.
      Inform the user whether the update was successful or not.
    - is invalid or doesn’t have any items, inform the user of this.
    */
    private void updateItemDescInNote() {
        // Implementation for updating an item's description in a note
        if (noteAPI.numberOfActiveNotes() == 0) {
            System.out.println("No active notes!");
        }
        else {
            System.out.println(noteAPI.listActiveNotes());
            int noteIndex = readNextInt("Enter active note index to update item description from => ");
            Note note = noteAPI.findNote(noteIndex);
            if ((note == null) || !(noteAPI.isValidIndex(noteIndex))) {
                System.out.println("Unable to access note! Invalid note index [" + noteIndex + "].");
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
                    }
                    else {
                        String newDesc = readNextLine("Enter new item description => ");
                        boolean oldStatus = itemToUpdate.isItemCompleted();
                        if (note.updateItem(itemIndex, newDesc, oldStatus)) {
                            System.out.println("Item updated successfully.");
                        }
                        else {
                            System.out.println("Unable to update item having completed status: " +
                                    Utilities.booleanToYN(oldStatus) +", and index: " + itemIndex + ", " +
                                    "with new description [" + newDesc + "].");
                    }
                }
            }
        }
    }

    /*
    Option 8 - Delete an item from a note
    Create a deleteItemFromNote() method in Driver.
    This method lists all the ACTIVE notes and asks the user to select the index
     number of an ACTIVE note.

    If the selected note:
    - is valid and has items, ask the user to choose the item index number.
      Pass the selected item index number to the note for deleting.
      Inform the user whether the delete was successful or not.
    - is invalid or doesn’t have any items, inform the user of this.
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
                }
                // Implementation for deleting an item from a note
            }
        }
    }

    /*
    Option 9 - Mark item as complete/todo
    Create a markCompletionOfItem() method in Driver.
    This method lists all the ACTIVE notes and asks the user to select the index
     number of an ACTIVE note.
    If the selected note:
    - is valid and has items, ask the user to choose the item index number.
      Then ask the user to enter the new item status (y/n).
      Call the setItemCompleted() mutator on the item to update the status.
      Inform the user whether the item is now set to completed or todo.
    - is invalid or doesn’t have any items, inform the user of this.
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
            }
            else {
                Note note = noteAPI.findNote(noteIndex);
                if (note == null) {
                    System.out.println("Nothing at that index! [" + noteIndex + "]");
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
                    // Implementation for marking an item's completion status
                }
            }
        }
    }

    private void printAllNotes() {
    // Helper method
        System.out.println("Number of active and archived notes: " + noteAPI.numberOfNotes());
        System.out.println(noteAPI.listAllNotes());
    }

    private void printArchivedNotes() {
        // Helper method for printActiveAndArchivedReport()
        System.out.println("Number of archived notes: " + noteAPI.numberOfArchivedNotes());
        System.out.println(noteAPI.listArchivedNotes());
    }

    private void printActiveNotes() {
        // Helper method for printActiveAndArchivedReport()
        System.out.println("Number of active notes: " + noteAPI.numberOfActiveNotes());
        System.out.println(noteAPI.listActiveNotes());
    }

    /*
    REPORT MENU FOR NOTES Options
    Option 10 - All notes and their items (active & archived)
    Create a printActiveAndArchivedReport() method in Driver. This method
        will first print the active notes followed by the archived notes.
    */
    private void printActiveAndArchivedReport() {
        // Implementation for printing all notes and their items
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
    Create an archiveNotesWithAllItemsComplete() method in Driver. This method
        will archive all active notes whose items are completed
            (hint: NoteAPI archiveNotesWithAllItemsComplete()).
    */
    private void archiveNotesWithAllItemsComplete() {
        // Implementation for archiving notes with all items complete
        System.out.println(noteAPI.archiveNotesWithAllItemsComplete());
    }

    /*
    Option 12 - All notes within a selected Category
    Create a printNotesBySelectedCategory() method in Driver. This method, if:
    - notes exist, will ask the user to enter a valid category. The applicable
        noteAPI method is then called to list all notes for that category.
    - notes don’t exist, inform the user of this.
    */
    private void printNotesBySelectedCategory() {
        // Implementation for printing notes by selected category
        if (noteAPI.numberOfNotes() == 0) {
            System.out.println("No notes saved!");
        }
        else {
            String category = readNextLine("Enter a category [Home, Work, Hobby, Holiday, College] => ");
            if ((category == null) || !(CategoryUtility.isValidCategory(category))) {
                System.out.println("Invalid category selected! [" + category + "]");
            }
            else {
                System.out.println(noteAPI.listNotesBySelectedCategory(category));
            }
        }
    }

    /*
    Option 13 - All notes within a selected Priority
    Create a printNotesByPriority() method in Driver. This method, if:
    - notes exist, will ask the user to enter a valid priority. The applicable
        noteAPI method is then called to list all notes for that priority.
    - notes don’t exist, inform the user of this.
    */
    private void printNotesByPriority() {
        // Implementation for printing notes by priority
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
            }
        }
    }

    /*
    Option 14 - Search for all notes (by note title)
    Create a searchNotesByTitle() method in Driver. This method, if:
    - notes exist, will ask the user to enter a title they wish to search notes
        by.
      The applicable noteAPI method is then called to list all notes whose title,
        or subset of their title, match the entered String.
    - notes don’t exist, inform the user of this.
    */
    private void searchNotesByTitle() {
        // Implementation for searching notes by title
        if (noteAPI.numberOfNotes() == 0) {
            System.out.println("No notes saved!");
        }
        else {
            String searchTitle = readNextLine("Enter search string for note title => ");
            System.out.println(noteAPI.searchNotesByTitle(searchTitle));
        }
    }


// ----------------REPORT MENU FOR ITEMS Options--------------------
    /*
    Option 15 - All items that are todo (with note title)
    Create a printAllTodoItems() method in Driver. This method will print all
        the TODO items to the console.
    */
    private void printAllTodoItems() {
        // Implementation for printing all TODO items
        if (noteAPI.numberOfNotes() == 0) {
            System.out.println("No notes saved!");
        }
        else {
            System.out.println(noteAPI.listTodoItems());
        }
    }

    /*
    Option 16 - Overall number of items todo/complete
    Create a printOverallItemsTodoComplete() method in Driver. This method, if
        items exist:
    - Will print out the number of completed items, followed by the number of
        TODO items.
    */
    private void printOverallItemsTodoComplete() {
        // Implementation for printing overall TODO and completed items count
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
    Create a printItemCompletionStatusByCategory() method in Driver. This method, if items exist:
    - Will ask the user to enter a valid category.
      This category is then used to list the item status (and number) by category to the user.
    */
    private void printItemCompletionStatusByCategory() {
        // Implementation for printing item completion status by category
        if (noteAPI.numberOfNotes() == 0) {
            System.out.println("No notes saved!");
        }
        else {
            String category = readNextLine("Enter a category [Home, Work, Hobby, Holiday, College] => ");
            if ((category == null) || !(CategoryUtility.isValidCategory(category))) {
                System.out.println("Invalid category selected! [" + category + "]");
            }
            else {
                System.out.println(noteAPI.listItemStatusByCategory(category));
            }

        }
    }

    /*
    Option 18 - Search for all items (by item description)
    Create a searchItemsByDescription() method in Driver. This method, if:
    - items exist, will ask the user to enter a description they wish to search items by.
      The applicable noteAPI method is then called to list all items whose
        description, or subset of their description, match the entered String.
    - items don’t exist, inform the user of this.
    */
    private void searchItemsByDescription() {
        // Implementation for searching items by description
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
    This method will save the notes ArrayList to an XML file.
    */
    private void save() {
        // Implementation for saving notes to XML
        try {
            noteAPI.save();
            System.out.println("Notes saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving notes! [" + e.getMessage() + "]");
        }
    }

    /*
    Option 21 - Load
    This method will load the notes ArrayList from the XML file that was
        created in Option 20.
    */
    private void load() {
        // Implementation for loading notes from XML
        try {
            noteAPI.load();
            System.out.println("Notes loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error loading notes! [" + e.getMessage() + "]");
        }
    }

    /*
    Option 0 - Exit
    This option exits the application.
    */
    private void exitApplication() {
        // Implementation for exiting the application
        System.out.println("Exiting... goodbye");
        System.exit(0);
    }
}