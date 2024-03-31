package me.sjihh.downloader;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;

public class DownloaderManager {

    private Context context;
    private DownloadManager downloadManager;

    public DownloaderManager(Context context) {
        this.context = context;
        this.downloadManager = (android.app.DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public void writeToDisk(@NonNull String imageUrl) {
        String customDirectoryPath = StaticData.DOWNLOAD_DIRECTORY;

        Uri imageUri = Uri.parse(imageUrl);
        String fileName = imageUri.getPath().split("/")[imageUri.getPath().split("/").length-1];

        // Use custom directory if provided, otherwise use default Downloads directory
        File directory;
        if (customDirectoryPath != null && !customDirectoryPath.isEmpty()) {
            directory = new File(customDirectoryPath);
        } else {
            directory = getDownloadsDirectory();
        }

        assert fileName != null;
        File downloadFile = new File(directory, fileName);
        Uri downloadUri = Uri.fromFile(downloadFile);

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(imageUri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(fileName);
        request.setDescription(imageUrl);
        request.allowScanningByMediaScanner();
        request.setDestinationUri(downloadUri);

        downloadManager.enqueue(request);
    }

    public long enqueueDownload(String url, String fileName) {
        writeToDisk(url);
        return -1;
        /*File directory = getDownloadsDirectory();
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                Log.e("DownloaderManager", "Failed to create downloads directory: " + directory.getAbsolutePath());
                return -1;
            }
        }

        File downloadFile = new File(directory, fileName);
        Uri downloadUri = Uri.fromFile(downloadFile);

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
                .setTitle(fileName)
                .setDescription("Downloading...")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationUri(downloadUri)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true);

        System.out.println(request.toString());

        try {
            long a = downloadManager.enqueue(request);
            System.out.println(a);
            return a;
        } catch (IllegalArgumentException e) {
            System.out.println("DownloaderManager: " +"Invalid download request: " + e.getMessage());
            return -1;
        } catch (Exception e) {
            System.out.println("DownloaderManager: " + "Failed to enqueue download: " + e.getMessage());
            return -1;
        }*/
    }

    private File getDownloadsDirectory() {
        return new File(context.getExternalFilesDir(null), StaticData.CUSTOM_PATH);
    }
}