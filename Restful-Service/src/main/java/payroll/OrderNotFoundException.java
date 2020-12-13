package payroll;

public class OrderNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7319459143267822216L;

	public OrderNotFoundException(Long id) {
		super("Could not find order #" + id);
	}
}
