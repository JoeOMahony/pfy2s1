package models;

import java.util.Objects;
import static utils.Utilities.*;
/**
 * The responsibility of the {@code Item} class is to manage a single Item in the system.
 * Each {@code Item} item belongs to a specific Note.
 * Each {@code Item} holds a description {@code itemDescription} and a completion status {@code isItemCompleted}.
 * {@code Item} calls validation methods from the {@link utils.Utilities} class.
 * <h4>Purpose:</h4>
 * <ul>
 *   <li>Store and manage an item object's description and completed state.</li>
 *   <li>Enforce validation rules for the item description.</li>
 *   <li>Provide CRUD methods to create, list, update, and delete items.</li>
 *   <li>Prove a string representation of an item object for {@param Note} and {@param NoteAPI} called in {@param Driver}.</li>
 * </ul>
 *
 * @author Joe O'Mahony, Dave Hearne
 * @version 2.0
 */
public class Item {
    /*The responsibility for this class is to manage a single Item in the
    system. An item belongs to a specific Note.

    Remember, as soon as you enter the fields in a class in IntelliJ, you can
    GENERATE getters, setters, constructors and toString methods. They are only
     basic methods and may need to be modified e.g. to add validation, change
     the format of the generated toString, etc.*/
    /**
     * Constructs a new {@code Item} object with a description passed as a parameter.
     * The description will be validated by {@code setItemDescription(String itemDescription)}.
     * If {@code itemDescription} exceeds 50 characters it will be truncated, so long as a valid description
     * is not already set.
     *
     * @param itemDescription the description of the item
     */
    public Item(String itemDescription) {
        this.setItemDescription(itemDescription);
    }
    /**
     * Constructs a new {@code Item} object with the description and completion status passed as parameters.
     * If {@code itemDescription} exceeds 50 characters it will be truncated, if no {@code itemDescription} is passed,
     * then it will default to "No Description".
     * {@code isItemCompleted} defaults to false, i.e. {@code [TODO]} in the {@code toString()} method.
     *
     * @param itemDescription the description of the item (Max length of 50, default of "No Description")
     * @param isItemCompleted the initial completion status of the item (default of false)
     */
    public Item(String itemDescription, boolean isItemCompleted) {
        this.setItemDescription(itemDescription);
        this.setItemCompleted(isItemCompleted); // two param constructor used
    }

/*
Ensure that the constructor and mutators adhere to the validation rules
listed above.
*/
    /**
     * Gets the current item object's description.
     * @return the current item description
     */
    public String getItemDescription() {
        return this.itemDescription;
    }

    /**
     * Sets the current item object's description.
     * @param passedItemDescription the description of the item.
     * If {@code passedItemDescription} has not been modified already, this method truncates to 50 characters if necessary.
     * Otherwise, no changes will be made. Checks changes through {@code "No Description} equality with {@code ItemDescription}.
     */
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
    /**
     * Checks whether the item is completed.
     * @return {@code true} if the item is completed, and {@code false} (default) if the item is not completed.
     */
    public boolean isItemCompleted() {
        return this.isItemCompleted;
    }
    /**
     * Sets whether the item is completed.
     * @param itemCompleted {@code true} if the item is completed, and {@code false} (default) if the item is not completed.
     */
    public void setItemCompleted(boolean itemCompleted) {
        this.isItemCompleted = itemCompleted;
    }

    /**
     * Determines whether the current item object is equal to another object.
     * This class was barely modified from the assignment specification <b>provided method</b> {@code equals()} in
     * the {@code Note} class.
     * @param o the object to compare with this item object
     * @return {@code true} if the given object equals our item object, {@code false} if not.
     * @author Dave Hearne, Joe O'Mahony
     */
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

    /**
     * Creates a friendly String representation of the current item object's state, with {@code itemDescription} and
     * {@code isItemCompleted} fields formatted/modified to text.<br />
     * {@code isItemCompleted} will be represented as follows:
     * <ul>
     *     <li> if {@code isItemCompleted} is {@code true} => {@code [Completed]} is appended to the String.</li>
     *      <li></li>if {@code isItemCompleted} is {@code false} => {@code [TODO]} is appended to the String.</li>
     * </ul>
     *
     * Sample output <b>straight from assignment spec (Author: Dave Hearne)</b>: <br />
     * Secondary School 3:30. [Completed] <br />
     * Primary School 2.30. [TODO] <br />
     * Book flights. [Completed] <br />
     * Book hotel. [Completed] <br />
     *
     * @return a friendly String representation of the current item object's state.
     */
    @Override
    public String toString() {
        String itemFormat = "";
        itemFormat += this.getItemDescription() + ". ";
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
    /**
     * Description of the item.
     * Maximum length of 50 characters.
     * If the description provided in {@code setItemDescription()} is longer than 50 characters, it's truncated - unless
     * {@code itemDescription} already contains a valid, non-default String, i.e. {@code itemDescription != "No Description"}.
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
    /**
     * Completed state of the item object, default of {@code false}.
     * Corresponds to {@code [TODO]} or {@code [Completed]} in listing methods in {@code toString()}
     */
    private boolean isItemCompleted = false;
}