import java.util.NoSuchElementException;

public class AssignManageToStudent implements Command{

    /**
     * Assigns a Manager to a student
     */
    @Override
    public void execute() {
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
}

