package r2s.MockProject.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import r2s.MockProject.entity.Account;
import r2s.MockProject.entity.FeedbackProduct;
import r2s.MockProject.entity.Product;
import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.FeedbackInDto;
import r2s.MockProject.model.dto.FeedbackOutDto;
import r2s.MockProject.model.entity.FeedbackModel;
import r2s.MockProject.repository.AccountRepository;
import r2s.MockProject.repository.FeedbackRepository;
import r2s.MockProject.repository.ProductReponsitory;
import r2s.MockProject.service.FeedbackService;
import r2s.MockProject.utils.CurrentUserUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ProductReponsitory productReponsitory;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ActionResult getAll(Integer page, Integer size) {

        ActionResult result = new ActionResult();

        Page<FeedbackProduct> feedbacksPage = feedbackRepository.findAll(PageRequest.of(page - 1, size));
        if (feedbacksPage.isEmpty()){
            result.setErrorCodeEnum(ErrorCodeEnum.NO_CONTENT);
            return result;
        }

        List<FeedbackModel> feedbackModels = feedbacksPage.stream().map(FeedbackModel::transform).collect(Collectors.toList());

        FeedbackOutDto outDto = new FeedbackOutDto();
        outDto.setFeedbacks(feedbackModels);
        outDto.setTotal(feedbackModels.size());

        result.setData(outDto);
        return result;
    }

    @Override
    public ActionResult getAllByStarAndProduct(Integer id, Integer star, Integer page, Integer size) {
        ActionResult result = new ActionResult();
        Product product = productReponsitory.getProductById(id);
        if (product == null) {
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }

        Page<FeedbackProduct> feedbacksPage = feedbackRepository.getFeedbacksByStarAndProductId(id, star, PageRequest.of(page - 1, size));
        if (feedbacksPage.isEmpty()){
            result.setErrorCodeEnum(ErrorCodeEnum.NO_CONTENT);
            return result;
        }

        List<FeedbackModel> feedbackModels = feedbacksPage.stream()
                .map(FeedbackModel::transform)
                .collect(Collectors.toList());

        FeedbackOutDto outDto = new FeedbackOutDto();
        outDto.setFeedbacks(feedbackModels);
        outDto.setTotal(feedbackModels.size());

        result.setData(outDto);
        return result;
    }

    @Override
    public ActionResult getAllFeedbackByProduct(Integer id, Integer page, Integer size) {
        ActionResult result = new ActionResult();
        Product product = productReponsitory.getProductById(id);

        if (product == null) {
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }

        Page<FeedbackProduct> feedbackPage = feedbackRepository.getFeedbacksByProductId(id, PageRequest.of(page - 1, size));
        if (feedbackPage == null) {
            result.setErrorCodeEnum(ErrorCodeEnum.NO_CONTENT);
            return result;
        }

        List<FeedbackModel> feedbackModels = feedbackPage.stream()
                .map(FeedbackModel::transform)
                .collect(Collectors.toList());

        FeedbackOutDto outDto = new FeedbackOutDto();
        outDto.setFeedbacks(feedbackModels);
        outDto.setTotal(feedbackModels.size());

        result.setData(outDto);
        return result;
    }


    @Override
    public ActionResult create(FeedbackInDto feedbackInDto) {
        ActionResult result = new ActionResult();

        FeedbackProduct feedback = new FeedbackProduct();
        Product product = productReponsitory.getProductById(feedbackInDto.getProductId());
        if (product == null) {
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }

        Account account = accountRepository.findByUsername(CurrentUserUtils.getCurrentUsernames());
        if (account == null) {
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
        }

        feedback.setAccount(account);
        feedback.setProduct(product);
        feedback.setStar(feedbackInDto.getStar());
        feedback.setContent(feedbackInDto.getContent());
        feedback.setCreateDate(new Date());

        FeedbackProduct feedbackSave = feedbackRepository.save(feedback);
        if (feedbackSave == null) {
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_CREATE);
            return result;
        }

        result.setData(FeedbackModel.transform(feedbackSave));
        return result;
    }

    @Override
    public ActionResult delete(Integer id) {
        ActionResult result = new ActionResult();
        FeedbackProduct feedbackProduct = feedbackRepository.getReferenceById(id);
        
        if (feedbackProduct == null) {
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }
        FeedbackProduct feedbackProductDelete = feedbackProduct;

        feedbackRepository.delete(feedbackProduct);

        result.setData(FeedbackModel.transform(feedbackProductDelete));
        return result;
    }

	@Override
	public ActionResult findAverageStarByProductId(Integer id) {
		ActionResult result = new ActionResult();
		
		Product product = productReponsitory.getProductById(id);
		if (product == null) {
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }
		
		Double avgStar = feedbackRepository.findAverageStarByProductId(id);
		
		result.setData(avgStar);
		return result;
	}

	@Override
	public ActionResult deleteByActiveAccount(Integer id) {
		ActionResult result = new ActionResult();
		
		FeedbackProduct feedbackProduct = feedbackRepository.getReferenceById(id);
        
        if (feedbackProduct == null) {
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }
        
        Account acitveAccount = accountRepository.findByUsername(CurrentUserUtils.getCurrentUsernames());
        if (!feedbackProduct.getAccount().getId().equals(acitveAccount.getId())) {
        	 result.setErrorCodeEnum(ErrorCodeEnum.NOT_CREATED_BY_ACTIVE_ACCOUNT);
             return result;
		}
        
        FeedbackProduct feedbackProductDelete = feedbackProduct;
        feedbackRepository.delete(feedbackProduct);
        result.setData(FeedbackModel.transform(feedbackProductDelete));
		
		return result;
	}
}
