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

/** 幻灯片新闻列表自定义标签类 */
public class SlidenewsTag extends SimpleTagSupport {
	BaseDAO dao = null; 	//数据库DAO接口
	News obj= null;
	String hql= null;
	String newstype="4";	//新闻类型:0-最新新闻 1-头条新闻 2-热点新闻 3-精彩推荐  4-所有类型
	String section;	 		//新闻栏目编号,多个栏目编号中间用号码分隔,如:I_001,I_002
	int number;    			//传入的新闻条数
	int width;     			//幻灯区域的宽度
	int height;    			//幻灯区域的高度
	int titlelen=20;   		//标题字数
	String baseurl =""; 	//基本URL
	String slideno ="1"; 	//在本页中当前幻灯区的序号
	
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
		sb.append("    <script language=javascript>\n");
		sb.append("	var focus_width"+slideno+"="+width+";     /*幻灯片新闻图片宽度*/\n");
		sb.append("	var focus_height"+slideno+"="+height+";    /*幻灯片新闻图片高度*/\n");
		sb.append("	var text_height"+slideno+"=20;    /*幻灯片新闻文字标题高度*/\n");
		sb.append("	var swf_height"+slideno+" = focus_height"+slideno+"+text_height"+slideno+";\n");
		sb.append("	var pics"+slideno+" = '';\n");
		sb.append("	var links"+slideno+" = '';\n");
		sb.append("	var texts"+slideno+" = '';\n");
		sb.append("	function ati"+slideno+"(url, img, title)\n");
		sb.append("	{\n");
		sb.append("		if(pics"+slideno+" != '')\n");
		sb.append("		{\n");
		sb.append("			pics"+slideno+" = \"|\" + pics"+slideno+";\n");
		sb.append("			links"+slideno+" = \"|\" + links"+slideno+";\n");
		sb.append("			texts"+slideno+" = \"|\" + texts"+slideno+";\n");
		sb.append("		}");
		sb.append("		pics"+slideno+" = escape(img) + pics"+slideno+";\n");
		sb.append("		links"+slideno+" = escape(url) + links"+slideno+";\n");
		sb.append("		texts"+slideno+" = title + texts"+slideno+";\n");
		sb.append("	}\n");
		sb.append("    </script>\n");
		sb.append("    <script language=javascript>	\n");
		while(it.hasNext()){
			obj=(News)it.next();
			if (obj.getTitle().length()>titlelen){
				sb.append("      ati"+slideno+"('"+baseurl+obj.getHtmlPath()+"', '"+baseurl+"/"+obj.getPicture().trim()+"', '"+Tools.cutString(obj.getTitle(), titlelen*2)+"');\n");
			}else{
				sb.append("      ati"+slideno+"('"+baseurl+obj.getHtmlPath()+"', '"+baseurl+"/"+obj.getPicture().trim()+"', '"+obj.getTitle()+"');\n");
			}
		}		
		sb.append("	document.write('<embed src=\""+baseurl+"/js/pixviewer.swf\" wmode=\"opaque\" FlashVars=\"pics='+pics"+slideno+"+'&links='+links"+slideno+"+'&texts='+texts"+slideno+"+'&borderwidth='+focus_width"+slideno+"+'&borderheight='+focus_height"+slideno+"+'&textheight='+text_height"+slideno+"+'\" menu=\"false\" bgcolor=\"#DADADA\" quality=\"high\" width=\"'+ focus_width"+slideno+"+'\" height=\"'+ swf_height"+slideno+" +'\" allowScriptAccess=\"sameDomain\" type=\"application/x-shockwave-flash\"/>');	\n");
		sb.append("</script>\n");
    	//输出处理结果到页面上
    	getJspContext().getOut().println(sb);
	  }

	public String getBaseurl() {
		return baseurl;
	}
	public void setBaseurl(String baseurl) {
		this.baseurl = baseurl;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getNewstype() {
		return newstype;
	}
	public void setNewstype(String newstype) {
		this.newstype = newstype;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
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
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getSlideno() {
		return slideno;
	}
	public void setSlideno(String slideno) {
		this.slideno = slideno;
	}

}
