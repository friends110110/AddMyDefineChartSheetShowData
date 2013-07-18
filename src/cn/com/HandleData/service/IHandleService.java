package cn.com.HandleData.service;

import java.util.List;

import javax.swing.JTable;

import org.springframework.context.ApplicationContext;

import cn.com.domain.DtuData;

public interface IHandleService {
	public List<DtuData> findAll();
//	public List<DtuData> findCondition(String sql);
//	public List<DtuData> findCondition(String id,String time,String content);
	public void clearTable(JTable jTable);
	public void showTableAllData(JTable jTable);
	public void showTableCondition(JTable jTable,String sql);
	public void showTableCondition(JTable jTable,String id,String time,String content);
	public boolean testConnection();
	public boolean testConnection(ApplicationContext ac,String url,String user,String pwd);

	
	public boolean save(DtuData dtudata);
	public boolean delete(DtuData dtudata);
	public boolean delete(Integer index);
	public boolean update(DtuData dtudata);
	
	
	public boolean showChart();
}
