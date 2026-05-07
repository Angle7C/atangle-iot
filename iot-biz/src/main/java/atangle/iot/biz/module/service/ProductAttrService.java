package atangle.iot.biz.module.service;

import atangle.iot.entity.*;
import atangle.iot.entity.dto.ProductAttrPage;
import org.atangle.core.PageReq;
import org.atangle.core.exceptions.PIException;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.spring.repository.support.JRepositoryImpl;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode;
import org.babyfish.jimmer.sql.ast.mutation.SaveMode;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ProductAttrService extends JRepositoryImpl<ProductAttr, Long> {

    protected ProductAttrService(JSqlClient sqlClient) {
        super(sqlClient);
    }
    public Page<ProductAttr> page(PageReq<ProductAttrPage> req){
        return  sqlClient.createQuery(ProductAttrTable.$)
                .where(req.getQuery())
                .orderBy(ProductAttrTable.$.updatedTime().desc())
                .select(ProductAttrTable.$.fetch(ProductAttrFetcher.$.product()
                        .attrType().readType().code().name()))
                .fetchPage(req.getPageIndex(), req.getPageSize());
    }
    public void addProductAttr(ProductAttr  attr){
        boolean exists = sqlClient.createQuery(ProductAttrTable.$)
                .where(ProductAttrTable.$.productId().eq(attr.product().id()))
                .where(ProductAttrTable.$.code().eq(attr.code())).exists();
        if(exists){
            throw new RuntimeException("属性编码已存在");
        }
        sqlClient.saveCommand( attr).setMode(SaveMode.INSERT_ONLY).execute();
    }
    public void deleteProductAttr(Collection< Long> ids){
        sqlClient.deleteById(ProductAttr.class, ids);

    }
    public void updateProductAttr(ProductAttr attr){
        boolean exists = sqlClient.createQuery(ProductAttrTable.$)
                .where(ProductAttrTable.$.productId().eq(attr.product().id()))
                .where(ProductAttrTable.$.code().eq(attr.code()))
                .where(ProductAttrTable.$.id().ne(attr.id())).exists();
        if(exists){
            throw new RuntimeException("属性编码已存在");
        }
        //更新，不修改关联关系
        sqlClient.saveCommand( attr).setMode(SaveMode.UPDATE_ONLY)
                .setAssociatedMode(ProductAttrProps.PRODUCT,AssociatedSaveMode.UPDATE)
                .execute() ;


    }
    public ProductAttr detailProductAttr(Long id){
        return sqlClient.createQuery(ProductAttrTable.$)
                .where(ProductAttrTable.$.id().eq(id))
                .select(ProductAttrTable.$.fetch(ProductAttrFetcher.$.product()
                        .attrType().readType().code().name()))
                .fetchOptional().orElseThrow(()->new PIException("属性不存在"));

    }
}
