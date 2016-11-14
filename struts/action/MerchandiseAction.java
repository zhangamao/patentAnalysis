package com.patent.struts.action;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.patent.ORM.*;
import com.patent.service.*;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import com.patent.util.*;

/** 商品管理控制器 */
@SuppressWarnings("serial")
public class MerchandiseAction extends ActionSupport implements ModelDriven<Merchandise>{
	/** 通过依赖注入MerchandiseServiceImpl与CategoryServiceImpl组件实例 */
	MerchandiseService service;
	CategoryService cateService;
	
	/** 商品管理所有请求中常用的参数值 */
	private String actionMsg;	//Action间传递的消息参数
	private List<Merchandise> merList;//商品列表
	private String categoryId;	//所属分类ID
	private List<DoubleSelectNode> doubleSelectNodes;//级联商品分类列表
	private String category1;	//当前选中的第一级商品分类
	private String category2;	//当前选中的第二级商品分类
	
	/** 上传图片文件的属性 */
	private File pic;	//上传的图片文件
	private File vd;	//上传的视频文件	
	private String picContentType;	//上传图片文件的类型
	private String picFileName;		//上传图片文件的文件名
	private String vdContentType;	//上传视频文件的类型
	private String vdFileName;		//上传视频文件的文件名	
	
	//采用模型驱动
	private Merchandise model=new Merchandise();//用于封装商品属性模型
	public Merchandise getModel() {
		return model;
	}
	
	/** 处理浏览商品请求 */
	@SuppressWarnings("unchecked")
	public String browseMerchandise(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		merList = service.browseMerchandise();//调用业务逻辑组件取得商品列表
		return SUCCESS;
	}
	
	/** 处理新增商品请求 */
	public String addMerchandise(){
		try {
			model.setHtmlPath("/html/mer/"+Tools.getRndFilename()+".html");
			//对商品内容进行Escape处理
			model.setMerDesc(Tools.escape(model.getMerDesc().trim()));			
			//处理商品的上传图片文件
			if (getPicFileName()!=null && getPicFileName().trim().length()>0){
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
			//处理商品的上传视频文件
			if (getVdFileName()!=null && getVdFileName().trim().length()>0){
				String tempfilename = Tools.getRndFilename()+Tools.getFileExtName(getVdFileName());
				//读取web.xml中配置的streams参数值做为flv文件存放的路径
				String filename =ServletActionContext.getServletContext().getInitParameter("streams")+"/"+tempfilename;
				FileOutputStream fos = new FileOutputStream(filename);
				FileInputStream fis = new FileInputStream(getVd());
				byte[] buf = new byte[1024];
				int len = 0;
				while((len=fis.read(buf))>0){
					fos.write(buf,0,len);
				}
				if (fis!=null)fis.close();
				if (fos!=null)fos.close();
				//只保留flv的文件名,不需要保留路径
				model.setVideo(tempfilename);
			}			
			
			//关联相应的商品分类
			if (category2!=null){
				//调用业务逻辑组件装载指定的商品类别
				model.setCategory(cateService.loadCategory(Integer.valueOf(category2)));
			}else if(category1!=null){
				//调用业务逻辑组件装载指定的商品类别
				model.setCategory(cateService.loadCategory(Integer.valueOf(category1)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		if (service.saveOrUpdateMerchandise(model)){//调用业务逻辑组件保存新增的商品
			addActionMessage(getText("mer_add_succ"));
		}else{
			addActionMessage(getText("mer_add_fail"));
		}
		//构造商品分类级联数据
		createDoubleSelect();		
		return SUCCESS;
	}
	
	/** 处理删除商品请求 */
	public String delMerchandise(){
		if (model.getId()!=null){
			if (service.delMerchandise(model.getId())){//调用业务逻辑组件删除指定的商品
				actionMsg = getText("mer_del_succ");
			}else{
				actionMsg = getText("mer_del_fail");
			}			
		}else{
			actionMsg = getText("mer_del_fail");
		}
		return "toBrowseMerchandise";
	}
	
	/** 处理查看商品请求 */
	public String viewMerchandise(){
		if (model.getId()!=null){
			//调用业务逻辑组件装载指定的商品
			Merchandise tempMerchandise = service.loadMerchandise(model.getId());
			if (tempMerchandise!=null){
				try {
					//快速复制源对象中的所有属性到目标对象中
					BeanUtils.copyProperties(model, tempMerchandise);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Category tmpCate=tempMerchandise.getCategory();
				//取得所属栏目当前值
				if (tmpCate.getCategory()!=null){
					category2 = tmpCate.getId().toString();
					category1 = tmpCate.getCategory().getId().toString();
				}else{
					category1 = tmpCate.getId().toString();
				}
				//构造商品分类级联数据
				createDoubleSelect();
				return SUCCESS;
			}else{
				actionMsg = getText("mer_view_fail");
				return "toBrowseMerchandise";
			}	
		}else{
			actionMsg = getText("mer_view_fail");
			return "toBrowseMerchandise";
		}		
	}
	
	/** 处理装载商品请求 */
	public String editMerchandise(){
		if (model.getId()!=null){
			//调用业务逻辑组件装载指定的商品
			Merchandise tempMerchandise = service.loadMerchandise(model.getId());
			if (tempMerchandise!=null){
				try {
					//快速复制源对象中的所有属性到目标对象中
					BeanUtils.copyProperties(model, tempMerchandise);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Category tmpCate=tempMerchandise.getCategory();
				//取得所属栏目当前值
				if (tmpCate.getCategory()!=null){
					category2 = tmpCate.getId().toString();
					category1 = tmpCate.getCategory().getId().toString();
				}else{
					category1 = tmpCate.getId().toString();
				}
				//构造商品分类级联数据
				createDoubleSelect();
				return SUCCESS;
			}else{
				actionMsg = getText("mer_view_fail");
				return "toBrowseMerchandise";
			}	
		}else{
			actionMsg = getText("mer_view_fail");
			return "toBrowseMerchandise";
		}			
	}
	
	/** 处理更新商品请求 */
	public String updateMerchandise(){
		//调用业务逻辑组件装载指定的商品
		Merchandise tempMerchandise = service.loadMerchandise(model.getId());
		try {
			//保留几个字段的原始值
			model.setHtmlPath(tempMerchandise.getHtmlPath());
			model.setPicture(tempMerchandise.getPicture());
			model.setVideo(tempMerchandise.getVideo());
			
			//对商品内容进行Escape处理
			model.setMerDesc(Tools.escape(model.getMerDesc().trim()));			
			//处理商品的上传图片文件
			if (getPicFileName()!=null && getPicFileName().trim().length()>0){
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
			//处理商品的上传视频文件
			if (getVdFileName()!=null && getVdFileName().trim().length()>0){
				String tempfilename = Tools.getRndFilename()+Tools.getFileExtName(getVdFileName());
				//读取web.xml中配置的streams参数值做为flv文件存放的路径
				String filename =ServletActionContext.getServletContext().getInitParameter("streams")+"/"+tempfilename;
				FileOutputStream fos = new FileOutputStream(filename);
				FileInputStream fis = new FileInputStream(getVd());
				byte[] buf = new byte[1024];
				int len = 0;
				while((len=fis.read(buf))>0){
					fos.write(buf,0,len);
				}
				if (fis!=null)fis.close();
				if (fos!=null)fos.close();
				model.setVideo(tempfilename);
			}			
			
			//关联相应的商品分类
			if (category2!=null){
				//调用业务逻辑组件装载指定的商品类别
				model.setCategory(cateService.loadCategory(Integer.valueOf(category2)));
			}else if(category1!=null){
				//调用业务逻辑组件装载指定的商品类别
				model.setCategory(cateService.loadCategory(Integer.valueOf(category1)));
			}
			
			//将商品置为"未发布"
			model.setStatus(0);			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		if (service.saveOrUpdateMerchandise(model)){//调用业务逻辑组件更新指定的商品
			addActionMessage(getText("mer_edit_succ"));
		}else{
			addActionMessage(getText("mer_edit_fail"));
		}
		//构造商品分类级联数据
		createDoubleSelect();		
		return INPUT;		
	}
	
	/** 处理商品分类级联下拉列表请求 */
	public String preAddMerchandise(){
		//构造商品分类级联数据
		createDoubleSelect();
		return SUCCESS;		
	}
	
	/** 构造商品分类级联数据 */
	private void createDoubleSelect(){
		doubleSelectNodes=new ArrayList<DoubleSelectNode>();
		DoubleSelectNode node = null;
		DoubleSelectNode tempnode = null;
		List<DoubleSelectNode> nodes = null;
		List<Category> cateList = cateService.listCategory();
		List<Category> childCateList = null;
		Category cate = null;
		Category child_cate = null;
		Iterator<Category> it = cateList.iterator();
		Iterator<Category> it1 = null;
		while(it.hasNext()){
			cate = it.next();
			node = new DoubleSelectNode();
			node.setName(cate.getCateName().trim());
			node.setValue(cate.getId().toString());
			childCateList = cateService.listChildCategory(cate);
			it1 = childCateList.iterator();
			nodes = new ArrayList<DoubleSelectNode>();
			while(it1.hasNext()){
				child_cate = it1.next();
				tempnode = new DoubleSelectNode();
				tempnode.setName(child_cate.getCateName().trim());
				tempnode.setValue(child_cate.getId().toString());
				nodes.add(tempnode);
			}
			node.setSubNodes(nodes);
			doubleSelectNodes.add(node);
		}		
	}
	
	/** 发布指定商品 */
	public String publisMerchandise(){
		if (model.getId()!=null){
			Merchandise mer = service.loadMerchandise(model.getId());
			if (mer!=null){
				HttpServletRequest request = ServletActionContext.getRequest();
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();				
				String url=basePath+"/admin/viewMerchandise.action?id="+mer.getId();
				//创建静态页面生成器实例
				HtmlGenerator hg = new HtmlGenerator(basePath);
				//发布成静态页面
				if (hg.createHtmlPage(url, request.getRealPath(mer.getHtmlPath()))){
					actionMsg = getText("mer_publish_succ");
					//将该商品标记成已发布
					mer.setStatus(1);					
				}else{
					actionMsg = getText("mer_publish_fail");
					//将该商品标记成未发布
					mer.setStatus(0);					
				}
				service.saveOrUpdateMerchandise(mer);	
			}		
		}
		return "toBrowseMerchandise";		
	}	
	
	/** 手动进行新增商品的表单验证 */
	public void validateAddMerchandise(){
		//商品名称必填
		if (model.getMerName()==null||model.getMerName().trim().length()<1){
			addFieldError("merName",getText("mer_validation_name"));
		}
		//使用正则表达式验证商品市场价格
		if (model.getPrice()==null || !Pattern.matches("\\d{1,6}(\\.\\d{1,2})?", model.getPrice().toString())){
			addFieldError("price",getText("mer_validation_price"));
		}
		//使用正则表达式验证商品优惠价格
		if (model.getSpecial()==1){
			if (model.getSprice()==null || !Pattern.matches("\\d{1,6}(\\.\\d{1,2})?", model.getSprice().toString())){
				addFieldError("sprice",getText("mer_validation_sprice"));
			}			
		}		
		//商品型号必填
		if (model.getMerModel()==null||model.getMerModel().trim().length()<1){
			addFieldError("merModel",getText("mer_validation_model"));
		}
		//商品描述必填
		if (model.getMerDesc()==null||model.getMerDesc().trim().length()<1){
			addFieldError("merDesc",getText("mer_validation_desc"));
		}
		//对上传的图片文件类型进行有效性验证
		if (getPicFileName()!=null && getPicFileName().trim().length()>0){
			if (!Tools.isEnableUploadType(1, getPicFileName())){
				String[] args = new String[1];
				args[0] = "商品图片";				
				addFieldError("picture",getText("upload_picture_invalid",args));
			}
		}
		//对上传的视频文件类型进行有效性验证
		if (getVdFileName()!=null && getVdFileName().trim().length()>0){
			if (!Tools.isEnableUploadType(2, getVdFileName())){
				String[] args = new String[1];
				args[0] = "商品视频";
				addFieldError("video",getText("upload_video_invalid",args));
			}
		}		
		//表单验证失败
		if (hasFieldErrors()){
			//构造商品分类级联数据
			createDoubleSelect();
		}
	}
	
	/** 手动进行修改商品的表单验证 */
	public void validateUpdateMerchandise(){
		//调用新增商品的的表单验证方法来验证修改商品的表单
		validateAddMerchandise();
	}	

	public MerchandiseService getService() {
		return service;
	}

	public void setService(MerchandiseService service) {
		this.service = service;
	}

	public String getActionMsg() {
		return actionMsg;
	}

	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
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

	public CategoryService getCateService() {
		return cateService;
	}

	public void setCateService(CategoryService cateService) {
		this.cateService = cateService;
	}

	public List<Merchandise> getMerList() {
		return merList;
	}

	public void setMerList(List<Merchandise> merList) {
		this.merList = merList;
	}

	public File getVd() {
		return vd;
	}

	public void setVd(File vd) {
		this.vd = vd;
	}

	public String getVdContentType() {
		return vdContentType;
	}

	public void setVdContentType(String vdContentType) {
		this.vdContentType = vdContentType;
	}

	public String getVdFileName() {
		return vdFileName;
	}

	public void setVdFileName(String vdFileName) {
		this.vdFileName = vdFileName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategory1() {
		return category1;
	}

	public void setCategory1(String category1) {
		this.category1 = category1;
	}

	public String getCategory2() {
		return category2;
	}

	public void setCategory2(String category2) {
		this.category2 = category2;
	}
}