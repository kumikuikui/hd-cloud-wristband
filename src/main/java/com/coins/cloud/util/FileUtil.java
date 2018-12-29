package com.coins.cloud.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.springframework.util.ResourceUtils;

public class FileUtil {
	
	public static String getIncrNum(){
		String incrStr = "";
		try {
	        /* 读入TXT文件 */  
			String pathname = "classpath:incr.txt";  
	        File filename = ResourceUtils.getFile(pathname);
	        InputStreamReader reader = new InputStreamReader(  
	                new FileInputStream(filename));
	        BufferedReader br = new BufferedReader(reader);
	        String line = br.readLine();  
	        int incr = Integer.parseInt(line) + 1;
	        incrStr = String.valueOf(incr);
	        /* 写入Txt文件 */  
	        File writeName = ResourceUtils.getFile(pathname); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile();
	        BufferedWriter out = new BufferedWriter(new FileWriter(writeName));  
	        out.write(incrStr);
	        out.flush();
	        out.close();
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }
		return incrStr;
	}
}
