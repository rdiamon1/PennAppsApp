package pennapps2015.pennappsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        return super.onOptionsItemSelected(item);
    }
}
