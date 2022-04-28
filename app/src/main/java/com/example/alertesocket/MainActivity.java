package com.example.alertesocket;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    TextView tvMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMessages = findViewById(R.id.tvMessages);
        // new Thread(new ClientThread()).start();
        Thread myThread = new Thread(new ClientThread());
        myThread.start();

    }


    class ClientThread implements Runnable {
        Socket client;
        BufferedReader istream;
        Handler h = new Handler();
        String message;

        @Override
        public void run() {
            try {
                client = new Socket("192.168.8.100", 8080);
                Log.e("TAG", "connected: ");
                // tvMessages.setText("Connected\n");
                istream = new BufferedReader(new InputStreamReader(client.getInputStream()));
                message = istream.readLine();

                h.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        //  tvMessages.append("server: " + message + "\n");
                    }
                });
                istream.close();
                client.close();
            } catch (UnknownHostException e) {
                Log.e("TAG", "run: ", e);
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("TAG", "run: ", e);
            }
        }
    }
}