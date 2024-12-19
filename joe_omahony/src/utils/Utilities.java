/*

Provided by assignment spec

The ShopV5.0 Utilities code is here and the class structure is:

You can choose to add additional validation methods to this
 class should you wish.

Regardless, just add the code above to your project,
into your utils package and use it like we have been doing
 in labs. Refer to ShopV5.0 to see it in action as well as
 Topic05 materials.
 */

package utils;
/**
 * The responsibility of the {@code Utilities} class is to provide utility methods used throughout the application.
 *
 * <h4>Purpose</h4>
 * <ul>
 *     <li>Truncating and validating string lengths.</li>
 *     <li>Converting boolean values to/from specific formats.</li>
 *     <li>Ensuring numeric values fall within certain ranges.</li>
 *     <li>Prompting the user to try an action again in case of invalid input, improving user experience.</li>
 * </ul>
 * <b>This class was provided in the assignment spec, with the exception of the {@code tryAgain()} method.</b>
 *
 * @author Dave Hearne, Joe O'Mahony
 * @version 1.1
 */
public class Utilities {

    /**
     * This method takes in a decimal point number and truncates it to two decimal places.  Note
     * that the method does NOT round when truncating; the numbers after the two decimal places are
     * just removed.
     * The method does the truncating in this manner:
     *  - multiply the number by 100 e.g. 16.543235523 * 100 = 1654.3235523
     *  - cast the multiplied number as an in e.g. 1654.3235523 = 1654
     *  - finally, the multiplied and casted number is divided by 100 and returned e.g. 1654 = 16.54
     *
     * @param number  Number to be truncated to two decimal places
     * @return  the number, passed as a parameter, truncated to two decimal places (note: not rounded)
     */
    public static double toTwoDecimalPlaces(double number){
        return (int) (number * 100 ) / 100.0;
    }

    /**
     * This method returns true if the charToConvert value is Y or y. Returns false in all other cases.
     *
     * @param charToConvert The char value that will be used to determine true/false.
     * @return Returns true if the charToConvert value is Y or y. Returns false otherwise.
     */
    public static boolean YNtoBoolean(char charToConvert){
        return ((charToConvert == 'y') || (charToConvert == 'Y'));
    }

    /**
     * This method returns Y if the booleanToConvert value is true. Returns N otherwise.
     *
     * @param booleanToConvert The boolean value that will be used to determine Y/N
     * @return Returns Y if the booleanToConvert value is true. Returns N otherwise.
     */
    public static char booleanToYN(boolean booleanToConvert){
        return booleanToConvert ? 'Y' : 'N';
    }


    /**
     *  This method returns a string that was passed as a parameter, truncated to a specific length, also
     *  passed as a parameter.  If the original String is less than the passed length, then the original string
     *  is just returned.
     *
     * @param stringToTruncate  The string that will be truncated to a specific length
     * @param length The length to which to truncate the string to
     * @return The string truncated to a specific length
     */
    public static String truncateString(String stringToTruncate, int length){
        if (stringToTruncate.length() <= length) {
            return stringToTruncate;
        }
        else{
            return stringToTruncate.substring(0, length);
        }
    }


    /**
     * This method takes in a string, passed as a parameter and valdiates whether it is less than or equal to
     * a specific length or not.
     *
     * @param strToCheck The string that will be checked to see if it is a specific length
     * @param maxLength The length that the string will be validated against
     * @return True if the string is less than or equal the max length and false otherwise.
     */
    public static boolean validateStringLength(String strToCheck, int maxLength){
        return strToCheck.length() <= maxLength;
    }


    /**
     * This method returns true if the numberToCheck is between min and max (both inclusive)
     *
     * @param numberToCheck The number whose range is being checked.
     * @param min The minimum range number to check against (inclusive)
     * @param max The maximum range number to check against (inclusive)
     * @return Returns true if the numberToCheck is between min and max (both inclusive), false otherwise.
     */
    public static boolean validRange(int numberToCheck, int min, int max) {
        return ((numberToCheck >= min) && (numberToCheck <= max));
    }

    /**
     * Prompts the user to try again, then converts the user's response to a boolean value.
     * {@link main.Driver} uses the Boolean response to do something, i.e. call the method from which it was invoked
     * again to give the user a chance to re-enter/correct details. This method was added to avoid the user needing to
     * go through the menu process again, including re-displaying the menu, when an input is deemed invalid.
     * <br /><br />
     * This method:
     * <ul>
     *   <li>Asks the user: "Would you like to try again? [Y/N] => "</li>
     *   <li>Reads the user’s char input ("Y" or "y", case-insensitive due to
     *   {@link ScannerInput readNextChar(char)} implementation).</li>
     *   <li>Converts the character to a boolean:
     *       <ul>
     *          <li>"Y" or "y" => {@code true}</li>
     *          <li>"N" or "n" => {@code false}</li>
     *       </ul>
     *   </li>
     * </ul>
     * Any character input other than "Y"/"y"/"N"/"n" is handled by {@link ScannerInput readNextChar(char)}. This will
     * ask the user to re-input the character if it's not valid.
     *
     * @return {@code true} if the user enters "Y" or "y", {@code false} if the user enters "N" or "n" or any other character.
     * @author Joe O'Mahony
     */
    public static boolean tryAgain() {
        char ynTryAgainChar = ScannerInput.readNextChar("Would you like to try again? [Y/N] => ");
        return YNtoBoolean(ynTryAgainChar);
    }

}