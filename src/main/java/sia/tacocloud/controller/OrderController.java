package sia.tacocloud.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.model.Order;
import sia.tacocloud.repository.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

	private OrderRepository repository;
	
	@Autowired	
	public OrderController(OrderRepository repository) {
		super();
		this.repository = repository;
	}

	@GetMapping("/current")
	public String orderForm(Model model) {
		model.addAttribute("order", new Order());
		return "orderForm";
	}

	@PostMapping
	public String processOrder(@Valid Order order, Errors erros, SessionStatus sessionStatus) {
		if(erros.hasErrors())
			return "orderForm";
		
		repository.save(order);
		sessionStatus.setComplete();
		
		log.info("Order submitted: " + order);
		return "redirect:/";
	}

}
