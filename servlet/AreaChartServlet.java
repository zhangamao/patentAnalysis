package com.patent.servlet;

import java.awt.Font;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class AreaChartServlet extends HttpServlet {

	private static final long serialVersionUID = 5852523271721922574L;

	public AreaChartServlet(){
		
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置返回类型为图片
		response.setContentType("image/png");
		// 获取数据集对象
		PieDataset dataset = createPieDataset();
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
				500, 270);
	}

	public static PieDataset createPieDataset() {

		// 创建饼形图数据对象
		DefaultPieDataset defaultPieDataset = new DefaultPieDataset();
		// 分别图形区域的说明和数据
		defaultPieDataset.setValue("江苏省", 682);
		defaultPieDataset.setValue("北京市", 231);
		defaultPieDataset.setValue("广东省", 143);
		defaultPieDataset.setValue("上海市", 89);
		defaultPieDataset.setValue("浙江省", 84);
		defaultPieDataset.setValue("山东省", 47);
		defaultPieDataset.setValue("湖北省", 41);
		defaultPieDataset.setValue("辽宁省", 41);
		defaultPieDataset.setValue("河南省", 36);
		defaultPieDataset.setValue("美国", 35);
		return defaultPieDataset;
	}
}
