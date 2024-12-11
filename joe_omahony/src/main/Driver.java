package main;

import controllers.NoteAPI;
import models.Note;
import models.Item;
import java.util.Scanner;
import java.util.ArrayList;

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
    }

    private void runMenu() {
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
        // Implementation for adding a note
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
        // Implementation for deleting an item from a note
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
        // Implementation for marking an item's completion status
    }

    private void printAllNotes() {
    // In Picture of spec class, but no more detail given! Beware.
    }

    private void printArchivedNotes {
// In Picture of spec class, but no more detail given! Beware.
    }

    private void printActiveNotes() {
        // In Picture of spec class, but no more detail given! Beware.
    }

    /*
    REPORT MENU FOR NOTES Options
    Option 10 - All notes and their items (active & archived)
    Create a printActiveAndArchivedReport() method in Driver. This method
        will first print the active notes followed by the archived notes.
    */
    private void printActiveAndArchivedReport() {
        // Implementation for printing all notes and their items
    }

    /*
    Option 11 - Archive notes whose items are all complete
    Create an archiveNotesWithAllItemsComplete() method in Driver. This method
        will archive all active notes whose items are completed
            (hint: NoteAPI archiveNotesWithAllItemsComplete()).
    */
    private void archiveNotesWithAllItemsComplete() {
        // Implementation for archiving notes with all items complete
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
    }


// ----------------REPORT MENU FOR ITEMS Options--------------------
    /*
    Option 15 - All items that are todo (with note title)
    Create a printAllTodoItems() method in Driver. This method will print all
        the TODO items to the console.
    */
    private void printAllTodoItems() {
        // Implementation for printing all TODO items
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
    }

    /*
    Option 17 - Todo/complete items by specific Category
    Create a printItemCompletionStatusByCategory() method in Driver. This method, if items exist:
    - Will ask the user to enter a valid category.
      This category is then used to list the item status (and number) by category to the user.
    */
    private void printItemCompletionStatusByCategory() {
        // Implementation for printing item completion status by category
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
    }


    // NO OPTION 19

    /*
    SETTINGS MENU Options
    Option 20 - Save
    This method will save the notes ArrayList to an XML file.
    */
    private void save() {
        // Implementation for saving notes to XML
    }

    /*
    Option 21 - Load
    This method will load the notes ArrayList from the XML file that was
        created in Option 20.
    */
    private void load() {
        // Implementation for loading notes from XML
    }

    /*
    Option 0 - Exit
    This option exits the application.
    */
    private void exitApplication() {
        // Implementation for exiting the application
    }
}