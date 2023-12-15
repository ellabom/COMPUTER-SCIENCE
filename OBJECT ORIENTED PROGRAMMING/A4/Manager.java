//EMMANUELLA EYO
//eee917
//11291003
//CMPT 270

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Manager extends BasicManager {

    private ArrayList<Student> students;

    /**
     * @param name string name for the Manager
     * @param ssn social security number for the Manager
     * @param employedId Manager's employee ID
     */
    public Manager(String name, int ssn, String employedId) {
        super(name, ssn, employedId);
        this.students = new ArrayList<Student>();

        //check if employeeId and name are empty inputs
        if(employedId.equals("") || name.equals("")){
            throw new IllegalArgumentException("Invalid constructor parameters");
        }
    }

    /**
     * Adds a student to the list of the Manager's students
     * @param s the Student to be added to the list
     */
    public void addStudent(Student s){

        //check if this student already exists
        if(hasStudent(s.getNsid())){
            throw new IllegalStateException("%n is already a student assigned to this " +
                    "Manager".formatted(s.getName()));
        }

        this.students.add(s);
    }

    /**
     * Remove a student from the list of this Manager
     * @param NSID the nsid of the student
     */
    public void removeStudent(String NSID){

        //check if this NSID is not associated with any student
        if(!hasStudent(NSID)){
            throw new NoSuchElementException("No student was assigned with this NSID, %n".formatted(NSID));
        }
        students.removeIf(s -> s.getNsid().equals(NSID));

    }

    /**
     * Checks to see if a student has been assigned to this Manager
     * @param NSID the nsid of the student
     */
    public boolean hasStudent(String NSID){

        for(Student s: students){
            if(s.getNsid().equals(NSID)){
                return true;
            }
        }return false;
    }

    /**
     * Returns a string representation of the Manager's List
     */
    public String toString() {

        String to_String = super.toString() + "Students:";
        for (Student s : students) {
            to_String = to_String + "\n-" + s.getName() + "\nNSID: " + s.getNsid();
        }return to_String + "\n";
    }

    public static void main(String[] args){
        int numErrors = 0;

        Manager m = new Manager("Andrew Barber", 123000, "Drew15");

        String student1 = "Kevin Graham";
        String s1NSID = "kvg129";
        String student2 = "Charlotte Jane";
        String s2NSID = "ctj888";

        Student s1 = new Student(student1, 159080, s1NSID);
        Student s2 = new Student(student2, 945861, s2NSID);

        //TEST addStudent() and hasStudent()
        m.addStudent(s1);
        m.addStudent(s2);

        if (!m.hasStudent(s1NSID) || !m.hasStudent(s2NSID)){
            System.out.println("Error in hasStudent(): returned false");
            numErrors++;
        }

        //TEST removeStudent()
        m.removeStudent(s1NSID);
        if(m.hasStudent(s1NSID)){
            System.out.println("Error in removeStudent()");
            numErrors++;
        }

        Student s3 = new Student("Jessica Blue", 12345, "jsb771");
        Student s4 = new Student("Jorja Smith", 53256, "jst000");
        Student s5 = new Student("Emmanuel Eyo", 20201, "eey342");
        m.addStudent(s3); m.addStudent(s4); m.addStudent(s5);

        String expected = "Name: Andrew Barber\n" +
                "SSN: 123000\n" +
                "Employee Id: Drew15\n" +
                "Students:\n" +
                "-Charlotte Jane\n" +
                "NSID: ctj888\n" +
                "-Jessica Blue\n" +
                "NSID: jsb771\n" +
                "-Jorja Smith\n" +
                "NSID: jst000\n" +
                "-Emmanuel Eyo\n" +
                "NSID: eey342";

        if(!m.toString().equals(expected)) {
            System.out.println("Error in toString()");
            numErrors++;
        }


        System.out.println("The number of errors: " + numErrors);
    }
}

