package pennapps2015.pennappsapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DrivingText extends Service {

    public int onStartCommand(Intent intent, int flags, int startId)
    {

        return 0;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Check for errors
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
