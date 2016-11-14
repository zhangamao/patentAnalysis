package com.crawl;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Crawl {

	BaiTenLinkDB db = null;
	Patent patent = null;
	public  int count(String url) throws Exception {
		
		Document doc = Jsoup.connect(url).get();

		Elements countStr = doc.select("[class=count]");
		Element count = countStr.get(0);
		System.out.println(count.text());
		int number = Integer.parseInt(count.text());
		return number;
	}
	public void getContent(String url, int page){
		String surl = url+"&page=" + (page);
		Document doc = null;
		try{
			doc = Jsoup.connect(surl).get();
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			String estr = url+"&page="+(page+1);
			try{
				doc = Jsoup.connect(estr).get();
			}catch (IOException ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		Elements imageEles = doc.select("[class=mleft]");
		Elements imgs = imageEles.select("img");
		Elements types = doc.select("[class=mlr256]");
		Elements names = doc.select("[class=srl-detail-ti f16]");
		Elements pubNumes = doc.select("[class=srl-detail-an]");
		Elements lws = doc.getElementsByAttributeValueContaining("class", "mrl6");
		Elements applys = doc.select("[class=sm-c-r-color]");
		
		Elements sps = applys.select("span");
		Elements links = sps.select("a");
		Elements urls = doc.select("[class=sm-c-right fl]");
		
		 db = new BaiTenLinkDB();
		 for(int i = 0; i < 10; i++)
		 {
			 patent = new Patent();
			 patent.setImage(imgs.get(i).absUrl("xsrc"));
			 patent.setName(names.get(i).text());
				patent.setType(types.get(i).text());
				patent.setPublicNumber(pubNumes.get(i).text());
				if(lws.size()< 10){
					patent.setLw("未知");
					
				}else{
					patent.setLw(lws.get(i).text());
				}
				patent.setApplicant(links.get(i).text());
				
				Element ul = urls.get(i);
				
				int index1 = ul.text().indexOf("申请日：");
				int endindex1 = ul.text().indexOf("主分类号");
				String date = ul.text().substring(index1+4, endindex1-2);
				patent.setApplyDate(date);
				
				int index2 = ul.text().indexOf("分类号");
				int endindex2 = ul.text().indexOf("摘要");
				String claimNumber = null;;
				if(endindex2 != -1){
					
					claimNumber = ul.text().substring(index2+4, endindex2);
				}else{
					claimNumber = "没有取到分类号";
				}
				
				patent.setIPC(claimNumber);
				
				int index3 = ul.text().indexOf("摘要");
				int endindex3 = ul.text().indexOf("阅读全文");
				String introduce = ul.text().substring(index3+3,endindex3);
				patent.setIntroduce(introduce);
				
				//System.out.println(introduce);
				db.saveToDB(patent);
		 }
		
		
	}

}
