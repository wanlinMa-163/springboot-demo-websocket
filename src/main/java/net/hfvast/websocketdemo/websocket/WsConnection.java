package net.hfvast.websocketdemo.websocket;

import org.springframework.web.socket.WebSocketSession;
import java.net.URI;

public class WsConnection {

    private URI uri;
    private WebSocketSession webSocketSession;

    public WsConnection(URI uri, WebSocketSession webSocketSession) {
        this.uri = uri;
        this.webSocketSession=webSocketSession;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }
}
