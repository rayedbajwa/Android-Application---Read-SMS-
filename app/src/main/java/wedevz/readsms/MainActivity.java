package wedevz.readsms;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.app.TabActivity;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

public class MainActivity extends TabActivity implements OnTabChangeListener{

    /** Called when the activity is first created. */
    TabHost tabHost;
BroadcastReceiver br;
     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         // Get TabHost Refference
        tabHost = getTabHost();

        // Set TabChangeListener called when tab changed
        tabHost.setOnTabChangedListener(this);

        TabHost.TabSpec spec;
        Intent intent,intent3;

        /************* TAB1 ************/
        // Create  Intents to launch an Activity for the tab (to be reused)



        //Add intent to tab

        intent3= new Intent().setClass(this, Inbox.class);
        spec = tabHost.newTabSpec("Inbox").setIndicator("Inbox")
                .setContent(intent3);
        tabHost.addTab(spec);

        /************* TAB2 ************/
        intent = new Intent().setClass(this, Tab2.class);
        spec = tabHost.newTabSpec("Inbox Settings").setIndicator("Settings")
                .setContent(intent);

        tabHost.addTab(spec);

        /************* Button ************/



        // Set drawable images to tab


        // Set Tab1 as Default tab and change image
        tabHost.getTabWidget().setCurrentTab(0);


    }

    @Override
    public void onTabChanged(String tabId) {

        /************ Called when tab changed *************/

        //********* Check current selected tab and change according images *******/

        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            if(i==0)
               tabHost.getTabWidget().getChildAt(i);
            else if(i==1)
              tabHost.getTabWidget().getChildAt(i);
        }


        Log.i("tabs", "CurrentTab: "+tabHost.getCurrentTab());

        if(tabHost.getCurrentTab()==0)
            tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());
        else if(tabHost.getCurrentTab()==1)
            tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());


    }

}