package com.example.netty.client;


import com.example.netty.vo.Message;
import io.socket.client.IO;
import io.socket.client.IO.Options;
import io.socket.client.Socket;
import io.socket.emitter.Emitter.Listener;
import java.net.URISyntaxException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author 夏先鹏
 * @date 2019/07/23
 * @time 10:28
 */
public class SocketClient {

    public static void main(String[] args) throws URISyntaxException, JSONException {
        IO.Options options = new Options();
        options.transports = new String[]{"websocket", "xhr-polling", "jsonp-polling"};
        options.reconnectionAttempts = 2;
        options.reconnectionDelay = 1000;
        options.timeout = 500;

        final Socket socket = IO.socket("http://192.168.1.150:9091", options);

        socket.on(Socket.EVENT_CONNECT, new Listener() {
            @Override
            public void call(Object... objects) {
                System.out.println("client connect...");
            }
        });

        socket.on(Socket.EVENT_DISCONNECT, new Listener() {
            @Override
            public void call(Object... objects) {
                System.out.println("disconnect ... ");
            }
        });

        socket.on(Socket.EVENT_MESSAGE, new Listener() {
            @Override
            public void call(Object... objects) {
                socket.send();
            }
        });

        socket.connect();
        Message message = new Message();
        message.setUsername("xxx");
        message.setMessage("msg");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "asd");
        jsonObject.put("message", "66");
        socket.send(jsonObject);

    }
}
