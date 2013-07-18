package cn.com.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DtuData implements Serializable{
	int id;
	Timestamp time;
	String content;
	
	public DtuData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DtuData(int id, Timestamp time, String content) {
		super();
		this.id = id;
		this.time = time;
		this.content = content;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "DtuData [id=" + id + ", time=" + time + ", content=" + content
				+ "]";
	}

//	public static void main(String []args)
//	{
//		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
//		SessionFactory facSession=(SessionFactory) ac.getBean("sessionFactory");
//		Session session=facSession.openSession();
//		System.out.println(session.isOpen());
////		DtuData a=new DtuData();
////		a.deal();
//		
//	}
}
