package r2s.MockProject.entity;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.IdClass;

@Embeddable
@IdClass(OrderDetail.class)
public class OrderDetailId {
	private Integer order;
	private Integer product;
	@Override
	public int hashCode() {
		return Objects.hash(order, product);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetailId other = (OrderDetailId) obj;
		return Objects.equals(order, other.order) && Objects.equals(product, other.product);
	}
}
