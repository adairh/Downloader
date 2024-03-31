package me.sjihh.downloader;

public class DownloadedItem {
    private String name;
    private String downloadDate;
    private long fileSize;
    private boolean isDirectory;

    public DownloadedItem(String name, String downloadDate, long fileSize, boolean isDirectory) {
        this.name = name;
        this.downloadDate = downloadDate;
        this.fileSize = fileSize;
        this.isDirectory = isDirectory;
        System.out.println(toString());
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getDownloadDate() {
        return downloadDate;
    }

    public long getFileSize() {
        return fileSize;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    @Override
    public String toString() {
        return "DownloadedItem{" +
                "name='" + name + '\'' +
                ", downloadDate='" + downloadDate + '\'' +
                ", fileSize=" + fileSize +
                ", isDirectory=" + isDirectory +
                '}';
    }
}