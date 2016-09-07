package antuone.internetlooper;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.Toast;

public class ActivityMain extends AppCompatActivity {
    Handler handler;
    Runnable runOn;
    Runnable runOff;
    int timeOff;
    int timeOn;
    Toast toast;
    WifiManager Wifi;
    NumberPicker numberPicker;
    NumberPicker numberPicker2;
    public void alert(String text) {
        toast.setText(text);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toast = Toast.makeText(ActivityMain.this, "=)", Toast.LENGTH_SHORT);
        Wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        handler = new Handler();
        runOn = new Runnable() {
            @Override
            public void run() {
                wifiOn();
            }
        };
        runOff = new Runnable() {
            @Override
            public void run() {
                wifiOff();
            }
        };

        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(1);

        numberPicker2 = (NumberPicker) findViewById(R.id.numberPicker2);
        numberPicker2.setMaxValue(60);
        numberPicker2.setMinValue(10);
        numberPicker2.setValue(15);
        Switch switch1 = (Switch) findViewById(R.id.switch1);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    start();
                } else {
                    stop();
                }
            }
        });
    }

    private void start() {
        timeOn = numberPicker2.getValue() * 1000;
        timeOff = numberPicker.getValue() * 60000;
        wifiOn();
    }
    private void stop() {
        alert("Остановливаю");
        handler.removeCallbacks(runOn);
        handler.removeCallbacks(runOff);
    }
    private void wifiOn() {
        alert("Включаю WiFi на " + timeOn/1000 + " секунд");
        Wifi.setWifiEnabled(true);
        handler.postDelayed(runOff, timeOn);
    }
    private void wifiOff() {
        alert("Выключаю WiFi на " + timeOff/1000/60 + " минут");
        Wifi.setWifiEnabled(false);
        handler.postDelayed(runOn, timeOff);
    }
}
