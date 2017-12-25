package com.pengyue.bbsTools.bbscore.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pengyue.bbsTools.bbscore.Loginer;


public abstract class BeanUtils {
	
	public static final Log LOG = LogFactory.getLog(Loginer.class);
	
	public static void setProp(Object bean, String value, String name,
			boolean isAppendValue) {
		try {
			if(isAppendValue){
				Object oldVal = org.apache.commons.beanutils.BeanUtils.getProperty(bean, name);
				if(oldVal!=null){
					value = oldVal.toString()+","+value;
				}
			}
			String mapName = System.getProperty("maping."+bean.getClass().getName()+"."+name.replaceAll("\\s+", "")); 
			if(LOG.isDebugEnabled()){
				LOG.debug("映射实体名称："+name.trim()+" = "+mapName);
			}
			if(mapName!=null&&mapName.trim().length()>0){
				name=mapName;
			}
			org.apache.commons.beanutils.BeanUtils.copyProperty(bean, name, value);
		} catch (IllegalAccessException e) {
			if(LOG.isDebugEnabled()){
				LOG.error(" 没有设置属性成功！ name:"+name+" value:"+value,e);
			}
		} catch (InvocationTargetException e) {
			if(LOG.isDebugEnabled()){
				LOG.error(" 没有设置属性成功！ name:"+name+" value:"+value,e);
			}
		} catch (NoSuchMethodException e) {
			if(LOG.isDebugEnabled()){
				LOG.error(" 没有获取属性成功！ name:"+name+" value:"+value,e);
			}
		}
	}

	public static boolean isEmptyPojo(Object bean) {
		try {
			Map<?,?> beanData = org.apache.commons.beanutils.BeanUtils.describe(bean);
			if(beanData==null)return true;
			boolean isEmpty = true;
			for (Entry<?, ?> prop : beanData.entrySet()) {
				if(prop.getValue()!=null && !prop.getKey().equals("class")){
					return false;
				}
			}
			return isEmpty;
		} catch (IllegalAccessException e) {
			if(LOG.isDebugEnabled()){
				LOG.error(" 判定是否為空实体 失败！:"+bean,e);
			}
			return true;
		} catch (InvocationTargetException e) {
			if(LOG.isDebugEnabled()){
				LOG.error(" 判定是否為空实体 失败！:"+bean,e);
			}
			return true;
		} catch (NoSuchMethodException e) {
			if(LOG.isDebugEnabled()){
				LOG.error(" 判定是否為空实体 失败！:"+bean,e);
			}
			return true;
		}
	}
	
}
