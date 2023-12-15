//Emmanuella Eyo eee917 11291003 CMPT270

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleIO implements InputOutputInterface{

    private Scanner input = new Scanner(System.in);


    /**
     *  Display a prompt to the console and read strings
     * @param prompt the string to be displayed as a prompt
     * @return the String read
     */
    @Override
    public String readString(String prompt) {

        System.out.println(prompt);

        return input.nextLine();
    }


    /**
     * Displays the prompt and reads integer
     * @param prompt the string to be displayed as a prompt
     *
     * @return the int read
     */
    @Override
    public int readInt(String prompt) {

        int integer;
        boolean readInteger;

        do {

            readInteger = true;
            try {
                System.out.println(prompt);
                integer = input.nextInt();

            } catch (InputMismatchException e) {
                readInteger = false;
                input.nextLine();
                System.out.println("Integer was not given...Give an integer.");
                return readInt(prompt);
            }
        }while(!readInteger);
        input.nextLine();
        return integer;
    }

    /**Display a list of choices/options and read the integer
     * representing the choice
     *
     * @param options an array with the options that are presented to the user
     * @return the int specifying the array index for the option selected by the user
     */
    @Override
    public int readChoice(String[] options) {
        String option = "";

        for(String opt :options){
            option += opt + "\n";
        }
        option += "Enter a valid input: ";
        int value = readInt(option);
        if(value < 0 || value > options.length){
            System.out.printf("You gave an input that is not between 0 and %d, try again.%n", value);
            return readChoice(options);
        }
        else {return value;}
    }

    /**Output the String parameter.
     *
     * @param outString the string whose value is to be displayed
     */
    @Override
    public void outputString(String outString) {
        System.out.println(outString);

    }

    public static void main(String[] args){

        ConsoleIO consoleIO = new ConsoleIO();

        consoleIO.outputString("Welcome to Silverbird Cinemas! What movie would you like to watch? ");
        consoleIO.readString("Enter the movie name: ");
        int Adult = consoleIO.readInt("How many adults are going to watch?: ");

        int child = 0;
        String[] choice = {"YES", "NO"};
        consoleIO.outputString("Are they going to be children? (if yes enter 0, else 1):");
        int answer = consoleIO.readChoice(choice);
        if(answer == 0) {
            child = consoleIO.readInt("How many children are going to watch?: ");
        }

        String[] time = {"1","2", "3", "4", "5", "6"};
        consoleIO.outputString("Please choose from(0-5) to choose a movie time..");
        int movieTime = consoleIO.readChoice(time);

        consoleIO.outputString("Your movie is by %s:00pm, with %d adults and %d child/children.".formatted(time[movieTime], Adult, child));
        consoleIO.outputString("Thank you for using Silverbird Cinemas.");
    }
}
