package r2s.MockProject.service;

import java.math.BigDecimal;

import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.ProductInDto;

public interface ProductService {
    ActionResult getAll(Integer page, Integer size); // Integer page, Integer size

    ActionResult getById(Integer id);
    
    ActionResult getActiveProductByActiveBrand(Integer brandId, Integer page, Integer size);

    ActionResult create(ProductInDto productIn);

    ActionResult update(ProductInDto productIn, Integer id);

    ActionResult updateStock(Integer id, Integer stock);
    
    ActionResult updateStatus(Integer id, boolean status);
    
    ActionResult findByNameContainingIgnoreCase(String name, Integer page, Integer size);
    
    ActionResult findByStatusIsTrueAndPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Integer page, Integer size);
}
