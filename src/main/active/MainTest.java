package active;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.util.FileUtil;

import dao.SearchDao;
import dao.UpdateDao;
import util.DbUtil;


public class MainTest {
	
	public static List getSuspiciousRecordSet() throws Exception {
	// TODO Auto-generated method stub
	DbUtil dbUtil = new DbUtil();
	List list = new ArrayList();  
	ResultSet rs=SearchDao.Search(dbUtil.getCon(), "0", "urlblack");
    while (rs.next()) {  
    	 String url= rs.getString("url");
          list.add(url);
    }
    return list;
}
	public static List getSuspiciousRecordSet(String url) throws Exception {
	// TODO Auto-generated method stub
	DbUtil dbUtil = new DbUtil();
	List list = new ArrayList();  
	ResultSet rs=SearchDao.Search(dbUtil.getCon(), "0",url, "urlblack");
    while (rs.next()) {
          list.add(url);
    }
    return list;
}
	
	public static String phishingDetection(String textpath,String imagepath)
	{
		String num="";
		try {
			ArrayList list =FileUtil.getFiles("D:/configuration/html/");
			ArrayList<File> fileList = new ArrayList<File>();
			File file1 = new File(textpath);
			File imgfile1 = new File(imagepath);
				for(int j=0;j<list.size();j++)
				{
					File file2=(File) list.get(j);
					String imgPath=file2.getAbsolutePath().replaceAll("html", "images").replaceAll("txt", "png");
					File imgfile2 = new File(imgPath);
					int text= FileUtil.textSimilarity(file1, file2);
					
					if(text<3)
					{
						if(num.equals(""))
						{
							num=file2.getName().substring(0,file2.getName().lastIndexOf("."));
						}else
						{
							num+=";"+file2.getName().substring(0,file2.getName().lastIndexOf("."));
						}
					}else 
					{
						float image=FileUtil.imageSimilarity(imgfile1, imgfile2);
						if(image>0.9)
						{
							if(num.equals(""))
							{
								num=file2.getName().substring(0,file2.getName().lastIndexOf("."));
							}else
							{
								num+=";"+file2.getName().substring(0,file2.getName().lastIndexOf("."));
							}
						}
						
					}
					
				}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
	
	public void findHookedWebsite()
	{
		DownLoadImg img = new DownLoadImg();
		DownLoadHTML html = new DownLoadHTML();
		DbUtil dbUtil = new DbUtil();
		try 
		{
			List list=getSuspiciousRecordSet();
			for(int i=0;i<list.size();i++)
			{
				String startUrl=list.get(i).toString();
				String imagepath=img.start(startUrl);
			    String textpath=html.start(startUrl);
			    String num= phishingDetection(textpath,imagepath);
			    UpdateDao.Update(dbUtil.getCon(), num, startUrl, "urlblack");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {
		DownLoadImg img = new DownLoadImg();
		DownLoadHTML html = new DownLoadHTML();
		DbUtil dbUtil = new DbUtil();
		try {
			List list=getSuspiciousRecordSet();
			for(int i=0;i<list.size();i++)
			{
				String startUrl=list.get(i).toString();
				String imagepath=img.start(startUrl);
			    String textpath=html.start(startUrl);
			    String num= phishingDetection(textpath,imagepath);
			    UpdateDao.Update(dbUtil.getCon(), num, startUrl, "urlblack");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		String htmlurl="http://www.baidu.com/";
//		URL url;
//		String temp;
//		StringBuffer sb = new StringBuffer();
//		try {
//		url = new URL(htmlurl);
//		URLConnection connection = url.openConnection();
//		connection.setConnectTimeout(10);
//		connection.setReadTimeout(10);
//		BufferedReader in = new BufferedReader(new InputStreamReader(
//				url.openStream(), "utf-8"));// 读取网页内容
//		while ((temp = in.readLine()) != null ) {
//			sb.append(temp);
//			sb.append("\r\n");
//		}
//		in.close();
//		System.out.println( sb.toString());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

}
