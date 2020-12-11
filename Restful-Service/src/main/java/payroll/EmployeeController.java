package payroll;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


//import org.springframework.hateoas.*;

@RestController
public class EmployeeController {
	
	private final EmployeeRepository repository;
	
	private final EmployeeModelAssembler assembler;
	
	
	//Dependency injection
	EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler){
		this.repository = repository;
		this.assembler = assembler;
	}
	
	//TC = Tutorial Comment - or comment in the tutorial itself
	//Each Line from the tutorial will be marked TC. Anything without a TC, unless a mistake is made Are my own thoughts

	// TC - Aggregate Root
	
	//Map the get request for employees
	@GetMapping("/employees")
	CollectionModel<EntityModel<Employee>> all(){
		/*List<EntityModel<Employee>> employees = repository.findAll().stream()
			      .map(employee -> EntityModel.of(employee,
			          linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
			          linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
			      .collect(Collectors.toList());*/
		//Like the singular method - we can now delegate the employee conversion to the assembler
		List<EntityModel<Employee>> employees = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
		
		return CollectionModel.of(employees,
				linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
	}
		
	//Map the Post request for employees
	@PostMapping("/employees")
	ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {
		//return repository.save(newEmployee);
		//We continue to save the employee like before
		EntityModel<Employee> entityModel = assembler.toModel(repository.save(newEmployee));
		//However, we now respond to each request with an HTTP 201 Created
		//This includes a location and we use the URI derived from the model's self-related link
		//return the model based version of the saved object
		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())//
				.body(entityModel);
	}
	
	// TC - Single Item
	//So in other words - modifying/getting based off of single attributes or in this case the id
	/*@GetMapping("/employees/{id}")
	Employee one(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
	}*/
	
	@GetMapping("/employees/{id}")
	EntityModel<Employee> one(@PathVariable Long id){
		
		Employee employee = repository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException(id));
		/*return EntityModel.of(employee,
				//Ask HATEOAS to build a link to the EmployeController one method() as a self-link
				linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
				//Ask HATEOAS to build a link to the aggregate root all() and call it employees
				linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));*/
		//Thanks to the assembler, the above code is no longer needed
		return assembler.toModel(employee);
		  
	}
	
	
	@PutMapping("/employees/{id}")
	ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
		//Find an employee by an existing id and set new name and role
		Employee updatedEmployee = repository.findById(id).map(
				employee-> {
					employee.setName(newEmployee.getName());
					employee.setRole(newEmployee.getRole());
					return repository.save(employee);
			
		//Or create a new employee in the database
		}).orElseGet(() ->{
			newEmployee.setId(id);
			return repository.save(newEmployee);
		});
		
		//The employee object from the save event is wrapped by the assembler into an EntityModel object
		//using the getRequiredLink, retrieve the Link created by the EmployeeModelAssembler
		EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);
		
		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
		//Use the static method created where we can plug in the resource's URI
	}
	
	@DeleteMapping("/employees/{id}")
	ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
	  repository.deleteById(id);
	  
	  return ResponseEntity.noContent().build();
	}
	
}
