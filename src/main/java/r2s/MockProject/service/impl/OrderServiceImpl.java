package r2s.MockProject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import r2s.MockProject.entity.Order;
import r2s.MockProject.entity.Product;
import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.OrderInDto;
import r2s.MockProject.model.dto.OrderOutDto;
import r2s.MockProject.model.dto.ProductOutDto;
import r2s.MockProject.model.entity.OrderModel;
import r2s.MockProject.model.entity.ProductModel;
import r2s.MockProject.repository.OrderRepository;
import r2s.MockProject.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public ActionResult getAllOrders(Integer page, Integer size) {
		ActionResult result = new ActionResult();
		Page<Order> ordersPage = orderRepository.findAll((PageRequest.of(page - 1, size)));
		if (ordersPage.isEmpty()){
			result.setErrorCodeEnum(ErrorCodeEnum.NO_CONTENT);
			return result;
		}

		List<OrderModel> orderModels = ordersPage.stream().map(OrderModel::transform).collect(Collectors.toList());

		if (orderModels.isEmpty()){
			result.setErrorCodeEnum(ErrorCodeEnum.NO_CONTENT);
			return result;
		}
		OrderOutDto OutDto = new OrderOutDto();

		OutDto.setOrderModels(orderModels);
		OutDto.setTotal(orderModels.size());
		result.setData(OutDto);
		return result;
	}

	@Override
	public ActionResult createOrder(OrderInDto orderIn) {
		// TODO Auto-generated method stub
		return null;
	}

}
