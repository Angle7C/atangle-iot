package atangle.iot.biz.module.service;

import atangle.iot.entity.ProductService;
import atangle.iot.entity.ProductServiceFetcher;
import atangle.iot.entity.ProductServiceProps;
import atangle.iot.entity.ProductServiceTable;
import atangle.iot.entity.dto.ProductServicePage;
import org.atangle.core.PageReq;
import org.atangle.core.exceptions.PIException;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.spring.repository.support.JRepositoryImpl;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode;
import org.babyfish.jimmer.sql.ast.mutation.SaveMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class ProductThingServiceService extends JRepositoryImpl<ProductService, Long> {

    protected ProductThingServiceService(JSqlClient sqlClient) {
        super(sqlClient);
    }

    public Page<ProductService> page(PageReq<ProductServicePage> req) {
        return sqlClient.createQuery(ProductServiceTable.$)
                .where(req.getQuery())
                .orderBy(ProductServiceTable.$.updatedTime().desc())
                .select(ProductServiceTable.$.fetch(
                        ProductServiceFetcher.$.name().code().async().remark().result().product()
                ))
                .fetchPage(req.getPageIndex(), req.getPageSize());
    }

    @Transactional
    public void addProductService(ProductService service) {
        boolean exists = sqlClient.createQuery(ProductServiceTable.$)
                .where(ProductServiceTable.$.productId().eq(service.product().id()))
                .where(ProductServiceTable.$.code().eq(service.code()))
                .exists();
        if (exists) {
            throw new PIException("服务编码已存在");
        }
        sqlClient.saveCommand(service)
                .setMode(SaveMode.INSERT_ONLY)
                .execute();
    }

    @Transactional
    public void updateProductService(ProductService service) {
        boolean exists = sqlClient.createQuery(ProductServiceTable.$)
                .where(ProductServiceTable.$.productId().eq(service.product().id()))
                .where(ProductServiceTable.$.code().eq(service.code()))
                .where(ProductServiceTable.$.id().ne(service.id()))
                .exists();
        if (exists) {
            throw new PIException("服务编码已存在");
        }
        sqlClient.saveCommand(service)
                .setMode(SaveMode.UPDATE_ONLY)
                .setAssociatedMode(ProductServiceProps.PRODUCT, AssociatedSaveMode.UPDATE)
                .execute();
    }

    @Transactional
    public void deleteProductService(Collection<Long> ids) {
        sqlClient.deleteById(ProductService.class, ids);
    }

    public ProductService detailProductService(Long id) {
        return sqlClient.createQuery(ProductServiceTable.$)
                .where(ProductServiceTable.$.id().eq(id))
                .select(ProductServiceTable.$.fetch(
                        ProductServiceFetcher.$.name().code().async().remark().result().product()
                ))
                .fetchOptional()
                .orElseThrow(() -> new PIException("服务不存在"));
    }
}
