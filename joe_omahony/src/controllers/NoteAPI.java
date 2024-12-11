package controllers;

import models.Note;

import java.util.ArrayList;

public class NoteAPI {
    // The responsibility for the NoteAPI class is to manage the ArrayList of notes.

    /* You will notice that we don’t have constructors, getters, setters and
    toStrings in the above class structure. You can decide to generate these
    boilerplate methods for your class, should you wish. */

// -------------- START OF CRUD METHODS --------------

    public boolean add(Note note) {
        //This method adds a note object to the ArrayList notes and returns
        // the boolean result of the add.
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
    }

    public Note deleteNote(int indexToDelete) {
        /* This method deletes an note at the index parameter (if that index exists)
           and returns the deleted note object. If the index does not exist in the
           notes list, then null should be returned. */
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
    }

    public String archiveNotesWithAllItemsComplete() {
/*
This method first checks that active notes are in the ArrayList. ==>>If:
==>> no active notes exist, return “No active notes stored”.
==>> active notes exist, for every active note, if ALL items in the note are complete, archive the note. Also build a String of all newly archived notes and return it.

Hint: remember we wrote a method in Item - checkNoteCompletionStatus()
 */
    }

// -------------- END OF CRUD METHODS --------------

// -------------- START OF COUNTING METHODS --------------

    public int numberOfNotes() {
//returns the number of notes stored in the notes ArrayList.
    }

    public int numberOfArchivedNotes() {
// returns the number of ARCHIVED notes stored in the notes ArrayList
// (i.e. where isNoteArchived is set to true).
    }

    public int numberOfActiveNotes() {
// returns the number of ACTIVE notes stored in the notes ArrayList
// (i.e. where isNoteArchived is set to false).
    }

    public int numberOfNotesByCategory(String category) {
// returns the number of notes that are stored for the category passed as a
// parameter.
    }

    public int numberOfNotesByPriority(int priority) {
// returns the number of notes that are stored for the priority passed
// as a parameter.
    }

    public int numberOfItems() {
// adds up the number of items stored on ALL the notes in the ArrayList
// and returns it.
    }

    public int numberOfCompleteItems() {
// adds up the number of items stored on ALL the notes in the ArrayList where
// isItemCompleted is set to true. The number is then returned.
    }

    public int numberOfTodoItems() {
// adds up the number of items stored on ALL the notes in the ArrayList where
// isItemCompleted is set to false. The number is then returned.
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
    }

    public String listActiveNotes() {
/*
If the notes ArrayList is checked to see if Active notes are stored.
==>>If active notes:
    ==>> exist, a String is returned that contains a list of all active notes
        (including their index number).
    ==>>don’t exist, the String “No active notes stored” is returned.
 */
    }

    public String listArchivedNotes() {
/*
The functionality of this method is the same as the listActiveNotes()
functionality, except you are dealing with Archived notes, not active ones.
 */
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
    }

    public String listNotesBySelectedPriority(int priority) {
/*
The functionality of this method is the same as the
 listNotesBySelectedCategory(String category) functionality, except you are
 dealing with Priority, not Category.
 */
    }

    public String listTodoItems() {
/*
==>> If the notes list is:
    ==>> empty, the String “No notes stored” is returned.
    ==>> not empty, for every note, for all TODO items, add the note title and
         the TODO item to a String. This String is then returned. Sample output
         could be:
         School Run: Primary School 2:30. [TODO]
         Hoover House: Hoover Upstairs. [TODO]
 */
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
    }

    public String searchNotesByTitle(String str) {
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
    }

    public String searchItemByDescription(String str) {
/*
==>> If the notes list is:
    ==>> empty, the String “No notes stored” is returned.
    ==>> not empty, for each note, the description of each item is checked
         against the searchString passed as a parameter. If they match, the index of
         the note, the noteTitle and the item is added to a String. When all notes
         have been checked, ==>> if the String:
         ==>> When all notes have been checked, if the String:
            ==>> is still empty, the text “No items found for: ” is returned.
            ==>> is not empty, the String containing all items that matched is returned.
 */
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

    }

    // -------------- TWO PERSISTENCE METHODS  --------------
    public void load() {
// this method saves the notes ArrayList to an XML file on your hard disk.
    }

    public void save() {
// this method loads the XML file previously stored on your hard disk into the
// notes ArrayList.
    }

    // notes: This is the ArrayList of Note in the app. It is initialised at
    // field declaration time.
    private ArrayList<Note> notes = new ArrayList<Note>();
}
