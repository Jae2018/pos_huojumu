package com.huojumu.model;


/**
 * @author : Jie
 * Date: 2018/9/29
 * Description:
 */
public class SmallType {


    /**
     * fileName : 1 - 10啦啦啦啦.jpg
     * typeNo : CPT-0002-0002
     * typeName : 酸奶
     * fileUrl : http://192.168.1.155:8082/file/D:/bootstrap_file/92e0c450668a44ad9b961b5a1134cf2b.jpg
     * id : 33
     * parentId : 29
     */

    private String fileName;
    private String typeNo;
    private String typeName;
    private String fileUrl;
    private int id;
    private int parentId;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public SmallType(String typeName, int id, int parentId, boolean selected) {
        this.typeName = typeName;
        this.id = id;
        this.parentId = parentId;
        this.selected = selected;
    }

    public SmallType(String typeName, int id, int parentId) {
        this.typeName = typeName;
        this.id = id;
        this.parentId = parentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(String typeNo) {
        this.typeNo = typeNo;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
