package org.sunyata.octopus;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by leo on 17/4/18.
 */
public interface Session {

    String getSessionId();

    User getCurrentUser();

    long getCreationTime();

    long getLastAccessedTime();

    ChannelHandlerContext getHandlerContext();

    void setHandlerContext(ChannelHandlerContext context);

    Object getAttribute(String name);

    void setAttribute(String name, Object value);

    void removeAttribute(String name);

    void clear();
}
