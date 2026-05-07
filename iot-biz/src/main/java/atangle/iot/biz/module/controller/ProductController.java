package atangle.iot.biz.module.controller;

import atangle.iot.biz.module.service.ProductAttrService;
import atangle.iot.biz.module.service.ProductEventService;
import atangle.iot.biz.module.service.ProductService;
import atangle.iot.biz.module.service.ProductThingServiceService;
import atangle.iot.entity.Product;
import atangle.iot.entity.ProductAttr;
import atangle.iot.entity.ProductEvent;
import atangle.iot.entity.ProductService;
import atangle.iot.entity.dto.ProductAttrPage;
import atangle.iot.entity.dto.ProductEventPage;
import atangle.iot.entity.dto.ProductInput;
import atangle.iot.entity.dto.ProductPage;
import atangle.iot.entity.dto.ProductServicePage;
import jakarta.annotation.Resource;
import org.atangle.core.PageReq;
import org.atangle.core.Resp;
import org.babyfish.jimmer.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    ProductService productService;
    @Resource
    ProductAttrService productAttrService;
    @Resource
    ProductThingServiceService productThingServiceService;
    @Resource
    ProductEventService productEventService;


    @PostMapping("/page")
    public Resp<Page<Product>> page(@RequestBody PageReq<ProductPage> req){
        return Resp.success( productService.page(req));
    }
    @GetMapping("/detail")
    public Resp<Product> detail(@RequestParam(name = "id") Long id){
        return Resp.success(productService.detail(id));
    }

    @PostMapping("/save")
    public Resp<Void> save(@RequestBody ProductInput product){
        productService.saveEntity(product.toEntity());
        return Resp.success();
    }

    @PostMapping("/update")
    public Resp<Void> update(@RequestBody ProductInput product,@RequestParam(name = "id") Long id){

        productService.updateEntity( product.toEntityById(id));
        return Resp.success();
    }
    @PostMapping("/delete")
    public Resp<Void> delete(@RequestBody List< Long> ids){
        productService.delete(ids);
        return Resp.success();
    }

    @PostMapping("/attr/page")
    public Resp<Page<ProductAttr>> attrPage(@RequestBody PageReq<ProductAttrPage> req){
        return Resp.success( productAttrService.page(req));
    }

    @PostMapping("/service/page")
    public Resp<Page<ProductService>> servicePage(@RequestBody PageReq<ProductServicePage> req){
        return Resp.success(productThingServiceService.page(req));
    }

    @PostMapping("/event/page")
    public Resp<Page<ProductEvent>> eventPage(@RequestBody PageReq<ProductEventPage> req){
        return Resp.success(productEventService.page(req));
    }
}
