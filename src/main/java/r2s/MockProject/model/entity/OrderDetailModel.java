package r2s.MockProject.model.entity;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import r2s.MockProject.entity.OrderDetail;

@Data
@Builder
public class OrderDetailModel {
	private Integer id;
	private Integer productId;
	private Integer amount;
	private BigDecimal purchasePrice;
	
	public static OrderDetailModel transform(OrderDetail entity) {
		return OrderDetailModel.builder()
				.id(entity.getId())
				.productId(entity.getProduct().getId())
				.amount(entity.getAmount())
				.purchasePrice(entity.getPurchasePrice())
				.build();
	}
}
