package com.example.api.ui.gallery;

import retrofit2.Call;
import retrofit2.http.GET;

public interface paralaapi{

    String API_ROUTE ="/fact";
    @GET(API_ROUTE)
    Call<comotuquieras> getElement();


}
