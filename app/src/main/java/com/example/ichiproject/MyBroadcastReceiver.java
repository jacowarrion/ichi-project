package com.example.ichiproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private MainActivity activity;

    /**
     * @param manager  WifiP2pManager system service
     * @param channel  Wifi p2p channel
     * @param activity activity associated with the receiver
     */
    public MyBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                               MainActivity activity) {
        super();
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        WifiP2pManager.PeerListListener myPeerListListener = wifiP2pDeviceList -> {
            System.out.println(wifiP2pDeviceList);

            WifiP2pDevice device = wifiP2pDeviceList.getDeviceList().iterator().next();
            WifiP2pConfig config = new WifiP2pConfig();
            config.deviceAddress = device.deviceAddress;
            manager.connect(channel, config, new WifiP2pManager.ActionListener() {

                @Override
                public void onSuccess() {
                    //success logic
                    System.out.println("YATTA");
                    ServerSocket serverSocket = null;
                    try {
                        serverSocket = new ServerSocket(8888);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Socket client = serverSocket.accept();
                        client.getOutputStream().write("HELLO!".getBytes(StandardCharsets.UTF_8)); // or UTF-8 or any other applicable encoding..
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int reason) {
                    //failure logic
                    System.out.println("WOMP WOMP WOMP");
                }
            });

        };
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
            } else {
                // Wi-Fi P2P is not enabled
            }
        }

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // request available peers from the wifi p2p manager. This is an
            // asynchronous call and the calling activity is notified with a
            // callback on PeerListListener.onPeersAvailable()
            if (manager != null) {
                manager.requestPeers(channel, myPeerListListener);
            }

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
        }


    }


}
