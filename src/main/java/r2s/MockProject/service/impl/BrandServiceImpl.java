package r2s.MockProject.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import r2s.MockProject.entity.Brand;
import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.model.ActionResult;

import r2s.MockProject.model.dto.BrandOutDto;
import r2s.MockProject.model.entity.BrandModel;
import r2s.MockProject.repository.BrandReponsitory;
import r2s.MockProject.service.BrandService;

import java.util.ArrayList;
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
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }
        List<BrandModel> brandModels = brands.stream().map(BrandModel::transform).collect(Collectors.toList());
        BrandOutDto brandOutDto = new BrandOutDto();
        brandOutDto.setBrands(brandModels);
        brandOutDto.setTotal(brands.size());
        result.setData(brandOutDto);
        result.setErrorCodeEnum(ErrorCodeEnum.OK);
        return result;
    }

    @Override
    public ActionResult getById(Integer id) {
        ActionResult result = new ActionResult();
        List<Brand> brands = new ArrayList<>();
        Brand brand = brandReponsitory.getBrandById(id);
        if (brand == null){
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }
        brands.add(brand);
        List<BrandModel> brandModels = brands.stream().map(BrandModel::transform).collect(Collectors.toList());
        BrandOutDto brandOutDto = new BrandOutDto();
        brandOutDto.setBrands(brandModels);
        brandOutDto.setTotal(brands.size());
        result.setData(brandOutDto);
        result.setErrorCodeEnum(ErrorCodeEnum.OK);
        return result;
    }

    @Override
    public ActionResult create(Brand brand) {
        ActionResult result = new ActionResult();
        Brand brandTemp = brandReponsitory.save(brand);
        if (brandTemp == null){
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_CREATE);
            return result;
        }
        result.setErrorCodeEnum(ErrorCodeEnum.OK);
        result.setData(brandTemp);
        return result;
    }

    @Override
    public ActionResult update(Brand brand, Integer id) {
        ActionResult result = new ActionResult();
        Brand brandTemp = brandReponsitory.getBrandById(id);
        if (brandTemp == null) {
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }
        brandTemp.setName(brand.getName());
        result.setData(brandTemp);
        result.setErrorCodeEnum(ErrorCodeEnum.OK);
        return result;
    }

    @Override
    public ActionResult delete(Integer id) {
        ActionResult result = new ActionResult();
        Brand brand = brandReponsitory.getBrandById(id);
        Brand brandDelete = brand;
        if (brand == null) {
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }
        brandReponsitory.delete(brand);
        result.setData(brandDelete);
        result.setErrorCodeEnum(ErrorCodeEnum.OK);
        return result;
    }
}
