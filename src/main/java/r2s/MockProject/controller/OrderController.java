package r2s.MockProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.ResponseBuild;
import r2s.MockProject.model.ResponseModel;
import r2s.MockProject.model.dto.OrderInDto;
import r2s.MockProject.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private ResponseBuild responseBuild;

	@GetMapping("/all") // admin
	public ResponseModel getAll(@RequestParam Integer page, @RequestParam Integer size) {
		ActionResult result = null;
		try {
			result = orderService.getAllOrders(page, size);
		} catch (Exception e) {
			result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		return responseBuild.build(result);
	}

	@PostMapping("/") // user admin ?
	public ResponseModel create(@RequestBody OrderInDto orderIn) {
		ActionResult result = null;
		try {
			result = orderService.createOrder(orderIn);
		} catch (Exception e) {
			result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		return responseBuild.build(result);
	}

	@GetMapping("/{orderId}") // admin, user
	public ResponseModel getOrderById(@PathVariable Integer orderId) {
		ActionResult result = null;
		try {
			result = orderService.findOrderById(orderId);
		} catch (Exception e) {
			result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		return responseBuild.build(result);
	}

	@GetMapping("/current_account") //
	public ResponseModel getOrderByCurrentAccountPaging(@Param(value = "page") Integer page,
			@Param(value = "size") Integer size) {
		ActionResult result = null;
		try {
			result = orderService.findOrderByAccountCurrent(page, size);
		} catch (Exception e) {
			result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		return responseBuild.build(result);
	}
	
	@GetMapping("/export")
	public ResponseModel exportOrderss(@RequestParam List<Integer> orderId, @RequestParam String filePath) {
		ActionResult result = null;
		try {
			result = orderService.exportOrder(orderId, filePath);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		return responseBuild.build(result); 
	}
	
	@PutMapping("/complete/{orderId}")
	public ResponseModel updateStatusCompleteOrder(@PathVariable Integer orderId) {
		ActionResult result = null;
		try {
			result = orderService.updateStatusCompleteOrder(orderId);
		} catch (Exception e) {
			result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		return responseBuild.build(result);
	}
	
	@PutMapping("/cancel/{orderId}")
	public ResponseModel updateStatusCancelOrder(@PathVariable Integer orderId) {
		ActionResult result = null;
		try {
			result = orderService.updateStatusCancelOrder(orderId);
		} catch (Exception e) {
			result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		return responseBuild.build(result);
	}
}
