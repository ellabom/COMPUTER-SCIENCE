/*
EMMANUELLA EYO
eee917
11291003
CMPT 270
 */

import java.util.ArrayList;

public class DisplayEmptyBeds implements Command{

    /**
     * Display the empty beds in the residence
     */
    @Override
    public void execute() {
        ArrayList<Integer> Availablebeds = ResidenceAccess.getInstance().availableBeds();

        IOAccess.getInstance().outputString("\nThe following beds available: ");
        for(Integer bedLabel: Availablebeds){
            IOAccess.getInstance().outputString("bed: " + bedLabel);
        }
    }
}
