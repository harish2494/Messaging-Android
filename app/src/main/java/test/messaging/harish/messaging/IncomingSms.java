package test.messaging.harish.messaging;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
/**
 * Created by vinayagar on 6/5/2016.
 */

public class IncomingSms extends BroadcastReceiver {

    final SmsManager sms = SmsManager.getDefault();
    public static final String SMS_EXTRA_NAME ="pdus";

    public void onReceive(Context context, Intent intent) {


        Bundle extras = intent.getExtras();

        String messages = "";

        if ( extras != null ) {
            Object[] smsExtra = (Object[]) extras.get(SMS_EXTRA_NAME);

            String address = "", body;
            for (int i = 0; i < smsExtra.length; ++i) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsExtra[i]);

                body = sms.getMessageBody().toString();
                address = sms.getOriginatingAddress();

                messages += "SMS from " + address + " :\n";
                messages += body + "\n";
            }

            NotificationManager nm = (NotificationManager) context.getSystemService(
                    Context.NOTIFICATION_SERVICE);
            Intent i= new Intent(context, MainActivity.class);

            PendingIntent intent2 = PendingIntent.getActivity(context, 0,i
                    , PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder n = new Notification.Builder(context)
                    .setContentTitle( address + " has sent a message")
                    .setContentText("A new message has been received from "+ address)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.sms);
            n.setContentIntent(intent2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                nm.notify(0, n.build());
            }

        }
    }
}
