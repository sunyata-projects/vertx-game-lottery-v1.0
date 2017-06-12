package org.sunyata.octopus;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by leo on 17/4/17.
 */
public class OctopusResponse {
    private int errorCode = 0;

    Logger logger = LoggerFactory.getLogger(OctopusResponse.class);

    public OctopusOutMessage getMessage() {
        return message;
    }

    public OctopusResponse setMessage(OctopusOutMessage message) {
        this.message = message;
        return this;
    }

    public OctopusResponse(ChannelHandlerContext ctx, OctopusInMessage inMessage) {
        this.message = new OctopusOutMessage();
        this.message.setCmd(inMessage.getCmd());
        this.message.setSerial(inMessage.getSerial());
        this.context = ctx;
    }

    public OctopusResponse(ChannelHandlerContext ctx, int cmd, int serial) {
        this.message = new OctopusOutMessage();
        this.message.setCmd(cmd);
        this.message.setSerial(serial);
        this.context = ctx;
    }


    private OctopusOutMessage message;

    public ChannelHandlerContext getContext() {
        return context;
    }

    public OctopusResponse setContext(ChannelHandlerContext context) {
        this.context = context;
        return this;
    }

    ChannelHandlerContext context;

    public void writeAndFlush() {
        byte[] body = this.message.getBody();
        ByteBuf buffer = context.alloc().buffer();

        buffer.writeInt(this.message.getCmd());//4
        buffer.writeInt(this.message.getSerial());//8
        buffer.writeInt(this.errorCode);//4
        int length = body == null ? 0 : body.length;
//        if (body == null) {
//            body = "".getBytes();
//        }
        buffer.writeInt(length);//4
        if (length > 0) {
            buffer.writeBytes(body);
        }

        BinaryWebSocketFrame frame = new BinaryWebSocketFrame(buffer);
        context.writeAndFlush(frame);
        logger.info("response->cmd:{},code:{},length:{}", this.message.getCmd(), this.message.getCode(), this.message
                .getLength());
    }

    public OctopusResponse setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public OctopusResponse setBody(byte[] bytes) {
        this.message.setBody(bytes);
        return this;
    }
}
