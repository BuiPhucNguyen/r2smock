package r2s.MockProject.model.dto;

import lombok.Data;
import r2s.MockProject.model.entity.OrderModel;

import java.util.List;

@Data
public class OrderOutDto {
    private List<OrderModel> orderModels;
    private Integer total;
}
