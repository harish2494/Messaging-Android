package test.messaging.harish.messaging;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SMSGroup extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsgroup);
        Intent intent = getIntent();
        ArrayList<String> myList = (ArrayList<String>)intent.getSerializableExtra("messages");
        String sender = intent.getExtras().getString("sender");
        setTitle(sender);
        ListView list = (ListView) findViewById(R.id.messagelistView);
        list.setAdapter(new ArrayAdapter<String>(SMSGroup.this,
                android.R.layout.simple_list_item_1, myList));
    }
}
