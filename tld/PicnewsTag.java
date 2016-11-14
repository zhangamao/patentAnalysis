package com.patent.tld;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.patent.ORM.*;
import com.patent.DAO.*;
import com.patent.util.*;

/** 图片新闻列表自定义标签类 */
public class PicnewsTag extends SimpleTagSupport{
	BaseDAO dao = null;		//数据库DAO接口
	News obj= null;
	String hql= null;
	String newstype="4";	//新闻类型:0-最新新闻 1-头条新闻 2-热点新闻 3-精彩推荐  4-所有类型
	String section;	 		//新闻栏目编号,多个栏目编号中间用号码分隔,如:I_001,I_002
	int width;				//图片宽度
	int height;				//图片高度
	int number=1;			//列表条数
	String hastitle="0";   	//是否需要标题 0:不需要标题　１：需要标题
	int titlelen;          	//标题字数
	String baseurl =""; 	//基本URL	
	
	/** 标签体处理 */
    public void doTag() throws JspException, IOException{
    	//使用WebApplicationContextUtils工具类获取Spring IOC容器中的dao实例
    	dao = (BaseDAOImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(((PageContext)getJspContext()).getServletContext()).getBean("dao");		
    	//构造查询新闻列表的HQL语句
    	if (newstype.equals("4")){//所有类型
			hql=" from News as a where a.status=1 and a.newscolumns.columnCode in("+Tools.formatString(section)+") and a.isPicNews=1 order by a.priority desc,a.id desc";
		}else{
			hql=" from News as a where a.status=1 and a.newscolumns.columnCode in("+Tools.formatString(section)+") and a.newsType="+newstype+" and a.isPicNews=1 order by a.priority desc,a.id desc";
		}
		StringBuffer sb=new StringBuffer();
		List list=dao.query(hql,1,number);
		if(list==null||list.size()==0){
	    	//输出处理结果到页面上
	    	getJspContext().getOut().println(""); 			
			return;
		}
		Iterator it=list.iterator();
		sb.append("<ul>\n");
		while(it.hasNext()){
			obj=(News)it.next();
			sb.append("<li>");
			if(hastitle.equals("1")){//需要标题
				sb.append("<center><a href='"+baseurl+obj.getHtmlPath()+"'>\n");
				sb.append("<img width='"+width+"' alt='"+obj.getTitle()+"' height='"+height+"' src='"+baseurl+"/"+obj.getPicture().trim()+"'>\n");
				sb.append("</a><br/>\n");
				if (obj.getTitle().length()>titlelen){//标题字符过多
					sb.append("<a href='"+baseurl+obj.getHtmlPath()+"' title='"+obj.getTitle()+"'>"+Tools.cutString(obj.getTitle(), titlelen*2)+"</a></center>\n");
				}else{
					sb.append("<a href='"+baseurl+obj.getHtmlPath()+"'>"+obj.getTitle()+"</a></center>\n");
				}
			}else{//不需要标题
				sb.append("<a href='"+obj.getHtmlPath()+"'>\n");
				sb.append("<img width='"+width+"' alt='"+obj.getTitle()+"' height='"+height+"' src='"+baseurl+"/"+obj.getPicture().trim()+"'>\n");
				sb.append("</a>\n");
			}
			sb.append("</li>\n");
		}
		sb.append("</ul>\n");
    	//输出处理结果到页面上
    	getJspContext().getOut().println(sb);
	}

	public String getHastitle() {
		return hastitle;
	}
	public void setHastitle(String hastitle) {
		this.hastitle = hastitle;
	}
	public String getNewstype() {
		return newstype;
	}
	public void setNewstype(String newstype) {
		this.newstype = newstype;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public int getTitlelen() {
		return titlelen;
	}
	public void setTitlelen(int titlelen) {
		this.titlelen = titlelen;
	}
	public String getBaseurl() {
		return baseurl;
	}
	public void setBaseurl(String baseurl) {
		this.baseurl = baseurl;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}	
}
