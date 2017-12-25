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
 * @description 用于封装服务器返回的关于用户的所有的好友信息。
 */
public class FullContactListJson {
    
    @SerializedName("BaseResponse")
    private BaseResponse br;
    
    @SerializedName("MemberCount")
    private int memberCount;
    
    @SerializedName("MemberList")
    private List<ContactItem> memberList;
    
    public FullContactListJson(){
        br=new BaseResponse();
        memberList=new ArrayList ();
    }

    @Override
    public String toString() {
        return "FullContactListJson{" + "br=" + br + ", memberCount=" + memberCount + ", memberList=" + memberList + '}';
    }
    

    public BaseResponse getBr() {
        return br;
    }

    public void setBr(BaseResponse br) {
        this.br = br;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public List<ContactItem> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<ContactItem> memberList) {
        this.memberList = memberList;
    }
    
}
