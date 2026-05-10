package atangle.iot.biz.tdengine.service;

import atangle.iot.biz.tdengine.mapper.TDengineQueryMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@ConditionalOnBean(TDengineQueryMapper.class)
public class TDengineQueryService {

    private final TDengineQueryMapper queryMapper;

    public TDengineQueryService(TDengineQueryMapper queryMapper) {
        this.queryMapper = queryMapper;
    }

    public List<Map<String, Object>> selectLatest(String tableName, List<String> columns, Integer limit) {
        return queryMapper.selectLatest(tableName, columns, limit);
    }
}
