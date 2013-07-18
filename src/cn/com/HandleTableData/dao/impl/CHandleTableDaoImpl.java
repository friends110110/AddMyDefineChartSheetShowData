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
			 // ��ȡ���ڵ�
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
			//JOptionPane.showMessageDialog(null,"�������XML�ļ�,��ʾ��������");
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
				dataset.addValue(i+1, "����ֵ",dateStr );
				
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
				//dataset.addValue(i+1, "����ֵ",dateStr );
				
				
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
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("dtu������Ϣ����ͼ", "ʱ��", "id",xydataset, true, true, true);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		//��ȡx��
		//DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
		//�Զ���x��
		PeriodAxis  dateaxis= new PeriodAxis("ʱ��");//(DateAxis) xyplot.getDomainAxis();
		//��ȡy��
		 NumberAxis numberAxis=(NumberAxis)xyplot.getRangeAxis();
//y��ת����ʽ	 
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
		 
        //����y��Ŀ̶�
        numberAxis.setAutoTickUnitSelection(false);
        numberAxis.setTickUnit(new NumberTickUnit(1));
        
        //����x��Ŀ̶�

        //dateaxis.setTimeZone(TimeZone.getDefault());	// ʹ��Ĭ��ʱ��
		dateaxis.setAutoRangeTimePeriodClass(Month.class); // ���ø�ʱ����Ĭ���Զ�����ʱ�䵥λΪ��  
		dateaxis.setMajorTickTimePeriodClass(Month.class);
 		 SimpleDateFormat frm = new SimpleDateFormat("yyyy");   // ����ʱ����ʾ��ʽ  
 		 // ���ò�ͬ�ص�ʱ����ʾ��ʽ
	        PeriodAxisLabelInfo[] arrayOfPeriodAxisLabelInfo = new PeriodAxisLabelInfo[3];
//	        arrayOfPeriodAxisLabelInfo[0] = new PeriodAxisLabelInfo(Second.class, new SimpleDateFormat("ss")); // ��һ����ʾ  
//	        arrayOfPeriodAxisLabelInfo[1] = new PeriodAxisLabelInfo(Minute.class, new SimpleDateFormat("mm")); // ��һ����ʾ
//	        arrayOfPeriodAxisLabelInfo[2] = new PeriodAxisLabelInfo(Hour.class, new SimpleDateFormat("HH")); // ��һ����ʾ��  
	        arrayOfPeriodAxisLabelInfo[0] = new PeriodAxisLabelInfo(Day.class, new SimpleDateFormat("dd")); // ��һ����ʾ��  
//	        arrayOfPeriodAxisLabelInfo[0]=new PeriodAxisLabelInfo(Day.class, new SimpleDateFormat("d"),new RectangleInsets(2D, 2D, 2D, 2D), new Font("SansSerif", 1,
//	        	      10), Color.BLACK, false, new BasicStroke(0.0F),
//	        	      Color.lightGray);
	        arrayOfPeriodAxisLabelInfo[1] = new PeriodAxisLabelInfo(Month.class, new SimpleDateFormat("MMM"), 
	        		new RectangleInsets(2.0D, 2.0D, 2.0D, 2.0D), new Font("SansSerif", 1, 10), Color.blue, false, 
	        		new BasicStroke(0.0F), Color.lightGray); // �ڶ�����ʾ�� 
	        arrayOfPeriodAxisLabelInfo[2] = new PeriodAxisLabelInfo(Year.class, new SimpleDateFormat("yyyy��")); // ��������ʾ��  	        
	     
	        // ����X�����ֵ 
	     //   dateaxis.setMaximumDate(new Date(gc.getTimeInMillis())); 
	        
	        dateaxis.setLabelInfo(arrayOfPeriodAxisLabelInfo);
	        dateaxis.setLowerMargin(0.01D);
	        dateaxis.setUpperMargin(0.01D);
         //X��5��Ϊһ����λ
        // dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 5,frm));

 		xyplot.setDomainAxis(dateaxis);
 		 
 		 

        
        ChartPanel frame1=new ChartPanel(jfreechart,true);
        dateaxis.setLabelFont(new Font("����",Font.BOLD,14));         //ˮƽ�ײ�����
        dateaxis.setTickLabelFont(new Font("����",Font.BOLD,12));  //��ֱ����
        ValueAxis rangeAxis=xyplot.getRangeAxis();//��ȡ��״

       rangeAxis.setLabelFont(new Font("����",Font.BOLD,15));
        jfreechart.getLegend().setItemFont(new Font("����", Font.BOLD, 15));
        jfreechart.getTitle().setFont(new Font("����",Font.BOLD,20));//���ñ�������
        
        

        // �������ݵ�������ߵ���ʾ��ʽ  
        XYItemRenderer r = xyplot.getRenderer();  
        if (r instanceof XYLineAndShapeRenderer) {  
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;  
    
            
          //�������񱳾���ɫ 
//            xyplot.setBackgroundPaint(Color.white); 
//            //��������������ɫ 
//            xyplot.setDomainGridlinePaint(Color.pink); 
//            //�������������ɫ 
//            xyplot.setRangeGridlinePaint(Color.pink); 
//            //��������ͼ��xy��ľ��� 
//            xyplot.setAxisOffset(new RectangleInsets(0D, 0D, 0D, 10D)); 
   
           // renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT)); 
            renderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator()); 
            renderer.setBaseItemLabelsVisible(true);    // ���ݵ���ʾ
            renderer.setBaseItemLabelFont(new Font("����",Font.PLAIN,18)); 
          //�Ƿ���ʾ����
            renderer.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f, new float[] {10.0f}, 0.0f));
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);    // ���ݵ���������  
            //xyplot.setRenderer(renderer); 
        }        
        
    	JFrame frame=new JFrame("Java����ͳ��ͼ");
    	frame.setLayout(new GridLayout(1,1,10,10));
    	frame.add(frame1);    //�������ͼ
    	frame.setBounds(50, 50, 800, 600);
    	frame.setVisible(true);
        return true;
		/*
		  CategoryDataset dataset=getDataSet(list);
		  
		  JFreeChart chart=ChartFactory.createLineChart("dtu������ʾ",
		                             "ʱ��",
		                             "id", 
		                              dataset, 
		                              PlotOrientation.VERTICAL,
		                              true, 
		                              false, 
		                              false);
		  
		  //��ȡͼ�����
		  CategoryPlot categoryPlot=(CategoryPlot)chart.getPlot();
		  
		  //��ȡx��
		  CategoryAxis categoryAxis=(CategoryAxis)categoryPlot.getDomainAxis();
		
		  //��ȡy��
		  NumberAxis numberAxis=(NumberAxis)categoryPlot.getRangeAxis();
		  
		  //��ͼ����(��ɫ���ο�Ĳ���)
		  LineAndShapeRenderer lineAndShapeRenderer=(LineAndShapeRenderer)categoryPlot.getRenderer();
		 
	//
		  //��������������
		        chart.getTitle().setFont(new Font("����",Font.PLAIN,18));
		  
		  //�����ӱ�������
		        chart.getLegend().setItemFont(new Font("����",Font.PLAIN,18));
		  
		     //����x�������
		         categoryAxis.setLabelFont(new Font("����",Font.PLAIN,18));
		  
		  //����x���ϵ�����
		         categoryAxis.setTickLabelFont(new Font("����",Font.PLAIN,18));
		         
		  //����y������
		         numberAxis.setLabelFont(new Font("����",Font.PLAIN,18));
		   
		  //����y���ϵ�����
		         numberAxis.setTickLabelFont(new Font("����",Font.PLAIN,18));
		
		         //
		//����y��Ŀ̶�
		         numberAxis.setAutoTickUnitSelection(false);
		         numberAxis.setTickUnit(new NumberTickUnit(1));
		         
		  //    
		         //��������ͼ�ϵ�����
		         lineAndShapeRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		         //����ͼ���ϵ����ֿɼ�
		         lineAndShapeRenderer.setBaseItemLabelsVisible(true);
		         //����ͼ���ϵ���������
		         lineAndShapeRenderer.setBaseItemLabelFont(new Font("����",Font.PLAIN,18));
		   //
		         //��������ͼ�ս��ϵ�������
		         //����һ��������
		         Rectangle  shape=new Rectangle(8,8);
		         lineAndShapeRenderer.setSeriesShape(0, shape);
		         //���ùս���ͼ�οɼ�
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



