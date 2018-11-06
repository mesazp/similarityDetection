package com.alibaba.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.alibaba.MySimHash;
import com.alibaba.simpleimage.analyze.FingerPrint;

public class FileUtil {

	public static ArrayList<File> getFiles(String path) throws Exception {

		ArrayList<File> fileList = new ArrayList<File>();
		File file = new File(path);
		if(file.isDirectory())
		{
		File []files = file.listFiles();
			for(File fileIndex:files)
			{
              if(fileIndex.isDirectory())
              {
				getFiles(fileIndex.getPath());
				}
              else 
				{
				fileList.add(fileIndex);
				}
			}
		}
		return fileList;
	}
    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
    
	public static int textSimilarity(File file1, File file2)
	{
		String s1 = txt2String(file1);
		String s2 = txt2String(file2);
		MySimHash hash1 = new MySimHash(s1, 64);
		MySimHash hash2 = new MySimHash(s2, 64);
		return hash1.hammingDistance(hash2);	
	}
	
	public static float imageSimilarity(File file1, File file2)
	{
		float similarity=0;
		try {
			FingerPrint fp1 = new FingerPrint(ImageIO.read(file1));
			FingerPrint fp2 =new FingerPrint(ImageIO.read(file2));
		    similarity= fp1.compare(fp2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return similarity;   
	}
}
