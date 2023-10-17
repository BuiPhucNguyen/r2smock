package r2s.MockProject.model.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderDetailInDto {
	private Integer orderId;
	private Integer productId;
	private Integer amount;
	private BigDecimal purchasePrice;
}
