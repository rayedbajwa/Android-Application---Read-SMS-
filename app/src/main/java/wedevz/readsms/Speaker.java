package wedevz.readsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by hp on 4/21/2015.
 */
public class Speaker extends BroadcastReceiver {
    public static final String SMS_BUNDLE = "pdus";
    public static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    String smsMessageStr1 = "";
    String smsMessageStr2 = "";
    String smsMessageStr3 = "";String smsMessageStr = "";
String displayName="";
    String no=""; String str = "";
    SmsMessage[] msgs = null;
    String smsBody="";
    String displaysms="";
    String ContactName="";
    Boolean ipAdrs;
    Boolean ipAdrs1;
    //Context getcontext;
    SharedPreferences sharedPreferences;
@Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
     //getcontext=Tab2.getContext();
        sharedPreferences=context.getSharedPreferences("wedevz.readsms",context.MODE_PRIVATE);
      ipAdrs=sharedPreferences.getBoolean("1", true);



       ipAdrs1=sharedPreferences.getBoolean("2", true);



    if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get("pdus");


            msgs = new SmsMessage[sms.length];
            for (int i = 0; i < msgs.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);
                msgs[i] = SmsMessage.createFromPdu((byte[])sms[i]);
                str += msgs[i].getMessageBody().toString();
                str += "\n";
                no = msgs[i].getOriginatingAddress();
             smsBody = smsMessage.getMessageBody().toString();
                //Resolving the contact name from the contacts.
                Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(no));
                Cursor c = context.getContentResolver().query(lookupUri, new String[]{ContactsContract.Data.DISPLAY_NAME},null,null,null);
                try {
                    c.moveToFirst();
                   displayName = c.getString(0);
                   ContactName = displayName;
                    displaysms=   "SMS From: "+ContactName+
                            "\n" + smsBody + "\n";

                    smsMessageStr="New Message Recieved";
                    smsMessageStr1="New Message BY"+"\t"+ContactName+"\t";
                    smsMessageStr2="New Message Recieved"+"\t"+smsBody+"\t";
                    smsMessageStr3="New Message BY"+"\t"+ContactName+"\t"+smsBody;
                } catch (Exception e) {
                    // TODO: handle exception
                }finally{
                    c.close();
                }            }

            //this will update the UI with message
          //  Toast.makeText(context, displaysms, Toast.LENGTH_SHORT).show();
            Inbox inst =Inbox.instance();

            inst.updateList(displaysms);

            if((ipAdrs==false)&&(ipAdrs1==false))
            {

                Inbox.speakSMS(smsMessageStr);
                  }
            else
            if((ipAdrs==false)&&(ipAdrs1==true))
            {

                Inbox.speakSMS(smsMessageStr1);
            }
            else
            if((ipAdrs==true)&&(ipAdrs1==false))
            {

                Inbox.speakSMS(smsMessageStr2);
            }
            else
            if((ipAdrs==true)&&(ipAdrs1==true))
            {

                Inbox.speakSMS(smsMessageStr3);
            }

        }


    }
}

