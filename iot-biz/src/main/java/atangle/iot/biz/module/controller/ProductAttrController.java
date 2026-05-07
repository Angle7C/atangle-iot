package atangle.iot.biz.module.controller;

import atangle.iot.biz.module.service.ProductAttrService;
import atangle.iot.entity.ProductAttr;
import atangle.iot.entity.dto.ProductAttrInput;
import jakarta.annotation.Resource;
import org.atangle.core.Resp;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productAttr")
public class ProductAttrController {
    @Resource
    private ProductAttrService productAttrService;
    @PostMapping("/save")
    public Resp<Void> save(@RequestBody ProductAttrInput product){
        productAttrService.addProductAttr(product.toEntity());
        return Resp.success();
    }
    @PostMapping("/update")
    public Resp<Void> update(@RequestBody ProductAttrInput product,@RequestParam(name = "id") Long id){
        productAttrService.updateProductAttr(product.toEntityById(id));
        return Resp.success();
    }
    @PostMapping("/delete")
    public Resp<Void> delete(@RequestBody List< Long> ids){
        productAttrService.deleteProductAttr(ids);
        return Resp.success();
    }
    @GetMapping("/detail")
    public Resp<ProductAttr> detail(@RequestParam(name = "id") Long id){
        return Resp.success(productAttrService.detailProductAttr(id));
    }
}
