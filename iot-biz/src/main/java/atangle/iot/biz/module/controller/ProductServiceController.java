package atangle.iot.biz.module.controller;

import atangle.iot.biz.module.service.ProductThingServiceService;
import atangle.iot.entity.dto.ProductServiceInput;
import jakarta.annotation.Resource;
import org.atangle.core.Resp;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productService")
public class ProductServiceController {

    @Resource
    private ProductThingServiceService productThingServiceService;

    @PostMapping("/save")
    public Resp<Void> save(@RequestBody ProductServiceInput productService) {
        productThingServiceService.addProductService(productService.toEntity());
        return Resp.success();
    }

    @PostMapping("/update")
    public Resp<Void> update(@RequestBody ProductServiceInput productService, @RequestParam(name = "id") Long id) {
        productThingServiceService.updateProductService(productService.toEntityById(id));
        return Resp.success();
    }

    @PostMapping("/delete")
    public Resp<Void> delete(@RequestBody List<Long> ids) {
        productThingServiceService.deleteProductService(ids);
        return Resp.success();
    }

    @GetMapping("/detail")
    public Resp<atangle.iot.entity.ProductService> detail(@RequestParam(name = "id") Long id) {
        return Resp.success(productThingServiceService.detailProductService(id));
    }
}
