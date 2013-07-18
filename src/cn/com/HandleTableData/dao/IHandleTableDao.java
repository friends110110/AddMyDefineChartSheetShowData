package cn.com.HandleTableData.dao;

import java.util.List;

import javax.swing.JTable;

import cn.com.domain.DtuData;

public interface IHandleTableDao {
	public void clear(JTable jTable);
	public void show(JTable jTable,List<DtuData> list);
	
	public boolean showChart(List<DtuData> list);
}
