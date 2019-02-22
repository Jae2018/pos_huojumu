package com.huojumu.model;

public class MakesBean {
    /**     做法
     * practiceName : 加冰
     * groupId : 1
     * practiceId : 1
     * multiple : 1
     */

    private String practiceName;
    private String groupId;
    private int practiceId;
    private int multiple;
//    private boolean isSelected;

    public MakesBean(int practiceId) {
        this.practiceId = practiceId;
    }

//    public boolean isSelected() {
//        return isSelected;
//    }
//
//    public void setSelected(boolean selected) {
//        isSelected = selected;
//    }

    public String getPracticeName() {
        return practiceName;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getPracticeId() {
        return practiceId;
    }

    public void setPracticeId(int practiceId) {
        this.practiceId = practiceId;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }
}
