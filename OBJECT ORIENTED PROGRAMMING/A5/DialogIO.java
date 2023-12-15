/*
EMMANUELLA EYO
eee917
11291003
CMPT 270
 */


import javax.swing.*;

public class DialogIO extends AbstractDialogIO {

    /**Display a prompt and read the string entered.
     *
     * @param prompt the string to be displayed as a prompt
     * @return the String read
     */
    @Override
    public String readString(String prompt) {
        String inputDialog = JOptionPane.showInputDialog(null, prompt);

        if(!inputDialog.isEmpty()){
            return inputDialog;
        }
        else{
            JOptionPane.showMessageDialog(null, "No input was given.");
            return readString(prompt);
        }
    }


    /**
     * Display a prompt and read the int entered.
     *
     * @param prompt the string to be displayed as a prompt
     * @return the int read
     */
    @Override
    public int readInt(String prompt) {

        int integer;

        try{
            integer = Integer.parseInt(readString(prompt));
            return integer;
        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Input was not a valid integer, try again.");
            return readInt(prompt);
        }
    }

    /**
     * Output the String parameter.
     *
     * @param outString the string whose value is to be displayed
     */
    @Override
    public void outputString(String outString) {
        JOptionPane.showMessageDialog(null, outString + "\n");
    }

    /**Testing
     *
     */
    public static void main(String[] args) {

        DialogIO dialogIO = new DialogIO();

        dialogIO.outputString("Welcome to Silverbird cinemas...");

        int adult = dialogIO.readInt("How many adults are going to watch the movie? ");

        String[] moviesChoice = {"Eternals", "Venom 2", "Flinch", "The Hitman's Wife's Bodyguard","Black and Blue" };
        dialogIO.outputString("What movie are you going to watch today? ");
        int choice = dialogIO.readChoice(moviesChoice);
        dialogIO.outputString("That's a wonderful choice, %s is a great movie".formatted(moviesChoice[choice]));

        String y_or_n = dialogIO.readString("Would you like to buy a popcorn for the movie? (yes/no) ");

        if(y_or_n.equals("yes")){
            dialogIO.outputString("%d popcorn and ticket(s) for %d adults watching %s".formatted(adult, adult, moviesChoice[choice]));
        }
        else {
            dialogIO.outputString("%d ticket(s) for the movie %s".formatted(adult, moviesChoice[choice]));
        }
        dialogIO.outputString("Have a nice time watching the movie.");
    }
}
