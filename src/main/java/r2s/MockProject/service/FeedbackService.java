package r2s.MockProject.service;

import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.FeedbackInDto;

public interface FeedbackService {

    ActionResult getAll(Integer page, Integer size);

    ActionResult getAllByStarAndProduct(Integer id, Integer star,Integer page, Integer size);

    ActionResult getAllFeedbackByProduct(Integer id, Integer page, Integer size);

    ActionResult create(FeedbackInDto feedbackInDto);

    ActionResult updateContentAndStar(Integer id, FeedbackInDto feedbackInDto);

    ActionResult delete(Integer id);
    
    ActionResult findAverageStarByProductId(Integer id);
    
    ActionResult deleteByActiveAccount(Integer id);
}
