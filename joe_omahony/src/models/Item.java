package models;

public class Item {
/*The responsibility for this class is to manage a single Item in the
system. An item belongs to a specific Note.

Remember, as soon as you enter the fields in a class in IntelliJ, you can
GENERATE getters, setters, constructors and toString methods. They are only
 basic methods and may need to be modified e.g. to add validation, change
 the format of the generated toString, etc.*/
    public Item(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Item(String itemDescription, boolean isItemCompleted) {
        this.itemDescription = itemDescription;
        this.isItemCompleted = isItemCompleted;
    }

/*
Ensure that the constructor and mutators adhere to the validation rules
listed above.
*/

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public boolean isItemCompleted() {
        return isItemCompleted;
    }

    public void setItemCompleted(boolean itemCompleted) {
        isItemCompleted = itemCompleted;
    }

    public boolean equals(Object obj) {
        return false;
    }

/* Sample output from the toString is below (note how the boolean value
for isItemcompleted is set to either TODO or Completed when printing to
the console):
Secondary School 3:30. [Completed]
Primary School 2.30. [TODO]
Book flights. [COMPLETED]
Book hotel. [COMPLETED]
 */

    @Override
    public String toString() {
        return "Item{" + "itemDescription='" + itemDescription + '\'' + ", isItemCompleted=" + isItemCompleted + '}';
    }
/*
itemDescription: The description is maximum 50 characters. When creating a
new item, if the string is longer than 50 characters, you shoud truncate the
 string to the first 50 chars (Hint see Utilities for a useful method to do
  this) When updating itemDescription, you should only update if the value
  is less than or equal to 50.
*/
    private String itemDescription = "No Description";

/*
    Hint: you can use Utilities validation methods to perform the above
     validation (see Utilities tab for more information). We have provided
     some methods, but you would need to write more.
*/

/*
isItemCompleted: A boolean value, set to false for every new item
(unless the two parameter constructor is used).
 */
    private boolean isItemCompleted = false;
}
