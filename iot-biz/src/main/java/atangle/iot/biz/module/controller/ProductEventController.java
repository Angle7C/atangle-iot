package atangle.iot.biz.module.controller;

import atangle.iot.biz.module.service.ProductEventService;
import atangle.iot.entity.ProductEvent;
import atangle.iot.entity.dto.ProductEventInput;
import jakarta.annotation.Resource;
import org.atangle.core.Resp;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productEvent")
public class ProductEventController {

    @Resource
    private ProductEventService productEventService;

    @PostMapping("/save")
    public Resp<Void> save(@RequestBody ProductEventInput productEvent) {
        productEventService.addProductEvent(productEvent.toEntity());
        return Resp.success();
    }

    @PostMapping("/update")
    public Resp<Void> update(@RequestBody ProductEventInput productEvent, @RequestParam(name = "id") Long id) {
        productEventService.updateProductEvent(productEvent.toEntityById(id));
        return Resp.success();
    }

    @PostMapping("/delete")
    public Resp<Void> delete(@RequestBody List<Long> ids) {
        productEventService.deleteProductEvent(ids);
        return Resp.success();
    }

    @GetMapping("/detail")
    public Resp<ProductEvent> detail(@RequestParam(name = "id") Long id) {
        return Resp.success(productEventService.detailProductEvent(id));
    }
}
