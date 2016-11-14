package com.patent.DAO;

import java.sql.Connection;
import java.util.*;
import java.io.Serializable;

/** 统一数据访问接口 */
public interface BaseDAO {
	/** 加载指定ID的持久化对象 */
	public Object loadById(Class clazz,Serializable id);
	
	/** 加载满足条件的持久化对象 */
	public Object loadObject(String hql);
	
	/** 删除指定ID的持久化对象 */
	public void delById(Class clazz,Serializable id);
	
	/** 保存或更新指定的持久化对象 */
	public void saveOrUpdate(Object obj);
	
	/** 装载指定类的所有持久化对象 */
	public List listAll(String clazz);
	
	/** 分页装载指定类的所有持久化对象 */
	public List listAll(String clazz,int pageNo,int pageSize);
	
	/** 统计指定类的所有持久化对象 */
	public int countAll(String clazz);
	
	/** 查询指定类的满足条件的持久化对象 */
	public List query(String hql);
	
	/** 分页查询指定类的满足条件的持久化对象 */
	public List query(String hql,int pageNo,int pageSize);
	
	/** 统计指定类的查询结果 */
	public int countQuery(String hql);
	
	/** 条件更新数据 */
	public int update(String hql);
	
	/** 从连接池中取得一个JDBC连接 */
	public Connection getConnection();
}
