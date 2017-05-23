package org.sunyata.octopus;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.sunyata.octopus.handler.OctopusRequestHandler;
import org.sunyata.octopus.handler.WebSocketServerHandler;

public class OctopusServerInitializer extends ChannelInitializer<Channel> {


    private final Server server;

    public OctopusServerInitializer(Server server) {
        super();
        this.server = server;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

//        pipeline.addLast("haproxy.decoder", new ProxyProtocolDecoder());
//        pipeline.addLast("haproxy.handler", new ProxyProtocolHandler());

        pipeline.addLast(new HttpServerCodec());

        pipeline.addLast(new ChunkedWriteHandler());

        pipeline.addLast(new HttpObjectAggregator(64 * 1024));

        pipeline.addLast(new WebSocketServerHandler(server));

//        pipeline.addLast(new ProtobufVarint32FrameDecoder());
//        pipeline.addLast(new ProtobufDecoder(OctopusInMessage.Request.getDefaultInstance()));
//
//        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
//        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
//        pipeline.addLast(new ProtobufEncoder());


        pipeline.addLast(new IdleStateHandler(60, 0, 0));
        pipeline.addLast(new OctopusRequestHandler(this.server));
//        pipeline.addLast(new ClientIoHandler());

    }

}
