/*
EMMANUELLA EYO
eee917
11291003
CMPT 270
 */

import java.util.TreeMap;

public class StudentMapAccess {

    /**
     * The dictionary container for all students.
     */
    private static TreeMap<String, Student> dictionary;

    /**
     * The Private Constructor
     */
    private StudentMapAccess(){}

    /**Returns the dictionary containing all the Student objects.
     * @return  A student dictionary container
     */
    public static TreeMap<String, Student> getInstance() {
        if(dictionary == null){
            dictionary = new TreeMap<String, Student>();
        }

        return dictionary;
    }

    public static void main(String[] args) {
	// write your code here

    }
}
