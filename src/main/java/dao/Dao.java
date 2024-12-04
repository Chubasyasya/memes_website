package dao;

import mapper.RowMapper;

public abstract class Dao <T>{
    protected RowMapper mapper;
    public abstract T save(T t);
    public abstract T find(long e);
    public abstract boolean update(T t);
    public abstract int delete(long e);
}
