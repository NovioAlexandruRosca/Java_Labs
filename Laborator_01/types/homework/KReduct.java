package types.homework;

import java.util.logging.*;
import utility.Utility;

/**
 * The KReduct class provides functionality to validate command-line arguments that are later used to
 * calculate K-reductible values, and print the results along with the running time.
 * It defines methods for argument validation, K-reduction calculation, and addition of digits.
 * 
 * <p> Author: Rosca Alexandru-David /p>
 * <p> Group: A4 </p>
 * <p> Year: 2nd </p>
 */
public class KReduct {

    // used to log everything in the terminal
    private static final Logger logger = Logger.getLogger(KReduct.class.getName());

    /**
     * The main method of the application. Validates the command-line arguments,
     * calculates K-reductible values, and prints the results along with the running time.
     *
     * @param args the command-line arguments containing the range and k value
     */
    public static void main(String[] args) {

        int[] numbers = argumentValidation(args, 3);

        long startTime = System.nanoTime();

        String kReductibleValues = kReductibleValues(numbers);

        displayKReductible(kReductibleValues, numbers);
        displayRunningTime(startTime);
    }

    
    /**
     * Displays the K-reductible values in the given interval.
     *
     * @param kReductibleValues the string containing the K-reductible values
     */
    protected static void displayKReductible(String kReductibleValues, int[] numbers){
        if (kReductibleValues.length() == 0) {
            String message = Utility.textColoring("There are no k-reductible values in the given interval", Utility.ansiEscapeCodes.YELLOW);
            logger.log(Level.WARNING, message);
        } else {
            String message = Utility.textColoring(String.format("All the %d-reductible values in the interval [%d-%d] are: %s"
            , numbers[2], numbers[0], numbers[1], kReductibleValues), Utility.ansiEscapeCodes.GREEN);
            logger.log(Level.INFO, message);
        }
    }

    /**
     * Displays the running time of the application.
     *
     * @param startTime the start time of the application execution
     */
    protected static void displayRunningTime(long startTime){
        long elapsedTimeInNanoseconds = System.nanoTime() - startTime;
        long elapsedTimeInMilliseconds = elapsedTimeInNanoseconds / 1_000_000;

        String message = Utility.textColoring(String.format("Running time of the app is: %d nanoseconds / %d milliseconds", elapsedTimeInNanoseconds, elapsedTimeInMilliseconds), Utility.ansiEscapeCodes.GREEN);
        logger.log(Level.INFO, message);
    }

    /**
     * Validates the command-line arguments and returns an array of integers extracted from the arguments.
     * These arguments are as it follows:
     * The first argument is the lower-bound value of the searched interval,
     * The second argument is the upper-bound value of the searched interval,
     * The third argument represents the value 'k',
     * This method also performs additional validation checks on the arguments.
     *
     * @param args              the command-line arguments containing the range and k value
     * @param numberOfArguments the expected number of arguments
     * @return an array of integers extracted from the arguments
     * @throws CommonException if the arguments are invalid this is thrown
     */
    protected static int[] argumentValidation(String[] args, int numberOfArguments) {
        int[] numbers = new int[numberOfArguments];
        numbers[2] = -1;

        try {

            if (args.length < numberOfArguments) {
                throw new CommonException("You haven't provided enough arguments!");
            } else if (args.length > numberOfArguments) {
                throw new CommonException("You have provided too many arguments!");
            }

            int i = 0;
            for (i = 0; i < numberOfArguments; i++) {
                numbers[i] = Integer.parseInt(args[i]);
            }

            if (numbers[2] <= 0)
                throw new CommonException("k should be a natural number!");
            if (numbers[0] > numbers[1])
                throw new CommonException("The first argument has to be smaller or equal to the second argument!");

            String message = Utility.textColoring("The values you have provided are: " + numbers[0] + ", " + numbers[1] + ", "
            + numbers[2], Utility.ansiEscapeCodes.GREEN);
            logger.log(Level.INFO, message);

            return numbers;

        } catch (NumberFormatException e) {
            String message = Utility.textColoring("One of the argument isn't a number!", Utility.ansiEscapeCodes.RED);
            logger.log(Level.SEVERE, message);
        } catch (CommonException e) {
            String message = Utility.textColoring(e.getMessage(), Utility.ansiEscapeCodes.RED);
            logger.log(Level.SEVERE, message);
        }

        System.exit(0);
        return numbers;
    }

    /**
     * Finds and returns a string containing K-reductible values within a specified range.
     * A K-reductible value is defined as a value that reduces to a specific integer (k) through
     * the process of summing the squares of its digits repeatedly until a single digit is obtained.
     *
     * @param numbers an array of integers where numbers[0] represents the start of the range,
     *                numbers[1] represents the end of the range, and numbers[2] represents the value 'k'
     * @return a string containing K-reductible values within the specified range
     */
    private static String kReductibleValues(int[] numbers) {
        StringBuilder kReductibleValues = new StringBuilder();

        for (int i = numbers[0]; i <= numbers[1]; i++) {

            int value = i;
            do {
                if (value == numbers[2]) {
                    break;
                }

                value = additionOfDigits(Math.abs(value));

            } while (Math.abs(value) > 9);
            if (value == numbers[2]) {
                kReductibleValues.append(i).append(" ");
            }

        }

        return kReductibleValues.toString();

    }

    /**
     * Calculates the sum of the squares of the digits of a given Integer.
     * For example, if the integer is 123, the sum of the squares of its digits
     * would be 1^2 + 2^2 + 3^2 = 14.
     *
     * @param value the integer whose digits' squares sum needs to be calculated
     * @return the sum of the squares of the digits of the given integer
     */
    private static int additionOfDigits(int value) {
        int sum = 0;
        while (value > 0) {
            sum += (value % 10) * (value % 10);
            value = value / 10;
        }
        return sum;
    }
}
