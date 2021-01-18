package com.example.relationaldataaccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class RelationaldataaccessApplication implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(RelationaldataaccessApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(RelationaldataaccessApplication.class, args);
	}
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override //Is throwing all exceptions here bad programming, just for tutorials sake, or is there a better palce to handle these somewhere?
	public void run(String ... strings) throws Exception {
		log.info("Creating tables");
		
		jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
	    jdbcTemplate.execute("CREATE TABLE customers(" +
	        "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");
		//So a varying length string of limit 255? I think the standard is 80-90 for this instance?
		
		//So sample input comes in the form of a list of split up names
		//First Name 0, LastName 1
		List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long")
				.stream().map(name -> name.split(" ")).collect(Collectors.toList());
		
		//While we are logging this- we are doing the logging and adding separate
		splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));
		
		//One way to do multiple inserts without 
		jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?, ?)", splitUpNames);
		
		log.info("Querying for customer records where first_name = 'Josh'");
		
		//..... Their own tutorial uses code that is flagged as deprecated... that's concerning
		jdbcTemplate.query("SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
				new Object[] {"Josh"}, (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name")
						, rs.getString("last_name"))).forEach(customer -> log.info(customer.toString()));
		
	}
	
	

}
