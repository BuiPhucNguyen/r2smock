package r2s.MockProject.model.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;
import r2s.MockProject.enums.OrderStatusEnum;

@Data
public class OrderInDto {
	private Integer accountId;
	private String address;
	private String phone;
	private Date createdDate;
	private List<OrderDetailInDto> orderDetails;
	private BigDecimal shipPrice;
	private String note;
	private OrderStatusEnum status;
}
