/*
EMMANUELLA EYO
eee917
11291003
CMPT 270
 */

import java.util.NoSuchElementException;

public class AssignBed implements Command{

    /**
     *Assigns a student to a bed
     */
    @Override
    public void execute() {

        IOAccess.getInstance().outputString("Assigning a Student to Bed...");

        String NSID = IOAccess.getInstance().readString("Enter the NSID of the student: ");

        Student s = StudentMapAccess.getInstance().get(NSID);
        if(s == null){
            throw new NoSuchElementException("There is no student with this NSID %s.".formatted(NSID));
        }


        if(s.getBed_label() != -1){
            throw new IllegalStateException("Student has already been assigned a bed.");
        }

        int bedLabel = IOAccess.getInstance().readInt("Enter the bed number for the student: ");

        if(bedLabel < ResidenceAccess.getInstance().getMinBedLabel() || bedLabel > ResidenceAccess.getInstance().getMaxBedLabel()){
            throw new RuntimeException("Bed label is not a valid one, it is out of range %d - %d".formatted(ResidenceAccess.getInstance().getMinBedLabel(),
                    ResidenceAccess.getInstance().getMaxBedLabel()));

        }

        s.setBed_label(bedLabel);
        ResidenceAccess.getInstance().assignPersonToBed(s, bedLabel);
        IOAccess.getInstance().outputString("%s had been assigned to bed %d".formatted(s.getName(), bedLabel));

    }
}
