package atangle.iot.biz.tdengine.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface TDengineQueryMapper {

    @SelectProvider(type = TDengineSqlProvider.class, method = "selectLatest")
    List<Map<String, Object>> selectLatest(
            @Param("tableName") String tableName,
            @Param("columns") List<String> columns,
            @Param("limit") Integer limit
    );
}
