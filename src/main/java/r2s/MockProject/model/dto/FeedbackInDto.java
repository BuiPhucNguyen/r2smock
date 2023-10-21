package r2s.MockProject.model.dto;

import lombok.Data;

@Data
public class FeedbackInDto {
    private Integer productid;
    private Integer star;
    private String content;
}
