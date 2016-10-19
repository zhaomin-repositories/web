package web.framework.buz.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import web.framework.dao.Dao;

@Service("service")
@SuppressWarnings("unchecked")
public class ServiceImpl implements web.framework.buz.Service {
	
	private static final Log log = LogFactory.getLog(ServiceImpl.class);
	
	@Resource
	private Dao dao;

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	@Resource
	private MongoTemplate mongoTemplate;

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public boolean executeBySql(String sql, Object... parameters) throws SQLException {
		try {
			log.info("execute:"+sql);
			return getDao().executeBySql(sql, parameters);
		} catch (SQLException e) {
			log.error(e); 
			log.error(sql);
			throw e;
		}
	}

	public List<Map<String,String>> selectBySql(String sql, Object... parameters) throws SQLException {
		try {
			return getDao().selectBySql(sql, parameters);
		} catch (SQLException e) {
			log.error(e);
			log.error(sql);
			throw e;
		}
	}

	public int executeUpdateBySql(String sql, Object... parameters) throws SQLException {
		try {
			return getDao().executeUpdateBySql(sql, parameters);
		} catch (SQLException e) {
			log.error(e);
			log.error(sql);
			throw e;
		}
	}
	
	public <T> void delete(T object) throws RuntimeException {
		try {
			getDao().delete(object);
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		} 
	}

	public int executeUpdateByHql(String hql, Object... parameters) throws RuntimeException {
		try {
			return getDao().executeUpdateByHql(hql, parameters);
		} catch (RuntimeException e) {
			log.error(e);
			log.error(hql);
			throw e;
		}
	}

	public int executeUpdateByNql(String nql, Object... parameters) throws RuntimeException {
		try {
			return getDao().executeUpdateByNql(nql, parameters);
		} catch (RuntimeException e) {
			log.error(e);
			log.error(nql);
			throw e;
		}
	}

	public <T> Serializable insert(T object) throws RuntimeException {
	    Serializable pKey;
		try {
			pKey = getDao().insert(object);
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
		return pKey;
	}

	public <T> List<T> selectByHql(String hql, Object... parameters) throws RuntimeException {
		try {
			return getDao().selectByHql(hql, parameters);
		} catch (RuntimeException e) {
			log.error(e);
			log.error(hql);
			throw e;
		}
	}
	
	public <T> List<T> selectByHqlPaging(String hql, int start, int limit, Object... parameters) throws RuntimeException {
		try {
			return (List<T>) getDao().selectByHqlPaging(hql, start, limit, parameters);
		} catch (RuntimeException e) {
			log.error(e);
			log.error(hql);
			throw e;
		}
	}

	public <T> List<T> selectByExample(T object, int start, int limit) throws RuntimeException {
		try {
			return (List<T>) getDao().selectByExample(object, start, limit);
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
	}

	public <T> List<T> selectByExample(T object) throws RuntimeException {
		try {
			return (List<T>) getDao().selectByExample(object);
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
	}

	public <ID extends Serializable, T> T selectById(String entityName, ID id) throws RuntimeException {
		try {
			return (T) getDao().selectById(entityName, id);
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
	}
	
	public <ID extends Serializable, T> T selectBySystemid(Class<T> clazz, ID id) throws RuntimeException {
		try {
			return (T) getDao().selectById(clazz, id);
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
	}


	public List<?> selectByNql(String nql, Object... parameters) throws RuntimeException {
		try {
			return getDao().selectByNql(nql, parameters);
		} catch (RuntimeException e) {
			log.error(e);
			log.error(nql);
			throw e;
		}
	}

	public List<?> selectByNqlClasses(String nql, String[] alias, Class<?>[] clazzes, Object... parameters) throws RuntimeException {
		try {
			return getDao().selectByNqlClasses(nql, alias, clazzes, parameters);
		} catch (RuntimeException e) {
			log.error(e);
			log.error(nql);
			throw e;
		}
	}
	
	public List<?> selectByNqlPaging(String nql, int start, int limit, Object... parameters) throws RuntimeException {
		try {
			return getDao().selectByNqlPaging(nql, start, limit, parameters);
		} catch (RuntimeException e) {
			log.error(e);
			log.error(nql);
			throw e;
		}
	}

	public List<?> selectByNqlClassesPaging(String nql, int start, int limit, String[] alias, Class<?>[] clazzes, Object... parameters) throws RuntimeException {
		try {
			return getDao().selectByNqlClassesPaging(nql, start, limit, alias, clazzes, parameters);
		} catch (RuntimeException e) {
			log.error(e);
			log.error(nql);
			throw e;
		}
	}

	public long selectCountByHql(String hql, Object... parameters) throws RuntimeException {
		try {
			return getDao().selectCountByHql(hql, parameters);
		} catch (RuntimeException e) {
			log.error(e);
			log.error(hql);
			throw e;
		}
	}

	public long selectCountByNql(String nql, Object... parameters) throws RuntimeException {
		try {
			return getDao().selectCountByNql(nql, parameters);
		} catch (RuntimeException e) {
			log.error(e);
			log.error(nql);
			throw e;
		}
	}

	public long selectCountByNqlClasses(String nql, String[] alias, Class<?>[] clazzes, Object... parameters) throws RuntimeException {
		try {
			return getDao().selectCountByNqlClasses(nql, alias, clazzes, parameters);
		} catch (RuntimeException e) {
			log.error(e);
			log.error(nql);
			throw e;
		}
	}

	public <T> void update(T object) throws RuntimeException {
		try {
			getDao().update(object);
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
	}

	public <T> void insertOrUpdate(T object) throws RuntimeException {
		try {
			getDao().insertOrUpdate(object);
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
	}

	@Override
	public <ID extends Serializable, T> T selectById(Class<T> clazz, ID id) throws RuntimeException {
		try {
			return getDao().selectById(clazz, id);
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
	}

	@Override
	public <T> void delete(String entityName, T object) throws RuntimeException {
		try {
			getDao().delete(entityName, object);
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
	}

	@Override
	public <T> void insert(String entityName, T object) throws RuntimeException {
		try {
			getDao().insert(entityName, object);
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
	}

	@Override
	public <T> void update(String entityName, T object) throws RuntimeException {
		try {
			getDao().update(entityName, object);
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}		
	}

	@Override
	public <T> void insertOrUpdate(String entityName, T object) throws RuntimeException {
		try {
			getDao().insertOrUpdate(entityName, object);
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
	}

}