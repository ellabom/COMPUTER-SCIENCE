/*
Emmanuella Eyo
eee917
11291003
CMPT 270
 */

import java.util.TreeMap;

public class ManagerMapAccess {

    /**
     *
     */
    private static TreeMap<String, Manager> dictionary;

    /**
     * The Private Constructor
     */
    private ManagerMapAccess(){}

    /**Returns the dictionary containing the Manager's objects
     *
     * @return the dictionary containing the Manager's objects
     */
    public static TreeMap<String, Manager> getInstance(){

        if(dictionary == null){
            dictionary = new TreeMap<String, Manager>();
        }

        return dictionary;
    }

    public static void main(String[] args) {
	// write your code here
    }
}
