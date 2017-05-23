package org.sunyata.octopus;

import io.netty.channel.ChannelHandlerContext;
import org.sunyata.octopus.store.Store;

/**
 * Created by leo on 17/4/18.
 */
public class RedisSession implements Session {

    private User user;
    private String sessionId;
    Store store;
    private long lastAccessedTime;
    private long creationTime;

    public User getUser() {
        return user;
    }

    public RedisSession setUser(User user) {
        this.user = user;
        return this;
    }


    private ChannelHandlerContext context;

    public RedisSession setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
        return this;
    }

    public RedisSession setCreationTime(long creationTime) {
        this.creationTime = creationTime;
        return this;
    }




    public RedisSession(String sessionId, Store store, ChannelHandlerContext ctx) {
        this.store = store;
        this.sessionId = sessionId;
        this.context = ctx;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    @Override
    public User getCurrentUser() {
        return this.user;
    }

    @Override
    public long getCreationTime() {
        return this.creationTime;
    }

    @Override
    public long getLastAccessedTime() {
        return this.lastAccessedTime;
    }

    @Override
    public ChannelHandlerContext getHandlerContext() {
        return this.context;
    }

    @Override
    public void setHandlerContext(ChannelHandlerContext context) {
        this.context = context;
    }

    @Override
    public Object getAttribute(String name) {
        return store.get(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        store.set(name, value);
    }

    @Override
    public void removeAttribute(String name) {
        store.del(name);
    }

    @Override
    public void clear() {
        store.removeAll();
    }
}
