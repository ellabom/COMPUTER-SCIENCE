/*
EMMANUELLA EYO
eee917
11291003
CMPT 270
 */

import java.util.Set;

public class SystemState implements Command{

    /**
     * Display the system state
     */
    @Override
    public void execute() {

        String result = "";

        String rToString = " ";
        if (ResidenceAccess.getInstance() != null){
            rToString = ResidenceAccess.getInstance().toString();
        }

        String sToString = "\nStudents: ";
        if(StudentMapAccess.getInstance().size() > 0){
            Set<String> studkey = StudentMapAccess.getInstance().keySet();
            for(String NSID : studkey){
                sToString += StudentMapAccess.getInstance().get(NSID).toString();
            }
        }

        String mToString = "\nManagers:";
        if(ManagerMapAccess.getInstance().size() > 0){
            Set<String> manKey = ManagerMapAccess.getInstance().keySet();
            for(String employeeId: manKey){
                mToString += ManagerMapAccess.getInstance().get(employeeId).toString();
            }
        }
        result = rToString + sToString + mToString;
        IOAccess.getInstance().outputString(result);
    }
}
