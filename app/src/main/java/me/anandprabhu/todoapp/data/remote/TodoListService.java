package me.anandprabhu.todoapp.data.remote;

import java.util.List;

import me.anandprabhu.todoapp.data.model.TodoListItem;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Anand Prabhu.
 */

public interface TodoListService {

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://www.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build();

    @GET("/v2/582695f5100000560464ca40")
    Call<List<TodoListItem>> getTodoList();
}
