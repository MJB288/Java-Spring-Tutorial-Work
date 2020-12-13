package payroll;

//This class will automatically get loaded by spring

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LoadDatabase {
	
	
	//This Logger is one way to print to the command line when serving the spring boot application
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	
	
	//Spring runs all CommandLineRunner beans once context is loaded
	//This runner will request a copy of the EmployeeRepository that this just created
	//Using it, it will create two entities and store them
	@Bean
	CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
		return args -> {
			employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar"));
			employeeRepository.save(new Employee("Frodo", "Baggins", "thief"));
			//Yes, I am aware that's a title, not a name.
			employeeRepository.save(new Employee("Gandalf", "the Grey", "wizard"));
			//Now to actually log their creation
			employeeRepository.findAll().forEach(employee -> log.info("Preloaded : " + employee));
			
			//Now to preload the orders in the same fashion
			orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
		    orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));
		    orderRepository.save(new Order("Samsung Galaxy S4", Status.CANCELLED));
		    
		    orderRepository.findAll().forEach(order -> log.info("Preloaded"));
			
			
			
			
		};
	}
}
