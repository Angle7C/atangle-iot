package atangle.iot.biz.module.service;

import atangle.iot.entity.Product;
import atangle.iot.entity.ProductAttrFetcher;
import atangle.iot.entity.ProductEventFetcher;
import atangle.iot.entity.ProductFetcher;
import atangle.iot.entity.ProductServiceFetcher;
import atangle.iot.entity.ProductTable;
import atangle.iot.entity.dto.ProductPage;
import org.atangle.core.PageReq;
import org.atangle.core.exceptions.PIException;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.spring.repository.support.JRepositoryImpl;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.SaveMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class ProductService extends JRepositoryImpl<Product, Long> {
    protected ProductService(JSqlClient sqlClient) {
        super(sqlClient);
    }

    public Page<Product> page(PageReq<ProductPage> req){
       return  sqlClient.createQuery(ProductTable.$)
                .where(req.getQuery())
                .orderBy(ProductTable.$.updatedTime().desc())
                .select(ProductTable.$.fetch(ProductFetcher.$.name()
                                .liveTime()
                                .productType()
                        .code().createdTime()))
                .fetchPage(req.getPageIndex(), req.getPageSize());
    }

    public Product detail(Long id){
        Optional<Product> product = sqlClient.createQuery(ProductTable.$)
                .where(ProductTable.$.id().eq(id))
                .select(ProductTable.$.fetch(ProductFetcher.$.name().code()
                        .accessKey()
                        .secretKey()
                        .productType()
                        .liveTime()
                        .createdTime()
                        .attrs(ProductAttrFetcher.$.name().code().unit().attrType().readType().defaultValue().remark())
                        .services(ProductServiceFetcher.$.name().code().async().remark().result())
                        .events(ProductEventFetcher.$.name().code().eventType().remark())))
                .fetchOptional();
        return product.orElseThrow(()->new PIException("设备型号不存在"));
    }
    @Transactional
    public void saveEntity(Product product){
        sqlClient.saveCommand(product).setMode(SaveMode.INSERT_ONLY)
                .execute();
        return;
    }
    @Transactional
    public void updateEntity(Product product){
        sqlClient.saveCommand(product)
                .setMode(SaveMode.UPDATE_ONLY)
                .execute();
        return;
    }
    @Transactional
    public void delete(Collection<Long> ids){
        sqlClient.deleteById(Product.class, ids);
    }
}
