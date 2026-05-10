package atangle.iot.biz.tdengine.mapper;

import java.util.List;

public class TDengineSqlProvider {

    public String selectLatest(String tableName, List<String> columns, Integer limit) {
        validateIdentifier(tableName, "tableName");
        String selectColumns = (columns == null || columns.isEmpty()) ? "*" : joinColumns(columns);
        int queryLimit = limit == null || limit <= 0 ? 100 : limit;
        return "SELECT " + selectColumns + " FROM " + tableName + " LIMIT " + queryLimit;
    }

    private String joinColumns(List<String> columns) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            validateIdentifier(column, "column");
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(column);
        }
        return builder.toString();
    }

    private void validateIdentifier(String identifier, String fieldName) {
        if (identifier == null || identifier.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        if (!identifier.matches("[A-Za-z0-9_\\.]+")) {
            throw new IllegalArgumentException(fieldName + " contains illegal characters: " + identifier);
        }
    }
}
