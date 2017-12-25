/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pengyue.weixin.wxhelper.json;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Administrator
 */
public class MessageJson {

    private long MsgId;
    private String FromUserName = "";
    private String ToUserName = "";

    private int MsgType;

    private String Content = "";

    private int Status;

    private int ImgStatus;

    private long CreateTime;

    private int VoiceLength;

    private int PlayLength;

    private String FileName = "";
    private String FileSize = "";
    private String MediaId = "";
    private String Url = "";

    private int AppMsgType;
    private int StatusNotifyCode;
    private String StatusNotifyUserName;

    @SerializedName("RecommendInfo")
    private RecommendInfo recommendInfo;

    private int ForwardFlag;

    @SerializedName("AppInfo")
    private AppInfo appInfo;

    private int HasProductId;

    private String Ticket = "";

    public MessageJson() {
        recommendInfo = new RecommendInfo();
        appInfo = new AppInfo();
    }

    public long getMsgId() {
        return MsgId;
    }

    public void setMsgId(long MsgId) {
        this.MsgId = MsgId;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String FromUserName) {
        this.FromUserName = FromUserName;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String ToUserName) {
        this.ToUserName = ToUserName;
    }

    public int getMsgType() {
        return MsgType;
    }

    public void setMsgType(int MsgType) {
        this.MsgType = MsgType;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public int getImgStatus() {
        return ImgStatus;
    }

    public void setImgStatus(int ImgStatus) {
        this.ImgStatus = ImgStatus;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long CreateTime) {
        this.CreateTime = CreateTime;
    }

    public int getVoiceLength() {
        return VoiceLength;
    }

    public void setVoiceLength(int VoiceLength) {
        this.VoiceLength = VoiceLength;
    }

    public int getPlayLength() {
        return PlayLength;
    }

    public void setPlayLength(int PlayLength) {
        this.PlayLength = PlayLength;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public String getFileSize() {
        return FileSize;
    }

    public void setFileSize(String FileSize) {
        this.FileSize = FileSize;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String MediaId) {
        this.MediaId = MediaId;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public int getAppMsgType() {
        return AppMsgType;
    }

    public void setAppMsgType(int AppMsgType) {
        this.AppMsgType = AppMsgType;
    }

    public int getStatusNotifyCode() {
        return StatusNotifyCode;
    }

    public void setStatusNotifyCode(int StatusNotifyCode) {
        this.StatusNotifyCode = StatusNotifyCode;
    }

    public String getStatusNotifyUserName() {
        return StatusNotifyUserName;
    }

    public void setStatusNotifyUserName(String StatusNotifyUserName) {
        this.StatusNotifyUserName = StatusNotifyUserName;
    }

    public RecommendInfo getRecommendInfo() {
        return recommendInfo;
    }

    public void setRecommendInfo(RecommendInfo recommendInfo) {
        this.recommendInfo = recommendInfo;
    }

    public int getForwardFlag() {
        return ForwardFlag;
    }

    public void setForwardFlag(int ForwardFlag) {
        this.ForwardFlag = ForwardFlag;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public int getHasProductId() {
        return HasProductId;
    }

    public void setHasProductId(int HasProductId) {
        this.HasProductId = HasProductId;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String Ticket) {
        this.Ticket = Ticket;
    }

    @Override
    public String toString() {
        return "MessageJson{" + "MsgId=" + MsgId + ", FromUserName=" + FromUserName + ", ToUserName=" + ToUserName + ", MsgType=" + MsgType + ", Content=" + Content + ", Status=" + Status + ", ImgStatus=" + ImgStatus + ", CreateTime=" + CreateTime + ", VoiceLength=" + VoiceLength + ", PlayLength=" + PlayLength + ", FileName=" + FileName + ", FileSize=" + FileSize + ", MediaId=" + MediaId + ", Url=" + Url + ", AppMsgType=" + AppMsgType + ", StatusNotifyCode=" + StatusNotifyCode + ", StatusNotifyUserName=" + StatusNotifyUserName + ", recommendInfo=" + recommendInfo + ", ForwardFlag=" + ForwardFlag + ", appInfo=" + appInfo + ", HasProductId=" + HasProductId + ", Ticket=" + Ticket + '}';
    }

    class AppInfo {

        private String AppID;
        private int Type;
    }

    class RecommendInfo {

        private String UserName = "";
        private String NickName = "";
        private int QQNum;
        private String Province = "";
        private String City = "";
        private String Content = "";
        private String Signature = "";
        private String Alias = "";
        private int Scene;
        private int VerifyFlag;
        private int AttrStatus;
        private int Sex;
        private String Ticket = "";
        private int OpCode;

        @Override
        public String toString() {
            return "RecommendInfo{" + "UserName=" + UserName + ", NickName=" + NickName + ", QQNum=" + QQNum + ", Province=" + Province + ", City=" + City + ", Content=" + Content + ", Signature=" + Signature + ", Alias=" + Alias + ", Scene=" + Scene + ", VerifyFlag=" + VerifyFlag + ", AttrStatus=" + AttrStatus + ", Sex=" + Sex + ", Ticket=" + Ticket + ", OpCode=" + OpCode + '}';
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

        public int getQQNum() {
            return QQNum;
        }

        public void setQQNum(int QQNum) {
            this.QQNum = QQNum;
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

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public String getSignature() {
            return Signature;
        }

        public void setSignature(String Signature) {
            this.Signature = Signature;
        }

        public String getAlias() {
            return Alias;
        }

        public void setAlias(String Alias) {
            this.Alias = Alias;
        }

        public int getScene() {
            return Scene;
        }

        public void setScene(int Scene) {
            this.Scene = Scene;
        }

        public int getVerifyFlag() {
            return VerifyFlag;
        }

        public void setVerifyFlag(int VerifyFlag) {
            this.VerifyFlag = VerifyFlag;
        }

        public int getAttrStatus() {
            return AttrStatus;
        }

        public void setAttrStatus(int AttrStatus) {
            this.AttrStatus = AttrStatus;
        }

        public int getSex() {
            return Sex;
        }

        public void setSex(int Sex) {
            this.Sex = Sex;
        }

        public String getTicket() {
            return Ticket;
        }

        public void setTicket(String Ticket) {
            this.Ticket = Ticket;
        }

        public int getOpCode() {
            return OpCode;
        }

        public void setOpCode(int OpCode) {
            this.OpCode = OpCode;
        }

    }
}
