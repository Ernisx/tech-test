package tech.test;

public class UploadFileResponse {

    private String fileName;
    private String fileType;
    private long size;

    public UploadFileResponse() {
    }

    public UploadFileResponse(String fileName, String fileType, long size) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UploadFileResponse{");
        sb.append("fileName='").append(fileName).append('\'');
        sb.append(", fileType='").append(fileType).append('\'');
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }
}