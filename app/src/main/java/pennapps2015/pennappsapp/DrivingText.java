package pennapps2015.pennappsapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class DrivingText extends Service {

    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //SMS event receiver
        mSMSreceiver = new SMSReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        return 0;

    }

    private SMSReceiver mSMSreceiver;
    private IntentFilter mIntentFilter;
    private boolean receiverAlive = false;

    public class SMSReceiver extends BroadcastReceiver {
        public SmsMessage messages[] = null;

        private void showNotification(Context context, String sms) {
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, MainActivity.class), 0);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setContentTitle("My notification")
                            .setContentText(sms);
            mBuilder.setContentIntent(contentIntent);

            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            sendSMS("2673578751", "This is test");
            receiverAlive = false;
            mSMSreceiver = new SMSReceiver();
            mIntentFilter = new IntentFilter();
            mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
            registerReceiver(mSMSreceiver, mIntentFilter);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Check for errors
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void sendSMS(String phoneNumber, String message)
    {
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);
    }
}

