package Helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    public static Retrofit getRetrofit(){

        return new Retrofit.Builder()
                .baseUrl(YouTubeConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
