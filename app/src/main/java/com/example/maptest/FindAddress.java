package com.example.maptest;

import android.os.AsyncTask;

import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;

public class FindAddress extends AsyncTask<Object, String, String> {

    String googleAddressData;
    String url;
    MapsViewModel mapsViewModel = new ViewModelProvider(requireActivity()).get(MapsViewModel.class);

    @Override
    protected String doInBackground(Object... objects) {

        url = (String) objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();
        try{
            googleAddressData = downloadUrl.readUrl(url);
        }catch(IOException e){
            e.printStackTrace();
        }

        return googleAddressData;
    }


    @Override
    protected void onPostExecute(String s){
        if(s != null){

        }
        else{
            System.out.println("sorry did find it");
        }

    }


}
