package web.framework.buz;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoTemplate;

public interface Service {
	
	public MongoTemplate getMongoTemplate();
	
	public boolean executeBySql(String sql, Object... parameters) throws SQLException;

	public List<Map<String, String>> selectBySql(String sql, Object... parameters) throws SQLException;

	public int executeUpdateBySql(String sql, Object... parameters) throws SQLException;

	public <T> void delete(T object) throws RuntimeException;

	public int executeUpdateByHql(String hql, Object... parameters) throws RuntimeException;

	public int executeUpdateByNql(String nql, Object... parameters) throws RuntimeException;

	public <T> Serializable insert(T object) throws RuntimeException;

	public <T> List<T> selectByHql(String hql, Object... parameters) throws RuntimeException;

	public <T> List<T> selectByHqlPaging(String hql, int start, int limit, Object... parameters) throws RuntimeException;

	public <T> List<T> selectByExample(T object, int start, int limit) throws RuntimeException;

	public <T> List<T> selectByExample(T object) throws RuntimeException;

	public List<?> selectByNql(String nql, Object... parameters) throws RuntimeException;

	public List<?> selectByNqlClasses(String nql, String[] alias, Class<?>[] clazzes, Object... parameters) throws RuntimeException;

	public List<?> selectByNqlPaging(String nql, int start, int limit, Object... parameters) throws RuntimeException;

	public List<?> selectByNqlClassesPaging(String nql, int start, int limit, String[] alias, Class<?>[] clazzes, Object... parameters) throws RuntimeException;

	public long selectCountByHql(String hql, Object... parameters) throws RuntimeException;

	public long selectCountByNql(String nql, Object... parameters) throws RuntimeException;

	public long selectCountByNqlClasses(String nql, String[] alias, Class<?>[] clazzes, Object... parameters) throws RuntimeException;

	public <T> void update(T object) throws RuntimeException;

	public <T> void insertOrUpdate(T object) throws RuntimeException;

	public <ID extends Serializable, T> T selectById(Class<T> clazz, ID id) throws RuntimeException;

	public <ID extends Serializable, T> T selectById(String entityName, ID id) throws RuntimeException;

	public <T> void delete(String entityName, T object) throws RuntimeException;

	public <T> void insert(String entityName, T object) throws RuntimeException;

	public <T> void update(String entityName, T object) throws RuntimeException;

	public <T> void insertOrUpdate(String entityName, T object) throws RuntimeException;
	
}