package test.messaging.harish.messaging;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created by vinayagar on 6/5/2016.
 */

public class ListActivity extends ActionBarActivity {
    test.messaging.harish.messaging.ListAdapter adapter;
    public static int checkExist(String address, ArrayList<SMSData> list){
        for(int i=0;i<list.size();i++){
            SMSData sm = list.get(i);
            if(sm.getNumber().toString().equals(address)){
                return i;
            }
        }
        return -1;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title);
        setTitle("MESSAGES");
        final List<SMSData> smsList = new ArrayList<SMSData>();
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        int index;
        final ListView lv = (ListView) findViewById(R.id.listView);
        if (cursor.moveToFirst()) {
            do {
                for (int i = 0; i < cursor.getCount(); i++) {
                    SMSData sms = new SMSData();
                    index = checkExist(cursor.getString(cursor.getColumnIndexOrThrow("address")).toString(), (ArrayList<SMSData>) smsList);
                    if(index == -1){
                        sms.addBody(cursor.getString(cursor.getColumnIndexOrThrow("body")).toString());
                        sms.setNumber(cursor.getString(cursor.getColumnIndexOrThrow("address")).toString());
                        Date date = new Date(cursor.getLong(cursor.getColumnIndexOrThrow("date")));
                        String formattedDate = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(date);
                        sms.setDate(formattedDate);
                        smsList.add(sms);
                    } else{
                        SMSData old  = smsList.get(index);
                        old.addBody(cursor.getString(cursor.getColumnIndexOrThrow("body")).toString());
                        smsList.set(index, old);
                    }

                    cursor.moveToNext();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter = new test.messaging.harish.messaging.ListAdapter(this, smsList);
        lv.setAdapter(adapter);
        lv.setFocusable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.sNo);

                Toast.makeText(getApplicationContext(), textView.getText(), Toast.LENGTH_LONG).show();
                Intent appInfo = new Intent(getApplicationContext(), SMSGroup.class);
                appInfo.putExtra("sender",textView.getText().toString());
                appInfo.putExtra("messages", (Serializable) smsList.get(checkExist(textView.getText().toString(), (ArrayList<SMSData>) smsList)).getBody());
                startActivity(appInfo);
            }
        });
    }

    public void serachResult(View v){
        EditText etSearch = (EditText) findViewById(R.id.etSearch);
        Filter f = adapter.getFilter();
        f.filter(etSearch.getText().toString());
        etSearch.setText("");
    }
}