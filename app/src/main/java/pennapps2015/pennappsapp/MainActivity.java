package pennapps2015.pennappsapp;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Switch s = (Switch) view;
        final Intent drivingService = new Intent(this, DrivingText.class);
        if (s.isChecked()) {
            System.out.println("This switch is checked");
            //binds intent to service
            new Thread(new Runnable() {
                public void run() {
                    new Thread(new Runnable() {
                        public void run() {
                            startService(drivingService);
                        }
                    }).start();
                }
            }).start();
        } else {
            stopService(drivingService);
        }
        DrivingText dt = new DrivingText();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Switch toggle = (Switch) findViewById(R.id.onSwitch);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView onOffMsg = (TextView) findViewById(R.id.onOffText);
                if (isChecked) {
                    //Log.v("TAG", "on");
                    onOffMsg.setText("Automatic reply active");
                } else {
                    //Log.v("TAG", "off");
                    onOffMsg.setText("Automatic reply OFF");
                }
            }
        });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                //call fct.
                Log.d("Tag", "in onLocationChanged");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(MainActivity.this,
                        "Provider Enabled: " + provider, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(MainActivity.this,
                        "Provider Disabled: " + provider, Toast.LENGTH_SHORT)
                        .show();
            }
        };
        long minTime = 2 * 1000; //minimum time interval for update in seconds, i.e. 2 seconds
        long minDistance = 1; //minimum distance interval for update in meters, i.e. 1 meters

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return true;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, locationListener);

        return true;

    }
    }
