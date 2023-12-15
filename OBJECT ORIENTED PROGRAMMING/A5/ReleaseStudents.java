/*
EMMANUELLA EYO
eee917
11291003
CMPT 270
 */

import java.util.NoSuchElementException;

public class ReleaseStudents implements Command{

    /**
     * Release a Student from Residence
     */
    @Override
    public void execute() {

        IOAccess.getInstance().outputString("Releasing Student from Residence...");

        String NSID = IOAccess.getInstance().readString("Enter the students NSID: ");

        Student student = StudentMapAccess.getInstance().get(NSID);

        if(student == null){
            throw new NoSuchElementException("There is no student with such an NSID %s".formatted(NSID));
        }

        if(student.getBed_label() == -1){
            throw new IllegalStateException("Student " + student.getName() + " is currently not in a bed" +
                    ",so they cannot be released.");
        }

        ResidenceAccess.getInstance().freeBed(student.getBed_label());
        student.setBed_label(-1);
    }

}
