package payroll;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity
public class Employee {
	private @Id @GeneratedValue Long id;
	private String name;
	private String role;
	
	Employee() 
	{
		
	}
	
	Employee(String name, String role){
		this.name = name;
		this.role = role;
	}
	
	//Accessors
	//---------------------------------------
	public Long getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	//Mutators
	//-----------------------
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		//Check if object is an employee
		if(!(o instanceof Employee))
			return false;
		//After checking out type, convert and then compare member variables
		Employee employee = (Employee) o;
		return (Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name) 
				&& Objects.equals(this.role, employee.role));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.role);
	}
	
	
	@Override
	public String toString() {
		return "Employee{" + "id=" + this.id + ", name='" + this.name 
				+ '\'' + ", role='" + this.role + '\'' + '}';
	}
	
}
