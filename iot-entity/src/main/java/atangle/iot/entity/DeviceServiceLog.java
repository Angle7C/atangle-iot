package atangle.iot.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Nullable;
import org.atangle.framework.entity.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.Table;

@Entity
@Table(name = "iot_device_service_log")
public interface DeviceServiceLog extends BaseEntity {

    @ManyToOne
    Device device();

    @Nullable
    Long productServiceId();

    String serviceCode();

    @Nullable
    JsonNode inputParam();

    @Nullable
    JsonNode outputResult();

    String status();

    @Nullable
    String message();

    @Nullable
    String traceId();

    @Nullable
    java.time.OffsetDateTime invokeTime();

    @Nullable
    java.time.OffsetDateTime finishTime();
}
