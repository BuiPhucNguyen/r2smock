package r2s.MockProject.model.dto;

import lombok.Data;
import r2s.MockProject.model.entity.ProductModel;

import java.util.List;

@Data
public class ProductOutDto {
    private List<ProductModel> products;
    private Integer total;
}
