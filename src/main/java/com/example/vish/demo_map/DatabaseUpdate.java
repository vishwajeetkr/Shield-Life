package com.example.vish.demo_map;

import android.util.Log;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;
import java.util.TimerTask;
import java.util.Date;

public class DatabaseUpdate extends TimerTask{
    //private MobileServiceClient mClient = new MobileServiceClient("https://shieldlife3.azurewebsites.net",this);
    private MobileServiceTable<EarthquakeData> mTable;

    public void run(){
        try {
            EarthquakeData data = new EarthquakeData();
            //item.Text = "Awesome item";
            //item.Id = "first one";

//            data.DataTime = "Thursday October 25 2018, 11:42:20 UTC";
//            data.Region = "4km NW of The Geysers, CA"
//            data.Magnitude = 1.6;
//            data.Depth = -0.3;
//            data.source = "USGS Feed";
            /*mTable = mClient.getTable(EarthquakeData.class);
            mTable.insert(data, new TableOperationCallback<EarthquakeData>() {
                @Override
                public void onCompleted(EarthquakeData earthquakeData, Exception e1, ServiceFilterResponse serviceFilterResponse) {
                    if (e1 == null) {
                        // Insert succeeded
                    } else {
                        Log.e("earthquake_insert_error", e1.toString());
                    }
                }


            });*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
