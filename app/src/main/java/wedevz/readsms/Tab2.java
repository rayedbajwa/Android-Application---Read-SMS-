package wedevz.readsms;

/**
 * Created by hp on 4/20/2015.
 */

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Tab2 extends Activity {

    /**
     * Called when the activity is first created.
     */
    Switch sw2,sw1;
    static Context c;
    Button b1;
    BroadcastReceiver br;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = getApplicationContext();
        setContentView(R.layout.tab2);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        b1=(Button) findViewById(R.id.button1);
        sw1 = (Switch) findViewById(R.id.switch1);
        SharedPreferences sharedPrefs2 = getSharedPreferences("wedevz.readsms", MODE_PRIVATE);
        sw1.setChecked(sharedPrefs2.getBoolean("1", true));
        sw2 = (Switch) findViewById(R.id.switch2);
        SharedPreferences sharedPrefs1 = getSharedPreferences("wedevz.readsms", MODE_PRIVATE);
        sw2.setChecked(sharedPrefs1.getBoolean("2", true));
        sw1.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor1 = getSharedPreferences("wedevz.readsms", MODE_PRIVATE).edit();
                    editor1.putBoolean("1", true);
                    editor1.commit();
//sw1.setChecked(true);
                } else {
                    SharedPreferences.Editor editor1 = getSharedPreferences("wedevz.readsms", MODE_PRIVATE).edit();
                    editor1.putBoolean("1", false);
                    editor1.commit();

//sw1.setChecked(false);
                }
            }
        });

        sw2.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences("wedevz.readsms", MODE_PRIVATE).edit();
                    editor.putBoolean("2", true);
                    editor.commit();
//            sw2.setChecked(true);
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("wedevz.readsms", MODE_PRIVATE).edit();
                    editor.putBoolean("2", false);
                    editor.commit();
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    PackageManager pm = getPackageManager();
                    ComponentName compName =
                            new ComponentName(getApplicationContext(),
                                    Speaker.class);
                    pm.setComponentEnabledSetting(
                            compName,
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);

                }
                catch (Exception ie){

                    Log.i("ERROR AT CREATE", "LOL");
                }}
        });

    }
public static Context getContext ()
{

    return c;
}

}