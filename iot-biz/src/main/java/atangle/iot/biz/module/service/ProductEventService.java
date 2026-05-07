package atangle.iot.biz.module.service;

import atangle.iot.entity.ProductEvent;
import atangle.iot.entity.ProductEventFetcher;
import atangle.iot.entity.ProductEventProps;
import atangle.iot.entity.ProductEventTable;
import atangle.iot.entity.dto.ProductEventPage;
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
public class ProductEventService extends JRepositoryImpl<ProductEvent, Long> {

    protected ProductEventService(JSqlClient sqlClient) {
        super(sqlClient);
    }

    public Page<ProductEvent> page(PageReq<ProductEventPage> req) {
        return sqlClient.createQuery(ProductEventTable.$)
                .where(req.getQuery())
                .orderBy(ProductEventTable.$.updatedTime().desc())
                .select(ProductEventTable.$.fetch(
                        ProductEventFetcher.$.name().code().eventType().remark().product()
                ))
                .fetchPage(req.getPageIndex(), req.getPageSize());
    }

    @Transactional
    public void addProductEvent(ProductEvent event) {
        boolean exists = sqlClient.createQuery(ProductEventTable.$)
                .where(ProductEventTable.$.productId().eq(event.product().id()))
                .where(ProductEventTable.$.code().eq(event.code()))
                .exists();
        if (exists) {
            throw new PIException("事件编码已存在");
        }
        sqlClient.saveCommand(event)
                .setMode(SaveMode.INSERT_ONLY)
                .execute();
    }

    @Transactional
    public void updateProductEvent(ProductEvent event) {
        boolean exists = sqlClient.createQuery(ProductEventTable.$)
                .where(ProductEventTable.$.productId().eq(event.product().id()))
                .where(ProductEventTable.$.code().eq(event.code()))
                .where(ProductEventTable.$.id().ne(event.id()))
                .exists();
        if (exists) {
            throw new PIException("事件编码已存在");
        }
        sqlClient.saveCommand(event)
                .setMode(SaveMode.UPDATE_ONLY)
                .setAssociatedMode(ProductEventProps.PRODUCT, AssociatedSaveMode.UPDATE)
                .execute();
    }

    @Transactional
    public void deleteProductEvent(Collection<Long> ids) {
        sqlClient.deleteById(ProductEvent.class, ids);
    }

    public ProductEvent detailProductEvent(Long id) {
        return sqlClient.createQuery(ProductEventTable.$)
                .where(ProductEventTable.$.id().eq(id))
                .select(ProductEventTable.$.fetch(
                        ProductEventFetcher.$.name().code().eventType().remark().product()
                ))
                .fetchOptional()
                .orElseThrow(() -> new PIException("事件不存在"));
    }
}
