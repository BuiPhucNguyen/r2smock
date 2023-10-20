package r2s.MockProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.ResponseBuild;
import r2s.MockProject.model.ResponseModel;
import r2s.MockProject.model.dto.ProductInDto;
import r2s.MockProject.service.ProductService;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ResponseBuild responseBuild;

    @GetMapping("/all") // admin
    public ResponseModel  getAll(@RequestParam Integer page,@RequestParam Integer size){
        // @RequestParam Integer page,@RequestParam Integer size
        ActionResult result = null;
        try {
            result = productService.getAll(page, size);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @GetMapping("/{id}") // user admin
    public ResponseModel getByid(@PathVariable Integer id){
        ActionResult result = null;
        try {
            result = productService.getById(id);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }
    @GetMapping("/brand/{id}") // user admin
    public ResponseModel getActiveProductByActiveBrand(@PathVariable Integer id, @RequestParam Integer page,@RequestParam Integer size){
        ActionResult result = null;
        try {
            result = productService.getActiveProductByActiveBrand(id, page, size);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }
    
    @GetMapping("/name/{name}") // user admin
    public ResponseModel findByNameContainingIgnoreCase(@PathVariable String name, @RequestParam Integer page,@RequestParam Integer size){
        ActionResult result = null;
        try {
            result = productService.findByNameContainingIgnoreCase(name, page, size);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @PostMapping("/") // admin
    public ResponseModel create(@RequestBody ProductInDto productIn){
        ActionResult result = null;
        try {
            result = productService.create(productIn);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }


    @PutMapping("/{id}") // admin
    public ResponseModel update(@RequestBody ProductInDto productIn, @PathVariable Integer id){
        ActionResult result = null;
        try {
            result = productService.update(productIn, id);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @PutMapping("/{id}/stock/{add}") // admin
    public ResponseModel updateStock(@PathVariable Integer id,@PathVariable Integer add){
        ActionResult result = null;
        try {
            result = productService.updateStock(id, add);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @PutMapping("/{id}/status/{status}") // admin
    public ResponseModel updateStock(@PathVariable Integer id,@PathVariable boolean status){
        ActionResult result = null;
        try {
            result = productService.updateStatus(id, status);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }
}
