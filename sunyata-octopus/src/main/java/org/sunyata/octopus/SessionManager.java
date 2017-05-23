package org.sunyata.octopus;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sunyata.octopus.store.Store;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by leo on 17/5/17.
 */
public class SessionManager {
    final static Logger logger = LoggerFactory.getLogger(SessionManager.class);
    static ConcurrentMap<String, RedisSession> map = new ConcurrentHashMap<>();

    public static Session getSession(String accountId) {
        return map.getOrDefault(accountId, null);
    }

    public static RedisSession newInstance(String sessionId, Store store, ChannelHandlerContext ctx) throws Exception {
        Session session1 = getSession(sessionId);
        if (session1 != null) {
            throw new Exception(String.format("用户{}已经存在", session1));
        }
        RedisSession session = new RedisSession(sessionId, store, ctx);
        map.put(sessionId, session);
        return session;
    }

    public static void removeFromLocal(String sessionId) {
        RedisSession remove = map.remove(sessionId);
        logger.info("session removed from local,{}", remove);
    }
}
