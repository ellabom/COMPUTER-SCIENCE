/*
    EMMANUELLA EYO
    eee917
    11291003
    CMPT 270
 */


import java.util.*;

public class ResidenceSystemA5Q6{

    Command[] commands = new Command[9];

    /**
     * The constructor method for StudentResidenceSystem
     * Initializes an instance of the Residence System
     */
    public ResidenceSystemA5Q6() {


        IOAccess.getInstance().outputString("Residence Information....");

        String RName = IOAccess.getInstance().readString("Enter the name of the residence: ");

        int fBedLabel = IOAccess.getInstance().readInt("Enter the integer label of the first bed: ");

        int lBedLabel = IOAccess.getInstance().readInt("Enter the integer label for the last bed: ");

        //initialize the Residence
        ResidenceAccess.intialize(RName, fBedLabel, lBedLabel);

    }


    /**
     *
     * @return a string representation of the residence system
     */
    public String toString(){

        String rToString = " ";
        if (ResidenceAccess.getInstance() != null){
            rToString = ResidenceAccess.getInstance().toString();
        }

        String sToString = "\nStudents: ";
        if(StudentMapAccess.getInstance().size() > 0){
            Set<String> skeyset = StudentMapAccess.getInstance().keySet();
            for(String NSID : skeyset){
                sToString += StudentMapAccess.getInstance().get(NSID).toString();
            }
        }

        String mToString = "\nManagers:";
        if(ManagerMapAccess.getInstance().size() > 0){
            Set<String> mkeySet = ManagerMapAccess.getInstance().keySet();
            for(String employeeId: mkeySet){
                mToString += ManagerMapAccess.getInstance().get(employeeId).toString();
            }
        }
        return super.toString() + rToString + sToString + mToString;
    }

    public static void main (String[] args) {

        int task = -1;
        ResidenceSystemA5Q6 srs;

        ConsoleIO consoleIO = new ConsoleIO();

        String[] IOChoice = {"ConsoleIO", "DialogIO"};

        //Ask what IO is to be used
        int IO = consoleIO.readChoice(IOChoice);

        //Set the IO
        IOAccess.setIO(IO);

        IOAccess.getInstance().outputString("Initializing Residence System... ");

        while (true) {
            // keep trying until the user enters the data correctly
            try {
                srs = new ResidenceSystemA5Q6();
                break;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

        String[] task_option = {"0: Quit",
                "1: Add a new student",
                "2: Add a new manager",
                "3: Assign a manager to a student",
                "4: Display the empty beds in the residence",
                "5: Assign a student a bed",
                "6: Release a student",
                "7: Drop a manager-student association",
                "8: Display system state",
        };

        srs.commands[0] = new SystemState();
        srs.commands[1] = new AddStudent();
        srs.commands[2] = new AddManager();
        srs.commands[3] = new AssignManageToStudent();
        srs.commands[4] = new DisplayEmptyBeds();
        srs.commands[5] = new AssignBed();
        srs.commands[6] = new ReleaseStudents();
        srs.commands[7] = new DropAssociation();
        srs.commands[8] = new SystemState();

        IOAccess.getInstance().outputString("-------Running the system-------");
        while (task != 0) {
            try {
                task = IOAccess.getInstance().readChoice(task_option);
                srs.commands[task].execute();

            }catch (RuntimeException e){
                    IOAccess.getInstance().outputString(e.getMessage());
                }
        }
    }
}
