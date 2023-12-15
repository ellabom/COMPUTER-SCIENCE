/*
  CMPT 270 Course material
  Copyright (c) 2020
  All rights reserved.

  This document contains resources for homework assigned to students of
  CMPT 270 and shall not be distributed without permission.  Posting this
  file to a public or private website, or providing this file to a person
  not registered in CMPT 270, constitutes Academic Misconduct, according
  to the University of Saskatchewan Policy on Academic Misconduct.

  Synopsis:
     Starter File for Assignment 4
 */


/**
 * A very simple model of a Basic manager who has a name and an employee Id.
 */
public class BasicManager extends Person {

    /**
     * The employee id of the  manager.
     */

    private String employeeId;
    /**
     * Initialize an instance of a Basic Manager with the given name, social insurance number and employee id.
     * @param name  the name of the manager
     * @param ssn   the social insurance number of the manager
     * @param employeeId    the id of the manager
     * @precond name != null && !name.equals("") && ssn > 0 && employeeId != null && !employeeId.equals("")
     */
    public BasicManager(String name, int ssn, String employeeId)
    {
        super(name,ssn);
        this.employeeId= employeeId;
    }
    /**
     * Return  the employee Id of the manager
     *
     * @return the employeeID of the Manager
     */
    public String getEmployeeId()
    {
        return this.employeeId;
    }

    /**
     * Return a string representation of the Manager
     *
     * @return a string representation of the Manager
     */
    public String toString()
    {
        return super.toString() + "Employee Id: "+ this.getEmployeeId() + "\n";
    }
    /**
     * A method to test the BasicDoctor class.
     */
    public static void main(String[] args){
        int numErrors = 0;

        // testing all the methods with one instance of a BasicManager
        BasicManager bm = new BasicManager("Bob", 12345, "emp01");

        if(! bm.getName().equals("Bob") || bm.getSSN()!= 12345 || !bm.getEmployeeId().equals( "emp01")) {
            System.out.println("The constructor failed");
            numErrors++;
        }
        bm.setName("Mr. Bob");
        if(! bm.getName().equals("Mr. Bob")) {
            System.out.println("The setName failed");
            numErrors++;
        }
        //String expected = "\nName: Mr. Bob\n";
        //String expected = "\nName: Mr. Bob \nSSN: 12345\n" ;//+ "emp02" +"\n";
        String expected = "\nName: Mr. Bob\nSSN: 12345\nEmployee Id: emp01\n";
        if(!bm.toString().equals(expected)) {
            System.out.println("The toString failed");
            numErrors++;
        }

        // testing all the methods with a second instance of a BasicDoctor
        bm = new BasicManager("Debby", 12346, "emp02");

        if(! bm.getName().equals("Debby") || bm.getSSN()!= 12346 || !bm.getEmployeeId().equals( "emp02")) {
            System.out.println("The constructor or getName failed");
            numErrors++;
        }
        bm.setName("Ms. Debby");
        if(! bm.getName().equals("Ms. Debby")) {
            System.out.println("The constructor or setName failed");
            numErrors++;
        }
        expected = "\nName: Ms. Debby\nSSN: 12346\nEmployee Id: emp02\n";

        if(!bm.toString().equals(expected)) {
            System.out.println("The toString failed");
            numErrors++;
        }

        System.out.println("The number of errors found is " + numErrors);
    }
}
