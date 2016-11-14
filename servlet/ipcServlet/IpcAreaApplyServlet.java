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

public class IpcAreaApplyServlet extends HttpServlet {

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
		

		JFreeChart jfreechart = ChartFactory.createBarChart3D("IPC区域分布分析",
				"区域", "专利量", dataset, PlotOrientation.VERTICAL, true, true,
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
		DefaultCategoryDataset defaultDataset = new DefaultCategoryDataset();
		// 从数据库中查询
		DBUtil db = new DBUtil();
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT ProvinceCode, COUNT(id) FROM ai GROUP BY SUBSTRING(MIPC,1,1)";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		ArrayList<String> areaList = new ArrayList<String>();
		ArrayList<Integer> numList = new ArrayList<Integer>();
		while (rs.next()) {
			areaList.add(rs.getString(1));
			numList.add(rs.getInt(2));
		}
		System.out.println(areaList);
		System.out.println(numList);

		for (int i = 0; i < areaList.size(); i++) {
			String category = "ipc区域";
			defaultDataset.addValue(numList.get(i), areaList.get(i), category);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return defaultDataset;
	}

	public static void main(String[] args) throws SQLException {

		createDataset();
	}
}
