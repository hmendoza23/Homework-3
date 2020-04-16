package com.example.maptest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

public class MapsViewModel extends ViewModel {

    private MutableLiveData<HashMap<String, String>> addressData;

    public MapsViewModel(){
        super();

        addressData = new MutableLiveData<>();
    }

    public void postAddressData(HashMap<String, String> data){
        addressData.postValue(data);
    }

    public LiveData<HashMap<String, String>> getAddressData(){  return  addressData;  }
}
