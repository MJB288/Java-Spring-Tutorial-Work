package com.example.relationaldataaccess;

//This class will hold customer data that will be used in the demo relational database
public class Customer {
	private long id;
	private String firstName, lastName;
	
	public Customer(long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return String.format("Customer[id=%d, firstName='%s', lastName='%s']", id, firstName, lastName);
	}
	
	//Getters and setters are omitted in the actual tutorial so quickly setting them
	
	//Getters
	//-------------------------------------------------------
	public long getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	//Setters
	//----------------------------------------------------------
	public void setId(long newId) {
		id = newId;
	}
	
	public void setFirstName(String newFirstName) {
		firstName = newFirstName;
	}
	
	public void setLastName(String newLastName) {
		lastName = newLastName;
	}
	//Will watch the tutorial further to see if I need a full name method as well
	
	
}
