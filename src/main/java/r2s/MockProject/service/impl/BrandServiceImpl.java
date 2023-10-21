package r2s.MockProject.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import r2s.MockProject.entity.Brand;
import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.BrandInDto;
import r2s.MockProject.model.dto.BrandOutDto;
import r2s.MockProject.model.entity.BrandModel;
import r2s.MockProject.repository.BrandReponsitory;
import r2s.MockProject.service.BrandService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandReponsitory brandReponsitory;

    @Override
    public ActionResult getAll() {
        ActionResult result = new ActionResult();
        List<Brand> brands = brandReponsitory.findAll();
        if (brands.isEmpty()){
            result.setErrorCodeEnum(ErrorCodeEnum.NO_CONTENT);
            return result;
        }

        List<BrandModel> brandModels = brands.stream().map(BrandModel::transform).collect(Collectors.toList());
        
        BrandOutDto brandOutDto = new BrandOutDto();
        brandOutDto.setBrands(brandModels);
        brandOutDto.setTotal(brands.size());
        
        result.setData(brandOutDto);

        return result;
    }

    @Override
    public ActionResult getById(Integer id) {
        ActionResult result = new ActionResult();
        
        Brand brand = brandReponsitory.getBrandById(id);
        if (brand == null){
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }
        
        result.setData(BrandModel.transform(brand));

        return result;
    }


    @Override
    public ActionResult create(BrandInDto brandIn) {
        ActionResult result = new ActionResult();
        
        Brand brand = new Brand();
        brand.setName(brandIn.getName());
        brand.setStatus(false);
        
        Brand brandTemp = brandReponsitory.save(brand);
        
        if (brandTemp == null){
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_CREATE);
            return result;
        }

        result.setData(BrandModel.transform(brandTemp));
        return result;
    }

    @Override
    public ActionResult update(BrandInDto newBrand, Integer id) {
        ActionResult result = new ActionResult();
        
        Brand brandTemp = brandReponsitory.getBrandById(id);
        
        if (brandTemp == null) {
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }
        
        brandTemp.setName(newBrand.getName());
        
        Brand brandUpdate = brandReponsitory.save(brandTemp);
        if (brandUpdate == null){
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_CREATE);
            return result;
        }
        
        result.setData(BrandModel.transform(brandUpdate));

        return result;
    }

    @Override
    public ActionResult updateStatus(Integer id, Boolean status) {
        ActionResult result = new ActionResult();
        
        Brand brand = brandReponsitory.getBrandById(id);
        if (brand == null) {
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }

        brand.setStatus(status);
        result.setData(BrandModel.transform(brand));
        return result;
    }

	@Override
	public ActionResult getActiveBrands(Integer page, Integer size) {
		 ActionResult result = new ActionResult();
	        Page<Brand> pageResult = brandReponsitory.getActiveBrands(PageRequest.of(page-1, size));
	        
	        if (pageResult.getNumberOfElements()==0){
	            result.setErrorCodeEnum(ErrorCodeEnum.NO_CONTENT);
	            return result;
	        }

	        List<BrandModel> brandModels = pageResult.get()
	        		.map(BrandModel::transform)
	        		.collect(Collectors.toList());
	        
	        BrandOutDto brandOutDto = new BrandOutDto();
	        brandOutDto.setBrands(brandModels);
	        brandOutDto.setTotal(pageResult.getNumberOfElements());
	        
	        result.setData(brandOutDto);

	        return result;
	}
}
