package wedevz.readsms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by hp on 5/6/2015.
 */
public class Splash extends Activity {

    // Splash screen timer
    // time to display the splash screen in ms
    protected boolean _active = true;
    protected int _splashTime = 3000; // time to display the splash screen in ms

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (Exception e) {

                } finally {

                    startActivity(new Intent(Splash.this,
                            MainActivity.class));
                    finish();
                }
            };
        };
        splashTread.start();
    }
}