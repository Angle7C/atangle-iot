package atangle.iot.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Nullable;
import org.atangle.framework.entity.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.Table;

import java.util.List;

@Entity
@Table(name = "iot_device")
public interface Device extends BaseEntity {

    String name();

    @Key
    String code();

    @Key
    String sn();

    @ManyToOne
    Product product();

    @Nullable
    String productCode();

    String deviceType();

    String status();

    String onlineState();

    @Nullable
    String accessToken();

    @Nullable
    String secretKey();

    @Nullable
    Long gatewayId();

    @Nullable
    JsonNode config();

    @Nullable
    String remark();

    @Nullable
    java.time.OffsetDateTime activeTime();

    @Nullable
    java.time.OffsetDateTime lastOnlineTime();

    @Nullable
    java.time.OffsetDateTime lastOfflineTime();

    @OneToMany(mappedBy = "device")
    List<DeviceAttr> attrs();

    @OneToMany(mappedBy = "device")
    List<DeviceServiceLog> serviceLogs();

    @OneToMany(mappedBy = "device")
    List<DeviceEventLog> eventLogs();
}
