package models;

import java.util.ArrayList;
import java.util.Objects;

import static utils.CategoryUtility.*;
import static utils.Utilities.*;
/**
 * The responsibility of the {@code Note} class is to manage a single Note object in the system.
 * Each {@code Note} object has the following attributes representing its state:
 * <ul>
 *  <li>{@code noteTitle} The title of the note object, 20 character maximum, defaults to "No Title".</li>
 *  <li>{@code notePriority} The priority of the note object, integer between 1 and 5, defaults to 1.</li>
 *  <li>{@code noteCategory} The category of the note object, must be one of "Home", "Work", "Hobby", "Holiday", "College"</li>
 *  <li>{@code isNoteArchived} => the archived state of the note object, defaults to false.</li>
 * </ul>
 * <b>Additionally,</b> each {@code Note} object stores a collection (ArrayList) of {@link Item} objects [0 to many],
 * with their respective state(s), defaults to "".
 * {@code Note} also accesses CRUD methods in the {@link Item} class.
 * {@code Note} calls validation methods from the {@link utils.Utilities} and {@link utils.CategoryUtility} classes.
 * <br />
 * <br /><h4>Purpose:</h4>
 * <ul>
 *   <li>Store and manage a note's attributes.</li>
 *   <li>Maintain a collection of {@link Item} objects that belong to this note (ArrayList, 0 to many),
 *   all {@link Item} objects must be linked to a note.</li>
 *   <li>Provide CRUD methods to create, list, update, and delete items associated with the note.</li>
 *   <li>Enforce validation rules for the {@code Note} object's attributes.</li>
 * </ul>
 *
 * @author Joe O'Mahony, Dave Hearne
 * @version 2.0
 */
public class Note {
/*
The responsibility for this class is to manage a single Note in the System.
 A note has multiple items in it.

 Remember, as soon as you enter the fields in a class in IntelliJ, you can
 GENERATE getters, setters, constructors and toString methods. They are only
  basic methods and may need to be modified e.g. to add validation, change the
   format of the generated toString, etc.
 */

    /*
    Constructor

    There is one constructor that should have the same method signature as the
    diagram above e.g.:
    public Note(String noteTitle, int notePriority, String noteCategory)
    The constructor should enforce the validation rules outlined for each field above.
     */
    /**
     * Constructs a new {@code Note} object with specified note title, priority, and category.
     * This constructor enforces validation rules for each field.
     *
     * @param noteTitle title of the note (max length 20, defaults to "No Title")
     * @param notePriority priority of the note (integer between 1 and 5, defaults to 1)
     * @param noteCategory category of the note (either "Home", "Work", "Hobby", "Holiday", "College"; defaults to "")
     */
    public Note(String noteTitle, int notePriority, String noteCategory) {
        this.setNoteTitle(noteTitle);
        this.setNotePriority(notePriority);
        this.setNoteCategory(noteCategory);
    }

    /**
     * Gets the current note's title.
     * @return note's title
     */
    public String getNoteTitle() {
        return this.noteTitle;
    }

    /**
     * Sets the note's title, only if it meets the below validation rules. Calls {@link utils.CategoryUtility} to check.
     * <ul>
     *   <li>Must not exceed 20 characters.</li>
     *   <li>If the current title is "No Title" and the new title exceeds 20 characters, truncate.</li>
     *   <li>If the current title is valid and the passed note title exceeds 20 characters, do not truncate or update.</li>
     * </ul>
     *
     * @param passedNoteTitle The title of the note object, 20 character maximum, defaults to "No Title".
     */
    public void setNoteTitle(String passedNoteTitle) {
        //noteTitle: is maximum 20 characters. When creating a new note, if no title is
        // supplied, you should default the text “No Title”. When updating noteTitle,
        // you should only update if the value is less than or equal to 20.
        if (validateStringLength(passedNoteTitle, 20)) {
            this.noteTitle = passedNoteTitle;
        }
        else if (this.noteTitle.equals("No Title")) {
            this.noteTitle = truncateString(passedNoteTitle, 20);
        }
        // else {} NO UPDATE if longer than 20 characters, don't truncate
    }

    /**
     * Gets the current note's priority.
     * @return note's priority
     */
    public int getNotePriority() {
        return this.notePriority;
    }

    /**
     * Sets the current note's priority.
     * @param passedNotePriority desired priority of the note object, integer between 1 and 5, defaults to 1.
     */
    public void setNotePriority(int passedNotePriority) {
        // notePriority: should only contain a value from 1 to 5 inclusive. When creating a
        // new note, if no priority is supplied, you should default the Priority to 1.
        if ((passedNotePriority < 1) || (passedNotePriority > 5)) {
            // DO NOTHING
        }
        else {
            this.notePriority = passedNotePriority;
        }
    }
    /**
     * Gets the current note's category.
     * @return note's category
     */
    public String getNoteCategory() {
        return this.noteCategory;
    }

    /** Sets the current note's category.
     * Category validated by methods in {@link utils.CategoryUtility}, and must be one of the following:
     * <ul>
     * <li>"Home",</li>
     * <li>"Work",</li>
     * <li>"Hobby",</li>
     * <li>"Holiday",</li>
     * <li>"College".</li>
     * </ul>
     * Defaults to an empty string if no category or an invalid category is provided.
     * @param noteCategory the category of the note object, must be one of "Home", "Work", "Hobby", "Holiday", "College"
     */
    public void setNoteCategory(String noteCategory) {
        // noteCategory: should contain only one of the following categories: “Home”, “Work”,
        // “Hobby”, “Holiday”, “College”. When creating a new note, if no category is
        // supplied, you should default the empty String, “”.
        if (isValidCategory(noteCategory.trim())) {
            this.noteCategory = noteCategory;
        }
        else if (!(this.noteCategory.isEmpty())) {
            // do nothing
        }
        else {
            this.noteCategory = "";
        }
    }

    /**
     * Gets the current note's archive state (true if archived, false otherwise).
     * @return note's archive state
     */
    public boolean isNoteArchived() {
        return isNoteArchived;
    }

    /**
     * Sets the current note's archive state
     * @param noteArchived the archived state of the note object, defaults to false.
     */
    public void setNoteArchived(boolean noteArchived) {
        this.isNoteArchived = noteArchived;
    }

    /**
     * Gets the current note's collection of items (ArrayList).
     * @return note's collection of items.
     */
    public ArrayList<Item> getItems() {
        return this.items;
    }

    /**
     * Sets the current note's collection of items (ArrayList).
     * Stores a collection (ArrayList) of {@link Item} objects [0 to many], with their respective state(s), defaults to ""
     * @param items a collection of {@link Item} objects [0 to many], defaults to ""
     */
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    /**
     * Gets the number of items in the current note's collection.
     * @return number of items in the current note's collection.
     */
    public int numberOfItems() {
//This method simply returns the number of items stored in the items ArrayList.
        return this.items.size();
    }
    // ----------------------- END OF GETTERS & SETTERS -----------------------
    /**
     * Checks the completion status for each item on a note. <b>The following logic was copied from assignment spec.</b>
     * <ul>
     *     <li>If:</li>
     *     <ul>
     *      <li>ALL items are completed or the note has no items, return {@code true}.</li>
     *      <li>one or more item is TODO, return {@code false}.</li>
     *   </ul>
     * </ul>
     *
     * @return {@code true} if all items are completed or no note items; {@code false} otherwise
     * @author Dave Hearne
     */
    public boolean checkNoteCompletionStatus(){
        /*
        This method looks at the completion status for each item on a note.
    ==>> If:
==> ALL items are completed or the note has no items, return true.
==> one or more item is TODO, return false.
         */
        // You can simplify this with boolean ! and returning instead of using a flag,
        // but it gets very hard to follow so I've left it as is
        boolean noteCompletionFlag = true;
        for (Item individualItem : this.items) {
            if (individualItem.isItemCompleted() == false) {
                noteCompletionFlag = false;
            }
        }
        return noteCompletionFlag;
    }

    /**
     * Adds an item object to the {@param items} collection (ArrayList) and returns the boolean result of the add.
     * @param item the item object to add to the {@param items} collection.
     * @return {@code true} if the add was successful, {@code false} otherwise.
     */
    public boolean addItem(Item item){
        /*
        This method adds an item object to the ArrayList items and returns
        the boolean result of the add.

Hint: go to the Java API documentation for the ArrayList add method;
you will notice that it returns a boolean indicating success / failure of
the add.
         */
        return this.items.add(item);
    }

    /**
     * Lists all items associated with the note object in a friendly format.
     * Adds the index number of the {@code item} in the collection if there are items.
     * If no items have been added, returns "No items added".
     *
     * @return a String listing all items with their indexes or "No items added"
     */
    public String listItems() {
        /*
        This method should return a String, which should be either:
==>> the list of items (including index number) if there are items in the note.
==>> the string “No items added” if there are no items added yet.
         */
        String listItemsString = "";
        if ((this.items == null) || (this.items.isEmpty()))  {
            listItemsString = "\tNo items added" + "\n";
        }
        else {
            for (int i = 0; i < this.items.size(); i++) {
                listItemsString += "\t" + i + ": " + this.items.get(i).toString() + "\n";
            }
        }
        return listItemsString;
    }

    /**
     * Checks if the passed index is valid in the item's collection {@code items}.
     *
     * @param index index to check
     * @return {@code true} if the index is valid, {@code false} otherwise
     */
    public boolean isValidIndex(int index){
        /*
        This method checks that the index, passed as a parameter is a valid index
         in the items ArrayList. If it is a valid index, return true. If invalid,
          return false.
         */
        return (validRange(index, 0, (this.items.size()) - 1));
    }

    /**
     * Retrieves an {@link Item} by its index if valid. Otherwise returns {@code null}.
     *
     * @param index index of the item to retrieve
     * @return {@link Item} at the given index, or {@code null} if not found
     */
    public Item findItem(int index){
        if (isValidIndex(index)) {
            return this.items.get(index);
        }
        else {
            return null;
        }
    }

    /**
     * Deletes an {@link Item} by its index if valid, and returns that {@param item} object.
     * Otherwise, returns {@code null}.
     *
     * @param index index of the item to delete
     * @return deleted {@link Item} at the given index, or {@code null} if unable to delete
     */
    public Item deleteItem(int index){
        /*
        This method deletes an item at the index parameter (if that index exists)
         and returns the deleted item object. If the index does not exist in the
          items list, then null should be returned.
         */
        if (isValidIndex(index)) {
            return this.items.remove(index);
        }
        else {
            return null;
        }
    }

    /**
     * Updates an existing {@link Item} at the passed index with a new description and completion status.
     * If the index is invalid, returns {@code false}.
     * If the index is valid, updates the {@link Item}'s description and completion status and returns {@code true}.
     * @param index index of the item to update
     * @param description new description
     * @param isArchived archive status of the item, i.e. {@code true} if the item is completed, {@code false} otherwise
     * @return {@code true} if update was successful, {@code false} if not
     */
    public boolean updateItem(int index, String description, boolean isArchived) {
        /*
        This method should attempt to retrieve the item stored at the index number
        passed as a parameter. ==>> If the item:
==>> doesn't exist, return false, indicating the update was not successful.
==>> exists, use the Item mutators to update both the description and the
     completion status with the details passed in the parameter list. Finally,
     return true to indicate a successful update.

     When updating noteTitle,
                // you should only update if the value is less than or equal to 20.*/
        if (isValidIndex(index)) {
            items.get(index).setItemDescription(description);
            items.get(index).setItemCompleted(isArchived);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * <b>This class was provided in full in the assignment spec.</b>
     * Determines whether the current note is equal to another object.
     * Two notes are equal if they have the same:
     * <ul>
     * <li>>title,</li>
     * <li>priority,</li>
     * <li>category,</li>
     * <li>archive status,</li>
     * <li>items.</li>
     * </ul>
     * @param o object to compare with note
     * @return {@code true} if the passed object equals the note, {@code false} otherwise
     * @author Dave Hearne
     */
    // COPIED FROM ASSIGNMENT SPEC
    // This method checks equality between notes. BELOW IS NOTE VERSION OF equals()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return notePriority == note.notePriority
                && isNoteArchived
                == note.isNoteArchived
                && Objects.equals(noteTitle, note.noteTitle)
                && Objects.equals(noteCategory, note.noteCategory)
                && Objects.equals(items, note.items);
    }


    /**
     * Returns a user-friendly string representation of the note object, including:
     * <ul>
     *   <li>note object attributes</li>
     *   <li>associated items and item attributes, through calling methods from {@link Item} </li>
     * Sample outputs <b>from the assignment spec</b>:
     * <pre>
     * Exam Boards, Priority=5, Category=Work, Archived=N
     * 0: BSc Ord IT, Year 3. [TODO]
     * 1: BSC Hons in Applied Computing, Year 1. [TODO]
     * 2: BS Hons in SSD, Year 2. [TODO]
     * School Run, Priority=5, Category=Home, Archived=N
     * 0: Secondary School 3:30. [Completed]
     * 1: Primary School 2:30. [TODO]|
     * Hoover House, Priority=1, Category=Home, Archived=N
     * 0: Hoover Upstairs. [TODO]
     * Polish Furniture, Priority=2, Category=Home, Archived=N
     * No items added
     * Surf, Priority=4, Category=Hobby, Archived=Y
     * No items added
     * Book Holiday, Priority=5, Category=Home, Archived=N
     * 0: Book flights. [Completed]
     * 1: Book hotel. [Completed]
     * </pre>
     * @return a formatted string representation of the note and its items
     * @author Joe O'Mahony, Dave Hearne
     */
    @Override
    public String toString() {
        // This method builds a user-friendly string representation of the
        // object and returns it e.g.
        return noteTitle + ", " + "Priority=" + notePriority + ", " + "Category=" + noteCategory + ", " + "Archived="
                + booleanToYN(isNoteArchived()) +  "\n" + this.listItems() + "\n";
    }

    /*
    Fields

    There are five private fields in the Note class:
    noteTitle: is maximum 20 characters. When creating a new note, if no title is supplied, you should default the text
    “No Title”. When updating noteTitle, you should only update if the value is less than or equal to 20.

    notePriority: should only contain a value from 1 to 5 inclusive. When creating a new note, if no priority is
    supplied, you should default the Priority to 1.

    noteCategory: should contain only one of the following categories: “Home”, “Work”, “Hobby”, “Holiday”, “College”.
     When creating a new note, if no category is supplied, you should default the empty String, “”.

    isNoteArchived: should be either true or false. When creating a new note, it
    should be defaulted to false.

    items: is an ArrayList of Item. This should be initialised at variable declaration
     time. There are several methods listed below for handling this list.

    Hint: you can use Utilities validation methods to perform the above validation
     (see Utilities tab for more information). We have provided some methods, but you
      would need to write more.
     */

    /**
     * Title of the note.
     * Defaults to "No Title" if a title isn't provided or is invalid.
     * Maximum length of 20 characters. Attempts to <b>>update</b with a string over 20 characters are ignored
     * unless the current title is "No Title", in which case the new value is truncated.
     */
    private String noteTitle = "No Title";

    /**
     * Priority of the note. Must be an integer between 1 and 5, inclusive.
     * Defaults to 1 if no priority or an invalid priority is provided.
     */
    private int notePriority = 1;

    /**
     * Category of the note, which must be either:
     * <ul>
     * <li>"Home",</li>
     * <li>"Work",</li>
     * <li>"Hobby",</li>
     * <li>"Holiday",</li>
     * <li>"College".</li>
     * </ul>
     * Defaults to an empty string if no category or an invalid category is provided.
     */
    private String noteCategory = "";

    /**
     * Archived status of the note.
     * Defaults to false (not archived) when a new note is created.
     */
    private boolean isNoteArchived = false;

    /**
     * The ArrayList of {@link Item} objects associated with this instance of {@code note}.
     * Empty by default.
     */
    private ArrayList<Item> items = new ArrayList<Item>();
}