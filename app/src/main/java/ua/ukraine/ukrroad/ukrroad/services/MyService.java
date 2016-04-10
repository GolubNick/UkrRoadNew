package ua.ukraine.ukrroad.ukrroad.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.sql.SQLException;

import ua.ukraine.ukrroad.ukrroad.Internet.Connect;
import ua.ukraine.ukrroad.ukrroad.Internet.OnInternetListener;
import ua.ukraine.ukrroad.ukrroad.database.table.Issue;
import ua.ukraine.ukrroad.ukrroad.helpers.HelperFactory;
import ua.ukraine.ukrroad.ukrroad.helpers.UploadIssueToServer;

public class MyService extends Service{
    Issue issue;
    boolean busy;
    Connect connect;

    final String LOG_TAG = "myLogs";

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        try {
            issue = HelperFactory.getHelper().getIssueDAO().getIssueById(intent.getExtras().getInt("isIssue"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        someTask(issue);
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        connect.stopTimer();
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind");
        return null;
    }

    void someTask(final Issue issue) {
        connect = new Connect(getApplicationContext());
        connect.addListener(new OnInternetListener() {
            @Override
            public void onDisconnect() {
                Log.d(LOG_TAG, "disconnect");

            }

            @Override
            public void onConnect() {
                try {
                    new UploadIssueToServer(getApplicationContext()) {
                        @Override
                        public void getResult(String result) {
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        }
                    }.execute(issue).get();
                    stopSelf();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(LOG_TAG, "connect");
            }
        });
    }
}
