/*
  CMPT 270 Course material
  Copyright (c) 2021
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
 * The model of a person who has a name and a health number
 * that cannot be changed.
 */
public class Person {

    /**
     * The name of the person.
     */
    private String name;

    /**
     * The person's social insurance number.
     */
    private int ssn;

    /**
     * Initialize an instance of a Person with the given name and health number.
     *
     * @param pName the person's name
     * @param ssn the person's social Insurance number
     */
    public Person(String pName, int ssn) {
        this.name = pName;
        this.ssn = ssn;
    }

    /**
     * Return the name of the person.
     *
     * @return the name of the person
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the social insurance number of the person.
     *
     * @return the ssn of the person
     */
    public int getSSN() {
        return this.ssn;
    }

    /**
     * Change the name of the person.
     *
     * @param newName the name of the person
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Return a string representation of the person.
     *
     * @return a string representation of the person
     */
    public String toString() {
        return "\nName: " + this.name + "\nSSN: " + this.ssn +"\n";
    }

    /**
     * A method to test the Person class.
     */
    public static void main(String[] args) throws Exception {
        int numErrors = 0;

        // testing all the methods with one instance of a Person
        Person p = new Person("Pete", 12345);

        if(! p.getName().equals("Pete") && p.getSSN() != 12345) {
            System.out.println("The constructor failed");
            numErrors++;
        }
        if(p.getSSN() != 12345) {
            System.out.println("getSSN failed");
            numErrors++;
        }
        p.setName("Jim");
        if(! p.getName().equals("Jim")) {
            System.out.println("setName failed");
            numErrors++;
        }
        String expected = "\nName: Jim\nSSN: 12345\n";
        if(!p.toString().equals(expected)) {
            System.out.println("The toString failed");
            numErrors++;
        }

        // testing all the methods with a second instance of a Person
        p = new Person("Mary", 12346);

        if(! p.getName().equals("Mary"))
        {
            System.out.println("The constructor or getName failed");
            numErrors++;
        }
        if(p.getSSN() != 12346)
        {
            System.out.println("The constructor or getSSN failed");
            numErrors++;
        }
        p.setName("Sue");
        if(! p.getName().equals("Sue"))
        {
            System.out.println("setName failed");
            numErrors++;
        }
        expected = "\nName: Sue\nSSN: 12346\n";
        if(!p.toString().equals(expected)) {
            System.out.println("The toString failed");
            numErrors++;
        }

        System.out.println("The number of errors found is " + numErrors);
    }
}
