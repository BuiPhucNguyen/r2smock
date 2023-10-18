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
import r2s.MockProject.model.dto.ProductInDto;
import r2s.MockProject.model.dto.ProductOutDto;
import r2s.MockProject.model.entity.ProductModel;
import r2s.MockProject.repository.BrandReponsitory;
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

    @Autowired
    private BrandReponsitory brandReponsitory;

    @Override
    public ActionResult getAll(Integer page, Integer size) {
        // Integer page, Integer size
        ActionResult result = new ActionResult();

        Page<Product> productPage = productReponsitory.findAll(PageRequest.of(page - 1, size));

        if (productPage.isEmpty()){
            result.setErrorCodeEnum(ErrorCodeEnum.NO_CONTENT);
            return result;
        }

        List<ProductModel> productModels = productPage.stream().map(ProductModel::transform).collect(Collectors.toList());

        if (productModels.isEmpty()){
            result.setErrorCodeEnum(ErrorCodeEnum.NO_CONTENT);
            return result;
        }
        ProductOutDto OutDto = new ProductOutDto();
        OutDto.setProductModels(productModels);
        OutDto.setTotal(productModels.size());

        result.setData(OutDto);
        return result;
    }

    @Override
    public ActionResult getById(Integer id) {
        ActionResult result = new ActionResult();
        Product product = productReponsitory.getProductById(id);
        if (product == null){
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }

        result.setData(ProductModel.transform(product));
        return result;
    }

    @Override
    public ActionResult getByBrandId(Integer id) {
        ActionResult result = new ActionResult();
        Brand brand = brandReponsitory.getBrandById(id);
        if (brand == null){
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }
        List<Product> products = productReponsitory.findAll();
        if (products.isEmpty()){
            result.setErrorCodeEnum(ErrorCodeEnum.NO_CONTENT);
            return result;
        }
        List<Product> productsByBrand = new ArrayList<>();
        for(Product product : products){
            if (product.getBrand().getId().equals(brand.getId())){
                productsByBrand.add(product);
            }
        }

        if (productsByBrand.isEmpty()){
            result.setErrorCodeEnum(ErrorCodeEnum.NO_CONTENT);
            return result;
        }
        ProductOutDto dto = new ProductOutDto();
        dto.setProductModels(productsByBrand.stream().map(ProductModel::transform).collect(Collectors.toList()));
        dto.setTotal(productsByBrand.size());
        result.setData(dto);
        return result;
    }

    @Override
    public ActionResult create(ProductInDto productIn) {
        ActionResult result = new ActionResult();
        Brand brand = new Brand();
        Product product = new Product();

        brand = brandReponsitory.getBrandById(productIn.getBrandId());
        if (brand == null) {
            result.setErrorCodeEnum(ErrorCodeEnum.NO_HAVE_ID_BRAND);
            return result;
        }else {
            if (brand.getStatus() == false){
                result.setErrorCodeEnum(ErrorCodeEnum.NO_HAVE_ID_BRAND);
                return result;
            }
        }
        product.setBrand(brand);
        product.setName(productIn.getName());
        product.setDescription(productIn.getDescription());
        product.setPrice(productIn.getPrice());
        product.setSold(0);
        product.setStock(0);
        product.setStatus(false);

        product = productReponsitory.save(product);
        if (product == null){
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_CREATE);
            return result;
        }
        result.setData(ProductModel.transform(product));
        return result;
    }

    @Override
    public ActionResult update(ProductInDto productIn, Integer id) {
        ActionResult result = new ActionResult();
        Product updateP = productReponsitory.getProductById(id);
        if (updateP == null){
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }
        updateP.setName(productIn.getName());
        updateP.setDescription(productIn.getDescription());
        updateP.setPrice(productIn.getPrice());
//        productReponsitory.save(updateP);

        Product product = productReponsitory.save(updateP);

        result.setData(ProductModel.transform(product));
        return result;
    }

    @Override
    public ActionResult updateStock(Integer id, Integer addStock) {
        ActionResult result = new ActionResult();
        Product updateP = productReponsitory.getProductById(id);
        if (updateP == null){
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }
        updateP.setStock(updateP.getStock() + addStock);
        productReponsitory.save(updateP);
        result.setData(ProductModel.transform(updateP));
        return result;
    }

    @Override
    public ActionResult updateStatus(Integer id, boolean status) {
        ActionResult result = new ActionResult();
        Product updateP = productReponsitory.getProductById(id);
        if (updateP == null){
            result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
            return result;
        }
        updateP.setStatus(status);
        productReponsitory.save(updateP);
        result.setData(ProductModel.transform(updateP));
        return result;
    }
}
