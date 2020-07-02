package com.ty.tms.core.bean.vo;

public class UserBuddyVO {

    // 用户ID
    private String userId;

    // 好友ID
    private String buddyId;

    // 好友头像
    private String buddyUserAvatar;

    // 好友名字
    private String buddyUserName;

    // 好友备注名
    private String noteName;

    // 好友启航ID
    private String qihangId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBuddyId() {
        return buddyId;
    }

    public void setBuddyId(String buddyId) {
        this.buddyId = buddyId;
    }

    public String getBuddyUserAvatar() {
        return buddyUserAvatar;
    }

    public void setBuddyUserAvatar(String buddyUserAvatar) {
        this.buddyUserAvatar = buddyUserAvatar;
    }

    public String getBuddyUserName() {
        return buddyUserName;
    }

    public void setBuddyUserName(String buddyUserName) {
        this.buddyUserName = buddyUserName;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getQihangId() {
        return qihangId;
    }

    public void setQihangId(String qihangId) {
        this.qihangId = qihangId;
    }
}
