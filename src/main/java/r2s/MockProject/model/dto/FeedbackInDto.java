package r2s.MockProject.model.dto;

import lombok.Data;
import r2s.MockProject.entity.Product;

@Data
public class FeedbackInDto {
    private Integer id;
    private Integer productid;
    private Integer star;
    private String content;
}
