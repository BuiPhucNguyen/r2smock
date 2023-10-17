package r2s.MockProject.model.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;


@Data
public class OrderInDto {
	private Integer accountId;
	private String address;
	private String phone;
	private List<OrderDetailInDto> orderDetails;
	private BigDecimal shipPrice;
	private String note;
}
