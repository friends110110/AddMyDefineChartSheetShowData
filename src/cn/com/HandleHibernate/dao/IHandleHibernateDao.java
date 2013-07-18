package cn.com.HandleHibernate.dao;

import java.util.List;

import org.springframework.context.ApplicationContext;

import cn.com.domain.DtuData;

public interface IHandleHibernateDao {
	public boolean testConnection();
	public boolean testConnection(ApplicationContext ac,String url,String user,String pwd);
	public List<DtuData> findAll();
	public List<DtuData> findCondition(String sql);
	public List<DtuData> findCondition(String id,String time,String content);
}
