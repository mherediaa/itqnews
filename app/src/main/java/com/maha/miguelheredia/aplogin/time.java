package com.maha.miguelheredia.aplogin;

import android.content.Context;
import android.os.AsyncTask;

public class time extends AsyncTask <Void, Integer, Boolean>{
    public Context context;

    public time(Context c){
        this.context=c;
    }
    @Override
    protected Boolean doInBackground(Void... params){
        try{
            Thread.sleep(2500); //1000=1seg
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void  onPostExecute(Boolean b){
        new myRestful(context,"update").execute();
    }
}
