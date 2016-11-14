package com.patent.servlet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
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

public class InventorChartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置返回类型为图片
		response.setContentType("image/png");
		// 创建图像对象
		CategoryDataset dataset = createDataset();
		JFreeChart jfreechart = ChartFactory.createLineChart("发明人数量趋势分析", null,
				"发明人数", dataset, PlotOrientation.VERTICAL, true, true, true);
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
		categoryplot.setRangeGridlinesVisible(false);
		// 获显示线条对象
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
				.getRenderer();
		lineandshaperenderer.setBaseShapesVisible(true);
		lineandshaperenderer.setDrawOutlines(true);
		lineandshaperenderer.setUseFillPaint(true);
		lineandshaperenderer.setBaseFillPaint(Color.white);

		// 设置折现加粗
		lineandshaperenderer.setSeriesStroke(0, new BasicStroke(2F));
		lineandshaperenderer.setSeriesOutlineStroke(0, new BasicStroke(2.0F));

		// 设置折线拐点
		lineandshaperenderer.setSeriesShape(0,
				new java.awt.geom.Ellipse2D.Double(-5D, -5D, 10D, 10D));
		// 将图表以数据流的方式返回给客户端
		ChartUtilities.writeChartAsPNG(response.getOutputStream(), jfreechart,
				600, 400);

	}

	public static CategoryDataset createDataset() {

		DefaultCategoryDataset defaultDataset = new DefaultCategoryDataset();

		int[] number = { 0, 91, 135, 212, 181, 278, 357, 422, 535, 564 };
		int j = 2006;
		for (int i = 0; i < 10; i++) {

			defaultDataset.addValue(number[i], "申请数量", j + "年");
			j++;
		}
		return defaultDataset;
	}
}
