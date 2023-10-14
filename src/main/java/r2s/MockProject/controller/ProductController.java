package r2s.MockProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import r2s.MockProject.entity.Product;
import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.ResponseBuild;
import r2s.MockProject.model.ResponseModel;
import r2s.MockProject.service.ProductService;

@RestController
@RequestMapping(name = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ResponseBuild responseBuild;

    @GetMapping("/")
    public ResponseModel  getAll(@RequestParam Integer page,@RequestParam Integer size){
        ActionResult result = null;
        try {
            result = productService.getAll(page, size);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @GetMapping("/{id}")
    public ResponseModel getByid(@PathVariable Integer id){
        ActionResult result = null;
        try {
            result = productService.getById(id);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @PutMapping("/")
    public ResponseModel create(@RequestBody Product product){
        ActionResult result = null;
        try {
            result = productService.create(product);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }


    @PostMapping("/{id}")
    public ResponseModel update(@RequestBody Product product, @PathVariable Integer id){
        ActionResult result = null;
        try {
            result = productService.update(product, id);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @PostMapping("/{id}+{add}")
    public ResponseModel updateStock(@PathVariable Integer id,@PathVariable Integer add){
        ActionResult result = null;
        try {
            result = productService.updateStock(id, add);
        }catch (Exception e){
            result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return responseBuild.build(result);
    }

    @PostMapping("/{id}+{status}")
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
