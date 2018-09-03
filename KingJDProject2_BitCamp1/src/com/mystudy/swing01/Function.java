package com.mystudy.swing01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Function {
	static File file;
	static FileOutputStream fos;
	static OutputStreamWriter osw;
	Function(){
		super();
	}
	
	static String path() {
		file = new File("file/print.txt");
		
		String result = "";
		result += "parent dir : "+file.getParent()+"\n";
		result += "file path : "+file.getPath();
		
		return result;
	}
	
	static boolean print(String str) {
		boolean result = false;
		try {
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos);
			osw.write(str);
			result = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				osw.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				return result;
			}
		}
	}
}
