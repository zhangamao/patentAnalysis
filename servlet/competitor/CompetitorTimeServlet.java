package com.patent.servlet.competitor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;

import com.crawl.DBUtil;

public class CompetitorTimeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置返回类型为图片
		response.setContentType("image/png");
		// 创建图像对象
		CategoryDataset dataset;
		try {
			dataset = createDataset();

			JFreeChart jfreechart = ChartFactory.createLineChart("主要发明人申请趋势分析",
					"李宗诚", "专利量", dataset, PlotOrientation.VERTICAL, true, true,
					true);
			// 设置图表的子标题
			jfreechart.addSubtitle(new TextTitle("按年份"));
			TextTitle texttitle = new TextTitle("日期" + new Date());

			// 设置标题字体
			texttitle.setFont(new Font("黑体", 0, 10));
			// 设置标题向下对齐
			texttitle.setPosition(RectangleEdge.BOTTOM);
			// 设置标题向右对齐
			texttitle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
			// 添加图表的子标题
			jfreechart.addSubtitle(texttitle);
			// 设置图表的背景色为白色
			jfreechart.setBackgroundPaint(Color.white);

			// 获得图表区域对象
			CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
			categoryplot.setBackgroundPaint(Color.lightGray);
			categoryplot.setRangeGridlinesVisible(true);
			// 获显示线条对象
			LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
					.getRenderer();
			lineandshaperenderer.setBaseShapesVisible(true);
			lineandshaperenderer.setDrawOutlines(true);
			lineandshaperenderer.setUseFillPaint(true);
			lineandshaperenderer.setBaseFillPaint(Color.white);

			lineandshaperenderer.setBaseLinesVisible(true);

			// 设置折现加粗
			lineandshaperenderer.setSeriesStroke(0, new BasicStroke(2F));
			lineandshaperenderer.setSeriesOutlineStroke(0,
					new BasicStroke(2.0F));

			// 设置折线拐点
			lineandshaperenderer.setSeriesShape(0,
					new java.awt.geom.Ellipse2D.Double(-5D, -5D, 10D, 10D));
			// 将图表以数据流的方式返回给客户端
			ChartUtilities.writeChartAsPNG(response.getOutputStream(),
					jfreechart, 600, 400);
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
		
		ArrayList<Integer> numList = new ArrayList<Integer>();
		
		int i = 2006;String sql;
		for(int j = 0; j < 10; j++){
			sql = "SELECT COUNT(id) FROM ai WHERE Inventor LIKE '%李宗诚%' AND ApplicationDate LIKE '%"+(2006+j)+"%'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				numList.add(rs.getInt(1));
			}
		}
		System.out.println(numList);
		for(int k = 0; k < numList.size(); k++){
			defaultDataset.addValue(numList.get(k), "申请量", (2006+k)+"年");
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
