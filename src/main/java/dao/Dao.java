package dao;

import mapper.RowMapper;
import util.StatementBuilder;

public abstract class Dao <T>{
    protected RowMapper mapper;
    protected StatementBuilder statementBuilder = new StatementBuilder();
    public abstract void save(T t);
    public abstract T find(long e);
    public abstract void update(T t);
    public abstract void delete(long e);
}
