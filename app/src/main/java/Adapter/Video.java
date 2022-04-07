package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtube.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Video extends RecyclerView.Adapter<Video.MyViewHolder> {

    private List<Model.Item> videoList;
    private Context context;

    public Video(List<Model.Item> videos, Context c) {
        this.videoList = videos;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Model.Item video = videoList.get(position);

        holder.title.setText(video.snippet.title);

        String url = video.snippet.thumbnails.high.url;
        Picasso.get().load(url).into(holder.ivThumb);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private AppCompatTextView title, description, date, videoId;
        private AppCompatImageView ivThumb;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvTitle);
            //description = itemView.findViewById(R.id.t)
            //date = itemView.findViewById(R.id.t)
            ivThumb = itemView.findViewById(R.id.ivThumb);
            //videoId = itemView.findViewById(R.id.t)
        }
    }
}
