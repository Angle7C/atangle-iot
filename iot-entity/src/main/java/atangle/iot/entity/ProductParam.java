package atangle.iot.entity;

import org.atangle.framework.entity.BaseEntity;
import org.babyfish.jimmer.sql.Entity;

@Entity
public interface ProductParam extends BaseEntity {

    String paramType();

    Long ownerId();

    String code();

    String name();

    String dataType();

    String direction();

    boolean required();

    String unit();

    String enumValue();
}
