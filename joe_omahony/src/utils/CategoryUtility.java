/*
CategoryUtility

The static field, categories is an ArrayList of String and,
 at declaration time, defines the ArrayList values of:
Home
Work
Hobby
Holiday
College

Hints for initialising an ArrayList at declaration time: https://www.geeksforgeeks.org/initialize-an-arraylist-in-java/
There are two static methods in it:
- getCategories() - a basic accessor that returns the categories ArrayList.
- isValidCategory(String) - a basic validation method that
    takes in a category as a parameter and checks to see if
    it exists in the categories ArrayList. If it exists,
    return true. Having looked at each element in the
    ArrayList, if it doesn’t exist, return false.

This utility class can be used to validate the category
    when reading it from the user (isValidCategory).

It can also be used to print out valid categories to the
 user (getCategories).
 */

package utils;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * The responsibility of the {@code CategoryUtility} class is to manage and validate categories in the system.
 * Each category is a String representing a category that can be assigned to a note.
 * <h4>Valid categories:</h4>
 * <ul>
 *   <li>"Home"</li>
 *   <li>"Work"</li>
 *   <li>"Hobby"</li>
 *   <li>"Holiday"</li>
 *   <li>"College"</li>
 * </ul>
 *
 * <h4>Purpose:</h4>
 * <ul>
 *   <li>Provide a predefined list of valid categories.</li>
 *   <li>Perform validation for a given category against this predefined list.</li>
 *   <li>Format category strings to ensure they are handled properly, i.e. a lowercase first character doesn't cause an
 *   invalid category issue.</li>
 *   <li>Provide the full list of valid categories for selection.</li>
 * </ul>
 *
 * @author Joe O'Mahony
 * @version 2.0
 */
public class CategoryUtility {

    /**
     * Gets the current list of valid categories.
     *
     * @return a collection (ArrayList) of all valid categories as strings
     */
    public static ArrayList<String> getCategories() {
        return categories; // new ArrayList<>(categories) -> IntelliJ auto, against spec
    }

    /**
     * Checks whether a given category is valid.
     * A valid category must not be {@code null} or blank, and must match one of the default categories.
     <h4>Valid categories:</h4>
     * <ul>
     *   <li>"Home"</li>
     *   <li>"Work"</li>
     *   <li>"Hobby"</li>
     *   <li>"Holiday"</li>
     *   <li>"College"</li>
     * </ul>
     *
     * Returns {@code true} if the category exists in the default category list, {@code false} otherwise.
     *
     * @param category category to validate
     * @return {@code true} if category is valid, {@code false} otherwise
     */
    public static boolean isValidCategory(String category) {
        if (category == null || category.isBlank()) {
            return false;
        }

        for (String cat : categories) {
            category = categoryFormatter(category);
            if (category.equals(cat)) { // We haven't overridden String implementation
                return true;
            }
        }
        return false;
    }

    /**
     * Formats a category string into the expected form if it matches one of the predefined categories when converted to
     * lowercase. This method was added to ensure categories taken as input from users don't flag as invalid due to
     * capitalisation or trailing/leading whitespace, and is used before validation.
     *
     * <br />If the category parameter is {@code null} or blank or invalid, it returns the category back to the user.
     * This will fail later validation tests.
     * <br />If the category parameter, when trimmed of whitespace and converted to lowercase, matches a predefined
     * category- it is returned in its proper format, either => Home, Work, Hobby, Holiday, or College.
     *
     * @param category the category string to format
     * @return the formatted category string if it matches a known category, the original string otherwise
     */
    public static String categoryFormatter(String category) {
        if ((category == null) || (category.isBlank())) {
            return category;
        }
        category = category.toLowerCase().trim();
        return switch (category) {
            case "home" -> "Home";
            case "work" -> "Work";
            case "hobby" -> "Hobby";
            case "holiday" -> "Holiday";
            case "college" -> "College";
            default -> category;
        };
    }
    /**
     * The {@code ArrayList} collection holding the predefined categories [Home, Work, Hobby, Holiday, College].
     * This collection is final (immutable).
     */
    private static final ArrayList<String> categories = new ArrayList<String>(
            Arrays.asList("Home",
            "Work",
            "Hobby",
            "Holiday",
            "College")); // Geeks4Geeks
}

