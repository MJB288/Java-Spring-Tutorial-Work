package payroll;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//ORDER is not a valid name for a table, so we must rename it to CUSTOMER_ORDER
@Table(name = "CUSTOMER_ORDER")
public class Order {
	
	private @ID @GeneratedValue Long id;
	
	private String description;
	private Status status;
	
	Order (){
		
	}
	
	Order(String description, Status status){
		this.description = description;
		this.status = status;
	}
	
	public Long getID() {
		return this.id;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Order))
			return false;
		Order order = (Order) o;
		
		return Objects.equals(this.id, order.id) && Objects.equals(this.description, order.description)
				&& this.status == order.status;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.description, this.status);
	}
	
	@Override
	public String toString() {
		return "Order{" + "id=" + this.id + ", description='" + 
				this.description + '\'' + ", status=" + this.status + '}';
	}
}
