package r2s.MockProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.ResponseBuild;
import r2s.MockProject.model.ResponseModel;
import r2s.MockProject.model.dto.FeedbackInDto;
import r2s.MockProject.service.FeedbackService;

@RestController
@RequestMapping(path = "/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private ResponseBuild responseBuild;

    @GetMapping("/all")
    public ResponseModel getAll(@RequestParam Integer page, @RequestParam Integer size){
        ActionResult result = null;
        try {
            result = feedbackService.getAll(page, size);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @GetMapping("/product/{productId}")
    public ResponseModel  getAllFeedbackByProduct(@PathVariable Integer productId, @RequestParam Integer page, @RequestParam Integer size){
        ActionResult result = null;
        try {
            result = feedbackService.getAllFeedbackByProduct(productId, page, size);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @GetMapping("/product/{productId}/star/{star}")
    public ResponseModel  getAllByStarAndProduct(@PathVariable Integer productId,@PathVariable Integer star, @RequestParam Integer page, @RequestParam Integer size){
        ActionResult result = null;
        try {
            result = feedbackService.getAllByStarAndProduct(productId, star, page, size);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }
    
    @GetMapping("/avgstar/product/{productId}")
    public ResponseModel  findAverageStarByProductId(@PathVariable Integer productId){
        ActionResult result = null;
        try {
            result = feedbackService.findAverageStarByProductId(productId);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @PostMapping("/")
    public ResponseModel create(@RequestBody FeedbackInDto feedbackInDto){
        ActionResult result = null;
        try {
            result = feedbackService.create(feedbackInDto);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

//    @GetMapping("")
//    public ResponseModel  updateContent(@RequestBody FeedbackInDto feedbackInDto){
//        ActionResult result = null;
//        try {
//            result = feedbackService.create(feedbackInDto);
//        }catch (Exception e){
//            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
//        }
//        return responseBuild.build(result);
//    }

    @DeleteMapping("/remove/{id}")
    public ResponseModel  delete(@PathVariable Integer id){
        ActionResult result = null;
        try {
            result = feedbackService.delete(id);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }
    
    @DeleteMapping("/removemine/{id}")
    public ResponseModel  deleteByActiveAccount(@PathVariable Integer id){
        ActionResult result = null;
        try {
            result = feedbackService.deleteByActiveAccount(id);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }
}
