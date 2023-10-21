package r2s.MockProject.model.entity;

import lombok.Builder;
import lombok.Data;
import r2s.MockProject.entity.FeedbackProduct;

@Data
@Builder
public class FeedbackModel {
    private Integer id;
    private Integer productId;
    private String name;
    private Integer star;
    private String content;

    public static FeedbackModel transform(FeedbackProduct enity){
        return FeedbackModel.builder()
                .id(enity.getId())
                .productId(enity.getProduct().getId())
                .name(enity.getAccount().getLastName())
                .star(enity.getStar())
                .content(enity.getContent())
                .build();
    }
}
