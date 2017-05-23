package org.sunyata.octopus.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.sunyata.octopus.OctopusInMessage;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;
import org.sunyata.octopus.Server;

/**
 * Created by leo on 17/4/14.
 */
public class OctopusRequestHandler extends SimpleChannelInboundHandler<Object> {

    private final Server server;

    public OctopusRequestHandler(Server server) {
        this.server = server;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        OctopusInMessage inMessage = (OctopusInMessage) msg;
//        LoginReq.LoginRequest loginRequest = LoginReq.LoginRequest.parseFrom(mybytebuf.getBody());
        //System.out.println(loginRequest.getUserName());
//        ctx.writeAndFlush(loginRequest.getUserName());
//        ByteBuf bytes = ctx.alloc().buffer();
//        bytes.writeInt(3333);
        OctopusRequest requestWrapper = new OctopusRequest(ctx);
        requestWrapper.setMessage(inMessage);
        OctopusResponse responseWrapper = new OctopusResponse(ctx, inMessage);
        server.service(requestWrapper, responseWrapper);
        //ctx.writeAndFlush(new BinaryWebSocketFrame(bytes));
        //ctx.channel().writeAndFlush(buffer);
        //ReferenceCountUtil.retain(msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent e = (IdleStateEvent) evt;
//            if (e.state() == IdleState.WRITER_IDLE) {
//                ctx.writeAndFlush("ping");
//            } else if (e.state() == IdleState.READER_IDLE) {
//                String userId = ctx.channel().attr(KEY_USER_ID).get();
//                //logger.error("Idle: channel:{},userid : {} is time out.", ctx.channel(),userId);
//                if (null != userId)
//                    GameWorld.getInstance().removeUserFromUserId(userId);
//            }
//        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
