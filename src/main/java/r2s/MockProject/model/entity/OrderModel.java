package r2s.MockProject.model.entity;

import lombok.Builder;
import lombok.Data;
import r2s.MockProject.entity.Order;
import r2s.MockProject.enums.OrderStatusEnum;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class OrderModel {
    private Integer id;
    private Integer accountId;
    private String address;
    private String phone;
    private Date date;
    private BigDecimal orderDetailsPrice;
    private BigDecimal shipPrice;
    private BigDecimal totalPrice;
    private String note;
    private OrderStatusEnum status;

    public static OrderModel transform(Order order){
        return OrderModel.builder().id(order.getId())
                .accountId(order.getAccount().getId())
                .address(order.getAddress())
                .phone(order.getPhone())
                .date(order.getCreatedDate())
                .orderDetailsPrice(order.getOrderDetailsPrice())
                .shipPrice(order.getShipPrice())
                .totalPrice(order.getTotalPrice())
                .note(order.getNote())
                .status(order.getStatus())
                .build();
    }
}
