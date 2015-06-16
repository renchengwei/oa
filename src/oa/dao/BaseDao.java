package oa.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	private void setQueryParam(List<String> params, Query query) {
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				query.setString(i, params.get(i));
			}
		}
	}

	/**
	 * 
	 * author：fenggaowei createTime：2014-4-14 上午10:22:47 description: 根据hql语句和参数
	 * 分页查询
	 * 
	 * @param hql
	 *            查询的hql语句
	 * @param params
	 *            查询的参数列表
	 * @param pageNo
	 *            查询的当前页数 从1 开始
	 * @param maxResult
	 *            每页显示的最大记录数
	 * @return
	 */
	public List queryForPage(String hql, List<String> params, int pageNo,
			final int maxResult) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		setQueryParam(params, query);
		query.setFirstResult((pageNo - 1) * maxResult);
		query.setMaxResults(maxResult);
		return query.list();
	}

	/**
	 * 
	 * author：fenggaowei createTime：2014-4-14 上午10:26:08 description: 查询行数
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public int queryCountByHql(String hql, List<String> params) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		setQueryParam(params, query);
		Object object = query.uniqueResult();
		if (object != null) {
			return Integer.parseInt(object.toString());
		}
		return 0;
	}

	public boolean isParamsValidate(String param, Map<String,String> map) {
		if (map.containsKey(param)) {
			Object value = map.get(param);
			if (value != null) {
				if (StringUtils.isNotEmpty(value.toString())) {
					return true;
				}
			}
		}
		return false;
	}

	public List query(String hql, List<String> params) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		setQueryParam(params, query);
		return query.list();
	}

	public Object get(Class<?> clazz, Serializable id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(clazz, id);
	}

	public Object load(Class<?> clazz, Serializable id) {
		Session session = sessionFactory.getCurrentSession();
		return session.load(clazz, id);
	}

	public void saveObject(Object object) {
		Session session = sessionFactory.getCurrentSession();
		session.save(object);
	}

	public void updateObject(Object object) {
		Session session = sessionFactory.getCurrentSession();
		session.update(object);
	}

	public int updateByQuery(String hql, List<String> params) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		setQueryParam(params, query);
		return query.executeUpdate();
	}

	public int updateBySql(String sql, List<String> params) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		setQueryParam(params, query);
		return query.executeUpdate();
	}

	public void updateId(Class<?> clzz, String oldid, String newid) {
		List<String> param = new ArrayList<String>();
		param.add(newid);
		param.add(oldid);
		this.updateByQuery(
				"update " + clzz.getName() + "  set id=? where id=?", param);
	}

	public void deleteObject(Class<?> clzz, String id) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(session.load(clzz, id));
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
