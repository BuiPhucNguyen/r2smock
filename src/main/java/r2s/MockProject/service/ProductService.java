package r2s.MockProject.service;

import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.ProductInDto;

public interface ProductService {
    ActionResult getAll(Integer page, Integer size); // Integer page, Integer size

    ActionResult getById(Integer id);

    ActionResult create(ProductInDto productIn);

    ActionResult update(ProductInDto productIn, Integer id);

    ActionResult updateStock(Integer id, Integer stock);
    ActionResult updateStatus(Integer id, boolean status);
}
