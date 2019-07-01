package com.huojumu.model;

public class MemberBean {

    /**
     * joinTime : 2019-07-01 14:12:13
     * nickname : null
     * totalConsumption : 0.01
     * memberId : 39
     */

    private String joinTime;
    private String nickname;
    private double totalConsumption;
    private int memberId;

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

}
