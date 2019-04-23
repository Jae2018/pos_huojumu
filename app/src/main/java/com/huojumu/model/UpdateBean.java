package com.huojumu.model;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/11/29
 * Description: qq：494669467，wx：s494669467
 */
public class UpdateBean {

    /**
     * id : 2c988ba46a05b276016a47f741810003
     * projectName : pos
     * versionNum : 1.0.0.1
     * createTime : 2019-04-23 10:12:27
     * fileDetailModelList : [{"id":"2c988ba46a05b276016a47f741de0005","fileName":"pos.apk","fileMd5":"7960f817c3e410680932ba0fe3564311","createTime":"2019-04-23 10:12:27","fileSize":7524208,"fileType":"file"}]
     */

    private String id;
    private String projectName;
    private String versionNum;
    private String createTime;
    private List<FileDetailModelListBean> fileDetailModelList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<FileDetailModelListBean> getFileDetailModelList() {
        return fileDetailModelList;
    }

    public void setFileDetailModelList(List<FileDetailModelListBean> fileDetailModelList) {
        this.fileDetailModelList = fileDetailModelList;
    }

    public static class FileDetailModelListBean {
        /**
         * id : 2c988ba46a05b276016a47f741de0005
         * fileName : pos.apk
         * fileMd5 : 7960f817c3e410680932ba0fe3564311
         * createTime : 2019-04-23 10:12:27
         * fileSize : 7524208
         * fileType : file
         */

        private String id;
        private String fileName;
        private String fileMd5;
        private String createTime;
        private int fileSize;
        private String fileType;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileMd5() {
            return fileMd5;
        }

        public void setFileMd5(String fileMd5) {
            this.fileMd5 = fileMd5;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getFileSize() {
            return fileSize;
        }

        public void setFileSize(int fileSize) {
            this.fileSize = fileSize;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }
    }
}
