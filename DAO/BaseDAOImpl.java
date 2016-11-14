package com.patent.DAO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.*;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/** 统一数据访问接口实现 */
public class BaseDAOImpl extends HibernateDaoSupport implements BaseDAO {
	
	/** 统计指定类的所有持久化对象 */
	public int countAll(String clazz) {
		final String hql = "select count(*) from "+clazz+ " as a";
		Long count = (Long)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException{
				Query query = session.createQuery(hql);
				query.setMaxResults(1);
				return query.uniqueResult();
			}
		});	
		return count.intValue();
	}

	/** 统计指定类的查询结果 */
	public int countQuery(String hql) {
		final String counthql = hql;
		Long count = (Long)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException{
				Query query = session.createQuery(counthql);
				query.setMaxResults(1);
				return query.uniqueResult();
			}
		});
		return count.intValue();
	}

	/** 删除指定ID的持久化对象 */
	public void delById(Class clazz,Serializable id) {
		getHibernateTemplate().delete(getHibernateTemplate().load(clazz, id));			
	}

	/** 装载指定类的所有持久化对象 */
	public List listAll(String clazz) {
		return getHibernateTemplate().find("from "+clazz+" as a order by a.id desc");
	}
	
	/** 分页装载指定类的所有持久化对象 */
	public List listAll(String clazz, int pageNo, int pageSize) {
		final int pNo = pageNo;
		final int pSize = pageSize;
		final String hql = "from "+clazz+ " as a order by a.id desc";
		List list = getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException{
				Query query = session.createQuery(hql);
				query.setMaxResults(pSize);
				query.setFirstResult((pNo-1)*pSize);
				List result = query.list();
				if (!Hibernate.isInitialized(result))Hibernate.initialize(result);
				return result;
			}
		});	
		return list;
	}
	
	/** 加载指定ID的持久化对象 */
	public Object loadById(Class clazz,Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}
	
	/**加载满足条件的持久化对象*/
	public Object loadObject(String hql) {
		final String hql1 = hql;
		Object obj = null;
		List list = getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException{
				Query query = session.createQuery(hql1);
				return query.list();
			}
		});			
		if(list.size()>0)obj=list.get(0);	
		return obj;
	}

	/** 查询指定类的满足条件的持久化对象 */
	public List query(String hql) {
		final String hql1 = hql;
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException{
				Query query = session.createQuery(hql1);
				return query.list();
			}
		});	
	}

	/** 分页查询指定类的满足条件的持久化对象 */
	public List query(String hql, int pageNo, int pageSize) {
		final int pNo = pageNo;
		final int pSize = pageSize;
		final String hql1 = hql;
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException{
				Query query = session.createQuery(hql1);
				query.setMaxResults(pSize);
				query.setFirstResult((pNo-1)*pSize);
				List result = query.list();
				if (!Hibernate.isInitialized(result))Hibernate.initialize(result);
				return result;
			}
		});	
	}

	/** 保存或更新指定的持久化对象 */
	public void saveOrUpdate(Object obj) {
		getHibernateTemplate().saveOrUpdate(obj);
	}

	/** 条件更新数据 */
	public int update(String hql) {
		final String hql1 = hql; 
		return ((Integer)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException{
				Query query = session.createQuery(hql1);
				return query.executeUpdate();
			}
		})).intValue();	
	}
	
	/** 从连接池中取得一个JDBC连接 */
	public Connection getConnection() {
		return getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
	}
}