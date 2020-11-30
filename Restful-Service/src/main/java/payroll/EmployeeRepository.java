package payroll;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	/*Empty here, but this interface supports:
	
		Creating new instances
		Updating existing ones
		Deleting
		Finding
	 * 
	 * 
	 */
}
