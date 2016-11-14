package com.report.predict;

import com.crawl.DBUtil;
import com.report.lda.Corpus;
import com.report.lda.LdaGibbsSampler;
import com.report.lda.LdaUtil;
import com.report.textrank.TextRankSummary;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
public class Report {

	public DBUtil dbManager;
	public Connection conn;
	public PreparedStatement ps;
	public ResultSet rs;
	public GM gm;
	public TextRankSummary summary;
	
	/**
	 * 报告内容如下：
	 * 1.技术成熟度参数
	 * 2.数据的预测值
	 * 4.得到的主题
	 * 3.相关专利的概要显示
	 * @return
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String geReport() throws SQLException, IOException{
		//生成报告内容
		
		StringBuffer reportSb = new StringBuffer();
		StringBuffer maturitysb = new StringBuffer("技术成熟度参数:\n");
		
		double[] data = TechS();
		String[] dataM = {"技术生长率", "技术成熟系数", "技术衰老系数", "新技术特征系数"};
		for(int i = 0; i < data.length; i++){
			maturitysb.append(dataM[i]+ "： "+ data[i]+ "\n");
		}
		
		double predictValue = predictValue();
		String topicStr = getTopic();
		String summaryStr = getAbstract();
		
		reportSb.append(maturitysb);
		reportSb.append("数据预测结果为："+predictValue+"\n");
		reportSb.append("得到的技术主题为："+"\n"+topicStr+"\n");
		reportSb.append("随机选取的专利的概括：\n"+ summaryStr+"\n");
		
		return reportSb.toString();
	}
	/**
	 * 计算技术成熟度的参数
	 * @return
	 * @throws SQLException 
	 */
	public double[] TechS() throws SQLException{
		double[] data = new double[4];
		int a = 0; //发明专利数目
		int b = 0;  //实用新型专利数
		int c = 0; //外观设计专利数
		int A = 0;  //
		
		dbManager = new DBUtil();
		conn = dbManager.getConnection();
		
		String sql = "select count(*) from ai where ApplicationDate like '%2015%' and Abstract like '%发明%'";
		 ps = conn.prepareStatement(sql);
		 rs = ps.executeQuery();
		while(rs.next()){
			a = rs.getInt(1);
		}
	     sql = "select count(*) from ai where ApplicationDate like '%2015%' and Abstract like '%实用新型%'";
		 ps = conn.prepareStatement(sql);
		 rs = ps.executeQuery();
		while(rs.next()){
			b = rs.getInt(1);
		}
		
		 sql = "select count(*) from ai where ApplicationDate like '%2015%' and Abstract like '%外观%'";
		 ps = conn.prepareStatement(sql);
		 rs = ps.executeQuery();
		while(rs.next()){
			c = rs.getInt(1);
		}
	   
		 sql = "select count(*) from ai where ApplicationDate >= '2011' and Abstract like '%发明%'";
		 ps = conn.prepareStatement(sql);
		 rs = ps.executeQuery();
		 while(rs.next()){
			A = rs.getInt(1);
		}
		
		 double r = (double)a/A;
		 double alfa = (double)a/(a+b);
		 double beta = (double)(a+b)/(a+b+c);
		 double N = r*r + alfa*alfa;
		 data[0] = r;
		 data[1] = alfa;
		 data[2] = beta;
		 data[3] = N;
//		 System.out.println(a+"  "+b + "  "+ c+"  "+ A);
//		 for(int i = 0; i < data.length; i++){
//			 System.out.println(data[i]);
//		 }
		 rs.close();
		 ps.close();
		 conn.close();
		 
		return data;
	}
	
	/**
	 * 数据量的预测
	 * @return
	 * @throws SQLException
	 */	
	public double predictValue() throws SQLException {
		double predictValue = 0.0;
		dbManager = new DBUtil();
		conn = dbManager.getConnection();
		String sql = "";
	
		int fs[] = new int[10];
		for(int i = 0;i < 10; i++){
			sql = "select count(*) from ai where ApplicationDate like '%"+(2006+i)+"%'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				fs[i] = rs.getInt(1);
			}
		}
		
		//integer convert to double
		double[] fs1 = new double[10];
		for(int i = 0; i < fs.length; i++){
			fs1[i] = Double.parseDouble(fs[i]+"");
		}
		
		//compute the predict  value
		 gm = new GM();
		predictValue = gm.gm(fs1, 3);
		//System.out.println(predictValue);
		rs.close();
		ps.close();
		conn.close();
		
		return predictValue;
	}
	
	/**
	 * 训练集主题结果显示
	 * LDA
	 * @throws IOException 
	 */
	public String getTopic() throws IOException{
		//1.load corpus from disk
		//Corpus corpus = Corpus.load("F:\\segCorpus\\AIseg\\seg");
		Corpus corpus = Corpus.load("F:\\segCorpus\\2016AISeg");
		
		//2.create a LDA sampler
		LdaGibbsSampler ldaGibbsSample = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());
		ldaGibbsSample.gibbs(10);
		double[][] phi = ldaGibbsSample.getPhi();
		Map<String, Double>[] topicMap = LdaUtil.translate(phi, corpus.getVocabulary(), 10);
		
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for(Map<String, Double> result: topicMap){
			
			sb.append("topic "+ (i++) +"\n");
			for(Map.Entry<String, Double> entry : result.entrySet()){
				sb.append(entry.getKey()+"  "+"\n");
				
			}
		}
		return sb.toString();
	}
	
	/**
	 * 提取文章的摘要
	 * @param args
	 * @throws SQLException 
	 * @throws SQLException
	 */
	public String getAbstract() throws SQLException{
		StringBuffer cnt = new StringBuffer();
		String sql = "select Abstract from ai ORDER BY RAND() LIMIT 0,10";
		dbManager = new DBUtil();
		conn = dbManager.getConnection();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		List<List<String>> res = new ArrayList<List<String>>();
		List<String> abst;
		StringBuffer sb = new StringBuffer();
		int i = 0;
		while(rs.next()){
			cnt.append(rs.getString(1));
			abst = TextRankSummary.getTopSentenceList(cnt.toString(), 4);
			sb.append((i++)+". "+abst.toString()+"\n");
			//res.add(abst);
		}
		rs.close();
		ps.close();
		conn.close();
		
		
		return sb.toString();  //返回最终的结果
	}
	
	
	public static void main(String[] args) throws SQLException, IOException {
		Report report = new Report();
		System.out.println(report.geReport());
	}
}
