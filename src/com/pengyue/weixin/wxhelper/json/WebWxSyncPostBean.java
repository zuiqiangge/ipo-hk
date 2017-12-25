/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pengyue.weixin.wxhelper.json;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class WebWxSyncPostBean {
    
    @SerializedName("BaseResponse")
    private BaseResponse br;
    
    private int AddMsgCount ;
    
    private List<MessageJson>  AddMsgList;
    
    private int ModContactCount;
    
    private List<ContactItem> ModContactList;
    
    private int DelContactCount;
    
    private List<ContactItem> DelContactList;
    
    private int ModChatRoomMemberCount;
    
    private List ModChatRoomMemberList;
    
    private ProfileJson Profile;
    
    private int ContinueFlag;
    
    @SerializedName("SyncKey")
    private SyncKey syncKey;
    
    private String SKey="";
    
    public WebWxSyncPostBean(){
        br=new BaseResponse();
        AddMsgList=new ArrayList<MessageJson>();
        ModContactList=new ArrayList<ContactItem>();
        DelContactList=new ArrayList<ContactItem>();
        ModChatRoomMemberList=new ArrayList();
        Profile=new ProfileJson();
        syncKey=new SyncKey();
    }

    @Override
    public String toString() {
        return "WebWxSyncPostBean{" + "br=" + br + ", AddMsgCount=" + AddMsgCount + ", AddMsgList=" + AddMsgList + ", ModContactCount=" + ModContactCount + ", ModContactList=" + ModContactList + ", DelContactCount=" + DelContactCount + ", DelContactList=" + DelContactList + ", ModChatRoomMemberCount=" + ModChatRoomMemberCount + ", ModChatRoomMemberList=" + ModChatRoomMemberList + ", Profile=" + Profile + ", ContinueFlag=" + ContinueFlag + ", syncKey=" + syncKey + ", SKey=" + SKey + '}';
    }

    public BaseResponse getBr() {
        return br;
    }

    public void setBr(BaseResponse br) {
        this.br = br;
    }

    public int getAddMsgCount() {
        return AddMsgCount;
    }

    public void setAddMsgCount(int AddMsgCount) {
        this.AddMsgCount = AddMsgCount;
    }

    public List getAddMsgList() {
        return AddMsgList;
    }

    public void setAddMsgList(List AddMsgList) {
        this.AddMsgList = AddMsgList;
    }

    public int getModContactCount() {
        return ModContactCount;
    }

    public void setModContactCount(int ModContactCount) {
        this.ModContactCount = ModContactCount;
    }

    public List<ContactItem> getModContactList() {
        return ModContactList;
    }

    public void setModContactList(List<ContactItem> ModContactList) {
        this.ModContactList = ModContactList;
    }

    public int getDelContactCount() {
        return DelContactCount;
    }

    public void setDelContactCount(int DelContactCount) {
        this.DelContactCount = DelContactCount;
    }

    public List<ContactItem> getDelContactList() {
        return DelContactList;
    }

    public void setDelContactList(List<ContactItem> DelContactList) {
        this.DelContactList = DelContactList;
    }

    public int getModChatRoomMemberCount() {
        return ModChatRoomMemberCount;
    }

    public void setModChatRoomMemberCount(int ModChatRoomMemberCount) {
        this.ModChatRoomMemberCount = ModChatRoomMemberCount;
    }

    public List getModChatRoomMemberList() {
        return ModChatRoomMemberList;
    }

    public void setModChatRoomMemberList(List ModChatRoomMemberList) {
        this.ModChatRoomMemberList = ModChatRoomMemberList;
    }

    public ProfileJson getProfile() {
        return Profile;
    }

    public void setProfile(ProfileJson Profile) {
        this.Profile = Profile;
    }

    public int getContinueFlag() {
        return ContinueFlag;
    }

    public void setContinueFlag(int ContinueFlag) {
        this.ContinueFlag = ContinueFlag;
    }

    public SyncKey getSyncKey() {
        return syncKey;
    }

    public void setSyncKey(SyncKey syncKey) {
        this.syncKey = syncKey;
    }

    public String getSKey() {
        return SKey;
    }

    public void setSKey(String SKey) {
        this.SKey = SKey;
    }
    
}
