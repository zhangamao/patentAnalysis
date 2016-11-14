package com.patent.tld;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.patent.ORM.*;
import com.patent.service.*;
import com.patent.util.Tools;
import com.patent.DAO.*;

/** 视频商品列表自定义标签类 */
public class VideoListTag extends SimpleTagSupport{
	int size = 10;		//商品列表条数,默认为10条
	int pageNo = 1; 	//页号,默认为第一页
	int picWidth=120;	//商品图片宽度,默认值120
	String baseurl =""; //基本URL
	BaseDAO dao = null; 	//数据库DAO接口
	String hql= null;
	List<Merchandise> merlist;
	Iterator<Merchandise> it;
	Merchandise mer;
	String filename;
	Map map;
	String height_width;
	
	/** 标签体处理 */
    @SuppressWarnings("unchecked")
	public void doTag() throws JspException, IOException{
    	//使用WebApplicationContextUtils工具类获取Spring IOC容器中的dao实例
    	dao = (BaseDAOImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(((PageContext)getJspContext()).getServletContext()).getBean("dao");
		hql = "from Merchandise as a where a.video is not null and a.status=1 order by a.id desc";
    	StringBuffer sb=new StringBuffer();
    	sb.append("<ul>\n");
    	merlist = dao.query(hql, pageNo, size);
    	it = merlist.iterator();
    	while(it.hasNext()){
    		mer = it.next();
    		sb.append("<li>\n");
    		sb.append("<div class='videolist_photo'>");
    		if (mer.getPicture()!=null && mer.getPicture().trim().length()>0){//有商品图片
        		//分析商品图片的宽度与高度,以确保等比显示
    			filename=((PageContext)getJspContext()).getRequest().getRealPath(mer.getPicture().replaceAll("\\\\","/"));
    			map = Tools.getPicWidthHeight(filename); 
    			height_width ="height='"+picWidth+"'";
    			if (map!=null){
    				int width = Integer.valueOf(map.get("width").toString());
    				int height = Integer.valueOf(map.get("height").toString());
    				if (width>=height){//横向图片,控制宽度即可
    					height_width ="width='"+picWidth+"'";
    				}else{//竖向图片,控制高度即可
    					height_width ="height='"+picWidth+"'";
    				}
    			}    			
    			sb.append("<a href='"+baseurl+mer.getHtmlPath()+"' title='商品名称:"+mer.getMerName()+"\n商品型号:"+mer.getMerModel()+"'><img src='"+baseurl+mer.getPicture().trim()+"' "+height_width+"/></a>");
    		}else{//无商品图片
    			sb.append("<a href='"+baseurl+mer.getHtmlPath()+"' title='商品名称:"+mer.getMerName()+"\n商品型号:"+mer.getMerModel()+"'><img align='absmiddle' src='"+baseurl+"/images/noimages.gif' width='"+picWidth+"'/></a>");
    		}    		
    		sb.append("</div>\n");
    		if (mer.getPicture()!=null && mer.getPicture().trim().length()>0){//有商品图片
    			sb.append("<img src='"+baseurl+"/images/play.gif' style='cursor:pointer' onclick=\"playFlv('"+baseurl+mer.getPicture()+"','"+mer.getVideo()+"')\"/>\n");
    		}else{//无商品图片
    			sb.append("<img src='"+baseurl+"/images/play.gif' style='cursor:pointer' onclick=\"playFlv('"+baseurl+"/images/noimages.gif','"+mer.getVideo()+"')\"/>\n");
    		}    		
    		sb.append("<a href='"+baseurl+mer.getHtmlPath()+"' title='商品名称:"+mer.getMerName()+"\n商品型号:"+mer.getMerModel()+"'><img src='"+baseurl+"/images/icon_info.gif'/></a>\n");
    		sb.append("</li>\n");
    	}
    	if (merlist==null || merlist.size()<1){
    		sb.append("<br/>&nbsp;&nbsp;对不起,暂时没有满足条件的商品记录!\n");
    	}    	
    	sb.append("</ul>\n");
    	//输出处理结果到页面上
    	getJspContext().getOut().println(sb);     	
    }

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPicWidth() {
		return picWidth;
	}

	public void setPicWidth(int picWidth) {
		this.picWidth = picWidth;
	}

	public String getBaseurl() {
		return baseurl;
	}

	public void setBaseurl(String baseurl) {
		this.baseurl = baseurl;
	}
}
