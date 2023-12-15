/*
    EMMANUELLA EYO
    eee917
    11291003
    CMPT 270
 */


import java.util.TreeMap;
import java.util.*;

public class ResidenceSystemA5Q3{
    /**
     * The residence referred to in the Residence system
     */
    private Residence residence;

    /**
     * a collection of students identified by their student NSID
     */
    private Map<String, Student> students;

    /**
     * a collection of managers identified by their employeeID
     */
    private Map<String, Manager> managers;

    /**
     * One ConsoleIO for all methods
     */
    private static ConsoleIO consoleIO = new ConsoleIO();

    /**
     * One DialogIO for all methods
     */
    private static DialogIO dialogIO = new DialogIO();

    /**
     * The constructor method for StudentResidenceSystem
     */
    public ResidenceSystemA5Q3() {

        dialogIO.outputString("Residence Information...");
        String RName = dialogIO.readString("Enter the name of the residence: ");

        int fBedLabel = dialogIO.readInt("Enter the integer label of the first bed: ");

        int lBedLabel = dialogIO.readInt("Enter the integer label for the last bed: ");

        this.residence = new Residence(RName, fBedLabel, lBedLabel);
        this.students = new TreeMap<String, Student>();
        this.managers = new TreeMap<String, Manager>();

    }

    /**
     * Adds a student to the residence system
     *
     */
    public void addStudent () {

        dialogIO.outputString("Adding a Student to the Residence...");
        String name = dialogIO.readString("Enter the name of the student: ");

        int ssn = dialogIO.readInt("Enter the social security number(ssn) for the student: ");

        String NSID = dialogIO.readString("Enter the NSID for student: ");

        if(students.containsKey(NSID)){
            throw new IllegalStateException("Student with NSID %s already exits".formatted(NSID));
        }

        Student student = new Student(name, ssn, NSID);
        //student is identified by their NSID(key)
        students.put(NSID, student);
        dialogIO.outputString("%s has been added to the Residence System.".formatted(name));

    }

    /**
     * Adds a manager to the residence system
     *
     */
    public void addManager(){

        dialogIO.outputString("Adding Manager to the Residence...");
        String Mname = dialogIO.readString("Enter the name of the manager: ");

        int ssn = dialogIO.readInt("Enter the social security number of the manager: ");

        String employeeId = dialogIO.readString("Enter the manager's employeeID: ");

        if(managers.containsKey(employeeId)){
            throw new IllegalStateException("A manager with this employeeID %s already exist.".formatted(employeeId));
        }

        String c_input = dialogIO.readString("Is the Manager a consultant? (Y or N): ");

        Manager manager;
        if(c_input.charAt(0) == 'Y'){
            manager = new Consultant(Mname,ssn, employeeId);
        }
        else{
            manager = new Manager(Mname, ssn, employeeId);
        }

        managers.put(employeeId, manager);
        dialogIO.outputString("%s has been added to the Residence System.".formatted(Mname));
    }

    /**
     * Assigns a Manager to a student and adds student into manager's list
     */
    public void assignManagerToStudent(){

        dialogIO.outputString("Assigning a Manager to a Student...");

        String NSID = dialogIO.readString("Enter the NSID of the student: ");

        //check to make sure a student exist with that NSID
        if(students.get(NSID) == null){
            throw new NoSuchElementException("There is no student with this NSID %s in the student list".formatted(NSID));
        }

        Student s = students.get(NSID);

        String employeeId = dialogIO.readString("Enter the employeeID of the manager: ");

        //check to make sure the manager exist with the employeeId
        if(managers.get(employeeId)==null){
            throw new NoSuchElementException("There is no manager with this employeeID %s in the manager list".
                    formatted(employeeId));
        }

        Manager m = managers.get(employeeId);

        s.addManager(m);
        m.addStudent(s);
        dialogIO.outputString("Student %s has been assigned to %s .".formatted(s.getName(), m.getName()));

    }

    /**
     * Assigns a bed to a student in the residence
     */
    public void assignBed(){

        dialogIO.outputString("Assigning a Student to Bed...");

        String NSID = dialogIO.readString("Enter the NSID of the student: ");

        if(students.get(NSID)==null){
            throw new NoSuchElementException("There is no student with this NSID %s.".formatted(NSID));
        }

        Student stud = students.get(NSID);

        if(students.get(NSID).getBed_label() != -1){
            throw new IllegalStateException("Student has already been assigned a bed.");
        }

        int bedLabel = dialogIO.readInt("Enter the bed number for the student: ");

        if(bedLabel < residence.getMinBedLabel() || bedLabel > residence.getMaxBedLabel()){
            throw new RuntimeException("Bed label is not a valid one, it is out of range %d - %d".formatted(residence.getMinBedLabel(), residence.getMaxBedLabel()));

        }

        stud.setBed_label(bedLabel);
        residence.assignPersonToBed(stud, bedLabel);
        dialogIO.outputString("%s had been assigned to bed %d".formatted(stud.getName(), bedLabel));
    }

    /**
     *  Drop the association between a student and a manager
     */
    public void dropAssociation(){

        dialogIO.outputString("Dropping Student-Manager association...");

        String NSID = dialogIO.readString("Enter the NSID of the student: ");

        if(students.get(NSID)==null){
            throw new NoSuchElementException("There is no student with this NSID %s.".formatted(NSID));
        }
        Student student = students.get(NSID);

        String employeeId = dialogIO.readString("Enter the employeeID of the manager: ");

        //check to make sure the manager exist with the employeeId
        if(managers.get(employeeId)==null){
            throw new NoSuchElementException("There is no manager with this employeeID %s in the manager list".
                    formatted(employeeId));
        }
        Manager manager = managers.get(employeeId);

        student.removeManager(employeeId);
        manager.removeStudent(NSID);
    }

    /**
     * returns the state of the residence system as string format
     */
    public void systemState(){

        dialogIO.outputString(toString());
    }

    /**
     *
     * @return a string representation of the residence system
     */
    public String toString(){

        String rToString = " ";
        if (residence != null){
            rToString = residence.toString();
        }

        String sToString = "\nStudents: ";
        if(students.size() > 0){
            Set<String> skeyset = students.keySet();
            for(String NSID : skeyset){
                sToString += students.get(NSID).toString();
            }
        }

        String mToString = "\nManagers:";
        if(managers.size() > 0){
            Set<String> mkeySet = managers.keySet();
            for(String employeeId: mkeySet){
                mToString += managers.get(employeeId).toString();
            }
        }
        return super.toString() + rToString + sToString + mToString;
    }

    /**
     * display the empty beds in the residence
     */
    public void displayEmptyBeds(){

        ArrayList<Integer> Availablebeds = residence.availableBeds();

        dialogIO.outputString("\nThe following beds available: ");
        for(Integer bedLabel: Availablebeds){
            dialogIO.outputString("bed: " + bedLabel);
        }
    }

    /**
     *  Release a student from the residence
     */
    public void releaseStudents(){

        dialogIO.outputString("Releasing Student from Residence...");

        String NSID = dialogIO.readString("Enter the students NSID: ");

        Student student = students.get(NSID);

        if(student == null){
            throw new NoSuchElementException("There is no student with such an NSID %s".formatted(NSID));
        }

        residence.freeBed(student.getBed_label());
        student.setBed_label(-1);
    }


    public static void main (String[] args) {

        int task = -1;
        dialogIO.outputString("Initializing Residence System... ");
        ResidenceSystemA5Q3 srs = new ResidenceSystemA5Q3();

        String[] task_option = {"Quit",
                "Add a new student",
                "Add a new manager",
                "Assign a manager to a student",
                "Display the empty beds in the residence",
                "Assign a student a bed",
                "Release a student",
                "Drop a manager-student association",
                "Display system state",
        };

        while (task != 0) {
            try {
                task = dialogIO.readChoice(task_option);

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
                    dialogIO.outputString("Invalid input, try again.");
                }
            }catch (RuntimeException e){
                    dialogIO.outputString(e.getMessage());
                }
        }
    }
}
