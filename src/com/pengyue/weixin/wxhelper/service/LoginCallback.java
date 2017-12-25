/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pengyue.weixin.wxhelper.service;

/**
 *
 * @author Administrator
 */
public interface LoginCallback {
    
    //当用户通过手机扫描后，通过确定结果，进行处理
    //@param rs代表传入的结果参数
    //@param target 代表根据结果参数可以操控的对象
    public void handleResult(Object target,String rs) ;
    
}
