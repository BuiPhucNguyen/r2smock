package r2s.MockProject.service.impl;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.OrderInDto;
import r2s.MockProject.service.OrderService;
@Service
@Transactional
public class OrderServiceImpl implements OrderService{

	@Override
	public ActionResult getAllOrders(Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult createOrder(OrderInDto orderIn) {
		// TODO Auto-generated method stub
		return null;
	}

}
