package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtube.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import Api.YouTubeService;
import Helper.RecyclerItemClickListener;
import Helper.RetrofitConfig;
import Helper.YouTubeConfig;
import Model.Item;
import Model.Result;
import Model.Video;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private MaterialSearchView svMain;
    private RecyclerView rvVideos;
    private List<Item> videos = new ArrayList<>();
    private Result result;
    private Adapter.Video adapter;

    //Retrofit
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recoverVideos("");
        initAndConfigComponents();


    }

    private void initAndConfigComponents(){

        Toolbar toolbar = findViewById(R.id.tbMain);
        toolbar.setTitle("Youtube");
        setSupportActionBar(toolbar);

        //Init and config Search View
        svMain = findViewById(R.id.svMain);
        svMain.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                recoverVideos(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        svMain.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

                recoverVideos("");
            }
        });
    }

    private void recoverVideos(String search){

        String q = search.replaceAll(" ", "+");
        //Init and config Retrofit
        retrofit = RetrofitConfig.getRetrofit();
        YouTubeService youTubeService = retrofit.create(YouTubeService.class);
        youTubeService.recoverVideos(
                "snippet", "date", "20", YouTubeConfig.API_KEY, YouTubeConfig.CHANNEL_ID,q
        ).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if(response.isSuccessful()){

                    result = response.body();
                    videos = result.items;
                    initAndConfigRecyclerView();

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.search);
        svMain.setMenuItem(item);
        return true;
    }

    public void initAndConfigRecyclerView(){

        adapter = new Adapter.Video(videos, getApplicationContext());

        //Init and config Recycler View
        rvVideos = findViewById(R.id.rvVideos);
        rvVideos.setHasFixedSize(true);
        rvVideos.setLayoutManager(new LinearLayoutManager(this));
        rvVideos.setAdapter(adapter);

        rvVideos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        rvVideos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Item video = videos.get(position);
                                String videoId = video.id.videoId;
                                Intent intent = new Intent(MainActivity.this, Player.class);
                                intent.putExtra("videoId", videoId);
                                startActivity(intent);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }
}