package com.pengyue.bbsTools.bbscore.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

public abstract class WrapperUtil {

	public static Document toDocument(DocumentWrapper documentWrapper) {
		try {
			String charset = documentWrapper.getCharset();
			if(charset==null || charset.length()==0){
				charset = "big5";
			}
			Document result = Jsoup.parse(new String(documentWrapper.getBytes(),
					charset));
			result.outputSettings().charset(charset);
			return result;
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

	public static DocumentWrapper toDocumentWrapper(Document document) {
		DocumentWrapper result = new DocumentWrapper();
		try {
			result.setBytes(document.toString().getBytes(document.outputSettings().charset().name()));
		} catch (UnsupportedEncodingException e) {}
		result.setCharset(document.outputSettings().charset().name());
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> json2Map(String cookiesJSON) {
		return JSONObject.fromObject(cookiesJSON);
	}

	public static String map2Json(Map<String, String> loginCookies) {
		return JSONObject.fromObject(loginCookies).toString();
	}

	public static ResponseWrapper toResponseWrapper(Response resp) {
		ResponseWrapper result = new ResponseWrapper();
		result.setDocument(new DocumentWrapper());
		result.setCookies(map2Json(resp.cookies()));
		result.getDocument().setBytes(resp.bodyAsBytes());
		result.getDocument().setCharset(resp.charset());
		return result;
	}

	public static Document toDocument(ResponseWrapper resp) {
		return toDocument(resp.getDocument());
	}

}
