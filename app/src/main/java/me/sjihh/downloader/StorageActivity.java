package me.sjihh.downloader;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StorageActivity extends AppCompatActivity {
    private RecyclerView downloadedItemsRecyclerView;
    private DownloadedItemsAdapter adapter;
    private List<DownloadedItem> downloadedItems;

    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        System.out.println("CONTEXT: " + getApplicationContext().getFilesDir().getPath());

        downloadedItemsRecyclerView = findViewById(R.id.downloaded_items_list);
        downloadedItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        downloadedItems = loadDownloadedItemsFromDirectory(StaticData.DOWNLOAD_DIRECTORY);

        adapter = new DownloadedItemsAdapter(downloadedItems, this);
        downloadedItemsRecyclerView.setAdapter(adapter);

        delete.setOnClickListener(v -> {

        });
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


    private String getDownloadDateFromFile(File file) {
        // Implement logic to get the download date from the file metadata or creation time
        // For simplicity, we'll use the current date
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }
}