/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pengyue.weixin.wxhelper.json;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class ContactItem {
    private long Uin;
    private String UserName="";
    private String NickName="";
    private String HeadImgUrl="";
    private int ContactFlag;
    private int MemberCount;
    private List MemberList =new ArrayList();
    private String RemarkName="";
    private String HideInputBarFlag="";
    private int Sex;
    private String Signature="";
    private int VerifyFlag;
    private int OwnerUin;
    private String PYInitial="";
    private String PYQuanPin="";
    private String RemarkPYInitial="";
    private String RemarkPYQuanPin="";
    private int StarFriend;
    private int AppAccountFlag;
    private int Statues;
    private int AttrStatus;
    private String Province="";
    private String City="";
    private String Alias="";
    private int SnsFlag;
    private int UniFriend;

    public long getUin() {
        return Uin;
    }

    public void setUin(long Uin) {
        this.Uin = Uin;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getHeadImgUrl() {
        return HeadImgUrl;
    }

    public void setHeadImgUrl(String HeadImgUrl) {
        this.HeadImgUrl = HeadImgUrl;
    }

    public int getContactFlag() {
        return ContactFlag;
    }

    public void setContactFlag(int ContactFlag) {
        this.ContactFlag = ContactFlag;
    }

    public int getMemberCount() {
        return MemberCount;
    }

    public void setMemberCount(int MemberCount) {
        this.MemberCount = MemberCount;
    }

    public List getMemberList() {
        return MemberList;
    }

    public void setMemberList(List MemberList) {
        this.MemberList = MemberList;
    }

    public String getRemarkName() {
        return RemarkName;
    }

    public void setRemarkName(String RemarkName) {
        this.RemarkName = RemarkName;
    }

    public String getHideInputBarFlag() {
        return HideInputBarFlag;
    }

    public void setHideInputBarFlag(String HideInputBarFlag) {
        this.HideInputBarFlag = HideInputBarFlag;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int Sex) {
        this.Sex = Sex;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String Signature) {
        this.Signature = Signature;
    }

    public int getVerifyFlag() {
        return VerifyFlag;
    }

    public void setVerifyFlag(int VerifyFlag) {
        this.VerifyFlag = VerifyFlag;
    }

    public int getOwnerUin() {
        return OwnerUin;
    }

    public void setOwnerUin(int OwnerUin) {
        this.OwnerUin = OwnerUin;
    }

    public String getPYInitial() {
        return PYInitial;
    }

    public void setPYInitial(String PYInitial) {
        this.PYInitial = PYInitial;
    }

    public String getPYQuanPin() {
        return PYQuanPin;
    }

    public void setPYQuanPin(String PYQuanPin) {
        this.PYQuanPin = PYQuanPin;
    }

    public String getRemarkPYInitial() {
        return RemarkPYInitial;
    }

    public void setRemarkPYInitial(String RemarkPYInitial) {
        this.RemarkPYInitial = RemarkPYInitial;
    }

    public String getRemarkPYQuanPin() {
        return RemarkPYQuanPin;
    }

    public void setRemarkPYQuanPin(String RemarkPYQuanPin) {
        this.RemarkPYQuanPin = RemarkPYQuanPin;
    }

    public int getStarFriend() {
        return StarFriend;
    }

    public void setStarFriend(int StarFriend) {
        this.StarFriend = StarFriend;
    }

    public int getAppAccountFlag() {
        return AppAccountFlag;
    }

    public void setAppAccountFlag(int AppAccountFlag) {
        this.AppAccountFlag = AppAccountFlag;
    }

    public int getStatues() {
        return Statues;
    }

    public void setStatues(int Statues) {
        this.Statues = Statues;
    }

    public int getAttrStatus() {
        return AttrStatus;
    }

    public void setAttrStatus(int AttrStatus) {
        this.AttrStatus = AttrStatus;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String Province) {
        this.Province = Province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String Alias) {
        this.Alias = Alias;
    }

    public int getSnsFlag() {
        return SnsFlag;
    }

    public void setSnsFlag(int SnsFlag) {
        this.SnsFlag = SnsFlag;
    }

    public int getUniFriend() {
        return UniFriend;
    }

    public void setUniFriend(int UniFriend) {
        this.UniFriend = UniFriend;
    }

    @Override
    public String toString() {
        return "ContactItem{" + "Uin=" + Uin + ", UserName=" + UserName + ", NickName=" + NickName + ", HeadImgUrl=" + HeadImgUrl + ", ContactFlag=" + ContactFlag + ", MemberCount=" + MemberCount + ", MemberList=" + MemberList + ", RemarkName=" + RemarkName + ", HideInputBarFlag=" + HideInputBarFlag + ", Sex=" + Sex + ", Signature=" + Signature + ", VerifyFlag=" + VerifyFlag + ", OwnerUin=" + OwnerUin + ", PYInitial=" + PYInitial + ", PYQuanPin=" + PYQuanPin + ", RemarkPYInitial=" + RemarkPYInitial + ", RemarkPYQuanPin=" + RemarkPYQuanPin + ", StarFriend=" + StarFriend + ", AppAccountFlag=" + AppAccountFlag + ", Statues=" + Statues + ", AttrStatus=" + AttrStatus + ", Province=" + Province + ", City=" + City + ", Alias=" + Alias + ", SnsFlag=" + SnsFlag + ", UniFriend=" + UniFriend + '}';
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (int) (this.Uin ^ (this.Uin >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContactItem other = (ContactItem) obj;
        if (this.Uin != other.Uin) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
    
}
