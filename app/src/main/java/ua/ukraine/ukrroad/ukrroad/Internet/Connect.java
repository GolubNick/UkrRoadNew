package ua.ukraine.ukrroad.ukrroad.Internet;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Connect {
    private List<OnInternetListener> listeners = new ArrayList<OnInternetListener>();
    private Context mContext;
    Timer timer;
    private boolean connect = true;

    public Connect(Context context){
        mContext = context;
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if(connect && !Utils.isOnline(mContext)){
                    connect = false;
                    callDisconnectListeners();
                }
                if(!connect && Utils.isOnline(mContext)){
                    connect = true;
                    callConnectListeners();
                }
            }
        }, 0, 2000);
    }

    public void addListener(OnInternetListener listener){
        listeners.add(listener);
    }

    private void callDisconnectListeners(){
        for (OnInternetListener listener : listeners) {
            listener.onDisconnect();
        }
    }

    private void callConnectListeners(){
        for (OnInternetListener listener : listeners) {
            listener.onConnect();
        }
    }

    public void stopTimer() {
        timer.cancel();
    }
}
