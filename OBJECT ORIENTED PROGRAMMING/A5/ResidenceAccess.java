/*
Emmanuella Eyo
eee917
11291003
CMPT 270
 */

public class ResidenceAccess {
    /**
     * Singleton attribute
     */
    private static Residence residence;

    /**
     * The private constructor
     */
    private ResidenceAccess(){}

    /** Initialize an instance of a Residence.
     *
     * @param name the Residence name
     * @param first the first bed label
     * @param last  the last bed label
     * @precond
     */
    public static void intialize(String name, int first, int last){

        if(name == null){
            throw new RuntimeException("The Residence name given is empty.");
        }
        if(first < 0 || first > last){
            throw new RuntimeException("The bed labels given are invalid.");
        }

        if(residence != null){
            throw new IllegalStateException("Residence has been initialized.");
        }
        residence = new Residence(name, first, last);
    }

    /**
     * Return the container for Residence
     * @precond
     * @return  the dictionary for Residence
     */
    public static Residence getInstance(){
        if(residence == null){
            throw new RuntimeException("Residence was not initialized.");
        }
        return residence;
    }
    public static void main(String[] args) {
	// write your code here
    }
}
