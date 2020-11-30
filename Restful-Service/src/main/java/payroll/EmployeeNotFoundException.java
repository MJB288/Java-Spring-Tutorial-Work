package payroll;


//Quickly defining the exception used in the tutorial
//However added an IDE generated serial id
public class EmployeeNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5321824598296334815L;

	EmployeeNotFoundException(Long id){
		super("Could not find employee " + id);
	}
}
