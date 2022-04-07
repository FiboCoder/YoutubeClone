package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.youtube.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import Helper.YouTubeConfig;

public class Player extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView youTubePlayerView;
    private String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){

            videoId = bundle.getString("videoId");
            youTubePlayerView = findViewById(R.id.youtubePlayerView);
            youTubePlayerView.initialize(YouTubeConfig.API_KEY, this);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean recovered) {

        youTubePlayer.setFullscreen(true);
        youTubePlayer.setShowFullscreenButton(false);
        youTubePlayer.loadVideo(videoId);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}