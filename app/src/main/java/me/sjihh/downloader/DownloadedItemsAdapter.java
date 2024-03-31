package me.sjihh.downloader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DownloadedItemsAdapter extends RecyclerView.Adapter<DownloadedItemsAdapter.ViewHolder> {
    private List<DownloadedItem> downloadedItems;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public DownloadedItemsAdapter(List<DownloadedItem> downloadedItems, Context context) {
        this.downloadedItems = downloadedItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DownloadedItem item = downloadedItems.get(position);
        holder.fileNameTextView.setText(item.getName());

        if (item.isDirectory()) {
            holder.downloadDateTextView.setText("Directory");
            holder.fileSizeTextView.setText("");
        } else {
            holder.downloadDateTextView.setText(item.getDownloadDate());
            holder.fileSizeTextView.setText(formatFileSize(item.getFileSize()));
        }
    }

    @Override
    public int getItemCount() {
        return downloadedItems.size();
    }

    private String formatFileSize(long fileSize) {
        // Implement logic to format the file size (e.g., 1.2 MB, 3.5 KB)
        return String.valueOf(fileSize);
    }

    public interface OnItemClickListener {
        void onItemClick(DownloadedItem item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView fileNameTextView;
        public TextView downloadDateTextView;
        public TextView fileSizeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileNameTextView = itemView.findViewById(R.id.item_name);
            downloadDateTextView = itemView.findViewById(R.id.download_date);
            fileSizeTextView = itemView.findViewById(R.id.item_size);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    DownloadedItem item = downloadedItems.get(position);
                    onItemClickListener.onItemClick(item);
                }
            }
        }
    }
}