package com.maha.miguelheredia.aplogin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Switch;

import com.maha.miguelheredia.aplogin.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Miguel Heredia on 03/10/2017.
 */

public class myRestful extends AsyncTask<Void, Void, String[][]>{
    private String HTTP_RESTFUL;
    //private String url_p = "http://catalogoin.com/itq/";
    private String url_p = "http://sieteanueveitq.xyz/noticias/";
    private ProgressDialog progressDialog;
    private Context context;
    private String info;
    private String pass, usr;

    public myRestful(Context c, String info){
        this.context=c;
        this.info=info;
        HTTP_RESTFUL=getURL(info);
    }

    public myRestful(Context c, String info, String usr, String pass){
        this.context=c;
        this.info=info;
        this.usr=usr;
        this.pass=pass;
        HTTP_RESTFUL=getURL(info);
    }



    public String getURL(String info){
        switch(info)
        {
            case "registro":
                return url_p+"control_login.php?action=registro&usr="+usr+"&pass="+pass;
            case "login":
                return url_p+"control_login.php?action=login&usr="+usr+"&pass="+pass;
            case "update":
                return url_p+"control_login.php?action="+"update";
            default:
                return url_p+"control_login.php?action=error";

        }
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog=ProgressDialog.show(context, "Espere..","Procesando...");
    }

    @Override
    protected String[][] doInBackground(Void... arg0 ){
        String [][] resul = getRestFul();
        progressDialog.dismiss();
        return resul;
    }

    private StringBuilder inputStreamToString(InputStream is){
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try{
            while ((line=rd.readLine())!=null) {
                stringBuilder.append(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return stringBuilder;
    }

    public String[][] getRestFul(){
        String jsonResult="";
        String[][] list3=new String[1][2];
        try{
            URL url= new URL(HTTP_RESTFUL);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", "App para RESTFUL");
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream= connection.getInputStream();
            Log.d("url","url:"+HTTP_RESTFUL); //Obtiene la respuesta del servidor
            jsonResult= inputStreamToString(inputStream).toString();
            Log.d("resuljason","este d"+jsonResult); //Localiza la estrcutura json
            int pos=jsonResult.indexOf('{');
            int pos2=jsonResult.lastIndexOf('}');
            if(pos==-1){
                list3[0][0]="0";
                return list3;
            }
            String jsnres=jsonResult.substring(pos,pos2+1);
            JSONObject object=new JSONObject(jsnres); //Obtiene el estatus
            String status = object.getString("status");
            list3[0][0]=status;
            if(status.equals("50"))
            {
                //Extrae los registros
                JSONArray array = new JSONArray((object.getString("Registros")));
                list3=new String[array.length()+1][5];
                for(int i=1; i<=array.length();i++)
                {
                    //recorre cada registro y concatena el resultado
                    JSONObject row = array.getJSONObject(i-1);
                    switch (this.info)
                    {
                        case "login":
                        case "registro":
                            list3[0][0]=status;
                            list3[0][1]=row.getString("respuesta");
                            break;
                        case "update":
                            list3[0][0]=status;
                            list3[0][1]=row.getString("respuesta");
                            list3[0][2]=row.getString("nombre");
                            break;
                    }
                }
                return list3;
            }
        }catch (IOException e){
            list3[0][0]=e.getMessage();
            e.printStackTrace();
        }catch (JSONException e){
            list3[0][0] = e.getMessage();
            e.printStackTrace();
        }
        return list3;
    }

    protected void onPostExecute (String resul[][]){
        if (resul[0][0].equals("50")){
            switch (this.info){
                case "login":
                    ((MainActivity)context).entrar();
                    break;
                case "registro":
                    ((MainActivity)context).mensaje("Regitro OK");
                    ((MainActivity)context).registroOk();
                    break;
                //case "update":
                    //((MainActivity)context).notificacion(2,"nuevo usuario");
                    //new time(context).execute();
                    //break;
            }
        }
        else
            //if(info.equals("update"))
                //new time(context).execute();
            //else
                ((MainActivity)context).mensaje("Error, verifique sus datos");
    }
}
