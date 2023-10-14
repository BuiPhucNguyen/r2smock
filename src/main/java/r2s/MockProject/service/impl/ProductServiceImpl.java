package r2s.MockProject.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import r2s.MockProject.entity.Brand;
import r2s.MockProject.entity.Product;
import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.BrandOutDto;
import r2s.MockProject.model.dto.ProductOutDto;
import r2s.MockProject.model.entity.AccountModel;
import r2s.MockProject.model.entity.BrandModel;
import r2s.MockProject.model.entity.ProductModel;
import r2s.MockProject.repository.ProductReponsitory;
import r2s.MockProject.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductReponsitory productReponsitory;
    @Override
    public ActionResult getAll(Integer page, Integer size) {
        ActionResult result = new ActionResult();
        Page<Product> productPage = productReponsitory.findAll(PageRequest.of(page - 1, size));
        if (productPage.isEmpty()){
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }
        List<ProductModel> productModels = productPage.get().map(ProductModel::transform).collect(Collectors.toList());
        ProductOutDto OutDto = new ProductOutDto();
        OutDto.setProductModels(productModels);
        OutDto.setTotal(productPage.getNumberOfElements());
        result.setData(OutDto);
        result.setErrorCodeEnum(ErrorCodeEnum.OK);
        return result;
    }

    @Override
    public ActionResult getById(Integer id) {
        ActionResult result = new ActionResult();
        List<Product> products = new ArrayList<>();
        Product product = productReponsitory.getProductById(id);
        if (product == null){
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }
        products.add(product);
        List<ProductModel> productModels = products.stream().map(ProductModel::transform).collect(Collectors.toList());
        ProductOutDto productOutDto = new ProductOutDto();
        productOutDto.setProductModels(productModels);
        productOutDto.setTotal(products.size());
        result.setData(productOutDto);
        result.setErrorCodeEnum(ErrorCodeEnum.OK);
        return result;
    }

    @Override
    public ActionResult create(Product product) {
        ActionResult result = new ActionResult();
        Product productTemp = productReponsitory.save(product);
        if (productTemp == null){
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_CREATE);
            return result;
        }
        result.setErrorCodeEnum(ErrorCodeEnum.OK);
        result.setData(productTemp);
        return result;
    }

    @Override
    public ActionResult update(Product product, Integer id) {
        ActionResult result = new ActionResult();
        Product updateP = productReponsitory.getProductById(id);
        if (updateP == null){
            result.setErrorCodeEnum(ErrorCodeEnum.OK);
            return result;
        }
        updateP.setName(product.getName());
        updateP.setDescription(product.getDescription());
        updateP.setPrice(product.getPrice());
        result.setErrorCodeEnum(ErrorCodeEnum.OK);
        result.setData(updateP);
        return result;
    }

    @Override
    public ActionResult updateStock(Integer id, Integer addStock) {
        ActionResult result = new ActionResult();
        Product updateP = productReponsitory.getProductById(id);
        if (updateP == null){
            result.setErrorCodeEnum(ErrorCodeEnum.OK);
            return result;
        }
        updateP.setStock(updateP.getStock()+addStock);
        result.setErrorCodeEnum(ErrorCodeEnum.OK);
        result.setData(updateP);
        return result;
    }

    @Override
    public ActionResult updateStatus(Integer id, boolean status) {
        ActionResult result = new ActionResult();
        Product updateP = productReponsitory.getProductById(id);
        if (updateP == null){
            result.setErrorCodeEnum(ErrorCodeEnum.OK);
            return result;
        }
        updateP.setStatus(status);
        result.setErrorCodeEnum(ErrorCodeEnum.OK);
        result.setData(updateP);
        return result;
    }
}
