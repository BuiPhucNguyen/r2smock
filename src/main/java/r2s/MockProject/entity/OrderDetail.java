package r2s.MockProject.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "order-detail")
public class OrderDetail {
	@Id
	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Order order;
	@Id
	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;
	
	@Column(name = "amount", columnDefinition = "bigint(20)")
	private Integer amount;
	
	@Column(name = "purchase_price", columnDefinition = "decimal(15,2)")
	private BigDecimal purchasePrice;
}
