package atangle.iot.biz.tdengine.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class TDengineRow {

    private Map<String, Object> values = new LinkedHashMap<>();

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }
}
