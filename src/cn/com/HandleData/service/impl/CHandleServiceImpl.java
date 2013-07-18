package cn.com.HandleData.service.impl;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.HandleData.service.IHandleService;
import cn.com.HandleHibernate.dao.IHandleHibernateDao;
import cn.com.HandleHibernate.dao.impl.CHandleHibernateDaoImpl;
import cn.com.HandleTableData.dao.IHandleTableDao;
import cn.com.domain.DtuData;

public class CHandleServiceImpl implements IHandleService {

	private IHandleHibernateDao handleHibernateDao;
	private IHandleTableDao handleTableDao;
	

	public IHandleHibernateDao getHandleHibernateDao() {
		return handleHibernateDao;
	}

	public void setHandleHibernateDao(IHandleHibernateDao handleHibernateDao) {
		this.handleHibernateDao = handleHibernateDao;
	}

	public IHandleTableDao getHandleTableDao() {
		return handleTableDao;
	}

	public void setHandleTableDao(IHandleTableDao handleTableDao) {
		this.handleTableDao = handleTableDao;
	}

	@Override
	public List<DtuData> findAll() {
		// TODO Auto-generated method stub
		return handleHibernateDao.findAll();
	}

//	@Override
//	public List<DtuData> findCondition(String sql) {
//		// TODO Auto-generated method stub
//		return handleHibernateDao.findCondition(sql);
//	}

	@Override
	public void clearTable(JTable jTable) {
		// TODO Auto-generated method stub
		handleTableDao.clear(jTable);
	}

	@Override
	public void showTableAllData(JTable jTable) {
		// TODO Auto-generated method stub
		List<DtuData> list=handleHibernateDao.findAll();
		handleTableDao.show(jTable, list);
	}

	@Override
	public void showTableCondition(JTable jTable,String sql) {
		// TODO Auto-generated method stub
		List<DtuData> list=handleHibernateDao.findCondition(sql);
		handleTableDao.show(jTable, list);
	}

	@Override
	public boolean testConnection() {
		// TODO Auto-generated method stub
		return handleHibernateDao.testConnection();
	}

	@Override
	public boolean testConnection(ApplicationContext ac,String url, String user, String pwd) {
		// TODO Auto-generated method stub
		//String myurl="jdbc:mysql://localhost:3306/"+url+"?useUnicode=true&characterEncoding=utf8";

		
		return handleHibernateDao.testConnection(ac,url, user, pwd);
	}

	@Override
	public void showTableCondition(JTable jTable, String id, String time,
			String content) {
		//this.clearTable(jTable);
		List list=handleHibernateDao.findCondition(id, time, content);
		this.clearTable(jTable);
		if(null==list)
		{
			JOptionPane.showMessageDialog(null, "null£¡");
			return ;
		}
		if(0==list.size())
		{
			JOptionPane.showMessageDialog(null, "no one£¡");
			return ;
		}
		handleTableDao.show(jTable, list);
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean save(DtuData dtudata) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(DtuData dtudata) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Integer index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(DtuData dtudata) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean showChart() {
		// TODO Auto-generated method stub
		List<DtuData> list=findAll();
		if(false==handleTableDao.showChart(list))
			return false;
		else 
			return true;
	}


//	@Override
//	public List<DtuData> findCondition(String id, String time, String content) {
//		// TODO Auto-generated method stub
//		return handleHibernateDao.findCondition(id, time, content);
//	}

}
