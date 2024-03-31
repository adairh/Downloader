package me.sjihh.downloader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloaderActivity extends AppCompatActivity {
    private DownloaderManager downloadManager;
    private EditText linkInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("CCONTEXT: " + getApplicationContext().getFilesDir().getPath());

        downloadManager = new DownloaderManager(this);
        linkInput = findViewById(R.id.link_input);

        Button downloadButton = findViewById(R.id.download_button);
        Button viewButton = findViewById(R.id.view_button);
        downloadButton.setOnClickListener(v -> {
            String url = linkInput.getText().toString();
            if (url.equals("")) {
                Toast.makeText(this, "Empty URL", Toast.LENGTH_SHORT).show();
                return;
            }
            if (isUrlValid(url)) {
                //String fileName = FileNameExtractor.getFileNameFromUrl(url);
                long downloadId = -1;
                try {
                    downloadId = downloadManager.enqueueDownload(url, "fileName");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show();
                }
                // You can save the downloadId for future reference or tracking
            } else {
                // Show an error message or handle the invalid URL case
            }
        });

        viewButton.setOnClickListener(v -> startActivity(new Intent(this, StorageActivity.class)));
    }

    private boolean isUrlValid(String url) {
        // Add your URL validation logic here
        // For example, you can use regular expressions or check for specific patterns
        // Return true if the URL is valid, false otherwise
        return true; // Placeholder, replace with your validation logic
    }


}