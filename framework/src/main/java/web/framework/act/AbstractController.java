package web.framework.act;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import web.framework.buz.Service;

public abstract class AbstractController<ID extends Serializable, T> {

	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public Class<T> getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	@Resource(name = "service")
	private Service service;

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	
	/*

	public abstract String get(ID id);

	public abstract String post(T t);

	public abstract String put(ID id, T t);

	public abstract String delete(ID id);
	
	*/

}