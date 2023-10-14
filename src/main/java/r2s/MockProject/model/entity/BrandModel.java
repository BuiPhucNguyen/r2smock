package r2s.MockProject.model.entity;

import lombok.Builder;
import lombok.Data;
import r2s.MockProject.entity.Brand;

@Data
@Builder
public class BrandModel {
    private Integer id;
    private String name;

    public static BrandModel transform(Brand brand){
        return BrandModel.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }
}
