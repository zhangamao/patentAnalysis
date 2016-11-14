package com.patent.tld;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.patent.ORM.*;
import com.patent.service.MemberLevelService;
import com.patent.service.MemberLevelServiceImpl;
import com.patent.util.Tools;
import com.patent.DAO.*;

/** 商品列表自定义标签类 */
public class MerMoreTag extends SimpleTagSupport{
	int listtype = 1;	//1-最新商品列表,2-促销商品列表,默认值为1
	int size = 6;		//商品列表条数,默认为6条
	int pageNo = 1; 	//页号,默认为第一页
	int picWidth=76;	//商品图片宽度,默认值76
	String baseurl =""; //基本URL
	String cateid ="";	//商品分类ID
	BaseDAO dao = null; 	//数据库DAO接口
	MemberLevelService memberLevelService; //会员级别业务逻辑接口		
	String hql= null;
	List<Merchandise> merlist;
	Iterator<Merchandise> it;
	Merchandise mer;
	List<Category> catelist;
	Iterator<Category> cateit;
	Category cate;	
	String filename;
	Map map;
	String height_width;
	String merDesc;
	String ids=null;
	
	/** 标签体处理 */
    @SuppressWarnings("unchecked")
	public void doTag() throws JspException, IOException{
    	//使用WebApplicationContextUtils工具类获取Spring IOC容器中的dao与memberLevelService实例
    	dao = (BaseDAOImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(((PageContext)getJspContext()).getServletContext()).getBean("dao");    	
    	memberLevelService = (MemberLevelServiceImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(((PageContext)getJspContext()).getServletContext()).getBean("memberLevelService");    	
		//如果指定了商品分类则查找出该类及其子类的ID号,形成ID串
    	if (cateid.trim().length()>0){//指定商品分类
			hql = "from Category as a where a.id="+cateid+" or a.category.id="+cateid;
			catelist = dao.query(hql);
			cateit = catelist.iterator();
			while (cateit.hasNext()){
				cate = cateit.next();
				if (ids==null){
					ids = cate.getId().toString();
				}else{
					ids = ids+","+cate.getId().toString();
				}
			}
		}   	
    	if (listtype==1){//最新商品列表
    		if (ids!=null){//指定商品分类
    			hql = "from Merchandise as a where a.status=1 and a.category.id in("+ids+") order by a.id desc";
    		}else{//所有商品分类
    			hql = "from Merchandise as a where a.status=1 order by a.id desc";
    		}    		
    	}else{//促销商品列表
    		if (ids!=null){//指定商品分类
    			hql = "from Merchandise as a where a.status=1 and a.special=1 and a.category.id in("+ids+") order by (a.price-a.sprice)*100/a.price desc";
    		}else{//所有商品分类
    			hql = "from Merchandise as a where a.status=1 and a.special=1 order by (a.price-a.sprice)*100/a.price desc";
    		}    		
    	}
    	StringBuffer sb=new StringBuffer();
    	sb.append("<ul>\n");
    	merlist = dao.query(hql, pageNo, size);
    	it = merlist.iterator();
    	while(it.hasNext()){
    		mer = it.next();
    		sb.append("<li>\n");
    		sb.append("<div class='merlist_photo'>");
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
    			sb.append("<a href='"+baseurl+mer.getHtmlPath()+"' title='商品名称:"+mer.getMerName()+"\n商品型号:"+mer.getMerModel()+"'><img src='"+baseurl+"/images/noimages.gif' width='"+picWidth+"'/></a>");
    		}
    		sb.append("</div>\n");
    		if (mer.getMerDesc()!=null){
        		//对商品描述的内容进行unescape处理
    			merDesc = Tools.unescape(mer.getMerDesc());
    			//剔除商品描述内容中的HTML标签,只留下文本内容
    			merDesc=merDesc.replaceAll("</?[^>]+>","");
    			merDesc=merDesc.replaceAll("&nbsp;","").replaceAll("\n", "").replaceAll("\r", "").trim(); 
    			//对超过150个汉字的内容进行截断处理
    			if (merDesc.getBytes().length>300){
    				merDesc = Tools.cutString(merDesc,300)+"...";
    			}
    		}else{
    			merDesc="";
    		}
    		sb.append("<div class='merlist_desc'>"+merDesc+"</div>\n"); 
    		sb.append("<div class='merlist_price'>\n");
    		sb.append("<a href='"+baseurl+mer.getHtmlPath()+"'><img src='"+baseurl+"/images/icon_info.gif'/></a><br/>\n");
    		sb.append("<img src='"+baseurl+"/images/icon_buy.gif' style='cursor:pointer' onclick='buy("+mer.getId()+")'/><br/>\n");    		
        	if (listtype==1){//最新商品列表,如果有优惠价,优惠价即为会员价,否则会员价为最低优惠会员价
        		sb.append("市场价："+Tools.formatCcurrency(mer.getPrice())+"<br/>\n");
        		double tmpprice = 0;
        		if (mer.getSpecial().intValue()==1){//有促销价
        			tmpprice = mer.getSprice().doubleValue();
        		}else{
        			tmpprice = mer.getPrice()*(100-memberLevelService.getInitMemberlevel().getFavourable().intValue())/100;
        		}        		
        		sb.append("<span class='redtext'>会员价："+Tools.formatCcurrency(tmpprice)+"</span>\n");
        	}else{//促销商品列表
        		sb.append("原价："+Tools.formatCcurrency(mer.getPrice())+"<br/>\n");        		
        		sb.append("<span class='redtext'>特价："+Tools.formatCcurrency(mer.getSprice())+"</span>\n");
        	}
        	sb.append("</div>\n");
        	sb.append("</li>\n");
    	}
    	if (merlist==null || merlist.size()<1){
    		sb.append("<br/>&nbsp;&nbsp;对不起,暂时没有满足条件的商品记录!\n");
    	}
    	sb.append("</ul>\n");
    	//输出处理结果到页面上
    	getJspContext().getOut().println(sb);     	
    }

	public int getListtype() {
		return listtype;
	}

	public void setListtype(int listtype) {
		this.listtype = listtype;
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

	public String getCateid() {
		return cateid;
	}

	public void setCateid(String cateid) {
		this.cateid = cateid;
	}
}
