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

public class TechChartServlet_IPC extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置返回类型为图片
		response.setContentType("image/png");
		// 获取数据集对象
		CategoryDataset dataset = createDataset();
		// 创建图形对象
		JFreeChart jfreechart = ChartFactory.createLineChart("技术分类专利量趋势分析--申请日",
				null, "专利量（件数）", dataset, PlotOrientation.VERTICAL, true,
				true, true);
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

	private CategoryDataset createDataset() {
		// TODO Auto-generated method stub
		DefaultCategoryDataset defaultDataset = new DefaultCategoryDataset();

		int[] G = {0,28,26,43,49,58,69,88,120,123};
		int[] H = {0,7,11,8,8,524,66,24,14,28};
		int[] A = {0,3,6,9,3,10,13,10,18,16};
		int[] B = {0,2,6,5,8,6,9,13,15,16};
		int[] F = {0,0,3,2,5,8,7,6,5,6};
		int[] E = {0,0,2,3,1,1,2,3,3,3};
		int[] C = {0,0,2,0,0,2,4,1,1,1};
		int[] D = {0,0,0,0,0,1,0,0,0,4};

		int i = 2006;
		for (int j = 0; j < 10; j++) {
			defaultDataset.addValue(G[j], "G", i + "年");
			defaultDataset.addValue(H[j], "H", i + "年");
			defaultDataset.addValue(A[j], "A", i + "年");
			defaultDataset.addValue(B[j], "B", i + "年");
			defaultDataset.addValue(F[j], "F", i + "年");
			defaultDataset.addValue(E[j], "E", i + "年");
			defaultDataset.addValue(C[j], "C", i + "年");
			defaultDataset.addValue(D[j], "D", i + "年");
			i++;
		}

		return defaultDataset;
	}
}
