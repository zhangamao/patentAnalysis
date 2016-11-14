package com.patent.struts.action;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;
import com.patent.DAO.BaseDAO;
import com.patent.ORM.*;
import com.patent.util.Tools;

/** 新闻采集线程 */
public class CrawlNewsThread implements Runnable {

	private Newsrule rule;//新闻采集规则
	private BaseDAO dao;//数据库访问组件
	private Admin admin;//系统管理员
	private StringBuffer statusMessage=new StringBuffer();//进度信息
	private int suc=0,fail=0;//采集成功与失败的新闻条数
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

	/** 构造方法 */
	public CrawlNewsThread(Newsrule rule,BaseDAO dao,Admin admin){
		this.rule =  rule;
		this.dao = dao;
		this.admin = admin;		
	}

	public void run() {
		//格式化采集规则的各特征串
		rule.setListBegin(formatHtml(rule.getListBegin()));
		rule.setListEnd(formatHtml(rule.getListEnd()));
		rule.setTitleBegin(formatHtml(rule.getTitleBegin()));
		rule.setTitleEnd(formatHtml(rule.getTitleEnd()));
		rule.setAuthorBegin(formatHtml(rule.getAuthorBegin()));
		rule.setAuthorEnd(formatHtml(rule.getAuthorEnd()));
		rule.setContentBegin(formatHtml(rule.getContentBegin()));
		rule.setContentEnd(formatHtml(rule.getContentEnd()));
		rule.setFromBegin(formatHtml(rule.getFromBegin()));
		rule.setFromEnd(formatHtml(rule.getFromEnd()));
		rule.setMidBegin(formatHtml(rule.getMidBegin()));
		rule.setMidEnd(formatHtml(rule.getMidEnd()));
		
		statusMessage.append("开始【"+rule.getRuleName()+"】的新闻采集!..................."+df.format(new Date())+"\n");
		String body = formatHtml(getBody(formatBodyTag(getPage(rule.getUrl(),rule.getEncode()))));
		if (body!=null && body.length()>0){
			statusMessage.append("1.抓取新闻列表页("+rule.getUrl()+")成功!\n");
			String newslistcode = getNewsListCode(body,rule.getListBegin(),rule.getListEnd());
			if (newslistcode!=null && newslistcode.length()>0){
				statusMessage.append("2.提取新闻列表HTML源码成功!\n");
				List newslist = getNewsList(newslistcode);
				if (newslist!=null){
					statusMessage.append("3.成功提取新闻列表项"+newslist.size()+"条!\n");
					newslist = getAbsUrl(newslist,rule.getUrl());
					statusMessage.append("4.开始采集各新闻列表项的具体内容...................\n");
					getAllNews(newslist,rule);
					statusMessage.append("5.共采集"+(suc+fail)+"条新闻,成功"+suc+"条,失败"+fail+"条!\n");
				}else{
					statusMessage.append("3.提取新闻列表项失败!\n");
				}
			}else{
				statusMessage.append("2.提取新闻列表HTML源码失败!\n");
			}
		}else{
			statusMessage.append("2.抓取新闻列表页("+rule.getUrl()+")失败!\n");
		}		
		statusMessage.append("完成【"+rule.getRuleName()+"】的新闻采集!..................."+df.format(new Date())+"\n");
	}
	
	/** 采集指定列表中的所有新闻内容 */
	private void getAllNews(List newslist,Newsrule rule){
		String url =null;
		String title = null;
		int i=0;
		if(newslist==null)return;
		List result = new ArrayList();
		News news = null;
		Map newsitem = null;
		Iterator it = newslist.iterator();
		while(it.hasNext()){
			newsitem = (Map)it.next();
			url = newsitem.get("href").toString();
			title = newsitem.get("title").toString();
			try {
				news = getNews(url,rule);
				if (news==null){
					statusMessage.append("\t采集第"+(i+1)+"条新闻《"+title+"》失败!\n");
				}else{
					statusMessage.append("\t采集第"+(i+1)+"条新闻《"+title+"》成功!\n");
				}
				//采完一条后休息一秒
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
	}
	
	/** 采集指定URL的新闻内容 */
	private News getNews(String url,Newsrule rule){
		News news = new News();		
		String body = formatHtml(getBody(formatBodyTag(getPage(url,rule.getEncode()))));
		int p1,p2;
		if (body==null)return null;
		//提取新闻标题
		p1 = body.indexOf(rule.getTitleBegin());
		if(p1!=-1){
			String title = null;
			p1=p1+rule.getTitleBegin().length();
			p2 = body.indexOf(rule.getTitleEnd(),p1);
			if (p2!=-1){
				title = getText(body.substring(p1, p2));
				//去除标题中的其它杂项
				p1 = title.indexOf(">");
				if (p1!=-1){
					p2 = title.indexOf("<");
					if (p2!=-1){
						title = title.substring(p1+1, p2);
					}else{
						title = title.substring(p1+1);
					}
				}
				news.setTitle(title.trim());
				//新闻摘要与关键字直接取标题的内容
				news.setAbstract_(title.trim());
				news.setKeyWords(title.trim());
			}
		}
		//提取新闻作者
		if (rule.getAuthorBegin()!=null && rule.getAuthorBegin().trim().length()>0 && rule.getAuthorEnd()!=null && rule.getAuthorEnd().trim().length()>0){
			p1 = body.indexOf(rule.getAuthorBegin());
			if(p1!=-1){
				String author = null;
				p1=p1+rule.getAuthorBegin().length();
				p2 = body.indexOf(rule.getAuthorEnd(),p1);
				if (p2!=-1){
					author = getText(body.substring(p1, p2));
					//去除作者中的其它杂项
					p1 = author.indexOf(">");
					if (p1!=-1){
						p2 = author.indexOf("<");
						if (p2!=-1){
							author = author.substring(p1+1, p2);
						}else{
							author = author.substring(p1+1);
						}
					}
					news.setAuthor(author.trim());				
				}
			}			
		}
		//提取新闻来源
		if (rule.getFromBegin()!=null && rule.getFromBegin().trim().length()>0 && rule.getFromEnd()!=null && rule.getFromEnd().trim().length()>0){
			p1 = body.indexOf(rule.getFromBegin());
			if(p1!=-1){
				String from = null;
				p1=p1+rule.getFromBegin().length();
				p2 = body.indexOf(rule.getFromEnd(),p1);
				if (p2!=-1){
					from = getText(body.substring(p1, p2));
					//去除来源中的其它杂项
					p1 = from.indexOf(">");
					if (p1!=-1){
						p2 = from.indexOf("<");
						if (p2!=-1){
							from = from.substring(p1+1, p2);
						}else{
							from = from.substring(p1+1);
						}
					}
					news.setFrom(from.trim());				
				}
			}			
		}
		//提取新闻内容
		String tempcontent=null;
		if (rule.getMidBegin()!=null && rule.getMidBegin().trim().length()>0 && rule.getMidEnd()!=null && rule.getMidEnd().trim().length()>0){//有画中画内容,需要去除	
			//提取第一段新闻内容
			p1 = body.indexOf(rule.getContentBegin());
			if(p1!=-1){
				p1=p1+rule.getContentBegin().length();
				p2 = body.indexOf(rule.getMidBegin(),p1);
				if (p2!=-1){
					tempcontent = body.substring(p1, p2);					
				}
			}
			//提取第二段新闻内容
			p1 = body.indexOf(rule.getMidEnd());
			if(p1!=-1){
				p1=p1+rule.getMidEnd().length();
				p2 = body.indexOf(rule.getContentEnd(),p1);
				if (p2!=-1){
					tempcontent = tempcontent+body.substring(p1, p2);					
				}
			}			
		}else{//无画中画内容
			p1 = body.indexOf(rule.getContentBegin());
			if(p1!=-1){
				p1=p1+rule.getContentBegin().length();
				p2 = body.indexOf(rule.getContentEnd(),p1);
				if (p2!=-1){
					tempcontent = body.substring(p1, p2);
				}
			}			
		}
		if (tempcontent!=null){
			//保留原始图片链接
			tempcontent = tempcontent.replaceAll("<img", "iiiiiiiiii");
			tempcontent = tempcontent.replaceAll("<IMG", "iiiiiiiiii");
			//保留原始换行格式
			tempcontent = tempcontent.replaceAll("<br/>", "############");
			tempcontent = tempcontent.replaceAll("<br />", "############");
			tempcontent = tempcontent.replaceAll("<br>", "############");
			tempcontent = tempcontent.replaceAll("</p>", "############");
			tempcontent = tempcontent.replaceAll("</P>", "############");
			//去除所有的HTML标签,保留纯文本内容
			tempcontent = getText(tempcontent);
			//将内容重新封装成段落
			tempcontent = tempcontent.replaceAll("iiiiiiiiii", "<img");
			tempcontent = tempcontent.replaceAll("############", "<br/>");			
			while (tempcontent.indexOf("<br/><br/>")!=-1){
				tempcontent = tempcontent.replaceAll("<br/><br/>", "<br/>");
			}
			tempcontent = tempcontent.replaceAll("<br/>", "</p><p>");
			tempcontent = "<p>"+tempcontent+"</p>";
			tempcontent = tempcontent.replaceAll("<p><p>", "<p>");
			tempcontent = tempcontent.replaceAll("</p></p>", "</p>");
			news.setContent(Tools.escape(tempcontent));
		}
		//判断采集内容是否完整
		if (news.getTitle()==null || news.getTitle().trim().length()<1 || news.getContent()==null || news.getContent().trim().length()<1){
			fail++;
			return null;			
		}else{
			//初始化news实例的其它属性
			news.setClicks(0);
			news.setPriority(0);
			news.setCdate(new Date());
			news.setHtmlPath("/html/news/"+Tools.getRndFilename()+".html");
			news.setStatus(0);
			news.setRed(0);
			news.setNewsType(0);
			if (news.getAuthor()==null||news.getAuthor().trim().length()<1){
				news.setAuthor("不详");
			}
			if (news.getFrom()==null||news.getFrom().trim().length()<1){
				news.setFrom("原创");
			}
			news.setEditor(admin.getLoginName());
			news.setIsPicNews(0);			
			//与新闻栏目进行关联
			news.setNewscolumns(rule.getNewscolumns());
			//查询当前新闻是否已经存在
			List temp = dao.query("from News as a where a.newscolumns.id="+rule.getNewscolumns().getId()+" and a.title='"+news.getTitle()+"'");
			if (temp==null||temp.size()<1){
				//保存当前采集的新闻
				dao.saveOrUpdate(news);
				suc++;
				return news;
			}else{
				fail++;
				return null;
			}
		}		
	}
	
	/** 使用HtmlParser组件去除内容中的HTML标签,得到纯文本内容 */
	private String getText(String content){
		String result = content;
		try{
			Parser parser = Parser.createParser(content, "GBK");
			TextExtractingVisitor visitor = new TextExtractingVisitor();
            parser.visitAllNodesWith(visitor);
            result = visitor.getExtractedText();
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return result;
	}
	
	/** 得到新闻列表项的绝对URL地址 */
	private List getAbsUrl(List newslist,String baseurl){
		if(newslist==null||baseurl==null)return null;
		List result = newslist;
		String temurl = null;
		for (int i=0;i<result.size();i++){
			temurl = ((Map)result.get(i)).get("href").toString();
			if (temurl.indexOf("http://")==-1 && temurl.indexOf("https://")==-1){
				temurl = baseurl+"/"+temurl;
				while(temurl.indexOf("//")!=-1){
					temurl = temurl.replaceAll("//", "/");
				}
				temurl = temurl.replaceAll("http:/", "http://");
				temurl = temurl.replaceAll("https:/", "https://");
				((Map)result.get(i)).put("href", temurl);
			}
		}
		return result;
	}
	
	/** 使用HtmlParser组件提取新闻列表项 */
	private List getNewsList(String newslistcode){
		List list = null;
		//创建HtmlParser实例用于解析指定html内容
		Parser parser = Parser.createParser(newslistcode, "GBK");
		//创建TagNameFilter实例用于提取a标签
        NodeFilter filter = new TagNameFilter ("A");
        try {
            NodeIterator it = parser.extractAllNodesThatMatch(filter).elements();
            TagNode node = null;
            Map map = null;
            while(it.hasMoreNodes()){
            	if (list==null)list = new ArrayList();
            	node = (TagNode)it.nextNode();
            	map = new HashMap();
            	map.put("href", node.getAttribute("href"));
            	map.put("title", node.toPlainTextString());
            	list.add(map);
            }
		} catch (ParserException e) {
			e.printStackTrace();
		}          
		return list;
	}
	
	/** 提取新闻列表代码段 */
	private String getNewsListCode(String body,String listBegin,String listEng){
		String listcode = null;
		int p1,p2;
		if (body!=null && listBegin!=null && listEng!=null){
			p1 = body.indexOf(listBegin);
			if (p1!=-1){
				p1 = p1 + listBegin.length();
				p2 = body.indexOf(listEng,p1);
				if (p2!=-1){
					listcode = body.substring(p1, p2);
				}
			}
		}
		return listcode;
	}	
	
	/** 使用HttpClient组件读取指定URL的页面HTML源码 */
	private String getPage(String url,String encode){
		//创建HttpClient实例
		HttpClient httpClient=new HttpClient();
		//设置编码参数
		if (encode!=null){
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,encode);
		}else{
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gbk");
		}		
		//忽略Cookies
		httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);

		//创建GetMethod实例访问指定URL
		GetMethod getMethod = new GetMethod(url);
		try{
			//访问指定URL并取得返回状态码
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode==200){//返回成功状态码200
				//读取页面HTML源码
				StringBuffer sb = new StringBuffer();
				InputStream in = getMethod.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String line;
				while((line=br.readLine())!=null){
					sb.append(line);
				}
				if(br!=null)br.close();
				return sb.toString();
			}else{
				return null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	/** 格式化Body标签 */
	private String formatBodyTag(String htmlcode){
		String result = htmlcode;
		if (htmlcode!=null){
			//统一将body标签转换成小写,此处只考虑几种习惯的写法
			while (result.indexOf("<BODY")!=-1){
				result = result.replaceAll("<BODY", "<body");
			}
			while (result.indexOf("<Body")!=-1){
				result = result.replaceAll("<Body", "<body");
			}
			while (result.indexOf("</BODY")!=-1){
				result = result.replaceAll("</BODY", "</body");
			}
			while (result.indexOf("</Body")!=-1){
				result = result.replaceAll("</Body", "</body");
			}			
		}
		return result;
	}
	
	/** 使用正则表达式提取body体内容 */
	private String getBody(String htmlcode){
		if(htmlcode==null)return null;
		Pattern p = Pattern.compile("<body(.*)>(.*)</body>",Pattern.MULTILINE|Pattern.DOTALL);
		Matcher m = p.matcher(htmlcode);
		if (m.find()){
			return m.group();
		}else{
			return null;
		}
	}	
	
	/** 格式化指定的HTML源码 */
	private String formatHtml(String htmlcode){
		String result = htmlcode;
		if (htmlcode!=null && htmlcode.trim().length()>0){
			//去除回车符
			while (result.indexOf("\r")!=-1){
				result = result.replaceAll("\r", "");
			}
			//去除换行符
			while (result.indexOf("\n")!=-1){
				result = result.replaceAll("\n", "");
			}			
			//去除制表符
			while (result.indexOf("\t")!=-1){
				result = result.replaceAll("\t", "");
			}			
			//去除多余空格
			while (result.indexOf("  ")!=-1){
				result = result.replaceAll("  ", " ");
			}
			//去除全角空格
			while (result.indexOf("　")!=-1){
				result = result.replaceAll("　", "");
			}
			return result;
		}else{
			return null;
		}		
	}

	/** 读取进度信息 */
	public StringBuffer getStatusMessage() {
		return statusMessage;
	}	

}
