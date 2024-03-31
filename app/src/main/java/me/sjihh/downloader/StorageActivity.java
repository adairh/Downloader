package me.sjihh.downloader;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StorageActivity extends AppCompatActivity implements DownloadedItemsAdapter.OnDeleteClickListener {
    private RecyclerView downloadedItemsRecyclerView;
    private DownloadedItemsAdapter adapter;
    private List<DownloadedItem> downloadedItems;


    private final DownloadedItemsAdapter.OnOpenClickListener openListener = new DownloadedItemsAdapter.OnOpenClickListener() {
        @Override
        public void onOpenClick(DownloadedItem item) {
            openFile(item);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        System.out.println("CONTEXT: " + getApplicationContext().getFilesDir().getPath());

        downloadedItemsRecyclerView = findViewById(R.id.downloaded_items_list);
        downloadedItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        downloadedItems = loadDownloadedItemsFromDirectory(StaticData.DOWNLOAD_DIRECTORY);
        adapter = new DownloadedItemsAdapter(downloadedItems, this, this, openListener);
        downloadedItemsRecyclerView.setAdapter(adapter);
    }

    private List<DownloadedItem> loadDownloadedItemsFromDirectory(String directoryPath) {
        List<DownloadedItem> items = new ArrayList<>();
        File directory = new File(directoryPath);
        File[] filesAndDirectories = directory.listFiles();
        if (filesAndDirectories != null) {
            for (File fileOrDirectory : filesAndDirectories) {
                String name = fileOrDirectory.getName();
                String downloadDate = getDownloadDateFromFile(fileOrDirectory);
                long fileSize = fileOrDirectory.isDirectory() ? 0 : fileOrDirectory.length();
                boolean isDirectory = fileOrDirectory.isDirectory();
                items.add(new DownloadedItem(name, downloadDate, fileSize, isDirectory));
            }
        }
        return items;
    }

    private void openFile(DownloadedItem item) {
        String fileName = item.getName();
        File file = new File(StaticData.DOWNLOAD_DIRECTORY, fileName);

        if (file.exists()) {
            Uri contentUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(contentUri, getMimeType(file));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                // Handle the case where no app can open the file
            }
        } else {
            System.out.println("File does not exist: " + file.getAbsolutePath());
            // Handle the case where the file does not exist
        }
    }

    private String getMimeType(File file) {
        // Implement logic to get the MIME type of the file based on its extension
        String extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    private String getDownloadDateFromFile(File file) {
        // Implement logic to get the download date from the file metadata or creation time
        // For simplicity, we'll use the current date
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    @Override
    public void onDeleteClick(int position) {
        // Handle delete button click for the item at the given position
        DownloadedItem item = downloadedItems.get(position);
        System.out.println("CLICKED: " + item.getName());

        // Get the full path of the file
        String filePath = StaticData.DOWNLOAD_DIRECTORY + File.separator + item.getName();

        // Create a File object with the full path
        File file = new File(filePath);

        // Check if the file exists
        if (file.exists()) {
            // Attempt to delete the file
            boolean deleted = file.delete();

            if (deleted) {
                System.out.println("File deleted successfully: " + filePath);
                // Remove the item from the list
                downloadedItems.remove(position);
                adapter.notifyItemRemoved(position);
            } else {
                System.out.println("Failed to delete file: " + filePath);
                // Handle the case where the file deletion failed
            }
        } else {
            System.out.println("File does not exist: " + filePath);
            // Handle the case where the file does not exist
        }
    }
}