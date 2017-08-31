package com.example.android.developersinformation;

import android.content.Context;

import java.util.List;

public class DeveloperLagosLoader extends android.support.v4.content.AsyncTaskLoader<List<DeveloperLagos>> {

    private String mUrl;

    public DeveloperLagosLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading(){
            forceLoad();
    }

    @Override
    public List<DeveloperLagos> loadInBackground() {
        if(mUrl == null){
            return null;
        }
        List<DeveloperLagos> result = Developer_Utils.fetchDeveloperLagosData(mUrl);
        return result;
    }
}
