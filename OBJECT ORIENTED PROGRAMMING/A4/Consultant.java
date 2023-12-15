//EMMANUELLA EYO
//eee917
//11291003
//CMPT 270

public class Consultant extends Manager {

    /**
     *
     * @param name  the name of the Consultant
     * @param ssn   the social security number of the consultant
     * @param employeeId  the employee ID of the consultant
     */
    public Consultant(String name, int ssn, String employeeId){
        super(name, ssn, employeeId);
    }
    /**
     * Return a string representation of the Consultant
     * @return a string representation of the consultant
     */
    public String toString(){
        return "Consultant" + super.toString();
    }

    /**
     *test out the methods
     */
    public static void main(String[] args){
        int num_error = 0;

        Consultant c = new Consultant("Chandler Bing",55745, "cdb745");

        String expected = "Consultant\n" +
                "Name: Chandler Bing\n" +
                "SSN: 55745\n" +
                "Employee Id: cdb745\n" +
                "Students:\n";

        if(!c.toString().equals(expected)){
            System.out.println("Error in toString()");
            num_error++;
        }


        Consultant c1 = new Consultant("Emmanuella Eyo", 86943, "eee990");
        String expected1 = "Consultant\n" +
                "Name: Emmanuella Eyo\n" +
                "SSN: 86943\n" +
                "Employee Id: eee990\n" +
                "Students:\n";

        if(!c1.toString().equals(expected1)){
            System.out.println("Error in toString()");
            num_error++;
        }

        if(num_error > 0) {
            System.out.println("The number of errors: " + num_error);
        }
        else{
            System.out.println("Test completed!");
        }
    }
}
