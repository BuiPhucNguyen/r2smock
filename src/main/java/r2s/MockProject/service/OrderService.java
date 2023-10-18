package r2s.MockProject.service;

import r2s.MockProject.enums.OrderStatusEnum;
import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.OrderInDto;

public interface OrderService {
	ActionResult getAllOrders(Integer page, Integer size); 

    ActionResult createOrder(OrderInDto orderIn);
    
    ActionResult findOrderById(Integer id);
    
    ActionResult findOrderByAccountId(Integer id, Integer page, Integer size);

    ActionResult updateStatus(Integer id, OrderStatusEnum status);
}
