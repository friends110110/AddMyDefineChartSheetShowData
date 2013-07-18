package cn.com.HandleHibernate.dao.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.com.HandleHibernate.dao.IHandleHibernateDao;
import cn.com.domain.DtuData;

public class CHandleHibernateDaoImpl extends HibernateDaoSupport implements IHandleHibernateDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<DtuData> findAll() {
		// TODO Auto-generated method stub
		try
		{
			return (List<DtuData>)getHibernateTemplate().find("from DtuData");
		}
		catch(Exception e)
		{
			return null;
		}
	}

	@Override
	public List<DtuData> findCondition(String sql) {
		// TODO Auto-generated method stub
		try{
		return (List<DtuData>)getHibernateTemplate().find("from DtuData where "+sql);
		}
		catch(Exception e)
		{
			return null;
		}
	}

	@Override
	public boolean testConnection() {
		// TODO Auto-generated method stub
		
		if(this.findAll()==null)//||this.findAll().size()==0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public boolean testConnection(ApplicationContext ac,String url, String user, String pwd) {
		// TODO Auto-generated method stub
		if(url.equals("")||user.equals("")||pwd.equals(""))
			return false;
		String myurl;
		if(false==url.contains(":"))
			 myurl="jdbc:mysql://localhost:3306/"+url+"?useUnicode=true&characterEncoding=gbk";		
		else		
			myurl="jdbc:mysql://"+url+"?useUnicode=true&characterEncoding=gbk";	
			Configuration configuration= new Configuration();//.addResource("classpath*:/cn/com/domain/DtuData.hbm.xml");
		
		configuration.addClass(cn.com.domain.DtuData.class)
					.setProperty("hibernate.connection.url", myurl)
					.setProperty("hibernate.connection.username", user)
					.setProperty("hibernate.connection.password", pwd);
		SessionFactory sessfacTemp=this.getSessionFactory();
		SessionFactory sessionfactory=configuration.buildSessionFactory();
		this.setSessionFactory(sessionfactory);

	
		if(this.findAll()==null)//this.findAll().size()==0)
		{
			this.setSessionFactory(sessfacTemp);
			sessionfactory.close();
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public List<DtuData> findCondition(String id, String time, String content) {
		// TODO Auto-generated method stub
		String searchSql="";
		boolean flag=false;
		if(!id.equals(""))
		{
			searchSql+="id = '"+id+"'";
			flag=true;
		}
		if(!time.equals(""))
		{
			if(true==flag)
			{
				searchSql+=" and ";
			}
			searchSql+="time like '%"+time+"%'";
			flag=true;
		}
		if(!content.equals(""))
		{
			if(true==flag)
			{
				searchSql+=" and ";
			}
			searchSql+="content like '%"+content+"%'";
			flag=true;
		}
		if(false==flag)
			return null;
		return findCondition(searchSql);
	}



}
