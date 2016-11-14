package com.patent.tld;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import java.text.SimpleDateFormat;
import com.patent.ORM.*;
import com.patent.DAO.*;
import com.patent.util.*;

/** 文本新闻列表自定义标签类 */
public class TextnewsTag extends SimpleTagSupport{
	SimpleDateFormat df = null;
	BaseDAO dao = null;		//数据库DAO接口
	News obj= null;
	String hql= null;
	String newstype="4";	//新闻类型:0-最新新闻 1-头条新闻 2-热点新闻 3-精彩推荐 4-所有类型
	String section;	 		//新闻栏目编号,多个栏目编号中间用号码分隔,如:I_001,I_002
	int number;      		//新闻的数量
	int pageNo = 1;			//页号
	int titlelen;    		//标题的字数
	String split="0";  		//是否需要分隔符　默认为无分隔符    
	String prex="";   		//前置图片，默认为无，'0'为'·',否则为其它．
	String prexchar=""; 	//前缀字符
	String splitchar=""; 	//分隔字符
	String baseurl =""; 	//基本URL
	String dateFormat="";   //日期格式，默认为无，y:年 M:月 d:日 k:时 m:分 s:秒 如：yyyy-MM-dd kk:mm:ss 需要外部提供.newslist_title与.newslist_date两个CSS样式来控制显示效果
	String dateStr="";		//格式化后的日期字符串
	
	/** 标签体处理 */
    public void doTag() throws JspException, IOException{
    	//使用WebApplicationContextUtils工具类获取Spring IOC容器中的dao实例
    	dao = (BaseDAOImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(((PageContext)getJspContext()).getServletContext()).getBean("dao");
		if (dateFormat.length()>0){//新闻列表需要日期
			try{
				df = new SimpleDateFormat(dateFormat);
			}catch(Exception ex){//传入的日期格式不正确时,使用默认的yyyy-MM-dd日期格式
				df = new SimpleDateFormat("yyyy-MM-dd");
			}
		}
		int i=1;
		//构造查询新闻列表的HQL语句
		if (newstype.equals("4")){//所有类型
			hql=" from News as a where a.status=1 and a.newscolumns.columnCode in("+Tools.formatString(section)+") order by a.priority desc,a.id desc";			
		}else{//指定类型
			hql=" from News as a where a.status=1 and a.newscolumns.columnCode in("+Tools.formatString(section)+") and a.newsType="+newstype+" order by a.priority desc,a.id desc";			
		}
		StringBuffer sb=new StringBuffer();
		List list=dao.query(hql,pageNo,number);//直接调用dao查询新闻列表
		if(list==null||list.size()==0){
	    	//查询结果集为空,输出空字串到页面上
	    	getJspContext().getOut().println(""); 			
			return;
		}
		//标题描红
		String redStart = "";
		String redEnd = "";
		String tempTile="";
		String divDate1="",divDate2="";		
		Iterator it=list.iterator();
		sb.append("<ul>\n");
		while(it.hasNext()){
			if(split.equals("1")){//处理栏间分割符				
				if(prex.equals("0")){//处理字符前缀
					if (i%2==1){					
						prexchar="&middot;";
					}else{
						prexchar="";
					}			
				}
				if (i%2==1){
					splitchar="&nbsp;|&nbsp;";
				}else{
					splitchar="";
				}
			}else{//无栏间分割符
				splitchar="";
				if(prex.equals("0")){//处理字符前缀		
					prexchar="&middot;";
				}				
			}			
			obj=(News)it.next();
			//标题是否需要描红
			if (obj.getRed()==1){
				redStart ="<span style='color:#ff0000;'>";
				redEnd = "</span>";
			}else{
				redStart = "";
				redEnd = "";				
			}
			if(obj.getTitle().length()>titlelen){//标题字符长度超长
				tempTile=Tools.cutString(obj.getTitle(), titlelen*2);
			}else{//标题字符长度不超长
				tempTile=obj.getTitle();
			}
			if (df!=null){//新闻列表需要日期
				dateStr = df.format(obj.getCdate());
				divDate1="<div class='newslist_title'>";
				divDate2="</div><div class='newslist_date'>"+dateStr+"</div>";
			}else{//新闻列表不需要日期
				divDate1="";
				divDate2="";
			}
			if(prex.endsWith("_.jpg")||prex.endsWith("_.gif")||prex.endsWith("_.png")){//标题前使用顺序图片做为前缀
				sb.append("<li>"+divDate1+"<img src='"+prex.replaceAll("_.","_"+i+".")+"'/><a href='"+baseurl+obj.getHtmlPath()+"' title='"+obj.getTitle()+"'>"+redStart+tempTile+redEnd+"</a>"+divDate2+splitchar+"</li>\n");
			}else if(prex.endsWith(".jpg")||prex.endsWith(".gif")||prex.endsWith(".png")){//标题前使用固定图片做为前缀
				sb.append("<li>"+divDate1+"<img src='"+prex+"'/><a href='"+baseurl+obj.getHtmlPath()+"' title='"+obj.getTitle()+"'>"+redStart+tempTile+redEnd+"</a>"+divDate2+splitchar+"</li>\n");
			}else{//标题前使用文本字符做为前缀			
				sb.append("<li>"+divDate1+prexchar+"<a href='"+baseurl+obj.getHtmlPath()+"' title='"+obj.getTitle()+"'>"+redStart+tempTile+redEnd+"</a>"+divDate2+splitchar+"</li>\n");
			}
			i++;
		}
		sb.append("</ul>\n");
    	//输出处理结果到页面上
    	getJspContext().getOut().println(sb); 
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getPrex() {
		return prex;
	}

	public void setPrex(String prex) {
		this.prex = prex;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSplit() {
		return split;
	}

	public void setSplit(String split) {
		this.split = split;
	}

	public int getTitlelen() {
		return titlelen;
	}

	public void setTitlelen(int titlelen) {
		this.titlelen = titlelen;
	}

	public String getNewstype() {
		return newstype;
	}

	public void setNewstype(String newstype) {
		this.newstype = newstype;
	}

	public String getBaseurl() {
		return baseurl;
	}

	public void setBaseurl(String baseurl) {
		this.baseurl = baseurl;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

}
