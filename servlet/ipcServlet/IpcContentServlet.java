package com.patent.servlet.ipcServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.crawl.DBUtil;

public class IpcContentServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/png");
		CategoryDataset dataset;
		try {
			dataset = createDataset();
		

		JFreeChart jfreechart = ChartFactory.createBarChart3D("主要IPC技术构成分析",
				"按部划分", "专利量", dataset, PlotOrientation.VERTICAL, true, true,
				true);
		// 获得图表区域对象
		CategoryPlot categoryPlot = (CategoryPlot) jfreechart.getPlot();
		// 设置网格线可见
		categoryPlot.setDomainGridlinesVisible(true);
		// 获得x轴对象
		CategoryAxis categoryAxis = categoryPlot.getDomainAxis();
		// 设置x轴显示的分类名称的显示位置，如果不设置则水平显示
		// 设置后，可以斜像显示，但分类角度，图表空间有限时，建议采用
		categoryAxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(0.39269908169872414D));
		categoryAxis.setCategoryMargin(0.0D);
		// 获显示图形对象
		BarRenderer3D barRenderer3d = (BarRenderer3D) categoryPlot
				.getRenderer();
		// 设置不显示边框线
		barRenderer3d.setDrawBarOutline(false);
		// 将图表已数据流的方式返回给客户端
		ChartUtilities.writeChartAsPNG(response.getOutputStream(), jfreechart,
				600, 400);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static CategoryDataset createDataset() throws SQLException {

		DefaultCategoryDataset defaultdataset = new DefaultCategoryDataset();

		DBUtil db = new DBUtil();
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Integer> ipcNumberList = new ArrayList<Integer>();
		ArrayList<String> ipcCategoryList = new ArrayList<String>();
		
		
		String sql;
		sql = "SELECT COUNT(*),SUBSTRING(MIPC,1,1) from ai GROUP BY SUBSTRING(MIPC,1,1)";
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        while(rs.next()){
        	ipcNumberList.add(rs.getInt(1));
        	ipcCategoryList.add(rs.getString(2));
        }
        System.out.println(ipcNumberList);
        System.out.println(ipcCategoryList);
		
        int[] number = new int[ipcNumberList.size()];
        String[] category = new String[ipcCategoryList.size()];
        for(int i = 0; i < number.length; i++){
        	number[i] = ipcNumberList.get(i);
        	category[i] = ipcCategoryList.get(i);
        }
        
        for(int i=0;i < number.length; i++){
        	String category2 = "IPC";
        	defaultdataset.addValue(number[i], category[i], category2);
        }
        
        rs.close();
		pstmt.close();
		conn.close();

		return defaultdataset;
	}
	public static void main(String[] args) throws SQLException {
		createDataset();
	}
}
