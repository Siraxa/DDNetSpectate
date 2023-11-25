package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.AsyncTask;
import java.net.*;
import java.io.*;
import com.example.myapplication.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
        System.loadLibrary("node");
    }
    public static boolean _startedNodeAlready=false;    //We just want one instance of node running in the background.
    private ActivityMainBinding binding;
    private Socket mSocket;

    private MessageAdapter messageAdapter;
    private RecyclerView messagesView;
    private List<Message> messages = new ArrayList<>();
    private Server selectedServer;

    private WebView myWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //starts node js
        if( !_startedNodeAlready ) {
            _startedNodeAlready=true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //The path where we expect the node project to be at runtime.
                    String nodeDir=getApplicationContext().getFilesDir().getAbsolutePath()+"/nodejs-project";
                    if (wasAPKUpdated()) {
                        //Recursively delete any existing nodejs-project.
                        File nodeDirReference=new File(nodeDir);
                        if (nodeDirReference.exists()) {
                            deleteFolderRecursively(new File(nodeDir));
                        }
                        //Copy the node project from assets into the application's data path.
                        copyAssetFolder(getApplicationContext().getAssets(), "nodejs-project", nodeDir);

                        saveLastUpdateTime();
                    }
                    startNodeWithArguments(new String[]{"node",
                            nodeDir+"/main.js"
                    });
                }
            }).start();
        }




        //Connect button
        final Button buttonVersions = (Button) findViewById(R.id.btVersions);
        buttonVersions.setOnClickListener(v -> {
            if (selectedServer != null) {
                JSONObject communicationData = new JSONObject();
                try {
                    // Předpokládá se, že Server má metody pro získání IP a portu
                    communicationData.put("address", selectedServer.getAddresses());
                    mSocket.emit("communication", communicationData);

                    switchToIngameView();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                //ask for masters
                try {
                    JSONObject getMasters = new JSONObject();
                    getMasters.put("getMasters", "getMasters");
                    mSocket.emit("communication", getMasters);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });







        try {
            mSocket = IO.socket("http://localhost:3000"); // IP adresa pro emulator, změňte na IP serveru při testování na skutečném zařízení
            mSocket.connect();
            mSocket.on("getMasters", onGetMasters);
            mSocket.on("chatMessage", onNewMessage);



        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }



    private void switchToIngameView() {
        setContentView(R.layout.ingame);

        messageAdapter = new MessageAdapter(this, messages);

        // Inicializace RecyclerView a jeho adapteru
        messagesView = findViewById(R.id.chatRecyclerView);
        messagesView.setLayoutManager(new LinearLayoutManager(this));
        messagesView.setAdapter(messageAdapter);


        //Send Message button
        final Button sendMessageButton = (Button) findViewById(R.id.sendButton);
        sendMessageButton.setOnClickListener(v -> {
            EditText editText = findViewById(R.id.chatEditText);
            String message = editText.getText().toString();

            try {
                JSONObject sendMessage = new JSONObject();

                sendMessage.put("sendMessage", message);
                mSocket.emit("communication", sendMessage);
                editText.setText("");
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });


        myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myWebView.loadUrl("https://ddnet.org/mappreview/?map=Firewatch");

    }


    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];

                    // Zpracujte data a přidejte zprávu do seznamu
                    try {
                        String messageText = data.getString("message");
                        String authorName = data.optJSONObject("author").optJSONObject("ClientInfo").optString("name", "Neznámý");
                        Message message = new Message(messageText, authorName);
                        messages.add(message);
                        messageAdapter.notifyItemInserted(messages.size() - 1);
                        messagesView.scrollToPosition(messages.size() - 1);

                    } catch (Exception e) {

                    }


                }
            });
        }
    };


    private Emitter.Listener onGetMasters = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Comparator<Server> serverComparator = new Comparator<Server>() {
                            @Override
                            public int compare(Server s1, Server s2) {
                                int clientsCount1 = (s1.getInfo().getClients() != null) ? s1.getInfo().getClients().size() : 0;
                                int clientsCount2 = (s2.getInfo().getClients() != null) ? s2.getInfo().getClients().size() : 0;
                                return Integer.compare(clientsCount2, clientsCount1);
                            }
                        };

                        PriorityQueue<Server> serverQueue = new PriorityQueue<>(serverComparator);


                        //List<Server> servers = new ArrayList<>();
                        JSONObject jsonObject =  (JSONObject) args[0];
                        JSONArray serversArray = null;

                        serversArray = jsonObject.getJSONArray("servers");


                        for (int i = 0; i < serversArray.length(); i++) {
                            JSONObject serverObj = serversArray.getJSONObject(i);
                            Server server = new Server();
                            server.setAddresses(getListFromStringJSONArray(serverObj.getJSONArray("addresses")));
                            server.setInfo(parseServerInfo(serverObj.getJSONObject("info")));
                            serverQueue.add(server);

                            //servers.add(server);
                        }

                        List<Server> sortedServers = new ArrayList<>();
                        while (!serverQueue.isEmpty()) {
                            sortedServers.add(serverQueue.poll());
                        }
                        setupRecyclerView(sortedServers);

                    } catch (Exception e) {

                    }
                }
            });
        }
    };






    private void setupRecyclerView(List<Server> servers) {
        RecyclerView recyclerView = findViewById(R.id.masterServers);
        ServerAdapter adapter = new ServerAdapter(servers, new OnItemClickListener() {
            @Override
            public void onItemClick(Server server) {
                // Uložení vybraného serveru
                selectedServer = server;
                // Můžete zde zobrazit nějaké informace o vybraném serveru
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }





    private List<String> getListFromStringJSONArray(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }

    private Server.ServerInfo parseServerInfo(JSONObject infoObj) throws JSONException {
        Server.ServerInfo info = new Server.ServerInfo();
        // Parse and set fields for ServerInfo
        // Example:
        info.setName(infoObj.getString("name"));
        // Continue for other fields...

        return info;
    }





    //methods for node js
    public native Integer startNodeWithArguments(String[] arguments);
    private boolean wasAPKUpdated() {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("NODEJS_MOBILE_PREFS", Context.MODE_PRIVATE);
        long previousLastUpdateTime = prefs.getLong("NODEJS_MOBILE_APK_LastUpdateTime", 0);
        long lastUpdateTime = 1;
        try {
            PackageInfo packageInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            lastUpdateTime = packageInfo.lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return (lastUpdateTime != previousLastUpdateTime);
    }

    private void saveLastUpdateTime() {
        long lastUpdateTime = 1;
        try {
            PackageInfo packageInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            lastUpdateTime = packageInfo.lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("NODEJS_MOBILE_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("NODEJS_MOBILE_APK_LastUpdateTime", lastUpdateTime);
        editor.commit();
    }
    private static boolean deleteFolderRecursively(File file) {
        try {
            boolean res=true;
            for (File childFile : file.listFiles()) {
                if (childFile.isDirectory()) {
                    res &= deleteFolderRecursively(childFile);
                } else {
                    res &= childFile.delete();
                }
            }
            res &= file.delete();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private static boolean copyAssetFolder(AssetManager assetManager, String fromAssetPath, String toPath) {
        try {
            String[] files = assetManager.list(fromAssetPath);
            boolean res = true;

            if (files.length==0) {
                //If it's a file, it won't have any assets "inside" it.
                res &= copyAsset(assetManager,
                        fromAssetPath,
                        toPath);
            } else {
                new File(toPath).mkdirs();
                for (String file : files)
                    res &= copyAssetFolder(assetManager,
                            fromAssetPath + "/" + file,
                            toPath + "/" + file);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private static boolean copyAsset(AssetManager assetManager, String fromAssetPath, String toPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(fromAssetPath);
            new File(toPath).createNewFile();
            out = new FileOutputStream(toPath);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

}







/*function vytvorBufferProKruh(gl, radius, segments) {
    var body = [];
    for (var i = 0; i <= segments; i++) {
        var theta = (i / segments) * 2 * Math.PI;
        body.push(radius * Math.cos(theta), radius * Math.sin(theta));
    }

    var bufferKruhu = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, bufferKruhu);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(body), gl.STATIC_DRAW);

    return bufferKruhu;
}

// Vytvoření bufferu (např. při inicializaci)

tw.mainLoop = function() {
    // Transform mouse coords to world coords
    tw.mouseDownInc[0] = tw.mouseDownInc[0] / tw.canvas.width * tw.worldView[0] * tw.aspect / tw.cameraZoom;
    tw.mouseDownInc[1] = tw.mouseDownInc[1] / tw.canvas.height * tw.worldView[1] / tw.cameraZoom;

    // Move camera
    tw.cameraPos[0] += tw.mouseDownInc[0];
    tw.cameraPos[1] += tw.mouseDownInc[1];

    tw.mouseDownInc[0] = 0;
    tw.mouseDownInc[1] = 0;

    tw.render();

    var canvas = tw.canvas;
    var gl = tw.gl;

    if (gl) {
if (tw.gl) {
    var bufferKruhu = vytvorBufferProKruh(tw.gl, 50.0, 32); // Radius 1.0, 32 segmentů

        tw.gl.useProgram(tw.stdShader); // Použití existujícího shaderu
    var colorLocation = tw.gl.getUniformLocation(tw.stdShader, "uColor");
var yellowColor = [1.0, 1.0, 0.0, 1.0]; // RGBA pro žlutou
            tw.gl.uniform4fv(colorLocation, yellowColor); // Nastavit barvu kruhu

        tw.gl.bindBuffer(tw.gl.ARRAY_BUFFER, bufferKruhu); // Připojení bufferu pro kruh
        // Nastavení atributů (pokud je potřeba)
        tw.gl.enableVertexAttribArray(tw.stdShader.vPosAttr);
        tw.gl.vertexAttribPointer(tw.stdShader.vPosAttr, 2, tw.gl.FLOAT, false, 0, 0);
        // Vykreslení kruhu
        tw.gl.drawArrays(tw.gl.TRIANGLE_FAN, 0, 50);
    } }


    // Reset zoomed state
    tw.zoomed = false;


    requestAnimFrame(tw.mainLoop);
}*/