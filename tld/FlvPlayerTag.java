package com.patent.tld;

import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/** FLV在线播放器自定义标签类 */
public class FlvPlayerTag extends SimpleTagSupport {

	String red5FullUrl;   //red5服务器应用的完整访问路径
	String red5ServerIp;  //red5服务器的IP地址
	String width = "400"; //播放器宽度,单位:像素,默认400
	String height = "300";//播放器高度,单位:像素,默认300
	String flvFilename;	  //FLV文件名
	String picFilename;	  //播放器封面图片文件,默认为red5服务器应用下的cover.jpg	
	
	/** 标签体处理 */
    public void doTag() throws JspException, IOException{
    	//规范属性值
    	if (!red5FullUrl.endsWith("/"))red5FullUrl=red5FullUrl+"/";
    	if (flvFilename.indexOf(".")!=-1)flvFilename = flvFilename.split("\\.")[0];
    	if (picFilename==null)picFilename=red5FullUrl+"cover.jpg";
    	else{
    		picFilename = ((HttpServletRequest)((PageContext)getJspContext()).getRequest()).getContextPath()+picFilename;
    	}    	
    	
    	//构造FLV在线播放器
    	StringBuffer sb = new StringBuffer();
    	sb.append("<center><div id='flvcontainer'>请<a href='http://www.macromedia.com/go/getflashplayer'>下载Flash播放器</a>播放此影片!</div></center>\n");
    	sb.append("<script type='text/javascript' src='"+red5FullUrl+"swfobject.js'></script>\n");
    	sb.append("<script type='text/javascript'>\n");
    	sb.append("	var so = new SWFObject('"+red5FullUrl+"mediaplayer.swf','mpl','"+width+"','"+height+"','7');\n");
    	sb.append("	so.addParam('allowfullscreen','true');\n");
    	sb.append("	so.addParam('allowscriptaccess','sameDomain');\n");
    	sb.append("	so.addVariable('width','"+width+"');\n");
    	sb.append("	so.addVariable('height','"+height+"');\n");
    	sb.append("	so.addVariable('file','rtmp://"+red5ServerIp+"/oflaDemo');\n");
    	sb.append("	so.addVariable('id','"+flvFilename+"');\n");
    	sb.append("	so.addVariable('type','rtmp');	\n");
    	sb.append("	so.addVariable('image','"+picFilename+"');\n");
    	sb.append("	so.write('flvcontainer');	\n");
    	sb.append("</script>\n");
    	//输出处理结果到页面上
    	getJspContext().getOut().println(sb);    	
    }

	public String getRed5FullUrl() {
		return red5FullUrl;
	}

	public void setRed5FullUrl(String red5FullUrl) {
		this.red5FullUrl = red5FullUrl;
	}

	public String getRed5ServerIp() {
		return red5ServerIp;
	}

	public void setRed5ServerIp(String red5ServerIp) {
		this.red5ServerIp = red5ServerIp;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getFlvFilename() {
		return flvFilename;
	}

	public void setFlvFilename(String flvFilename) {
		this.flvFilename = flvFilename;
	}

	public String getPicFilename() {
		return picFilename;
	}

	public void setPicFilename(String picFilename) {
		this.picFilename = picFilename;
	}
}
