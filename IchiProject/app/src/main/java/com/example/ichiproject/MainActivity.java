package com.example.ichiproject;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ichiproject.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    WifiP2pManager manager;
    WifiP2pManager.Channel channel;
    BroadcastReceiver receiver;

    IntentFilter intentFilter;

    public static GameManager turnManager = new GameManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("STARTO");
        initP2p();
        findViewById(R.id.server).setOnClickListener(view -> {
            System.out.println("CLICKKK");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {

                @Override
                public void onSuccess() {
                    System.out.println("SIUMM");
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                    manager.requestPeers(channel, System.out::println);
                }

                @Override
                public void onFailure(int reasonCode) {
                    System.out.println("NAHHHH - " + reasonCode);
                }

            });
        });
    }

    /* register the broadcast receiver with the intent values to be matched */
    @Override
    public void onResume() {
        super.onResume();
        //registerReceiver(receiver, intentFilter);
    }

    /* unregister the broadcast receiver */
    @Override
    public void onPause() {
        super.onPause();
        //unregisterReceiver(receiver);
    }

    private void initP2p() {
        manager = (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        receiver = new MyBroadcastReceiver(manager, channel, this);

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

}