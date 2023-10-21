package r2s.MockProject.service;

import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.FeedbackInDto;

public interface FeedbackService {

    ActionResult getAll(Integer page, Integer size);

    ActionResult getByStar(Integer star, Integer page, Integer size);

    ActionResult create(FeedbackInDto feedbackInDto);

//    ActionResult updateContent(FeedbackInDto feedbackInDto);

    ActionResult delete(Integer id);
}
