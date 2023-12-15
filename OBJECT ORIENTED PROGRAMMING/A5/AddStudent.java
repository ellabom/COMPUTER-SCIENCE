/*
EMMANUELLA EYO
eee917
11291003
CMPT 270
 */
public class AddStudent implements Command{

    /**
     * Add a student to the dictionary of all students
     */
    @Override
    public void execute() {

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
}
