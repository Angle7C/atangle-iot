package atangle.iot.entity;

import atangle.iot.entity.Product;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.atangle.framework.entity.BaseEntity;
import org.babyfish.jimmer.sql.*;

@Entity
public interface ProductAttr extends BaseEntity {
    // 属性名称
    String name();
    // 属性code
    @Key
    String code();
    // 属性单位
    String unit();

    /**
     * 属性类型
     */

    String attrType();

    /**
     * 读取类型
     */
    boolean readType();

    /**
     * 默认值
     */
    @Nullable
    String defaultValue();

    /**
     * 备注
     */
    @Nullable
    String remark();

    @ManyToOne
    @Key
    Product product();

    /**
     * 产品编码，对应product.productCode
     */
    @Nullable
    String productCode();
}