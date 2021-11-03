package co.edu.unipiloto.started_services;

import androidx.core.app.NotificationCompat;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;


public class DelayedMessageService extends IntentService {
public static final String EXTRA_MESSAGE = "mensaje";
public static final int NOTIFICACION_ID = 5453;

    public DelayedMessageService(){super("DelayedMessageService");}

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this){
            try{
                wait(1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            String texto = intent.getStringExtra(EXTRA_MESSAGE);
            mostrarMensaje(texto);
        }
    }

    public void mostrarMensaje(String mensaje){

        Log.v("DelayedMessageService", "Mensaje de servicio activo " + mensaje);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).
                setSmallIcon(android.R.drawable.sym_def_app_icon).
                setContentTitle(getString(R.string.pregunta)).
                setContentText(mensaje).
                setPriority(NotificationCompat.PRIORITY_HIGH).
                setVibrate(new long[]{0,1000}).
                setAutoCancel(true);

        Intent actionIntent = new Intent(this,MainActivity.class);
        PendingIntent actionPendingIntent = PendingIntent.getActivity(this,0,actionIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(actionPendingIntent);


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICACION_ID,builder.build());

    }
}