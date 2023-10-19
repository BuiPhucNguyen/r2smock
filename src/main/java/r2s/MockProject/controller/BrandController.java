package r2s.MockProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

    @GetMapping("/") //user admin
    public ResponseModel getAll(){
        ActionResult result = null;
        try {
            result = brandService.getAll();
        }catch(Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }
    
    @GetMapping("/active") // user admin
    public ResponseModel getActiveBrands(@Param(value = "page") Integer page, @Param(value = "size") Integer size){
        ActionResult result = null;
        try {
            result = brandService.getActiveBrands(page,size);
        }catch(Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @GetMapping("/{id}") // user admin
    public ResponseModel getById(@PathVariable Integer id){
        ActionResult result = null;
        try {
            result = brandService.getById(id);
        }catch(Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @PostMapping("/") // admin
    public ResponseModel create(@RequestBody BrandInDto brand){
        ActionResult result = null;
        try {
            result = brandService.create(brand);
        }catch(Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @PutMapping("/{id}") // admin
    public ResponseModel update(@RequestBody BrandInDto brand, @PathVariable Integer id){
        ActionResult result = null;
        try {
            result = brandService.update(brand, id);
        }catch(Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @PutMapping("/{id}/status/{status}") // admin
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
