package test.messaging.harish.messaging;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setTitle("Results");
        Intent intent = getIntent();
        ArrayList<SMSData> myList = (ArrayList<SMSData>)intent.getSerializableExtra("results");
        if(myList.size() == 0){
            TextView tv = (TextView) findViewById(R.id.noResult);
            tv.setVisibility(View.VISIBLE);
        }
        final ArrayList<SMSData> smsList = (ArrayList<SMSData>)intent.getSerializableExtra("all");
        ListView list = (ListView) findViewById(R.id.resultlistView);
        ListAdapter adp = new ListAdapter(getApplicationContext(),myList );
        list.setAdapter(adp);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.sNo);

                Intent appInfo = new Intent(getApplicationContext(), SMSGroup.class);
                appInfo.putExtra("sender", textView.getText().toString());
                appInfo.putExtra("messages", (Serializable) smsList.get(ListActivity.checkExist(textView.getText().toString(), (ArrayList<SMSData>) smsList)).getBody());
                startActivity(appInfo);
            }
        });
    }

}
