package r2s.MockProject.service;

import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.BrandInDto;


public interface BrandService {
    ActionResult getAll();

    ActionResult getById(Integer id);

    ActionResult create(BrandInDto brand);

    ActionResult update(BrandInDto brand, Integer id);

    ActionResult delete(Integer id);
}
