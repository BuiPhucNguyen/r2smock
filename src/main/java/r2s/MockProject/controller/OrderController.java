package r2s.MockProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.enums.OrderStatusEnum;
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

    @GetMapping("/")
    public ResponseModel  getAll(@RequestParam Integer page,@RequestParam Integer size){
        ActionResult result = null;
        try {
            result = orderService.getAllOrders(page, size);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }
	@PostMapping("/")
    public ResponseModel create(@RequestBody OrderInDto orderIn){
        ActionResult result = null;
        try {
            result = orderService.createOrder(orderIn);
        }catch(Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @PutMapping("/status/{id}_{statusEnum}")
    public ResponseModel updateStatus(@PathVariable Integer id,@PathVariable OrderStatusEnum statusEnum){
        ActionResult result = null;
        try {
            result = orderService.updateStatus(id, statusEnum);
        }catch(Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }
}
