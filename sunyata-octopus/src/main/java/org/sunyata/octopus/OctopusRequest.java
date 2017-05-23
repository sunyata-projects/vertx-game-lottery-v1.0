package org.sunyata.octopus;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by leo on 17/4/17.
 */
public class OctopusRequest {
    private Session session;

    public OctopusRequest(ChannelHandlerContext ctx) {
        this.context = ctx;
    }

    public OctopusInMessage getMessage() {
        return message;
    }

    public OctopusRequest setMessage(OctopusInMessage message) {
        this.message = message;
        return this;
    }

    private OctopusInMessage message;

    public ChannelHandlerContext getContext() {
        return context;
    }

    public OctopusRequest setContext(ChannelHandlerContext context) {
        this.context = context;
        return this;
    }

    ChannelHandlerContext context;

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
