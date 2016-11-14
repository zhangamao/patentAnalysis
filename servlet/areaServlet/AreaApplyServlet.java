package com.patent.servlet.areaServlet;

import java.awt.Font;
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
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.crawl.DBUtil;

public class AreaApplyServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置返回类型为图片
		response.setContentType("image/png");
		// 获取数据集对象
		PieDataset dataset;
		try {
			dataset = createDataset();
		
		// 创建图形对象
		JFreeChart jfreechart = ChartFactory.createPieChart3D("区域专利量分布",
				dataset, true, true, true);
		// 获得图表区域对象
		PiePlot piePlot = (PiePlot) jfreechart.getPlot();
		piePlot.setLabelFont(new Font("宋体", 0, 12));
		piePlot.setLabelFont(new Font("宋体", 0, 12));
		// 设置图表区域无数据时的默认显示文字
		piePlot.setNoDataMessage("没有专利数据");
		// 设置图表区域不是圆形，由于是3D的饼形图，建议设置为false
		piePlot.setCircular(false);
		// 设置图表区域文字与图表区域的间隔距离，0.02表示2%
		piePlot.setLabelGap(0.02D);
		// 将图表已数据流的方式返回给客户端
		ChartUtilities.writeChartAsPNG(response.getOutputStream(), jfreechart,
				600, 400);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static PieDataset createDataset() throws SQLException {
		DefaultPieDataset defaultPieDataset = new DefaultPieDataset();
		// 从数据库中查询
		DBUtil db = new DBUtil();
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT COUNT(id), ProvinceCode FROM ai GROUP BY ProvinceCode HAVING COUNT(id) > 20";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		ArrayList<String> areaList = new ArrayList<String>();
		ArrayList<Integer> numList = new ArrayList<Integer>();
		while (rs.next()) {
			areaList.add(rs.getString(2));
			numList.add(rs.getInt(1));
		}
		System.out.println(areaList);
		System.out.println(numList);

		for (int i = 0; i < areaList.size(); i++) {

			defaultPieDataset.setValue(areaList.get(i), numList.get(i));
		}

		rs.close();
		pstmt.close();
		conn.close();

		return defaultPieDataset;
	}

	public static void main(String[] args) throws SQLException {
		createDataset();
	}
}
