/*
    EMMANUELLA EYO
    eee917
    11291003
    CMPT 270
 */


import java.util.*;

public class ResidenceSystemA5Q5{

    /**
     * The constructor method for StudentResidenceSystem
     * Initializes an instance of the Residence System
     */
    public ResidenceSystemA5Q5() {


        IOAccess.getInstance().outputString("Residence Information....");

        String RName = IOAccess.getInstance().readString("Enter the name of the residence: ");

        int fBedLabel = IOAccess.getInstance().readInt("Enter the integer label of the first bed: ");

        int lBedLabel = IOAccess.getInstance().readInt("Enter the integer label for the last bed: ");

        //initialize the Residence
        ResidenceAccess.intialize(RName, fBedLabel, lBedLabel);

    }

    /**
     * Adds a student to the dictionary of all the students
     */
    public void addStudent () {
        Command cmd = new AddStudent();
        cmd.execute();
    }

    /**
     * Adds a manager to the dictionary for all Managers
     *
     */
    public void addManager(){
        Command cmd = new AddManager();
        cmd.execute();
    }

    /**
     * Assigns a Manager to a student
     */
    public void assignManagerToStudent(){
        Command cmd = new AssignManageToStudent();
        cmd.execute();
    }

    /**
     * Assigns a bed to a student in the residence
     */
    public void assignBed(){

        Command cmd = new AssignBed();
        cmd.execute();
    }

    /**
     *  Drop the association between a student and a manager
     */
    public void dropAssociation(){
        Command cmd = new DropAssociation();
        cmd.execute();
    }

    /**
     * Displays the system state
     */
    public void systemState(){
        Command cmd = new SystemState();
        cmd.execute();
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

    /**
     * Display the empty beds in the residence
     */
    public void displayEmptyBeds(){

        Command cmd = new DisplayEmptyBeds();
        cmd.execute();
    }

    /**
     *  Release a student from the residence
     */
    public void releaseStudents(){
        Command cmd = new ReleaseStudents();
        cmd.execute();
    }


    public static void main (String[] args) {

        int task = -1;
        ResidenceSystemA5Q5 srs;

        DialogIO consoleIO = new DialogIO();

        String[] IOChoice = {"ConsoleIO", "DialogIO"};

        //Ask what IO is to be used
        int IO = consoleIO.readChoice(IOChoice);

        //Set the IO
        IOAccess.setIO(IO);

        IOAccess.getInstance().outputString("Initializing Residence System... ");

        while (true) {
            // keep trying until the user enters the data correctly
            try {
                srs = new ResidenceSystemA5Q5();
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

        IOAccess.getInstance().outputString("-------Running the system-------");
        while (task != 0) {
            try {
                task = IOAccess.getInstance().readChoice(task_option);

                if(task == 0){srs.systemState();}
                else if(task == 1){srs.addStudent();}
                else if(task == 2){srs.addManager();}
                else if(task == 3){srs.assignManagerToStudent();}
                else if(task == 4){srs.displayEmptyBeds();}
                else if(task == 5){srs.assignBed();}
                else if(task == 6){srs.releaseStudents();}
                else if(task == 7){srs.dropAssociation();}
                else if(task == 8){srs.systemState();}
                else{
                    IOAccess.getInstance().outputString("Invalid input, try again.");
                }
            }catch (RuntimeException e){
                    IOAccess.getInstance().outputString(e.getMessage());
                }
        }
    }
}
