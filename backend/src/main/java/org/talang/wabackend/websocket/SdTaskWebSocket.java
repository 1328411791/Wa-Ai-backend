package org.talang.wabackend.websocket;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
@ServerEndpoint("/websocket/{token}")  // 接口路径 ws://localhost:8087/webSocket/userId;
public class SdTaskWebSocket {

        //与某个客户端的连接会话，需要通过它来给客户端发送数据
        private Session session;
        /**
         * 用户ID
         */
        private Integer userId;

        //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
        //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
        //  注：底下WebSocket是当前类名
        private static CopyOnWriteArraySet<SdTaskWebSocket> webSockets =new CopyOnWriteArraySet<>();
        // 用来存在线连接用户信息
        private static final Map<Integer,Session> sessionPool = new ConcurrentHashMap<>();

        /**
         * 链接成功调用的方法
         */
        @OnOpen
        public void onOpen(Session session, @PathParam(value="token")String token){
            try {
                String loginId = (String) StpUtil.getLoginIdByToken(token);
                if (loginId == null) {
                    session.close();
                    throw new RuntimeException("连接失败，无效Token：" + token);
                }
                this.session = session;
                this.userId =  Integer.valueOf(loginId);
                webSockets.add(this);
                sessionPool.put(userId, session);
                log.info("[websocket] 有新的连接，总数为:{} id:{}",webSockets.size(),userId);
            } catch (Exception e) {
                log.error("websocket连接失败",e);
            }
        }

        /**
         * 链接关闭调用的方法
         */
        @OnClose
        public void onClose() {
            try {
                webSockets.remove(this);
                sessionPool.remove(this.userId);
                log.info("[websocket] 连接断开，总数为:"+webSockets.size());
            } catch (Exception e) {
            }
        }
        /**
         * 收到客户端消息后调用的方法
         *
         * @param message
         * @param session
         */
        @OnMessage
        public void onMessage(String message) {
            log.info("[websocket] 收到客户端消息:"+message);
        }

        /** 发送错误时的处理
         * @param session
         * @param error
         */
        @OnError
        public void onError(Session session, Throwable error) {

            log.error("用户错误,原因:"+error.getMessage());
            error.printStackTrace();
        }


        // 此为广播消息
        public void sendAllMessage(String message) {
            log.info("[websocket] 广播消息:"+message);
            for(SdTaskWebSocket webSocket : webSockets) {
                try {
                    if(webSocket.session.isOpen()) {
                        webSocket.session.getAsyncRemote().sendText(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 此为单点消息
        public void sendOneMessage(Integer userId, String message) {
            Session session = sessionPool.get(userId);
            if (session != null&&session.isOpen()) {
                try {
                    log.info("[websocket] 单点消息:"+message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 此为单点消息(多人)
        public void sendMoreMessage(Integer[] userIds, String message) {
            for(Integer userId:userIds) {
                Session session = sessionPool.get(userId);
                if (session != null&&session.isOpen()) {
                    try {
                        log.info("[websocket] 单点消息:"+message);
                        session.getAsyncRemote().sendText(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
}
