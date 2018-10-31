package com.example.vish.demo_map;


import java.util.Random;
//import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
//import com.example.vish.demo_fucking_map_map.R;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.MobileServiceActivityResult;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.MalformedURLException;

//import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.*;
public class MainActivity extends Activity {
    private MobileServiceClient mClient;
    private MobileServiceTable<TodoItem> mTable;
    private MobileServiceTable<EarthquakeData> qTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startActivity(new Intent(this, MapsActivity.class));

        //Microsoft.WindowsAzure.MobileServices.CurrentPlatform.Init();
        try {
            mClient = new MobileServiceClient("https://shieldlife3.azurewebsites.net",this);
            /*final TodoItem item = new TodoItem();
            item.Text = "Awesome item";
            //item.Id = "first one";
            mTable = mClient.getTable(TodoItem.class);
            mTable.insert(item, new TableOperationCallback<TodoItem>() {
                public void onCompleted(TodoItem entity, Exception exception, ServiceFilterResponse response) {
                    if (exception == null) {
                        // Insert succeeded
                    } else {
                        // Insert failed
                    }
                }
            });
            refreshTable();*/
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        testFunction();
    }

    private void testFunction() {
        EarthquakeData data = new EarthquakeData();
        //item.Text = "Awesome item";
        //item.Id = "first one";*/

        data.Data = "Thursday October 25 2018";
        data.Time = "11:42:20 UTC";
        data.Region = "iit madras";
        data.Magnitude = 3.6;
        data.Depth = -0.3;
        data.Source = "USGS Feed";
        qTable = mClient.getTable(EarthquakeData.class);
        qTable.insert(data, new TableOperationCallback<EarthquakeData>() {
            @Override
            public void onCompleted(EarthquakeData earthquakeData, Exception e1, ServiceFilterResponse serviceFilterResponse) {
                if (e1 == null) {
                    // Insert succeeded
                } else {
                    Log.e("earthquake_insert_error", e1.toString());
                }
            }


        });
        //qTable.select(Region, Magnitude);
        //qTable.
    }

    private void refreshTable() {
    }

    public void currentLocationMap(View view) {
        EditText text = (EditText)findViewById(R.id.location_input);
        Intent mapIntent = new Intent(this, MapsActivity.class);
        mapIntent.putExtra("addString", "mylocation");
        startActivity(mapIntent);
    }

    public void searchLocationMap(View view) {
        Intent mapIntent = new Intent(this, MapsActivity.class);
        TodoItem item = new TodoItem();
        EditText location = (EditText)findViewById(R.id.location_input);
        Random rand = new Random();

        // Generate random integers in range 0 to 999
        int rand_int = rand.nextInt(1000);
        item.Text = location.getText().toString();
        //item.Id = Integer.toString(rand_int);
        mTable = mClient.getTable(TodoItem.class);
        mTable.insert(item, new TableOperationCallback<TodoItem>() {
            public void onCompleted(TodoItem entity, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    // Insert succeeded
                } else {
                    Log.e("insert_error", exception.toString());
                }
            }
        });
        mapIntent.putExtra("addString", location.getText().toString().replace(",", " "));
        startActivity(mapIntent);
    }

    private void refreshItemsFromTable() {

//		TODO Uncomment the the following code when using a mobile service
//		// Get the items that weren't marked as completed and add them in the adapter
	    new AsyncTask<Void, Void, Void>() {


	        @Override
	        protected Void doInBackground(Void... params) {
	            try {
	                final MobileServiceList<EarthquakeData> result = qTable.execute().get(); //where().field("complete").eq(false).
	                runOnUiThread(new Runnable() {

	                    @Override
	                    public void run() {
	                        //mAdapter.clear();

	                        for (EarthquakeData item : result) {
	                            //mAdapter.add(item)
                                String strReg = item.Region;
                                long mag = (long) item.Magnitude;
	                        }
	                    }
	                });
	            } catch (Exception exception) {
	                //createAndShowDialog(exception, "Error");
                    Log.e("error retrieve", "error in blah blah");
	            }
	            return null;
	        }
	    }.execute();
    }
}
