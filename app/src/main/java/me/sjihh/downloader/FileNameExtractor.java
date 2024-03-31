package me.sjihh.downloader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class FileNameExtractor {
    private static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int FILE_NAME_LENGTH = 20;

    public static String getFileNameFromUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");

            String contentDisposition = connection.getHeaderField("Content-Disposition");
            if (contentDisposition != null && contentDisposition.contains("filename=")) {
                String[] dispositionParts = contentDisposition.split(";");
                for (String part : dispositionParts) {
                    if (part.trim().startsWith("filename=")) {
                        String fileName = part.replaceAll("filename=", "").trim();
                        if (fileName.startsWith("\"") && fileName.endsWith("\"")) {
                            fileName = fileName.substring(1, fileName.length() - 1);
                        }
                        return fileName;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If the file name cannot be extracted from the Content-Disposition header,
        // you can try to extract it from the URL itself
        String urlPath = urlString.substring(urlString.lastIndexOf('/') + 1);
        if (!urlPath.isEmpty()) {
            return urlPath;
        }

        return null;
    }

    private static String generateRandomFileName(String fileExtension) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < FILE_NAME_LENGTH; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARS.length());
            char randomChar = ALLOWED_CHARS.charAt(randomIndex);
            sb.append(randomChar);
        }

        sb.append(".").append(fileExtension);
        return sb.toString();
    }
}