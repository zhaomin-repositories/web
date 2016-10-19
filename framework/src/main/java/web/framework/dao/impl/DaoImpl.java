package web.framework.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import web.framework.dao.Dao;


@SuppressWarnings({ "unchecked" })
@Repository("dao")
public class DaoImpl implements Dao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		if (session == null || !session.isOpen()) {
			session = sessionFactory.openSession();
		}
		Transaction transaction = session.getTransaction();
		if (transaction == null || !transaction.isActive()) {
			session.beginTransaction();
		}
		return session;
	}
	
	@Override
	public boolean executeBySql(String sql, Object... parameters) throws SQLException {
		Session session = getSession();
		try {
			BooleanWork work = new BooleanWork();
			work.setSql(sql);
			work.setParameters(parameters);
			session.doWork(work);
			return work.getValue();
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.flush();
			session.clear();
		}
	}

	@Override
	public List<Map<String, String>> selectBySql(String sql, Object... parameters) throws SQLException {
		Session session = getSession();
		try {
			ListWork work = new ListWork();
			work.setSql(sql);
			work.setParameters(parameters);
			session.doWork(work);
			return work.getValue();
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.flush();
			session.clear();
		}
	}
	
	@Override
	public int executeUpdateBySql(String sql, Object... parameters) throws SQLException {
		Session session = getSession();
		try {
			IntWork work = new IntWork();
			work.setSql(sql);
			work.setParameters(parameters);
			session.doWork(work);
			return work.getValue();
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.flush();
			session.clear();
		}
	}

	@Override
	public <T> void delete(T object) throws RuntimeException {
		Session session = getSession();
		try {
			session.delete(object);
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.flush();
			session.clear();
		}
	}

	@Override
	public int executeUpdateByHql(String hql, Object... parameters) throws RuntimeException {
		Session session = getSession();
		try {
			Query query = session.createQuery(hql);
			if (parameters != null) {
				int i = 0;
				for (Object object : parameters) {
					if (object instanceof List) {
						List<?> list = (List<?>) object;
						for (Object parameter : list) {
							query.setParameter(i++, parameter);
						}
					} else {
						query.setParameter(i++, object);
					}
				}
			}
			return query.executeUpdate();
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.flush();
			session.clear();
		}
	}

	@Override
	public int executeUpdateByNql(String nql, Object... parameters) throws RuntimeException {
		Session session = getSession();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(nql);
			if (parameters != null) {
				int i = 0;
				for (Object object : parameters) {
					if (object instanceof List) {
						List<?> list = (List<?>) object;
						for (Object parameter : list) {
							sqlQuery.setParameter(i++, parameter);
						}
					} else {
						sqlQuery.setParameter(i++, object);
					}
				}
			}
			return sqlQuery.executeUpdate();
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.flush();
			session.clear();
		}
	}

	@Override
	public <T> Serializable insert(T object) throws RuntimeException {
		Session session = getSession();
		Serializable pKey;
		try {
			pKey = session.save(object);
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.flush();
			session.clear();
		}
		return pKey;
	}

	@Override
	public <T> List<T> selectByHql(String hql, Object... parameters) throws RuntimeException {
		Query query = getSession().createQuery(hql);
		if (parameters != null) {
			int i = 0;
			for (Object object : parameters) {
				if (object instanceof List) {
					List<?> list = (List<?>) object;
					for (Object parameter : list) {
						query.setParameter(i++, parameter);
					}
				} else {
					query.setParameter(i++, object);
				}
			}
		}
		return (List<T>) query.list();
	}

	@Override
	public <T> List<T> selectByHqlPaging(String hql, int start, int limit, Object... parameters) throws RuntimeException {
		Query query = getSession().createQuery(hql);
		if (parameters != null) {
			int i = 0;
			for (Object object : parameters) {
				if (object instanceof List) {
					List<?> list = (List<?>) object;
					for (Object parameter : list) {
						query.setParameter(i++, parameter);
					}
				} else {
					query.setParameter(i++, object);
				}
			}
		}
		query.setMaxResults(limit).setFirstResult(start);
		return (List<T>) query.list();
	}

	@Override
	public <T> List<T> selectByExample(T object, int start, int limit) throws RuntimeException {
		Criteria criteria = getSession().createCriteria(object.getClass()).add(Example.create(object));
		criteria.setMaxResults(limit).setFirstResult(start);
		return (List<T>) criteria.list();
	}

	public <T> List<T> selectByExample(T object) throws RuntimeException {
		Criteria criteria = getSession().createCriteria(object.getClass()).add(Example.create(object));
		return (List<T>) criteria.list();
	}

	@Override
	public List<?> selectByNql(String nql, Object... parameters) throws RuntimeException {
		SQLQuery sqlQuery = getSession().createSQLQuery(nql);
		if (parameters != null) {
			int i = 0;
			for (Object object : parameters) {
				if (object instanceof List) {
					List<?> list = (List<?>) object;
					for (Object parameter : list) {
						sqlQuery.setParameter(i++, parameter);
					}
				} else {
					sqlQuery.setParameter(i++, object);
				}
			}
		}
		return sqlQuery.list();
	}

	@Override
	public List<?> selectByNqlClasses(String nql, String[] alias, Class<?>[] clazzes, Object... parameters)
			throws RuntimeException {
		SQLQuery sqlQuery = getSession().createSQLQuery(nql);
		if (parameters != null) {
			int i = 0;
			for (Object object : parameters) {
				if (object instanceof List) {
					List<?> list = (List<?>) object;
					for (Object parameter : list) {
						sqlQuery.setParameter(i++, parameter);
					}
				} else {
					sqlQuery.setParameter(i++, object);
				}
			}
		}
		for (int i = 0; i < alias.length; i++) {
			sqlQuery.addEntity(alias[i], clazzes[i]);
		}
		return sqlQuery.list();
	}

	@Override
	public List<?> selectByNqlPaging(String nql, int start, int limit, Object... parameters) throws RuntimeException {
		SQLQuery sqlQuery = getSession().createSQLQuery(nql);
		if (parameters != null) {
			int i = 0;
			for (Object object : parameters) {
				if (object instanceof List) {
					List<?> list = (List<?>) object;
					for (Object parameter : list) {
						sqlQuery.setParameter(i++, parameter);
					}
				} else {
					sqlQuery.setParameter(i++, object);
				}
			}
		}
		sqlQuery.setMaxResults(limit).setFirstResult(start);
		return sqlQuery.list();
	}

	@Override
	public List<?> selectByNqlClassesPaging(String nql, int start, int limit, String[] alias, Class<?>[] clazzes, Object... parameters) throws RuntimeException {
		SQLQuery sqlQuery = getSession().createSQLQuery(nql);
		if (parameters != null) {
			int i = 0;
			for (Object object : parameters) {
				if (object instanceof List) {
					List<?> list = (List<?>) object;
					for (Object parameter : list) {
						sqlQuery.setParameter(i++, parameter);
					}
				} else {
					sqlQuery.setParameter(i++, object);
				}
			}
		}
		for (int i = 0; i < alias.length; i++) {
			sqlQuery.addEntity(alias[i], clazzes[i]);
		}
		sqlQuery.setMaxResults(limit).setFirstResult(start);
		return sqlQuery.list();
	}

	@Override
	public long selectCountByHql(String hql, Object... parameters) throws RuntimeException {
		Query query = getSession().createQuery(hql);
		if (parameters != null) {
			int i = 0;
			for (Object object : parameters) {
				if (object instanceof List) {
					List<?> list = (List<?>) object;
					for (Object parameter : list) {
						query.setParameter(i++, parameter);
					}
				} else {
					query.setParameter(i++, object);
				}
			}
		}
		Object object = query.list().iterator().next();
		if (object instanceof BigDecimal) {
			return ((BigDecimal) object).longValue();
		} else if (object instanceof BigInteger) {
			return ((BigInteger) object).longValue();
		} else {
			return ((Long) object).longValue();
		}
	}

	@Override
	public long selectCountByNql(String nql, Object... parameters) throws RuntimeException {
		SQLQuery sqlQuery = getSession().createSQLQuery(nql);
		if (parameters != null) {
			int i = 0;
			for (Object object : parameters) {
				if (object instanceof List) {
					List<?> list = (List<?>) object;
					for (Object parameter : list) {
						sqlQuery.setParameter(i++, parameter);
					}
				} else {
					sqlQuery.setParameter(i++, object);
				}
			}
		}
		Object object = sqlQuery.list().iterator().next();
		if (object instanceof BigDecimal) {
			return ((BigDecimal) object).longValue();
		} else if (object instanceof BigInteger) {
			return ((BigInteger) object).longValue();
		} else {
			return ((Long) object).longValue();
		}
	}

	@Override
	public long selectCountByNqlClasses(String nql, String[] alias, Class<?>[] clazzes, Object... parameters) throws RuntimeException {
		SQLQuery sqlQuery = getSession().createSQLQuery(nql);
		if (parameters != null) {
			int i = 0;
			for (Object object : parameters) {
				if (object instanceof List) {
					List<?> list = (List<?>) object;
					for (Object parameter : list) {
						sqlQuery.setParameter(i++, parameter);
					}
				} else {
					sqlQuery.setParameter(i++, object);
				}
			}
		}
		for (int i = 0; i < alias.length; i++) {
			sqlQuery.addEntity(alias[i], clazzes[i]);
		}
		Object object = sqlQuery.list().iterator().next();
		if (object instanceof BigDecimal) {
			return ((BigDecimal) object).longValue();
		} else if (object instanceof BigInteger) {
			return ((BigInteger) object).longValue();
		} else {
			return ((Long) object).longValue();
		}
	}

	@Override
	public <T> void update(T object) throws RuntimeException {
		Session session = getSession();
		try {
			session.update(object);
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.flush();
			session.clear();
		}
	}

	@Override
	public <T> void insertOrUpdate(T object) throws RuntimeException {
		Session session = getSession();
		try {
			session.saveOrUpdate(object);
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.flush();
			session.clear();
		}
	}

	@Override
	public <ID extends Serializable, T> T selectById(Class<T> clazz, ID id) throws RuntimeException {
		return (T) getSession().get(clazz, id);
	}

	@Override
	public <ID extends Serializable, T> T selectById(String entityName, ID id) throws RuntimeException {
		return (T) getSession().get(entityName, id);
	}

	@Override
	public <T> void delete(String entityName, T object) throws RuntimeException {
		Session session = getSession();
		try {
			session.delete(entityName, object);
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.flush();
			session.clear();
		}
	}

	@Override
	public <T> void insert(String entityName, T object) throws RuntimeException {
		Session session = getSession();
		try {
			session.save(entityName, object);
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.flush();
			session.clear();
		}
	}

	@Override
	public <T> void update(String entityName, T object) throws RuntimeException {
		Session session = getSession();
		try {
			session.update(entityName, object);
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.flush();
			session.clear();
		}
	}

	@Override
	public <T> void insertOrUpdate(String entityName, T object) throws RuntimeException {
		Session session = getSession();
		try {
			session.saveOrUpdate(entityName, object);
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.flush();
			session.clear();
		}
	}
	
}

class BooleanWork implements Work {

	private String sql;
	private Object[] parameters;
	private boolean value;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public void execute(Connection connection) throws SQLException {
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		if (parameters != null) {
			int i = 1;
			for (Object object : parameters) {
				if (object instanceof List) {
					List<?> list = (List<?>) object;
					for (Object parameter : list) {
						prepareStatement.setObject(i++, parameter);
					}
				} else {
					prepareStatement.setObject(i++, object);
				}
			}
		}
		value = prepareStatement.execute();
	}

}

class IntWork implements Work {

	private String sql;
	private Object[] parameters;
	private int value;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void execute(Connection connection) throws SQLException {
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		if (parameters != null) {
			int i = 1;
			for (Object object : parameters) {
				if (object instanceof List) {
					List<?> list = (List<?>) object;
					for (Object parameter : list) {
						prepareStatement.setObject(i++, parameter);
					}
				} else {
					prepareStatement.setObject(i++, object);
				}
			}
		}
		value = prepareStatement.executeUpdate();
	}

}

class ListWork implements Work {

	private String sql;
	private Object[] parameters;
	private List<Map<String, String>> value;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public List<Map<String, String>> getValue() {
		return value;
	}

	public void setValue(List<Map<String, String>> value) {
		this.value = value;
	}

	public void execute(Connection connection) throws SQLException {
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		if (parameters != null) {
			int i = 1;
			for (Object object : parameters) {
				if (object instanceof List) {
					List<?> list = (List<?>) object;
					for (Object parameter : list) {
						prepareStatement.setObject(i++, parameter);
					}
				} else {
					prepareStatement.setObject(i++, object);
				}
			}
		}
		ResultSet resultSet = prepareStatement.executeQuery();
		int count = resultSet.getMetaData().getColumnCount();
		value = new ArrayList<Map<String, String>>();
		while (resultSet.next()) {
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < count; i++) {
				String l = resultSet.getMetaData().getColumnLabel(i);
				String v = resultSet.getString(l);
				map.put(l, v);
			}
			value.add(map);
		}
	}
	
}