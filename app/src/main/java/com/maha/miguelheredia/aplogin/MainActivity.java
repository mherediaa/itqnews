package com.maha.miguelheredia.aplogin;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public EditText usr, pass, usrReg, passReg;
    public Button btnIngresar, btnReg, btnAbout;
    public CheckBox chk;
    public int idn=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usr = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        usrReg = (EditText) findViewById(R.id.editText3);
        passReg=(EditText) findViewById(R.id.editText4);
        btnIngresar = (Button) findViewById(R.id.button);
        btnReg= (Button) findViewById(R.id.button2);
        btnAbout=(Button) findViewById(R.id.button3);
        chk = (CheckBox) findViewById(R.id.checkBox);

        usrReg.setVisibility(View.GONE);
        passReg.setVisibility(View.GONE);
        btnReg.setVisibility(View.GONE);
        new time(this).execute();
    }

    public void ingresar(View view) {
        String usr1 = usr.getText().toString();
        String pass1 = pass.getText().toString();
        mensaje("usr:"+usr1+"\npassword:"+pass1);
        //new myRestful (this, "reg").execute();
        new myRestful(this, "login", usr1, pass1).execute();
    }

    public void entrar(){
        notificacion(idn++, "¡Bienvenido!");
        Intent intent = new Intent(this,principal.class);
        startActivity(intent);
    }

    public void mensaje(String sms) {
        Toast.makeText(this,sms,Toast.LENGTH_SHORT).show();
    }

    public void verRegistro(View view) {
        if(chk.isChecked()){
            usr.setVisibility(View.GONE);
            pass.setVisibility(View.GONE);
            btnIngresar.setVisibility(View.GONE);
            usrReg.setVisibility(View.VISIBLE);
            passReg.setVisibility(View.VISIBLE);
            btnReg.setVisibility(View.VISIBLE);
        }
        else{
            usr.setVisibility(View.VISIBLE);
            pass.setVisibility(View.VISIBLE);
            btnIngresar.setVisibility(View.VISIBLE);
            usrReg.setVisibility(View.GONE);
            passReg.setVisibility(View.GONE);
            btnReg.setVisibility(View.GONE);
        }
    }

    public void registro(View view) {
        String usr=usrReg.getText().toString();
        String pas=passReg.getText().toString();
        new myRestful(this,"registro",usr,pas).execute();
    }

    public void registroOk(){
        usrReg.setText("");
        passReg.setText("");
        chk.setChecked(false);
        verRegistro(chk);
    }

    public void notificacion(int id, String titulo){
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSound(sound);
        builder.setContentTitle(titulo);
        builder.setSubText("Inicio de sesion correcto");
        builder.setContentText("Acabas de Ingresar");
        builder.setStyle(new Notification.BigTextStyle().bigText("Ingresaste al mural digital del ITQ"));
        builder.setVibrate(new long[] {100});
        builder.setSmallIcon(R.drawable.notif);
        Notification n = builder.build();
        NotificationManager notifManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        notifManager.notify(id,n);

    }

    public void acerca(View view) {
        mensaje("Contacto: AIPMJ@itqnews.edu.mx");
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSound(sound);
        builder.setContentTitle("¿Quieres ver tu noticia aquí?");
        builder.setStyle(new Notification.BigTextStyle().bigText("Puedes contactarnos a través de: AIPMJ@itqnews.edu.mx"));
        builder.setVibrate(new long[] {100});
        builder.setSmallIcon(R.drawable.ic_help_black_24px);
        Notification n = builder.build();
        NotificationManager notifManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        notifManager.notify(5,n);
    }
}
