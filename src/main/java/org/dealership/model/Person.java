package org.dealership.model;

public abstract class Person {
    private String firstName;
    private String lastName;
    private String CNP; // Unique identifier (e.g., Social Security Number)

    public Person(){

    }
    public Person(String firstName, String lastName, String CNP) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.CNP = CNP;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", CNP='" + CNP + '\'' +
                '}';
    }
}
