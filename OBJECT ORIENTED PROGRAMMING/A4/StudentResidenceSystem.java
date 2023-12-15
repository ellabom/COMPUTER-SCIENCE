/*
    EMMANUELLA EYO
    eee917
    11291003
    CMPT 270
 */


import java.util.Scanner;
import java.util.TreeMap;
import java.util.*;

public class StudentResidenceSystem {
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
     * The constructor method for StudentResidenceSystem
     */
    public StudentResidenceSystem() {
        Scanner input = new Scanner(System.in);


        System.out.print("Enter the name of the residence: ");
        String RName = input.nextLine();

        System.out.print("Enter the first bed label: ");
        int fBedLabel = input.nextInt();
        input.nextLine();

        System.out.print("Enter the last bed label: ");
        int lBedLabel = input.nextInt();
        input.nextLine();

        this.residence = new Residence(RName, fBedLabel, lBedLabel);
        this.students = new TreeMap<String, Student>();
        this.managers = new TreeMap<String, Manager>();
    }

    /**
     * Adds a student to the residence system
     *
     */
    public void addStudent () {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter the name of the student: ");
        String name = input.nextLine();

        System.out.print("Enter the social security number(ssn) for the student: ");
        int ssn = input.nextInt();
        input.nextLine();

        System.out.print("Enter the NSID for student: ");
        String NSID = input.nextLine();

        if(students.containsKey(NSID)){
            throw new IllegalStateException("Student with NSID %s already exits".formatted(NSID));
        }

        Student student = new Student(name, ssn, NSID);
        //student is identified by their NSID(key)
        students.put(NSID, student);

    }

    /**
     * Adds a manager to the residence system
     *
     */
    public void addManager(){

        Scanner input = new Scanner(System.in);

        System.out.print("Enter the name of the manager: ");
        String Mname = input.nextLine();

        System.out.print("Enter the social security number of the manager: ");
        int ssn = input.nextInt();
        input.nextLine();

        System.out.print("Enter the manager's employeeID: ");
        String employeeId = input.nextLine();

        if(managers.containsKey(employeeId)){
            throw new IllegalStateException("A manager with this employeeID %s already exist.".formatted(employeeId));
        }

        System.out.print("Is the Manager a consultant? (Y or N): ");
        String c_input = input.nextLine();

        Manager manager;
        if(c_input.charAt(0) == 'Y'){
            manager = new Consultant(Mname,ssn, employeeId);
        }
        else{
            manager = new Manager(Mname, ssn, employeeId);
        }

        managers.put(employeeId, manager);
    }

    /**
     * Assigns a Manager to a student and adds student into manager's list
     */
    public void assignManagerToStudent(){

        Scanner input = new Scanner(System.in);

        System.out.println("Enter the NSID of the student: ");
        String NSID = input.nextLine();

        //check to make sure a student exist with that NSID
        if(students.get(NSID) == null){
            throw new NoSuchElementException("There is no student with this NSID %s in the student list".formatted(NSID));
        }

        Student s = students.get(NSID);

        System.out.print("Enter the employeeID of the manager: ");
        String employeeId = input.nextLine();

        //check to make sure the manager exist with the employeeId
        if(managers.get(employeeId)==null){
            throw new NoSuchElementException("There is no manager with this employeeID %s in the manager list".
                    formatted(employeeId));
        }

        Manager m = managers.get(employeeId);

        s.addManager(m);
        m.addStudent(s);

    }

    /**
     * Assigns a bed to a student in the residence
     */
    public void assignBed(){

        Scanner input = new Scanner(System.in);

        System.out.print("Enter the NSID of the student: ");
        String NSID = input.nextLine();

        if(students.get(NSID)==null){
            throw new NoSuchElementException("There is no student with this NSID %s.".formatted(NSID));

        }

        Student s = students.get(NSID);

        if(students.get(NSID).getBed_label() != -1){
            throw new IllegalStateException("Student has already been assigned a bed.");
        }
        System.out.print("Enter the bed number for the student: ");
        int bedLabel = input.nextInt();
        input.nextLine();

        if(bedLabel < residence.getMinBedLabel() || bedLabel > residence.getMaxBedLabel()){
            throw new RuntimeException("Bed label is not a valid one, it is out of range %d - %d".formatted(residence.getMinBedLabel(), residence.getMaxBedLabel()));

        }

        s.setBed_label(bedLabel);
        residence.assignPersonToBed(s, bedLabel);
    }

    /**
     *  Drop the association between a student and a manager
     */
    public void dropAssociation(){

        Scanner input = new Scanner(System.in);

        System.out.print("Enter the NSID of the student: ");
        String NSID = input.nextLine();

        if(students.get(NSID)==null){
            throw new NoSuchElementException("There is no student with this NSID %s.".formatted(NSID));

        }

        Student student = students.get(NSID);

        System.out.print("Enter the employeeID of the manager: ");
        String employeeId = input.nextLine();

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

        System.out.println(toString());
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
        System.out.println("...................");
    }

    /**
     *  Release a student from the residence
     */
    public void releaseStudents(){
        System.out.println("......................");
    }


    public static void main (String[] args) {

        Scanner input = new Scanner(System.in);
        int task = -1;

        StudentResidenceSystem srs = new StudentResidenceSystem();

        try {
            while (task != 1) {
                System.out.print("""
                        Select an operation to do:\s
                        1: Quit
                        2: Add a new student
                        3: Add a new manager
                        4: Assign a manager to a student
                        5: Display the empty beds in the residence
                        6: Assign a student a bed
                        7: Release a student
                        8: Drop a manager-student association
                        9: Display system state
                        Enter the number selection:\s""");
                task = input.nextInt();
                input.nextLine();

                if(task == 1){srs.systemState();}
                else if(task == 2){srs.addStudent();}
                else if(task == 3){srs.addManager();}
                else if(task == 4){srs.assignManagerToStudent();}
                else if(task == 5){srs.displayEmptyBeds();}
                else if(task == 6){srs.assignBed();}
                else if(task == 7){srs.releaseStudents();}
                else if(task == 8){srs.dropAssociation();}
                else if(task == 9){srs.systemState();}
                else{
                    System.out.println("Enter a valid input.");
                }

            }
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }
}
