package atangle.iot.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Nullable;
import org.atangle.framework.entity.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.Table;

@Entity
@Table(name = "iot_device_event_log")
public interface DeviceEventLog extends BaseEntity {

    @ManyToOne
    Device device();

    @Nullable
    Long productEventId();

    String eventCode();

    @Nullable
    String eventType();

    @Nullable
    JsonNode outputData();

    @Nullable
    String source();

    @Nullable
    String traceId();

    @Nullable
    java.time.OffsetDateTime eventTime();
}
