package com.huojumu.model;

import java.util.List;

public class VipListBean {


    /**
     * total : 1
     * rows : [{"memberNo":"NOCN1100010000000005","score":0,"registerTime":"2018-12-04","level":"VIP1","loginName":null,"sumScore":0,"mobile":"15822812813","range":"0~499","memberId":5}]
     * pageNum : 1
     */

    private int total;
    private int pageNum;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * memberNo : NOCN1100010000000005
         * score : 0
         * registerTime : 2018-12-04
         * level : VIP1
         * loginName : null
         * sumScore : 0
         * mobile : 15822812813
         * range : 0~499
         * memberId : 5
         */

        private String memberNo;
        private int score;
        private String registerTime;
        private String level;
        private String loginName;
        private int sumScore;
        private String mobile;
        private String range;
        private int memberId;

        public String getMemberNo() {
            return memberNo;
        }

        public void setMemberNo(String memberNo) {
            this.memberNo = memberNo;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public int getSumScore() {
            return sumScore;
        }

        public void setSumScore(int sumScore) {
            this.sumScore = sumScore;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRange() {
            return range;
        }

        public void setRange(String range) {
            this.range = range;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }
    }
}
