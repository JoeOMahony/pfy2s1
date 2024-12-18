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

public class CategoryUtility {

    public static ArrayList<String> getCategories() {
        return categories; // new ArrayList<>(categories) -> IntelliJ auto, against spec
    }

    public static boolean isValidCategory(String category) {
        if (category == null || category.isBlank()) {
            return false;
        }

        for (String cat : categories) {
            if (category.equals(cat)) { // We haven't overridden String implementation
                return true;
            }
        }

        return false;
    }

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

    private static final ArrayList<String> categories = new ArrayList<String>(
            Arrays.asList("Home",
            "Work",
            "Hobby",
            "Holiday",
            "College")); // Geeks4Geeks
}

