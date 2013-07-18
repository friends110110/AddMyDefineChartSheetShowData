package cn.com.HandleTableData.dao.impl;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import cn.com.HandleTableData.dao.IHandleTableDao;
import cn.com.domain.DtuData;

public class CHandleTableDaoImpl implements IHandleTableDao {

	@Override
	public void clear(JTable jTable) {
		// TODO Auto-generated method stub
		DefaultTableModel tableModel=(DefaultTableModel)jTable.getModel();
		int rows=tableModel.getRowCount();
		if(rows!=0)
		{
			for(int i=0;i<rows;i++)
			{
				tableModel.removeRow(0);
			}
		}
	}

	@Override
	public void show(JTable jTable, List<DtuData> list){
		// TODO Auto-generated method stub
		clear(jTable);
		DefaultTableModel tableModel=(DefaultTableModel)jTable.getModel();
		DtuData dtudata=null;
		for(int i=0;i<list.size();i++)
		{
			dtudata=list.get(i);
			String id=String.valueOf(dtudata.getId());
			Timestamp tm=dtudata.getTime();
			//tm.getda
			DateFormat  df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr=df.format(tm);
			String showContent=dealWithIfXml(dtudata.getContent());
			String []data={id,dateStr,showContent};
			tableModel.addRow(data);
		}
	}

	@SuppressWarnings("finally")
	private String dealWithIfXml(String content) {
		// TODO Auto-generated method stub
		String showdata="";
	//	Map map = new LinkedHashMap();
		Document doc = null;
		boolean IsXmlFile=false;
		try {
			doc = DocumentHelper.parseText(content);
			 // 获取根节点
			Element rootElt = doc.getRootElement(); 
	        Iterator ite=rootElt.elementIterator();
	        while(ite.hasNext())
	        {
	       	 Element data=(Element)ite.next();
	       	 String key=data.getName().toString();
	       	 String value=data.getData().toString();
//		      System.out.println(key);
//		      System.out.println(value);
	       	// map.put(key, value);
	       	 showdata+=key+" = "+value+"  ";   
	       	 IsXmlFile=true;
	        } 
		}catch (DocumentException e) {
			IsXmlFile=false;
			//JOptionPane.showMessageDialog(null,"这个不是XML文件,显示所有数据");
			e.printStackTrace();
			
		} 
		finally
		{
			if(true==IsXmlFile)
				return showdata;
			else
				return content;
		}

	}
	private static CategoryDataset getDataSet(List<DtuData> list) {
		  DefaultCategoryDataset dataset=new DefaultCategoryDataset();
		    

	
		  for(int i=0;i<list.size();i++)
		  {
			  	DtuData dtudata=list.get(i);
				Timestamp tm=dtudata.getTime();
				//tm.getda
				DateFormat  df=new SimpleDateFormat("yy.MM.dd HH:mm:ss");
				String dateStr=df.format(tm);
				dataset.addValue(i+1, "数据值",dateStr );
				
		  }
		 
		  return dataset;
		 }
	@SuppressWarnings("deprecation")
	private XYDataset createDataset(List<DtuData> list) {
		// TODO Auto-generated method stub
		 TimeSeries timeseries = new TimeSeries("DTU",
	                org.jfree.data.time.Second.class);
		 
		  for(int i=0;i<list.size();i++)
		  {
			  	DtuData dtudata=list.get(i);
				Timestamp tm=dtudata.getTime();
				//tm.getda
				DateFormat  df=new SimpleDateFormat("yyyy MM dd HH mm ss");	
				
				Date date = null;			
				String dateStr=df.format(tm);
				String []timeData=dateStr.split(" ");
			
				int count=timeData.length;
				for (int k=0;k<count;k++)
				{
					System.out.println(timeData[k]);
				}
				//dataset.addValue(i+1, "数据值",dateStr );
				
				
				int seconds=Integer.valueOf(timeData[5]);//timeData[].getSeconds();
				int miniute=Integer.valueOf(timeData[4]);//date.getMinutes();
				int hour=Integer.valueOf(timeData[3]);//date.getHours();
				int day=Integer.valueOf(timeData[2]);//date.getDay();
				int month=Integer.valueOf(timeData[1]);//date.getMonth();
				int year=Integer.valueOf(timeData[0]);//date.getYear();
				timeseries.add(new Second(seconds,miniute,hour,day,month,year),dtudata.getId());
		  }
	        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
	        timeseriescollection.addSeries(timeseries);

	        return timeseriescollection;
	}
	@Override
	public boolean showChart(List<DtuData> list) {
		// TODO Auto-generated method stub
		XYDataset xydataset = createDataset(list);
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("dtu数据信息生成图", "时间", "id",xydataset, true, true, true);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		//获取x轴
		//DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
		//自定义x轴
		PeriodAxis  dateaxis= new PeriodAxis("时间");//(DateAxis) xyplot.getDomainAxis();
		//获取y轴
		 NumberAxis numberAxis=(NumberAxis)xyplot.getRangeAxis();
//y轴转换格式	 
//		 NumberFormat a=NumberFormat.getNumberInstance();
//		 a.set
////		 numberAxis.setNumberFormatOverride(new NumberFormat(){
//
//			@Override
//			public StringBuffer format(double arg0, StringBuffer arg1,
//					FieldPosition arg2) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public StringBuffer format(long arg0, StringBuffer arg1,
//					FieldPosition arg2) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public Number parse(String arg0, ParsePosition arg1) {
//				// TODO Auto-generated method stub
//				return null;
//			}});
		 
        //处理y轴的刻度
        numberAxis.setAutoTickUnitSelection(false);
        numberAxis.setTickUnit(new NumberTickUnit(1));
        
        //处理x轴的刻度

        //dateaxis.setTimeZone(TimeZone.getDefault());	// 使用默认时区
		dateaxis.setAutoRangeTimePeriodClass(Month.class); // 设置该时间轴默认自动增长时间单位为天  
		dateaxis.setMajorTickTimePeriodClass(Month.class);
 		 SimpleDateFormat frm = new SimpleDateFormat("yyyy");   // 设置时间显示样式  
 		 // 设置不同重的时间显示格式
	        PeriodAxisLabelInfo[] arrayOfPeriodAxisLabelInfo = new PeriodAxisLabelInfo[3];
//	        arrayOfPeriodAxisLabelInfo[0] = new PeriodAxisLabelInfo(Second.class, new SimpleDateFormat("ss")); // 第一行显示  
//	        arrayOfPeriodAxisLabelInfo[1] = new PeriodAxisLabelInfo(Minute.class, new SimpleDateFormat("mm")); // 第一行显示
//	        arrayOfPeriodAxisLabelInfo[2] = new PeriodAxisLabelInfo(Hour.class, new SimpleDateFormat("HH")); // 第一行显示天  
	        arrayOfPeriodAxisLabelInfo[0] = new PeriodAxisLabelInfo(Day.class, new SimpleDateFormat("dd")); // 第一行显示天  
//	        arrayOfPeriodAxisLabelInfo[0]=new PeriodAxisLabelInfo(Day.class, new SimpleDateFormat("d"),new RectangleInsets(2D, 2D, 2D, 2D), new Font("SansSerif", 1,
//	        	      10), Color.BLACK, false, new BasicStroke(0.0F),
//	        	      Color.lightGray);
	        arrayOfPeriodAxisLabelInfo[1] = new PeriodAxisLabelInfo(Month.class, new SimpleDateFormat("MMM"), 
	        		new RectangleInsets(2.0D, 2.0D, 2.0D, 2.0D), new Font("SansSerif", 1, 10), Color.blue, false, 
	        		new BasicStroke(0.0F), Color.lightGray); // 第二行显示月 
	        arrayOfPeriodAxisLabelInfo[2] = new PeriodAxisLabelInfo(Year.class, new SimpleDateFormat("yyyy年")); // 第三行显示年  	        
	     
	        // 设置X轴最大值 
	     //   dateaxis.setMaximumDate(new Date(gc.getTimeInMillis())); 
	        
	        dateaxis.setLabelInfo(arrayOfPeriodAxisLabelInfo);
	        dateaxis.setLowerMargin(0.01D);
	        dateaxis.setUpperMargin(0.01D);
         //X轴5天为一个单位
        // dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 5,frm));

 		xyplot.setDomainAxis(dateaxis);
 		 
 		 

        
        ChartPanel frame1=new ChartPanel(jfreechart,true);
        dateaxis.setLabelFont(new Font("黑体",Font.BOLD,14));         //水平底部标题
        dateaxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));  //垂直标题
        ValueAxis rangeAxis=xyplot.getRangeAxis();//获取柱状

       rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));
        jfreechart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
        jfreechart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体
        
        

        // 设置数据点和序列线的显示格式  
        XYItemRenderer r = xyplot.getRenderer();  
        if (r instanceof XYLineAndShapeRenderer) {  
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;  
    
            
          //设置网格背景颜色 
//            xyplot.setBackgroundPaint(Color.white); 
//            //设置网格竖线颜色 
//            xyplot.setDomainGridlinePaint(Color.pink); 
//            //设置网格横线颜色 
//            xyplot.setRangeGridlinePaint(Color.pink); 
//            //设置曲线图与xy轴的距离 
//            xyplot.setAxisOffset(new RectangleInsets(0D, 0D, 0D, 10D)); 
   
           // renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT)); 
            renderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator()); 
            renderer.setBaseItemLabelsVisible(true);    // 数据点显示
            renderer.setBaseItemLabelFont(new Font("黑体",Font.PLAIN,18)); 
          //是否显示虚线
            renderer.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f, new float[] {10.0f}, 0.0f));
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);    // 数据点外框内填充  
            //xyplot.setRenderer(renderer); 
        }        
        
    	JFrame frame=new JFrame("Java数据统计图");
    	frame.setLayout(new GridLayout(1,1,10,10));
    	frame.add(frame1);    //添加折线图
    	frame.setBounds(50, 50, 800, 600);
    	frame.setVisible(true);
        return true;
		/*
		  CategoryDataset dataset=getDataSet(list);
		  
		  JFreeChart chart=ChartFactory.createLineChart("dtu数据显示",
		                             "时间",
		                             "id", 
		                              dataset, 
		                              PlotOrientation.VERTICAL,
		                              true, 
		                              false, 
		                              false);
		  
		  //获取图表对象
		  CategoryPlot categoryPlot=(CategoryPlot)chart.getPlot();
		  
		  //获取x轴
		  CategoryAxis categoryAxis=(CategoryAxis)categoryPlot.getDomainAxis();
		
		  //获取y轴
		  NumberAxis numberAxis=(NumberAxis)categoryPlot.getRangeAxis();
		  
		  //绘图区域(红色矩形框的部分)
		  LineAndShapeRenderer lineAndShapeRenderer=(LineAndShapeRenderer)categoryPlot.getRenderer();
		 
	//
		  //处理主标题乱码
		        chart.getTitle().setFont(new Font("黑体",Font.PLAIN,18));
		  
		  //处理子标题乱码
		        chart.getLegend().setItemFont(new Font("黑体",Font.PLAIN,18));
		  
		     //处理x轴的乱码
		         categoryAxis.setLabelFont(new Font("黑体",Font.PLAIN,18));
		  
		  //处理x轴上的乱码
		         categoryAxis.setTickLabelFont(new Font("黑体",Font.PLAIN,18));
		         
		  //处理y轴乱码
		         numberAxis.setLabelFont(new Font("黑体",Font.PLAIN,18));
		   
		  //处理y轴上的乱码
		         numberAxis.setTickLabelFont(new Font("黑体",Font.PLAIN,18));
		
		         //
		//处理y轴的刻度
		         numberAxis.setAutoTickUnitSelection(false);
		         numberAxis.setTickUnit(new NumberTickUnit(1));
		         
		  //    
		         //生成折线图上的数字
		         lineAndShapeRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		         //设置图表上的数字可见
		         lineAndShapeRenderer.setBaseItemLabelsVisible(true);
		         //设置图表上的数字字体
		         lineAndShapeRenderer.setBaseItemLabelFont(new Font("黑体",Font.PLAIN,18));
		   //
		         //设置折线图拐角上的正方形
		         //创建一个正方形
		         Rectangle  shape=new Rectangle(8,8);
		         lineAndShapeRenderer.setSeriesShape(0, shape);
		         //设置拐角上图形可见
		         lineAndShapeRenderer.setSeriesShapesVisible(0, true);
	//	         
		  ChartFrame chartFrame=new ChartFrame("",chart);
		  chartFrame.setVisible(true);
		  chartFrame.pack();  
	//		 
		return true;
		*/
	}




	
}



