package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MasterServerApi {
    @GET("ddnet/15/servers.json")
    Call<List<Server>> getServers();
}
