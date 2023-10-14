package r2s.MockProject.model.dto;

import lombok.Data;
import r2s.MockProject.model.entity.BrandModel;

import java.util.List;

@Data
public class BrandOutDto {
    private List<BrandModel> brands;
    private Integer total;
}
