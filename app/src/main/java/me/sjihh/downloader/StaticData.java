package me.sjihh.downloader;

import android.os.Environment;

public class StaticData {
    public static final String CUSTOM_PATH = "/Downloader";
    public static final String DOWNLOAD_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + CUSTOM_PATH;
    //public static final String DOWNLOAD_DIRECTORY = "/data/user/0/me.sjihh.downloader/files";

    static {
        System.out.println("DSADS" + DOWNLOAD_DIRECTORY);
    }

}
