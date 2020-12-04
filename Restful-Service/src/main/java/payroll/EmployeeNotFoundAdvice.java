package payroll;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//Renders and HTTP 404

@ControllerAdvice
public class EmployeeNotFoundAdvice {
	
	//Signals that advice is rendered straight into body
	@ResponseBody
	//Advice only executes if this exception is thrown
	@ExceptionHandler(EmployeeNotFoundException.class)
	//What kind of status to issue when thrown
	@ResponseStatus(HttpStatus.NOT_FOUND)
	//generates the content. Here- gives the message of the exception
	String employeeNotFoundHandler(EmployeeNotFoundException ex) {
		return ex.getMessage();
	}
	
}
