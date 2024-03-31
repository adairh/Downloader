package me.sjihh.downloader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DownloadedItemsAdapter extends RecyclerView.Adapter<DownloadedItemsAdapter.ViewHolder> {
    private List<DownloadedItem> downloadedItems;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnDeleteClickListener deleteListener;

    private OnOpenClickListener openListener;

    public DownloadedItemsAdapter(List<DownloadedItem> downloadedItems, Context context, OnDeleteClickListener deleteListener, OnOpenClickListener openListener) {
        this.downloadedItems = downloadedItems;
        this.context = context;
        this.deleteListener = deleteListener;
        this.openListener = openListener;
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
            holder.downloadDateTextView.setText("Date: " + item.getDownloadDate());
            holder.fileSizeTextView.setText("Size: " + formatFileSize(item.getFileSize()) + "B");
        }
        holder.deleteButton.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDeleteClick(position);
            }
        });
        holder.openButton.setOnClickListener(v -> {
            if (openListener != null) {
                openListener.onOpenClick(item);
            }
        });
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

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public interface OnOpenClickListener {
        void onOpenClick(DownloadedItem item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView fileNameTextView;
        public TextView downloadDateTextView;
        public TextView fileSizeTextView;
        public Button deleteButton;
        public Button openButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileNameTextView = itemView.findViewById(R.id.item_name);
            downloadDateTextView = itemView.findViewById(R.id.download_date);
            fileSizeTextView = itemView.findViewById(R.id.item_size);
            deleteButton = itemView.findViewById(R.id.delete_button);
            openButton = itemView.findViewById(R.id.open_button);
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