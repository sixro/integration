package com.github.sixro.integration.db;

import com.github.sixro.integration.Integration;
import org.apache.commons.collections4.Transformer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.Map;

public class DbQueryIntegration<I, O> implements Integration<I, O> {

    private final NamedParameterJdbcOperations jdbcOperations;
    private final String sql;
    private final Transformer<I, Map<String, Object>> parametersFromInput;
    private final RowMapper<O> rowMapper;

    public DbQueryIntegration(NamedParameterJdbcOperations jdbcOperations, String sql, Transformer<I, Map<String, Object>> parametersFromInput, RowMapper<O> rowMapper) {
        this.jdbcOperations = jdbcOperations;
        this.sql = sql;
        this.parametersFromInput = parametersFromInput;
        this.rowMapper = rowMapper;
    }

    @Override
    public O run(I input) {
        Map<String, Object> params = parametersFromInput.transform(input);
        O object = jdbcOperations.queryForObject(sql, params, rowMapper);
        return object;
    }

}
