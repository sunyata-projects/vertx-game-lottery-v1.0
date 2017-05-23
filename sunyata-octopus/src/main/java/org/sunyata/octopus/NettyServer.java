package org.sunyata.octopus;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

/**
 * Created by leo on 17/4/13.
 */
public class NettyServer implements Server {
    private final ChannelGroup group = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup(8);
    private final OctopusConfiguration configuration;
    private MethodHandlerRouter route;

    public NettyServer(OctopusConfiguration configuration) {
        this.configuration = configuration;
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyServer(new OctopusConfiguration()).start();
    }

    public void start() throws InterruptedException {
        this.route = new MethodHandlerRouter();
        // TODO Auto-generated method stub
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(createInitializer
                (NettyServer.this));
        int port = 8000;
        b.bind(port).sync().channel();
    }

    @Override
    public void stop() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void service(OctopusRequest request, OctopusResponse response) throws Exception {
        this.route.route(request, response);
    }

    @Override
    public OctopusConfiguration getConfiguration() {
        return this.configuration;
    }

    protected ChannelHandler createInitializer(Server server) {
        return new OctopusServerInitializer(server);
    }
}
