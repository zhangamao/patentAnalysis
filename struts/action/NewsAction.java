package com.patent.struts.action;

import java.io.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.patent.ORM.*;
import com.patent.service.*;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import com.patent.util.*;

/** 新闻管理控制器 */
@SuppressWarnings("serial")
public class NewsAction extends ActionSupport implements ModelDriven<News>{
	/** 通过依赖注入NewsServiceImpl与ColumnsServiceImpl组件实例 */
	NewsService service;
	ColumnsService columnsService;
	
	/** 新闻管理所有请求中常用的参数值 */
	private String actionMsg;	//Action间传递的消息参数
	private List<News> newsList;//新闻列表
	private String columnId;	//所属栏目ID
	private List<DoubleSelectNode> doubleSelectNodes;	//级联新闻栏目列表
	private String column1;	//当前选中的第一级新闻栏目
	private String column2;	//当前选中的第二级新闻栏目
	
	/** 上传图片文件的属性 */
	private File pic;	//上传的文件
	private String picContentType;	//上传文件的类型
	private String picFileName;		//上传文件的文件名
	
	/** 当前点击数,以JSON结果类型异步返回给客户端 */
	private String jsonClicks = "0";
	
	//采用模型驱动
	private News model=new News();//用于封装新闻属性模型
	public News getModel() {
		return model;
	}
	
	/** 处理浏览新闻请求 */
	@SuppressWarnings("unchecked")
	public String browseNews(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		newsList = service.browseNews();//调用业务逻辑组件取得新闻列表
		return SUCCESS;
	}
	
	/** 处理新增新闻请求 */
	public String addNews(){
		try {
			//对部分参数进行验证并置默认值
			if (model.getClicks()==null){
				model.setClicks(0);
			}
			if (model.getPriority()==null){
				model.setPriority(0);
			}
			if (model.getFrom()==null||model.getFrom().trim().length()<1){
				model.setFrom("原创");
			}
			if (model.getAuthor()==null||model.getAuthor().trim().length()<1){
				model.setAuthor("不详");
			}
			model.setCdate(new Date());
			model.setEditor(((Admin)ServletActionContext.getRequest().getSession().getAttribute("admin")).getLoginName().trim());
			model.setHtmlPath("/html/news/"+Tools.getRndFilename()+".html");
			model.setStatus(0);

			//对新闻内容进行Escape处理
			model.setContent(Tools.escape(model.getContent().trim()));
			//处理图片新闻的上传文件
			if (model.getIsPicNews().intValue()==1){
				String tempfilename = Tools.getRndFilename()+Tools.getFileExtName(getPicFileName());
				String filename = ServletActionContext.getRequest().getRealPath("/upload").replaceAll("\\\\", "/")+"/"+tempfilename;
				System.out.println("filename="+filename);
				FileOutputStream fos = new FileOutputStream(filename);
				FileInputStream fis = new FileInputStream(getPic());
				byte[] buf = new byte[1024];
				int len = 0;
				while((len=fis.read(buf))>0){
					fos.write(buf,0,len);
				}
				if (fis!=null)fis.close();
				if (fos!=null)fos.close();
				model.setPicture("/upload/"+tempfilename);
			}
			
			//关联相应的新闻栏目
			if (column2!=null){
				model.setNewscolumns(columnsService.loadColumns(Integer.valueOf(column2)));
			}else if(column1!=null){
				model.setNewscolumns(columnsService.loadColumns(Integer.valueOf(column1)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		if (service.saveOrUpdateNews(model)){//调用业务逻辑组件保存新增的新闻
			addActionMessage(getText("news_add_succ"));
		}else{
			addActionMessage(getText("news_add_fail"));
		}
		//构造新闻栏目级联数据
		createDoubleSelect();		
		return SUCCESS;
	}
	
	/** 处理删除新闻请求 */
	public String delNews(){
		if (model.getId()!=null){
			if (service.delNews(model.getId())){//调用业务逻辑组件删除指定的新闻
				actionMsg = getText("news_del_succ");
			}else{
				actionMsg = getText("news_del_fail");
			}			
		}else{
			actionMsg = getText("news_del_fail");
		}
		return "toBrowseNews";
	}
	
	/** 处理查看新闻请求 */
	public String viewNews(){
		if (model.getId()!=null){
			//调用业务逻辑组件装载指定的新闻
			News tempNews = service.loadNews(model.getId());
			if (tempNews!=null){
				try {
					//快速复制源对象中的所有属性到目标对象中
					BeanUtils.copyProperties(model, tempNews);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Newscolumns tmpColumn=tempNews.getNewscolumns();
				//取得所属栏目当前值
				if (tmpColumn.getNewscolumns()!=null){
					column2 = tmpColumn.getId().toString();
					column1 = tmpColumn.getNewscolumns().getId().toString();
				}else{
					column1 = tmpColumn.getId().toString();
				}
				//构造新闻栏目级联数据
				createDoubleSelect();
				return SUCCESS;
			}else{
				actionMsg = getText("news_view_fail");
				return "toBrowseNews";
			}	
		}else{
			actionMsg = getText("news_view_fail");
			return "toBrowseNews";
		}		
	}
	
	/** 处理装载新闻请求 */
	public String editNews(){
		if (model.getId()!=null){
			//调用业务逻辑组件装载指定的新闻
			News tempNews = service.loadNews(model.getId());
			if (tempNews!=null){
				try {
					//快速复制源对象中的所有属性到目标对象中
					BeanUtils.copyProperties(model, tempNews);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Newscolumns tmpColumn=tempNews.getNewscolumns();
				//取得所属栏目当前值
				if (tmpColumn.getNewscolumns()!=null){
					column2 = tmpColumn.getId().toString();
					column1 = tmpColumn.getNewscolumns().getId().toString();
				}else{
					column1 = tmpColumn.getId().toString();
				}
				//构造新闻栏目级联数据
				createDoubleSelect();
				return SUCCESS;
			}else{
				actionMsg = getText("news_view_fail");
				return "toBrowseNews";
			}	
		}else{
			actionMsg = getText("news_view_fail");
			return "toBrowseNews";
		}			
	}
	
	/** 处理更新新闻请求 */
	public String updateNews(){
		News tempNews = service.loadNews(model.getId());//调用业务逻辑组件装载指定的新闻
		try {
			//对部分参数进行验证并置默认值
			if (model.getClicks()==null){
				model.setClicks(0);
			}
			if (model.getPriority()==null){
				model.setPriority(0);
			}
			if (model.getFrom()==null||model.getFrom().trim().length()<1){
				model.setFrom("原创");
			}
			if (model.getAuthor()==null||model.getAuthor().trim().length()<1){
				model.setAuthor("不详");
			}
			model.setCdate(new Date());
			
			//保留以下几个字段的原始值
			model.setEditor(tempNews.getEditor());
			model.setHtmlPath(tempNews.getHtmlPath());
			model.setStatus(tempNews.getStatus());
			model.setPicture(tempNews.getPicture());

			//对新闻内容进行Escape处理
			model.setContent(Tools.escape(model.getContent().trim()));
			//处理图片新闻的上传文件
			if (model.getIsPicNews().intValue()==1 && getPicFileName()!=null && getPicFileName().trim().length()>0){
				String tempfilename = Tools.getRndFilename()+Tools.getFileExtName(getPicFileName());
				String filename = ServletActionContext.getRequest().getRealPath("/upload").replaceAll("\\\\", "/")+"/"+tempfilename;
				FileOutputStream fos = new FileOutputStream(filename);
				FileInputStream fis = new FileInputStream(getPic());
				byte[] buf = new byte[1024];
				int len = 0;
				while((len=fis.read(buf))>0){
					fos.write(buf,0,len);
				}
				if (fis!=null)fis.close();
				if (fos!=null)fos.close();
				model.setPicture("/upload/"+tempfilename);
			}
			
			//关联相应的新闻栏目
			if (column2!=null){
				model.setNewscolumns(columnsService.loadColumns(Integer.valueOf(column2)));
			}else if(column1!=null){
				model.setNewscolumns(columnsService.loadColumns(Integer.valueOf(column1)));
			}
			
			//将新闻置为"未发布"
			model.setStatus(0);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		if (service.saveOrUpdateNews(model)){//调用业务逻辑组件更新指定的新闻
			addActionMessage(getText("news_edit_succ"));
		}else{
			addActionMessage(getText("news_edit_fail"));
		}
		//构造新闻栏目级联数据
		createDoubleSelect();		
		return INPUT;		
	}
	
	/** 处理新闻栏目级联下拉列表请求 */
	public String preAddNews(){
		//构造新闻栏目级联数据
		createDoubleSelect();
		return SUCCESS;		
	}
	
	/** 为指定新闻的点击数加一 */
	public String incNewsClicks(){
		if (model.getId()!=null){
			News tempNews = service.loadNews(model.getId());//调用业务逻辑组件装载指定的新闻
			if (tempNews!=null){
				tempNews.setClicks(tempNews.getClicks().intValue()+1);
				//加一后的点击数由jsonClicks属性带回客户端
				jsonClicks=tempNews.getClicks().toString();	
				service.saveOrUpdateNews(tempNews);//调用业务逻辑组件更新指定的新闻		
			}		
		}
		return SUCCESS;		
	}
	
	/** 发布指定新闻 */
	public String publisNews(){
		if (model.getId()!=null){
			News tempNews = service.loadNews(model.getId());
			if (tempNews!=null){
				HttpServletRequest request = ServletActionContext.getRequest();
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();				
				String url=basePath+"/admin/viewNews.action?id="+tempNews.getId();
				//创建静态页面生成器实例
				HtmlGenerator hg = new HtmlGenerator(basePath);
				//发布成静态页面
				if (hg.createHtmlPage(url, request.getRealPath(tempNews.getHtmlPath()))){
					actionMsg = getText("news_publish_succ");
					//将该新闻标记成"已发布"
					tempNews.setStatus(1);					
				}else{
					actionMsg = getText("news_publish_fail");
					//将该新闻标记成"未发布"
					tempNews.setStatus(0);
				}
				service.saveOrUpdateNews(tempNews);	//调用业务逻辑组件更新指定的新闻					
			}		
		}
		return "toBrowseNews";		
	}	
	
	/** 构造新闻栏目级联数据 */
	private void createDoubleSelect(){
		//构造一个级联下拉列表的数据集合
		doubleSelectNodes=new ArrayList<DoubleSelectNode>();
		DoubleSelectNode node = null;
		DoubleSelectNode tempnode = null;
		List<DoubleSelectNode> nodes = null;
		//调用新闻栏目业务逻辑组件装载一级新闻栏目列表
		List<Newscolumns> columnsList = columnsService.listColumns();
		List<Newscolumns> childColumnsList = null;
		Newscolumns column = null;
		Newscolumns child_column = null;
		Iterator<Newscolumns> it = columnsList.iterator();
		Iterator<Newscolumns> it1 = null;
		while(it.hasNext()){
			column = it.next();
			node = new DoubleSelectNode();
			node.setName(column.getColumnName().trim());
			node.setValue(column.getId().toString());
			//调用新闻栏目业务逻辑组件装载某一级新闻栏目的子栏目列表
			childColumnsList = columnsService.listChildColumns(column);
			it1 = childColumnsList.iterator();
			nodes = new ArrayList<DoubleSelectNode>();
			while(it1.hasNext()){
				child_column = it1.next();
				tempnode = new DoubleSelectNode();
				tempnode.setName(child_column.getColumnName().trim());
				tempnode.setValue(child_column.getId().toString());
				nodes.add(tempnode);
			}
			node.setSubNodes(nodes);
			doubleSelectNodes.add(node);
		}		
	}
	
	/** 手动进行新增新闻的表单验证 */
	public void validateAddNews(){
		//标题必填
		if (model.getTitle()==null||model.getTitle().trim().length()<1){
			addFieldError("title",getText("news_validation_title"));
		}
		//关键词必填
		if (model.getKeyWords()==null||model.getKeyWords().trim().length()<1){
			addFieldError("keyWords",getText("news_validation_keyWords"));
		}		
		//摘要必填
		if (model.getAbstract_()==null||model.getAbstract_().trim().length()<1){
			addFieldError("abstract_",getText("news_validation_abstract"));
		}
		//内容必填
		if (model.getContent()==null||model.getContent().trim().length()<1){
			addFieldError("content",getText("news_validation_content"));
		}
		//图片新闻有效性检查
		if (model.getIsPicNews().intValue()==1){
			if (model.getId()==null){//新增新闻表单需要进行图片文件必传检查
				if (getPicFileName()==null||getPicFileName().trim().length()<1){
					addFieldError("pic",getText("news_validation_pic"));
				}else{
					if (!Tools.isEnableUploadType(1, getPicFileName())){
						String[] args = new String[1];
						args[0] = "新闻图片";				
						addFieldError("picture",getText("upload_picture_invalid",args));
					}					
				}			
			}
		}
		//表单验证失败
		if (hasFieldErrors()){
			//构造新闻栏目级联数据
			createDoubleSelect();
		}
	}
	
	/** 手动进行修改新闻的表单验证 */
	public void validateUpdateNews(){
		//调用新增新闻的的表单验证方法来验证修改新闻的表单
		validateAddNews();
	}	

	public NewsService getService() {
		return service;
	}

	public void setService(NewsService service) {
		this.service = service;
	}

	public ColumnsService getColumnsService() {
		return columnsService;
	}

	public void setColumnsService(ColumnsService columnsService) {
		this.columnsService = columnsService;
	}

	public String getActionMsg() {
		return actionMsg;
	}

	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	public List<DoubleSelectNode> getDoubleSelectNodes() {
		return doubleSelectNodes;
	}

	public void setDoubleSelectNodes(List<DoubleSelectNode> doubleSelectNodes) {
		this.doubleSelectNodes = doubleSelectNodes;
	}

	public File getPic() {
		return pic;
	}

	public void setPic(File pic) {
		this.pic = pic;
	}

	public String getPicContentType() {
		return picContentType;
	}

	public void setPicContentType(String picContentType) {
		this.picContentType = picContentType;
	}

	public String getPicFileName() {
		return picFileName;
	}

	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getColumn2() {
		return column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	public String getJsonClicks() {
		return jsonClicks;
	}

	public void setJsonClicks(String jsonClicks) {
		this.jsonClicks = jsonClicks;
	}
}