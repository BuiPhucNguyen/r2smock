package r2s.MockProject.model.dto;
import lombok.Data;
import r2s.MockProject.model.entity.FeedbackModel;

import java.util.List;

@Data
public class FeedbackOutDto {
    private List<FeedbackModel> feedbacks;
    private Integer total;
}
