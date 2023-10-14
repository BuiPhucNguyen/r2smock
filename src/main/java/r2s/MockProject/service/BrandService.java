package r2s.MockProject.service;

import r2s.MockProject.entity.Brand;
import r2s.MockProject.model.ActionResult;


public interface BrandService {
    ActionResult getAll();

    ActionResult getById(Integer id);

    ActionResult create(Brand brand);

    ActionResult update(Brand brand, Integer id);

    ActionResult delete(Integer id);
}
