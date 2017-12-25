package com.pengyue.bbsTools.bbscore.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class IOUtils {

	public static final Log LOG = LogFactory.getLog(IOUtils.class);

	public static void loadFile2Prop(String file, Properties prop) {
		loadSystemFile2Prop(file, prop);
		InputStream inputStream =  markFileIs(file);
		if(inputStream==null){
			LOG.debug("忽视!");
			return;
		}
		Reader reader = inputStream2Reader(inputStream, "UTF-8");
		try {
			prop.load(reader);
		} catch (IOException e) {
			LOG.debug("加载文件：“" + file + "”失败！", e);
		} finally {
			close(reader);
		}
	}
	
	public static void loadSystemFile2Prop(String file, Properties prop) {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
		if(inputStream==null){
			LOG.debug("忽视!");
			return;
		}
		Reader reader = inputStream2Reader(inputStream, "UTF-8");
		try {
			prop.load(reader);
		} catch (IOException e) {
			LOG.debug("加载文件：“" + file + "”失败！", e);
		} finally {
			close(reader);
		}
	}

	public static final FileInputStream markFileIs(String file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			LOG.debug("打开文件输入流失败！找不到文件 :"+file);
			return null;
		}
	}

	public static final Reader inputStream2Reader(InputStream inputstream,
			String charset) {
		try {
			return new InputStreamReader(inputstream, charset);
		} catch (UnsupportedEncodingException e) {
			LOG.debug("转换字符输入流失败！找不到制定的字符集:"+charset);
			return null;
		}
	}

	public static void close(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				LOG.debug("关闭字节输入流失败！", e);
			}
		}
	}

	public static void close(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				LOG.debug("关闭字节输出流失败！", e);
			}
		}
	}

	public static void close(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				LOG.debug("关闭字符输入流失败！", e);
			}
		}
	}

	public static void close(Writer writer) {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				LOG.debug("关闭字符输出流失败！", e);
			}
		}
	}
}
