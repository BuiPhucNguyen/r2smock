package r2s.MockProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.ResponseBuild;
import r2s.MockProject.model.ResponseModel;
import r2s.MockProject.model.dto.BrandInDto;
import r2s.MockProject.service.BrandService;

@RestController
@RequestMapping(path = "/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ResponseBuild responseBuild;

    @GetMapping("/")
    public ResponseModel getAll(){
        ActionResult result = null;
        try {
            result = brandService.getAll();
        }catch(Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }
    
    @GetMapping("/status/{status}")
    public ResponseModel getAllStatusTrue(@PathVariable Boolean status){
        ActionResult result = null;
        try {
            result = brandService.getAllBrandByStatus(status);
        }catch(Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @GetMapping("/{id}")
    public ResponseModel getById(@PathVariable Integer id){
        ActionResult result = null;
        try {
            result = brandService.getById(id);
        }catch(Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @PostMapping("/")
    public ResponseModel create(@RequestBody BrandInDto brand){
        ActionResult result = null;
        try {
            result = brandService.create(brand);
        }catch(Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @PutMapping("/{id}")
    public ResponseModel update(@RequestBody BrandInDto brand, @PathVariable Integer id){
        ActionResult result = null;
        try {
            result = brandService.update(brand, id);
        }catch(Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @PutMapping("/status/{id}_{status}")
    public ResponseModel updateStatus(@PathVariable Integer id, @PathVariable boolean status){
        ActionResult result = null;
        try {
            result = brandService.updateStatus(id, status);
        }catch(Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }
}
