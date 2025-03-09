package com.example.classup.classup.classup.mycollege.ui.ebooks;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.classup.classup.classup.mycollege.R;

import java.util.List;

public class EbooksAdapter extends RecyclerView.Adapter<EbooksAdapter.ViewHolder> {
    private Context context;
    private List<String> ebookTitles;
    private List<String> ebookUrls;

    public EbooksAdapter(Context context, List<String> ebookTitles, List<String> ebookUrls) {
        this.context = context;
        this.ebookTitles = ebookTitles;
        this.ebookUrls = ebookUrls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_ebooks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pdfTitle.setText(ebookTitles.get(position));

        // Set click listener only for the download button
        holder.pdfDownloadBtn.setOnClickListener(v -> {
            String url = ebookUrls.get(position);
            downloadPdf(url, ebookTitles.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return ebookTitles.size();
    }

    private void downloadPdf(String url, String title) {
        // Use the DownloadManager to download the PDF
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(title); // Set the title for the download
        request.setDescription("Downloading " + title);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // Set the destination directory for the download
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title + ".pdf");

        // Get the DownloadManager system service
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (manager != null) {
            // Enqueue the request
            long downloadId = manager.enqueue(request);

            // Create a BroadcastReceiver to listen for download completion
            BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    // Check if the download is complete
                    if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                        if (id == downloadId) {
                            // Show completion message
                            Toast.makeText(context, "Download completed for: " + title, Toast.LENGTH_SHORT).show();

                            // Unregister the receiver to avoid memory leaks
                            context.unregisterReceiver(this);
                        }
                    }
                }
            };

            // Register the receiver for download completion
            IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED);
            } else {
                context.registerReceiver(receiver, filter);
            }

        } else {
            Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView pdfTitle;
        Button pdfDownloadBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            pdfTitle = itemView.findViewById(R.id.pdfTitle);
            pdfDownloadBtn = itemView.findViewById(R.id.downloadButton);
        }
    }
}
