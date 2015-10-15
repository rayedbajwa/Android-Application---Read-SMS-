package wedevz.readsms;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.PhoneLookup;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.ads.*;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Locale;
/**
 * Created by hp on 4/21/2015.
 */
public class Inbox  extends Activity implements AdapterView.OnItemClickListener {


    private static Inbox inst;
BroadcastReceiver br;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;

    IntentFilter filter;
    Context context;
    Intent intent;
    static String ipAdrs="";
    static String ipAdrs1="";
    ArrayAdapter arrayAdapter;
    private static TextToSpeech ttobj;
    public static Inbox instance() {
        return inst;
    }


    @Override
    public void onStart() {
        super.onStart();
        inst = this;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PackageManager pm = getPackageManager();
        ComponentName compName =
                new ComponentName(getApplicationContext(),
                        Speaker.class);
        pm.setComponentEnabledSetting(
                compName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        setContentView(R.layout.inboxview);
        smsListView = (ListView) findViewById(R.id.SMSList);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);
        smsListView.setOnItemClickListener(this);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
         refreshSmsInbox();

        ttobj=new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR){
                            ttobj.setLanguage(Locale.UK);
                        }
                    }
                });

    }
    Cursor cursor; Cursor c; String contact;
    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);


        int        indexBody = smsInboxCursor.getColumnIndex("body");

        int      indexAddress = smsInboxCursor.getColumnIndex("address");



        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();

        do {
try {
try{
    Log.i("ERROR AT CREATE","number: "+smsInboxCursor.getString(indexAddress));

            Uri lookupUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(smsInboxCursor.getString(2)));
             c = contentResolver.query(lookupUri, new String[]{ PhoneLookup.DISPLAY_NAME},null,null,null);
    if(!(c==null))
    {
        c.moveToFirst();
        contact =c.getString(c.getColumnIndex(PhoneLookup.DISPLAY_NAME));
        Log.i("ERROR AT CREATE","Contact: "+contact);
    }

  }
catch(Exception e)
{
    Log.i("ERROR AT CREATE","number: Not found");
    contact= smsInboxCursor.getString(2);

}




    String str = "SMS From: " + contact +
            "\n" + smsInboxCursor.getString(indexBody) + "\n";
    arrayAdapter.add(str);
}catch(Exception e){
    Log.i("ERROR AT CREATE","adding Exception: "+e.getMessage());

}        } while (smsInboxCursor.moveToNext());

    }
    public static void speakSMS(String sms)
    {
        ttobj.speak(sms, TextToSpeech.QUEUE_FLUSH,null);


    }
    public void updateList(final String smsMessage) {
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        try {
            String[] smsMessages = smsMessagesList.get(pos).split("\n");
            String address = smsMessages[0];
            String smsMessage = "";
            for (int i = 1; i < smsMessages.length; ++i) {
                smsMessage += smsMessages[i];
            }

            String smsMessageStr = address + "\n";
            smsMessageStr += smsMessage;
            Toast.makeText(this, smsMessageStr, Toast.LENGTH_SHORT).show();
            speakSMS(smsMessageStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}