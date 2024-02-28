package types.compulsory;

/**
 * Prints a specific language from a predefined list of programming languages
 */
public class LanguageRandom{
    // Author: Rosca Alexandru-David 
    // Group: A4
    // Year: 2nd
    private static String[] languages = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};

    /**
     * Kick-starts the app, it generates a random number that suffers modification then it's used 
     * to print a programming language from a specific index, the value of the calculated number
     * is between [0 - 9] so no testing is necessary to be done over it
     * 
     * @param args they arent used but are neccesary to run the function
     */
    public static void main(String[] args){

        int number = magicalCalculations();

        System.out.printf("Willy-nilly, this semester I will learn " + languages[number]);
    } 
    
    /**
     * It generates a random Integer between [0 - 999_999]
     * @return a random Integer between[0 - 999_999]
     */
    private static int randomNumberGenerator(){
        int number = (int) (Math.random() * 1_000_000);
        return number;
    }


    /**
     * It generates a random number using the defined method randomNumberGenerator,
     * it modifies it by adding and multiplying different values the it's returning it
     * for further use
     * @return a random Interger that has suffered changes 
     */
    public static int magicalCalculations()
    {
        int number = randomNumberGenerator();

        number *= 3;

        number += Integer.parseInt("10101", 2);
        number += Integer.parseInt("FF", 16);

        number *= 6;

        number = sumOfDigits(number);
        return number;
    }

    /**
     * Calculates the sum of all the digits of the number,
     * it repeats the process on the computed sum until it becames < 10,
     * 
     * @param number a number that's gonna be used to
     * @return the control digit of the given number
     */
    private static int sumOfDigits(int number) {
        while (number > 9) {
            int sum = 0;
            while (number != 0) {
                sum += number % 10;
                number /= 10;
            }
            number = sum;
        }
        return number;
    }

}
 
