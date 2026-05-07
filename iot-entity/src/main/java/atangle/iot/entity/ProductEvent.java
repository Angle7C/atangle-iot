package atangle.iot.entity;

import atangle.iot.entity.Product;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import org.atangle.framework.entity.BaseEntity;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;

@Entity
public interface ProductEvent extends BaseEntity {

    String name();
    String code();


    /**
     * 备注
     */
    @Nullable
    String remark();

    /**
     * 事件类型
     */
    String eventType();

    @ManyToOne
    Product product();

    /**
     * 产品编码，对应product.productCode
     */
    @Nullable
    String productCode();
}