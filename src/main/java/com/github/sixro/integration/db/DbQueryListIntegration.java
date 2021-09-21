package com.github.sixro.integration.db;

import com.github.sixro.integration.Integration;
import org.apache.commons.collections4.Transformer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.List;
import java.util.Map;

public class DbQueryListIntegration<I, O> implements Integration<I, List<O>> {

    private final NamedParameterJdbcOperations jdbcOperations;
    private final String sql;
    private final Transformer<I, Map<String, Object>> parametersFromInput;
    private final RowMapper<O> rowMapper;

    public DbQueryListIntegration(NamedParameterJdbcOperations jdbcOperations, String sql, Transformer<I, Map<String, Object>> parametersFromInput, RowMapper<O> rowMapper) {
        this.jdbcOperations = jdbcOperations;
        this.sql = sql;
        this.parametersFromInput = parametersFromInput;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<O> run(I input) {
        Map<String, Object> params = parametersFromInput.transform(input);
        List<O> list = jdbcOperations.query(sql, params, rowMapper);
        return list;
    }

}
