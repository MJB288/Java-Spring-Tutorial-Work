package payroll;

//import java.net.http.HttpHeaders;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
	private final OrderRepository orderRepository;
	private final OrderModelAssembler assembler;
	
	OrderController(OrderRepository orderRepository, OrderModelAssembler assembler) {

	    this.orderRepository = orderRepository;
	    this.assembler = assembler;
	  }

	  @GetMapping("/orders")
	  CollectionModel<EntityModel<Order>> all() {

	    List<EntityModel<Order>> orders = orderRepository.findAll().stream() //
	        .map(assembler::toModel) //
	        .collect(Collectors.toList());

	    return CollectionModel.of(orders, //
	        linkTo(methodOn(OrderController.class).all()).withSelfRel());
	  }

	  @GetMapping("/orders/{id}")
	  EntityModel<Order> one(@PathVariable Long id) {

	    Order order = orderRepository.findById(id) //An exception just like in the Employee controller
	        .orElseThrow(() -> new OrderNotFoundException(id));

	    return assembler.toModel(order);
	  }
	  
	  //Create a new order and set it's status to to in_progress
	  @PostMapping("/orders")
	  ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order) {

	    order.setStatus(Status.IN_PROGRESS);
	    Order newOrder = orderRepository.save(order);

	    return ResponseEntity //
	        .created(linkTo(methodOn(OrderController.class).one(newOrder.getID())).toUri()) //
	        .body(assembler.toModel(newOrder));
	  }
	  
	  //Create a cancel order or a delete request
	  @DeleteMapping("/orders/{id}/cancel")
	  ResponseEntity<?> cancel(@PathVariable Long id){
		  Order order = orderRepository.findById(id)
				  .orElseThrow(()-> new OrderNotFoundException(id));
		  //If in a valid state - allow the cancellation
		  if(order.getStatus() == Status.IN_PROGRESS) {
			  order.setStatus(Status.CANCELLED);
			  return ResponseEntity.ok(assembler.toModel(orderRepository.save(order)));
		  }
		  //Otherwise - notify of illegal action
		  
		  return ResponseEntity
				  .status(HttpStatus.METHOD_NOT_ALLOWED)
				  .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				  
				  .body(Problem.create()
				  .withTitle("Method not allowed")
				  .withDetail("You can't cancel an order that is in the " + order.getStatus() + " status!"));
	  }
	  //Now to create a complete operation
	  
	  @PutMapping("/orders/{id}/complete")
	  ResponseEntity<?> complete(@PathVariable Long id){
		  Order order = orderRepository.findById(id).orElseThrow(() -> 
		  new OrderNotFoundException(id));
		  
		  //If in progress, allow the transition request
		  if(order.getStatus() == Status.IN_PROGRESS) {
			  order.setStatus(Status.COMPLETED);
			  return ResponseEntity.ok(assembler.toModel(orderRepository.save(order)));
		  }
		  //Like with cancelling - issue a notice of illegal action
		  return ResponseEntity
				  .status(HttpStatus.METHOD_NOT_ALLOWED)
				  .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				  .body(Problem.create()
						  .withTitle("Method not allowed")
						  .withDetail("You can't complete an order that is in the "
				  + order.getStatus() + " status!"));
				  			
	  }
}
