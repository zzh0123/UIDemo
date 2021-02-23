package com.zzh.uidemo.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.zzh.uidemo.R;
import com.zzh.uidemo.recyclerview.adapter.DiscussItemAdapter;
import com.zzh.uidemo.recyclerview.bean.DiscussItemBean;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 多类型布局RecyclerView
 * mqtt
 */
public class TypeRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DiscussItemBean> discussList = new ArrayList<>();
    private DiscussItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_recycler_view);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        adapter = new DiscussItemAdapter(this, userId, discussList);
//        adapter.setOnItemMyClickListener(new DiscussItemAdapter.OnItemMyClickListener() {
//            @Override
//            public void onItemMyClick(View view, String id, boolean currentLikeFlag, int pos) {
//                if (!currentLikeFlag) {
//                    likeComment(id, currentLikeFlag, pos);
//                } else {
//                    unLikeComment(id, currentLikeFlag, pos);
//                }
//            }
//        });
//
//        adapter.setOnItemOtherClickListener(new DiscussItemAdapter.OnItemOtherClickListener() {
//            @Override
//            public void onItemOtherClick(View view, String id, boolean currentLikeFlag, int pos) {
//                if (!currentLikeFlag) {
//                    likeComment(id, currentLikeFlag, pos);
//                } else {
//                    unLikeComment(id, currentLikeFlag, pos);
//                }
//            }
//        });
//        recyclerView.setAdapter(adapter);
    }

    /**
     * 连接并接收服务器推送消息
     * topic是每个会议的id
     */
//    private void connectMqtt() {
//        // 读取订阅toplist
//        String serverURI = IPConfig.SERVER_URI;
//        String clientId = userId + "_8700@3400@000000";
//        String username = "{'routerIdentification':'8700@3400@000000'}";
//        String password = "";
//
//        LogUtil.i("zzz1", "clientId " + clientId);
//        int qos = 1;
//        try {
//            final MqttClient client = new MqttClient(serverURI, clientId, new MemoryPersistence());
//            MqttConnectOptions options = new MqttConnectOptions();
//            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
//            options.setCleanSession(true);
//            options.setUserName(username);
//            options.setPassword(password.toCharArray());
//            // 设置超时时间 单位为秒
//            options.setConnectionTimeout(10);
//            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
//            options.setKeepAliveInterval(20);
//            options.setMqttVersion(4);
//            options.setAutomaticReconnect(true);
//            client.setCallback(new MqttCallbackExtended() {
//                @Override
//                public void connectComplete(boolean reconnect, String serverURI) {
//                    // 回调即连接成功
////                    log.info("connectComplete {} {}", reconnect, serverURI);
////                    LogUtil.i("connectComplete", "reconnect " + reconnect + "serverURI " + serverURI);
//                    LogUtil.i("zzz1", "connectComplete " + "reconnect " + reconnect + " serverURI " + serverURI);
//                }
//
//                //                @SneakyThrows
//                @Override
//                public void connectionLost(Throwable cause) {
////                    log.info("this method is called when the connection to the server is lost");
////                    LogUtil.i("connectionLost", "this method is called when the connection to the server is lost ");
//                    LogUtil.i("zzz1", "connectionLost");
//
//                    try {
//                        Thread.sleep(500);
//                        LogUtil.i("zzz1", "reconnect");
//                        client.reconnect();
//                    } catch (Exception e) {
//
//                    }
//                }
//
//                @Override
//
//                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
//                    String message = new String(mqttMessage.getPayload(), Charset.defaultCharset());
////                    log.info(message);
////                    LogUtil.i("messageArrived", "" + message);
//                    LogUtil.i("zzz1", "messageArrived " + message + "  " + StringUtils.isStrEmpty(message));
//
//                    if (!StringUtils.isStrEmpty(message)) {
//                        LogUtil.i("zzz1", "更新 " + Thread.currentThread().getName());
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                getApplyCommentList(2);
//                            }
//                        });
//
//                    }
//                }
//
//                @Override
//
//                public void deliveryComplete(IMqttDeliveryToken token) {
////                    log.info("Called when delivery for a message has been completed");
////                    LogUtil.i("deliveryComplete", "" + token);
//                    LogUtil.i("zzz1", "deliveryComplete " + token);
//                }
//            });
//
//            try {
//                client.connect(options);
//                LogUtil.i("zzz1", "start connect");
//                client.subscribe(meetingApplyId, qos);
//            } catch (Exception ex) {
////                log.error("Connection Cause Exception {}", ex.toString());
////                LogUtil.i("Connection Cause Exception {}", "" + ex.toString());
//                LogUtil.i("zzz1", "connect Exception" + ex.toString());
//            }
//        } catch (Exception e) {
//            LogUtil.i("zzz1", "Exception1  " + e.toString());
//        }
//
//    }
}
