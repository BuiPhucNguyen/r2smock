package r2s.MockProject.service;

import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.OrderInDto;

public interface OrderService {
	ActionResult getAllOrders(Integer page, Integer size); 

    ActionResult createOrder(OrderInDto orderIn);
}
