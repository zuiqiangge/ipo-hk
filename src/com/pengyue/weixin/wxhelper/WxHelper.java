/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pengyue.weixin.wxhelper;

import java.io.IOException;
import java.util.Date;
import com.pengyue.weixin.wxhelper.json.HeartBeatBean;
import com.pengyue.weixin.wxhelper.json.WebWxSyncPostBean;

/**
 *
 * @author Administrator
 */
public class WxHelper {
     static {
        System.setProperty("jsse.enableSNIExtension", "false");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
    	System.out.println(111);
        WxBox box=new WxBox();
        box.login();
        box.configWxData();
        box.configWebWxInitBean();
        box.loadMsgAndUpdateSyncKey();
        if(box.wxStatusNotify()){
            System.out.println("状态同步成功！");
            HeartBeatBean beat;
           
            while(true){
                beat= box.syncCheck();
                System.out.println(new Date().toLocaleString()+"::"+box.syncCheck());
                if(beat.getRetcode().equals("0")){//信息通讯正确
                    if(!beat.getSelector().equals("0")){//有需要处理的消息
                        WebWxSyncPostBean syncBean=box.loadMsgAndUpdateSyncKey();
                       if(syncBean.getAddMsgCount()!=0){
                           System.out.println("AddMsgCount:"+syncBean.getAddMsgList());
                       }
                       if(syncBean.getDelContactCount()!=0){
                            System.out.println("DelContactCount:"+syncBean.getDelContactList());
                       }
                       if(syncBean.getModChatRoomMemberCount()!=0){
                           System.out.println("ModChatRoomMemberCount:"+syncBean.getModChatRoomMemberList());
                       }
                    }
                }else{
                    System.out.println(new Date().toLocaleString()+":: 心跳机制出错，系统退出！");
                    System.exit(-1);
                }
                Thread.sleep(5000);
            }
        } 
        
    }

}
