package atangle.iot.entity;

import atangle.iot.entity.Product;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import org.atangle.framework.entity.BaseEntity;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;

@Entity
public interface ProductService extends BaseEntity {

    String name();
    String code();

    /**
     * 返回结果定义(JSONB格式)
     */
    @Nullable
    JsonNode result();

    /**
     * 备注
     */
    @Nullable
    String remark();

    /**
     * 是否异步执行
     */
    boolean async();

    @ManyToOne
    Product product();


    /**
     * 产品编码，对应product.productCode
     */
    @Nullable
    String productCode();
}