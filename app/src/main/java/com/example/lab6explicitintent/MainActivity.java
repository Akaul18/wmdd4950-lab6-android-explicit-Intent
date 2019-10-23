package com.example.lab6explicitintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    OkHttpClient client = new OkHttpClient();
    private String url = "https://www.reddit.com/.json";

    List<String> titlesList = new ArrayList<>();
    List<String> urlList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Error in failure is ->", "error"+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                if(!response.isSuccessful()){
                    Log.d("response code ->", ""+response.code());
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject urlObj = new JSONObject(response.body().string());
                            JSONArray urlObjArray = urlObj.getJSONObject("data").getJSONArray("children");
                            String s = "";
                            String u = "";
                            for(int i=0;i<urlObjArray.length(); i++){
                                s = urlObjArray.getJSONObject(i).getJSONObject("data").getString("title");
                                u = urlObjArray.getJSONObject(i).getJSONObject("data").getString("url");

                                titlesList.add(s);
//                        Log.d("msg",titlesList.get(i));
                                urlList.add(u);
//                        Log.d("titlesList:", ""+titlesList.get(i)+" url: "+urlList.get(i));
                            }
                            recyclerAdapter = new RecyclerAdapter(titlesList, urlList);
                            recyclerView.setAdapter(recyclerAdapter);
//                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
                            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
//        recyclerView.setLayoutManager(new LinearLayoutManager(this)); or set this in the activity_main
    }
}
