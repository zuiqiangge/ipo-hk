package com.pengyue.bbsTools.bbscore.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class DocumentWrapper implements Serializable {
	private static final long serialVersionUID = 8631465753089030798L;
	
	private byte[] bytes;
	private String charset;
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	@Override
	public String toString() {
		try {
			return new String(bytes,charset);
		} catch (Exception e) {
			return null;
		}
	}
}
