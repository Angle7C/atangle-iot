package atangle.iot.entity;

import org.atangle.framework.entity.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.Table;

import java.util.List;

@Entity
@Table(name = "iot_product")
public interface Product extends BaseEntity {

    String name();
    @Key
    String code();

    String accessKey();

    String secretKey();

    String productType();

    default int liveTime(){
        return 0;
    };
    @OneToMany(mappedBy = "product")
    List<ProductAttr> attrs();
    @OneToMany(mappedBy = "product")
    List<ProductEvent> events();

    @OneToMany(mappedBy = "product")
    List< ProductService> services();
}
