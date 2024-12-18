package models;

import java.util.Objects;
import static utils.Utilities.*;

public class Item {
    /*The responsibility for this class is to manage a single Item in the
    system. An item belongs to a specific Note.

    Remember, as soon as you enter the fields in a class in IntelliJ, you can
    GENERATE getters, setters, constructors and toString methods. They are only
     basic methods and may need to be modified e.g. to add validation, change
     the format of the generated toString, etc.*/
    public Item(String itemDescription) {
        this.setItemDescription(itemDescription);
    }

    public Item(String itemDescription, boolean isItemCompleted) {
        this.setItemDescription(itemDescription);
        this.setItemCompleted(isItemCompleted); // two param constructor used
    }

/*
Ensure that the constructor and mutators adhere to the validation rules
listed above.
*/

    public String getItemDescription() {
        return this.itemDescription;
    }

    public void setItemDescription(String passedItemDescription) {
        if ((passedItemDescription == null) || (passedItemDescription.isBlank())) {
            // Do nothing
        }
        else if (validateStringLength(passedItemDescription, 50)) {
            this.itemDescription = passedItemDescription;
        }
        else if (this.itemDescription.equals("No Description")) {
            this.itemDescription = truncateString(passedItemDescription, 50);
        }
        else {
            // Do nothing
        }
    }

    public boolean isItemCompleted() {
        return this.isItemCompleted;
    }

    public void setItemCompleted(boolean itemCompleted) {
        this.isItemCompleted = itemCompleted;
    }

    @Override // MODIFIED FROM GIVEN equals NOTE CATEGORY
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return isItemCompleted == item.isItemCompleted &&
                Objects.equals(itemDescription, item.itemDescription); // Logical equality check
    }

/* Sample output from the toString is below (note how the boolean value
for isItemCompleted is set to either TODO or Completed when printing to
the console):
Secondary School 3:30. [Completed]
Primary School 2.30. [TODO]
Book flights. [COMPLETED]
Book hotel. [COMPLETED]
 */

    @Override
    public String toString() {
        String itemFormat = "";
        itemFormat += this.getItemDescription() + ".\t";
        if (this.isItemCompleted()) {
            itemFormat += "[Completed]";
        }
        else {
            itemFormat += "[TODO]";
        }
        return itemFormat;
    }
    /*
    itemDescription: The description is maximum 50 characters. When creating a
    new item, if the string is longer than 50 characters, you should truncate the
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