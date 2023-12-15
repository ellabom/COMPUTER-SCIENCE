//EMMANUELLA EYO
//eee917
//11291003
//CMPT 270

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Model of a student who has a bed label, nsid and a manager/managers
 */
public class Student extends Person {

    private int bed_label;
    private String nsid;
    private int ssn;
    private ArrayList<Manager> managers;


    /**
     * Initialize an instance of a Person with the given name and health number.
     *
     * @param name the person's name
     * @param ssn   the person's social Insurance number
     * @param NSID:  the person's NSID
     */
    public Student(String name, int ssn, String NSID) {
        super(name, ssn);
        this.nsid = NSID;
        this.bed_label = -1;
        this.managers = new ArrayList<Manager>();

        if(NSID.equals("") || name.equals("")){
            throw new IllegalArgumentException("Invalid constructor parameters");
        }
    }

    /**
     * Return the nsid of the student
     *
     * @return the nsid of the student
     */
    public String getNsid() {
        return nsid;
    }

    /**
     * Return the bed label of the student
     *
     * @return the bed label of the student
     */
    public int getBed_label() {
        return bed_label;
    }

    /**
     * Change the number for the bed label
     *
     * @param bed_label the bed label of the person
     */
    public void setBed_label(int bed_label) {
        this.bed_label = bed_label;
    }

    /**
     * Adds a manager to the list of managers for this student
     * @param m the new manager to be added to the list of managers
     */
    public void addManager(Manager m){

        //check if this manager is already in the manager list
        //throw an error then

        if(hasManager(m.getEmployeeId())){
            throw new IllegalStateException("%n is already a manager for this student".formatted(m.getName()));
        }

        this.managers.add(m);
    }

    /** Remove the manager using the employeeID parameter from the manager's list
     *
     * @param employeeId  the employee ID of the manager to be removed
     */
    public void removeManager(String employeeId){

        //check if the manager exists in the manager list, if not throw an exception
        if(!hasManager(employeeId)){
             throw new NoSuchElementException(("No manager with this employeeID is assigned" +
                     "to this student").formatted(employeeId));
        }
        managers.removeIf(m -> m.getEmployeeId().equals(employeeId));
    }

    /** Checks if this student has the manager with the specified employeeID
     * @param employeeId  employeeID of the manager
     * @return boolean value indicating whether the manager is assigned to this student
     */
    public boolean hasManager(String employeeId){

        for(Manager m : managers){
            if(m.getEmployeeId().equals(employeeId)){
                return true;
            }
        }return false;
    }

    /** Returns a string representation for this student
     * @return a string representation
     */
    public String toString(){

        String to_string = super.toString() + "bed label: " + bed_label + "\nManager:";

        for(Manager m : managers){
            to_string = to_string + "\n-" + m.getName() ;
        }
        return to_string + "\n";
    }


    public static void main(String[] args){
        int numErrors = 0;

        Student stud = new Student("Jessica Blue", 12345,"jsb771");

        //Test getNsid()
        if(!stud.getNsid().equals("jsb771")){
            System.out.println("Error in getNsid()");
            numErrors++;
        }

        //Test getBed_label()
        if(stud.getBed_label() != -1){
            System.out.println("Error in getBed_label()");
            numErrors++;
        }
        //Test setBed_label()
        stud.setBed_label(15);
        if(stud.getBed_label() == -1){
            System.out.println("Error in setBed_label()");
            numErrors++;
        }

        //Test addManager() and hasManager()
        Manager m = new Manager("Andrew Barber",123000, "Drew15");
        stud.addManager(m);

        if(!stud.hasManager("Drew15")){
            System.out.println("Error in addManager() and hasManger");
            numErrors++;
        }

        Manager m1 = new Manager("Chris Rogers", 33490, "Crog89");
        Manager m2 = new Manager("Tony Stark", 11156, "Iron45");
        Manager m3 = new Manager("Bruce Banner", 22332, "Hulk11");

        stud.addManager(m1); stud.addManager(m2); stud.addManager(m3);
        stud.removeManager("Drew15");

        if(stud.hasManager("Drew15")){
            System.out.println("Error in removeManager()");
            numErrors++;
        }

        String expected = """
                Name: Jessica Blue
                SSN: 12345
                bed label: 15
                Manager:
                -Chris Rogers
                -Tony Stark
                -Bruce Banner""";

        if(expected.equals(stud.toString())){
            System.out.println("Error with toString()");
            numErrors++;
        }

        if (numErrors == 0) {
            System.out.println("Number of errors: " + numErrors);
            System.out.println("TEST COMPLETED!!!!");
        }
    }
}
