package net.hfvast.websocketdemo.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SpringWebSocketHandler extends TextWebSocketHandler {
    private static final ConcurrentHashMap<WebSocketSession, WsConnection> wsConnectionMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        if (!wsConnectionMap.containsKey(webSocketSession)) {
            WsConnection wsConnection = new WsConnection(webSocketSession.getUri(), webSocketSession);
            wsConnectionMap.put(webSocketSession, wsConnection);
            System.out.println("连接建立，当前连接数：" + wsConnectionMap.size());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (wsConnectionMap.containsKey(session)) {
            wsConnectionMap.remove(session);
            System.out.println("连接断开，当前连接数:" + wsConnectionMap.size());
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (wsConnectionMap.containsKey(session)) {
            System.out.println("前端传入的数据:"+message.getPayload());
            session.sendMessage(new TextMessage("后端响应数据: 当前连接id:"+session.getId()+"\t"+message.getPayload()));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        if (wsConnectionMap.containsKey(session)) {
            wsConnectionMap.remove(session);
            System.err.println("连接断开，因为发生错误，当前连接数:" + wsConnectionMap.size());
        }
    }
}
