package atangle.iot.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Nullable;
import org.atangle.framework.entity.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.Table;

@Entity
@Table(name = "iot_device_attr")
public interface DeviceAttr extends BaseEntity {

    @ManyToOne
    @Key
    Device device();

    @Nullable
    Long productAttrId();

    @Key
    String attrCode();

    String attrName();

    @Nullable
    String valueType();

    @Nullable
    JsonNode value();

    @Nullable
    String source();

    @Nullable
    Integer quality();

    @Nullable
    String remark();

    @Nullable
    java.time.OffsetDateTime reportTime();

    @Nullable
    java.time.OffsetDateTime writeTime();
}
