package com.example.api.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.api.databinding.FragmentGalleryBinding;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private static paralaapi API_SERVICE;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        final Button BtnConsultar= binding.btnApi;
        BtnConsultar.setOnClickListener(new View.OnClickListener() {
            final  TextView texto=binding.txtConsultar;
            final  TextView textoDos=binding.txtLength;
            final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            @Override
            public void onClick(View v) {
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);



                final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(logging);
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("https://catfact.ninja/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();

                API_SERVICE=retrofit.create(paralaapi.class);
                Call<comotuquieras> call=API_SERVICE.getElement();

                call.enqueue(new Callback <comotuquieras>() {
                    @Override
                    public void onResponse(Call<comotuquieras> call, Response<comotuquieras> response) {
                        try {
                            if(response.isSuccessful()){
                                comotuquieras elemento=response.body();
                                texto.setText(elemento.getFact());
                                textoDos.setText(elemento.getLength());
                            }
                        }catch (Exception ex){
                        }
                    }
                    @Override
                    public void onFailure(Call<comotuquieras> call, Throwable t) {
                    }
                });

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}