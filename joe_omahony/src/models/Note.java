package models;

import java.util.ArrayList;
import java.util.Objects;

import static utils.CategoryUtility.*;
import static utils.Utilities.*;

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
    public Note(String noteTitle, int notePriority, String noteCategory) {
        this.setNoteTitle(noteTitle);
        this.setNotePriority(notePriority);
        this.setNoteCategory(noteCategory);
    }

    public String getNoteTitle() {
        return this.noteTitle;
    }

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

    public int getNotePriority() {
        return this.notePriority;
    }

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

    public String getNoteCategory() {
        return this.noteCategory;
    }

    public void setNoteCategory(String noteCategory) {
        // noteCategory: should contain only one of the following categories: “Home”, “Work”,
    // “Hobby”, “Holiday”, “College”. When creating a new note, if no category is
       // supplied, you should default the empty String, “”.
        if ((noteCategory == null) || (noteCategory.isBlank())) {
                this.noteCategory = "";
        }
        else if (isValidCategory(noteCategory.trim())) {
            this.noteCategory = noteCategory;
        }
        else if (noteCategory.trim() == "home") {
            this.noteCategory = "Home";
        }
        else if (noteCategory.trim() == "work") {
            this.noteCategory = "Work";
        }
        else if (noteCategory.trim() == "hobby") {
            this.noteCategory = "Hobby";
        }
        else if (noteCategory.trim() == "holiday") {
            this.noteCategory = "Holiday";
        }
        else if (noteCategory.trim() == "college") {
            this.noteCategory = "College";
        }
    }

    public boolean isNoteArchived() {
        return isNoteArchived;
    }

    public void setNoteArchived(boolean noteArchived) {
        this.isNoteArchived = noteArchived;
    }

    public ArrayList<Item> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

// ----------------------- END OF GETTERS & SETTERS -----------------------

    public int numberOfItems() {
//This method simply returns the number of items stored in the items ArrayList.
        return this.items.size();
    }

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

    public String listItems() {
        /*
        This method should return a String, which should be either:
==>> the list of items (including index number) if there are items in the note.
==>> the string “No items added” if there are no items added yet.
         */
        String listItemsString = "";
        if ((this.items == null) || (this.items.isEmpty()))  {
            listItemsString = "No items added";
        }
        else {
            for (int i = 0; i < this.items.size(); i++) {
                listItemsString += i + ": " + this.items.get(i).toString() + "\n";
            }
        }
        return listItemsString;
    }

    public boolean isValidIndex(int index){
        /*
        This method checks that the index, passed as a parameter is a valid index
         in the items ArrayList. If it is a valid index, return true. If invalid,
          return false.
         */
        return (validRange(index, 0, (this.items.size()) - 1));
    }

    public Item findItem(int index){
        if (isValidIndex(index)) {
            return this.items.get(index);
        }
        else {
            return null;
        }
    }

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

    @Override
    public String toString() {
        // This method builds a user friendly string representation of the
        // object and returns it e.g.
        /*
Exam Boards, Priority=5, Category=Work, Archived=N
0: BSc Ord IT, Year 3. [TODO]
1: BSC Hons in Applied Computing, Year 1. [TODO]
2: BS Hons in SSD, Year 2. [TODO]
School Run, Priority=5, Category=Home, Archived=N
0: Secondary School 3:30. [Completed]
1: Primary School 2:30. [TODO]|
Hoover House, Priority=1, Category=Home, Archived=N
0: Hoover Upstairs. [TODO]
Polish Furniture, Priority=2, Category=Home, Archived=N
No items added
Surf, Priority=4, Category=Hobby, Archived=Y
No items added
Book Holiday, Priority=5, Category=Home, Archived=N
0: Book flights. [Completed]
1: Book hotel. [Completed]*/
        return noteTitle + ", " + "Priority=" + notePriority + ", " + "Category=" + noteCategory + ", " + "Archived="
                + booleanToYN(isNoteArchived()) +  "\n" + this.listItems();
    }

    /*
    Fields

    There are five private fields in the Note class:







    isNoteArchived: should be either true or false. When creating a new note, it
    should be defaulted to false.

    items: is an ArrayList of Item. This should be initialised at variable declaration
     time. There are several methods listed below for handling this list.

    Hint: you can use Utilities validation methods to perform the above validation
     (see Utilities tab for more information). We have provided some methods, but you
      would need to write more.
     */
    private String noteTitle = "No Title";

    private int notePriority = 1;

    private String noteCategory = "";

    private boolean isNoteArchived = false;

    private ArrayList<Item> items = new ArrayList<Item>();
}
