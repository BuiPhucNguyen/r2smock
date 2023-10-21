package r2s.MockProject.model.entity;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import r2s.MockProject.entity.FeedbackProduct;

@Data
@Builder
public class FeedbackModel {
    private Integer id;
    private Integer productId;
    private Integer accountId;
    private Integer star;
    private String content;
    private Date createdDate;

    public static FeedbackModel transform(FeedbackProduct entity){
        return FeedbackModel.builder()
                .id(entity.getId())
                .productId(entity.getProduct().getId())
                .accountId(entity.getAccount().getId())
                .star(entity.getStar())
                .content(entity.getContent())
                .createdDate(entity.getCreateDate())
                .build();
    }
}
