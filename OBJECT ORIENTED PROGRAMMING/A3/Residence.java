package Package;//EMMANUELLA EYO
//eee917
//11291003
//270


public class Residence {

    private String Rname;
    private int first_bed;
    private int last_bed;
    private Person[] bed;

    public Residence(String rname, int f_bed, int l_bed) {
        this.Rname = rname;
        this.first_bed = f_bed;
        this.last_bed = l_bed;
        this.bed = new Person[last_bed - first_bed + 1];
    }

    public String getRname() {
        return Rname;
    }

    public int getFirst_bed() {
        return first_bed;
    }

    public int getLast_bed() {
        return last_bed;
    }

    public int labelToindex(int label) {

        return label - first_bed;
    }

    public int indexTolabel(int index) {

        return index + first_bed;
    }

    public boolean isBedoccupied(int label) {

        return bed[labelToindex(label)] != null;
    }

    public Person getPerson(int label) {

        return bed[labelToindex(label)];
    }

    public void assignPerson(int label, Person person) {
        //check if a bed is occupied

        if (!isBedoccupied(label)) {
            this.bed[labelToindex(label)] = person;
        } else {
            System.out.println("Bed not assigned.");
        }
    }

    public String toString() {
        String to_string = "Package.Residence %s contains %d beds \n"
                .formatted(Rname, bed.length);
        for (int index = 0; index < bed.length; index++) {
            if (bed[index] != null) {
                to_string = to_string + "bed #" +indexTolabel(index) + " with "
                        + getPerson(indexTolabel(index)) + " as a resident.\n";
            }
            else {
                to_string = to_string + "bed #" + indexTolabel(index) + "\n";
            }
        }
        return to_string;
    }

    public static void main(String[] args) {

        Residence R = new Residence("Spruce Hall", 50, 75);
        if (!R.getRname().equals("Spruce Hall")) {
            System.out.println("Error found in the getRname() method");
        }
        if (R.getFirst_bed() != 50) {
            System.out.println("Error found in the gerFirst_bed() method ");
        }
        if (R.getLast_bed() != 75) {
            System.out.println("Error found in the getLast_bed() method");
        }
        if (R.labelToindex(75) != 25 || R.labelToindex(50) != 0) {
            System.out.println("Error found in the labelToindex() method");
        }
        if (R.indexTolabel(3) != 53) {
            System.out.println("Error found in the indexTolabel() method");
        }
        if (R.isBedoccupied(50)) {
            System.out.println("Error found in isBedoccupied() method");
        }
        Person d = new Person("Randy", "Cunningham", 123456789);
        R.assignPerson(66, d);

        if (!R.getPerson(66).toString().equals("Randy Cunningham")) {
            System.out.println("Error found in R.assignPerson() and R.getPerson()");
        }
        if(!R.isBedoccupied(66)){
            System.out.println("Error found in isBedoccupied() method");
        }

        Person s = new Person("Andy", "Barber", 987654321);
        R.assignPerson(50, s);

        String c = R.toString();
        if(!c.equals("Package.Residence Spruce Hall contains 26 beds \n" +
                "bed #50 with Andy Barber as a resident.\n" +
                "bed #51\n" +
                "bed #52\n" +
                "bed #53\n" +
                "bed #54\n" +
                "bed #55\n" +
                "bed #56\n" +
                "bed #57\n" +
                "bed #58\n" +
                "bed #59\n" +
                "bed #60\n" +
                "bed #61\n" +
                "bed #62\n" +
                "bed #63\n" +
                "bed #64\n" +
                "bed #65\n" +
                "bed #66 with Randy Cunningham as a resident.\n" +
                "bed #67\n" +
                "bed #68\n" +
                "bed #69\n" +
                "bed #70\n" +
                "bed #71\n" +
                "bed #72\n" +
                "bed #73\n" +
                "bed #74\n" +
                "bed #75\n")){
            System.out.println("Error found in toString() method");
        }
        System.out.println("Test completed");
    }
}
