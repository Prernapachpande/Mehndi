package com.example.mehndi;

import com.example.mehndi.model.mehndi;
import com.example.mehndi.model.mehndiimg;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
  @GET("/v1/mehndi/mehndi_category_list")
  Call <mehndi> getData();
  @GET("/v1/mehndi/mehndi_post_list?")
  Call <ArrayList<mehndiimg>> design(@Query("category_id") String category_id);
}
