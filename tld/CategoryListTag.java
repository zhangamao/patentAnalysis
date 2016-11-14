package com.patent.tld;

import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.patent.ORM.*;
import com.patent.DAO.*;

/** 商品类别列表自定义标签类 */
public class CategoryListTag extends SimpleTagSupport{
	int listtype = 1;	//1-最新商品类别列表,2-促销商品类别列表,默认值为1	
	BaseDAO dao = null; 	//数据库DAO接口
	String hql= null;
	List<Category> cateList1,cateList2;
	Iterator<Category> it1,it2;
	Category cate1,cate2;
	String jsp = "newmer.jsp";
	
	/** 标签体处理 */
    @SuppressWarnings("unchecked")
	public void doTag() throws JspException, IOException{
    	if (listtype==2){//促销商品类别列表
    		jsp = "prommer.jsp";
    	}
    	String contextPath = ((HttpServletRequest)((PageContext)getJspContext()).getRequest()).getContextPath();
    	//使用WebApplicationContextUtils工具类获取Spring IOC容器中的dao实例
    	dao = (BaseDAOImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(((PageContext)getJspContext()).getServletContext()).getBean("dao");    	
    	hql = "from Category as a where a.category is null order by a.id asc";
    	//装载第一级商品类别
    	cateList1 = dao.query(hql);
    	StringBuffer sb=new StringBuffer();
    	//创建两层商品类别列表
    	it1 = cateList1.iterator();
    	while(it1.hasNext()){
    		cate1 = it1.next();
    		sb.append("<div class='cate_name'><a target='_self' href='"+contextPath+"/webs/"+jsp+"?cateid="+cate1.getId()+"'>"+cate1.getCateName().trim()+"</a></div>\n");
    		sb.append("<div class='cate_list'>\n");
    		sb.append("<ul>\n");
    		//装载第二级商品类别
    		cateList2 = dao.query("from Category as a where a.category.id="+cate1.getId());
    		it2 = cateList2.iterator();
    		while(it2.hasNext()){
    			cate2 = it2.next();
    			sb.append("<li><a target='_self' href='"+contextPath+"/webs/"+jsp+"?cateid="+cate2.getId()+"'>"+cate2.getCateName().trim()+"</a></li>\n");
    		}
    		sb.append("</ul>\n");
    		sb.append("</div>\n");    		
    	}
    	//输出处理结果到页面上
    	getJspContext().getOut().println(sb);     	
    }

	public int getListtype() {
		return listtype;
	}

	public void setListtype(int listtype) {
		this.listtype = listtype;
	}
}
