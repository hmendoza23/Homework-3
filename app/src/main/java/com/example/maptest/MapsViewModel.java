package com.example.maptest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapsViewModel extends ViewModel {

    private MutableLiveData<String> addressData;

    public MapsViewModel(){
        super();

        addressData = new MutableLiveData<>();
    }

    public void postAddressData(String data){
        addressData.postValue(data);
    }

    public LiveData<String> getAddressData(){  return  addressData;  }
}
