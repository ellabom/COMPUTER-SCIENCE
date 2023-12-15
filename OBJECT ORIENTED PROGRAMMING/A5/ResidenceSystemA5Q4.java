/*
    EMMANUELLA EYO
    eee917
    11291003
    CMPT 270
 */


import java.util.*;

public class ResidenceSystemA5Q4{

    /**
     * The constructor method for StudentResidenceSystem
     * Initializes an instance of the Residence System
     */
    public ResidenceSystemA5Q4() {


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

        IOAccess.getInstance().outputString("Adding a Student to the Residence...");
        String name = IOAccess.getInstance().readString("Enter the name of the student: ");

        int ssn = IOAccess.getInstance().readInt("Enter the social security number(ssn) for the student: ");

        String NSID = IOAccess.getInstance().readString("Enter the NSID for student: ");

        if(StudentMapAccess.getInstance().containsKey(NSID)){
            throw new IllegalStateException("Student with NSID %s already exits".formatted(NSID));
        }

        Student student = new Student(name, ssn, NSID);
        StudentMapAccess.getInstance().put(NSID, student);


        IOAccess.getInstance().outputString("%s has been added to the Residence System.".formatted(name));

    }

    /**
     * Adds a manager to the dictionary for all Managers
     *
     */
    public void addManager(){

        IOAccess.getInstance().outputString("Adding Manager to the Residence...");
        String Mname = IOAccess.getInstance().readString("Enter the name of the manager: ");

        int ssn = IOAccess.getInstance().readInt("Enter the social security number of the manager: ");

        String employeeId = IOAccess.getInstance().readString("Enter the manager's employeeID: ");

        if(ManagerMapAccess.getInstance().containsKey(employeeId)){
            throw new IllegalStateException("A manager with this employeeID %s already exist.".formatted(employeeId));
        }

        String consultant = IOAccess.getInstance().readString("Is the Manager a consultant? (Y or N): ");

        Manager manager;
        if(consultant.charAt(0) == 'Y' || consultant.charAt(0) == 'y'){
            manager = new Consultant(Mname,ssn, employeeId);
        }
        else{
            manager = new Manager(Mname, ssn, employeeId);
        }

        Manager sameNumbermanager = ManagerMapAccess.getInstance().put(employeeId, manager);

        if(sameNumbermanager != null) {
            ManagerMapAccess.getInstance().put(Mname, manager);
            throw new IllegalStateException("Employee number in the manager dictionary even though "
                    + "containsKey failed.  Manager " + Mname + " not entered.");
        }

        IOAccess.getInstance().outputString("%s has been added to the Residence System.".formatted(Mname));
    }

    /**
     * Assigns a Manager to a student
     */
    public void assignManagerToStudent(){

        IOAccess.getInstance().outputString("-------Assigning a new Manager-Student Association-------");

        String NSID = IOAccess.getInstance().readString("Enter the NSID of the student: ");

        //check to make sure a student exist with that NSID
        Student s = StudentMapAccess.getInstance().get(NSID);
        if(s == null){
            throw new NoSuchElementException("There is no student with this NSID %s in the student list".formatted(NSID));
        }

        String employeeId = IOAccess.getInstance().readString("Enter the employeeID of the manager: ");

        Manager m = ManagerMapAccess.getInstance().get(employeeId);
        //check to make sure the manager exist with the employeeId
        if(m == null){
            throw new NoSuchElementException("There is no manager with this employeeID %s in the manager list".
                    formatted(employeeId));
        }else {
            s.addManager(m);
            m.addStudent(s);
        }
        IOAccess.getInstance().outputString("Student %s has been assigned to %s .".formatted(s.getName(), m.getName()));

    }


    /**
     * Assigns a bed to a student in the residence
     */
    public void assignBed(){

        IOAccess.getInstance().outputString("Assigning a Student to Bed...");

        String NSID = IOAccess.getInstance().readString("Enter the NSID of the student: ");

        Student s = StudentMapAccess.getInstance().get(NSID);
        if(s == null){
            throw new NoSuchElementException("There is no student with this NSID %s.".formatted(NSID));
        }


        if(s.getBed_label() != -1){
            throw new IllegalStateException("Student has already been assigned a bed.");
        }

        int bedLabel = IOAccess.getInstance().readInt("Enter the bed number for the student: ");

        if(bedLabel < ResidenceAccess.getInstance().getMinBedLabel() || bedLabel > ResidenceAccess.getInstance().getMaxBedLabel()){
            throw new RuntimeException("Bed label is not a valid one, it is out of range %d - %d".formatted(ResidenceAccess.getInstance().getMinBedLabel(),
                    ResidenceAccess.getInstance().getMaxBedLabel()));

        }

        s.setBed_label(bedLabel);
        ResidenceAccess.getInstance().assignPersonToBed(s, bedLabel);
        IOAccess.getInstance().outputString("%s had been assigned to bed %d".formatted(s.getName(), bedLabel));

    }

    /**
     *  Drop the association between a student and a manager
     */
    public void dropAssociation(){

        IOAccess.getInstance().outputString("Dropping Student-Manager association...");

        String NSID = IOAccess.getInstance().readString("Enter the NSID of the student: ");

        Student s = StudentMapAccess.getInstance().get(NSID);
        if(s == null){
            throw new NoSuchElementException("There is no student with this NSID %s.".formatted(NSID));
        }

        String employeeId = IOAccess.getInstance().readString("Enter the employeeID of the manager: ");

        Manager m = ManagerMapAccess.getInstance().get(employeeId);
        //check to make sure the manager exist with the employeeId
        if(m == null){
            throw new NoSuchElementException("There is no manager with this employeeID %s in the manager list".
                    formatted(employeeId));
        }

        //check of the manager records the student
        if(!m.hasStudent(NSID)){
            throw new IllegalStateException("This manager is not associated with this student.");
        }

        if(!s.hasManager(employeeId)){
            throw new IllegalStateException("This manager and this student are incorrectly associated" +
                    ". The manager has the student, but the student does not have the manager.");
        }

        s.removeManager(employeeId);
        m.removeStudent(NSID);
    }

    /**
     * Displays the system state
     */
    public void systemState(){

        IOAccess.getInstance().outputString(this.toString());
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

        ArrayList<Integer> Availablebeds = ResidenceAccess.getInstance().availableBeds();

        IOAccess.getInstance().outputString("\nThe following beds available: ");
        for(Integer bedLabel: Availablebeds){
            IOAccess.getInstance().outputString("bed: " + bedLabel);
        }
    }

    /**
     *  Release a student from the residence
     */
    public void releaseStudents(){

        IOAccess.getInstance().outputString("Releasing Student from Residence...");

        String NSID = IOAccess.getInstance().readString("Enter the students NSID: ");

        Student student = StudentMapAccess.getInstance().get(NSID);

        if(student == null){
            throw new NoSuchElementException("There is no student with such an NSID %s".formatted(NSID));
        }

        if(student.getBed_label() == -1){
            throw new IllegalStateException("Student " + student + "is currently not in a bed" +
                    ",so they cannot be released.");
        }

        ResidenceAccess.getInstance().freeBed(student.getBed_label());
        student.setBed_label(-1);
    }


    public static void main (String[] args) {

        int task = -1;
        ResidenceSystemA5Q4 srs;

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
                srs = new ResidenceSystemA5Q4();
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
