package r2s.MockProject.service;

import r2s.MockProject.entity.Product;
import r2s.MockProject.model.ActionResult;

public interface ProductService {
    ActionResult getAll(Integer page, Integer size);

    ActionResult getById(Integer id);

    ActionResult create(Product product);

    ActionResult update(Product product, Integer id);

    ActionResult updateStock(Integer id, Integer addStock);
    ActionResult updateStatus(Integer id, boolean status);
}
