package payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


//The client will not handle checking state transitions, only issue orders to try to change them
//Thus it will be up to the server side of things to determine if a stat transition is a valid one
@Component
class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

  @Override
  public EntityModel<Order> toModel(Order order) {

    //TC - Unconditional links to single-item resource and aggregate root

    EntityModel<Order> orderModel = EntityModel.of(order,
        linkTo(methodOn(OrderController.class).one(order.getID())).withSelfRel(),
        linkTo(methodOn(OrderController.class).all()).withRel("orders"));

    //TC - Conditional links based on state of the order
    
    //These links will be used by the client to issue state transition requests
    if (order.getStatus() == Status.IN_PROGRESS) {
      orderModel.add(linkTo(methodOn(OrderController.class).cancel(order.getID())).withRel("cancel"));
      orderModel.add(linkTo(methodOn(OrderController.class).complete(order.getID())).withRel("complete"));
    }

    return orderModel;
  }
}