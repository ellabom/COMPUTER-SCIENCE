/*
EMMANUELLA EYO
eee917
11291003
CMPT 270
 */

public class AddManager implements Command {

    /**
     * Adds a Manager to the dictionary of all Managers
     */
    @Override
    public void execute() {


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
    }
}
