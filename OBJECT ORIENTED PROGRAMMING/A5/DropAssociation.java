/*
EMMANUELLA EYO
eee917
11291003
CMPT 270
 */

import java.util.NoSuchElementException;

public class DropAssociation implements Command{

    /**
     *Drop the association between a student and a manager
     */
    @Override
    public void execute() {

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
}

