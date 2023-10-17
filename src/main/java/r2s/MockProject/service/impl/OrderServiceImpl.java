package r2s.MockProject.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import r2s.MockProject.entity.Account;
import r2s.MockProject.entity.Order;
import r2s.MockProject.entity.OrderDetail;
import r2s.MockProject.entity.Product;
import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.enums.OrderStatusEnum;
import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.OrderDetailInDto;
import r2s.MockProject.model.dto.OrderInDto;
import r2s.MockProject.repository.AccountRepository;
import r2s.MockProject.repository.OrderRepository;
import r2s.MockProject.repository.ProductReponsitory;
import r2s.MockProject.service.OrderService;
@Service
@Transactional
public class OrderServiceImpl implements OrderService{
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private ProductReponsitory productReponsitory;

	@Override
	public ActionResult getAllOrders(Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult createOrder(OrderInDto orderIn) {
		ActionResult result = new ActionResult();
		
		Account account = accountRepository.getAccountById(orderIn.getAccountId());
		
		if (account==null) {
			result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
			return result;
		}
		
		Order order = new Order();
		order.setAccount(account);
		order.setAddress(orderIn.getAddress());
		order.setPhone(orderIn.getPhone());
		order.setCreatedDate(new Date());
		
		order.setShipPrice(orderIn.getShipPrice());
		order.setNote(orderIn.getNote());
		order.setStatus(OrderStatusEnum.PENDING.getStatus());
		
		List<OrderDetailInDto> dtos = orderIn.getOrderDetails();
		
		BigDecimal orderDetailsPrice = new BigDecimal(0);
		
		List<OrderDetail> details = new ArrayList<>();
		for (OrderDetailInDto orderDetailInDto : dtos) {
			OrderDetail orderDetail = new OrderDetail();
			Product product = productReponsitory.getProductById(orderDetailInDto.getProductId());
			
			orderDetail.setOrder(order);
			orderDetail.setProduct(product);
			orderDetail.setAmount(orderDetailInDto.getAmount());
			
			BigDecimal a = BigDecimal.valueOf(orderDetailInDto.getAmount());
			BigDecimal p = product.getPrice();
			BigDecimal pur = p.multiply(a);
			orderDetail.setPurchasePrice(pur);
			
			orderDetailsPrice.add(pur);
			
			details.add(orderDetail);
			
		}
		order.setOrderDetails(details);
		
		order.setOrderDetailsPrice(orderDetailsPrice);
		order.setTotalPrice(orderDetailsPrice.add(orderIn.getShipPrice()));
		
		Order orderTemp = orderRepository.save(order);
		result.setData(orderTemp);
		return result;
	}

}
