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
	CommandLineRunner initDatabase(EmployeeRepository repository) {
		return args -> {
			log.info("Preloading " + repository.save(new Employee("Bilbo", "Baggins", "burglar")));
			log.info("Preloading " + repository.save(new Employee("Frodo", "Baggins", "thief")));
			//Yes, I am aware that's a title, not a name.
			log.info("Preloading " + repository.save(new Employee("Gandalf", "the Grey", "wizard")));
			
		};
	}
}
