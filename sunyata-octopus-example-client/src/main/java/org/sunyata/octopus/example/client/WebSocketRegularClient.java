package org.sunyata.octopus.example.client; /**
 * Created by leo on 17/4/14.
 */

import com.xt.yde.protobuf.common.Common;
import com.xt.yde.protobuf.regular.GameRegular;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * io.netty.handler.codec.http.websocketx.extension
 * Created by leo on 17/4/13.
 */
public final class WebSocketRegularClient {

    static final String URL = System.getProperty("url", "ws://127.0.0.1:8000/websocket?token=tokenValuehahaha");
    private static AtomicLong serialCount = new AtomicLong();

    public static Channel connect() throws SSLException, URISyntaxException {
        URI uri = new URI(URL);
        String scheme = uri.getScheme() == null ? "ws" : uri.getScheme();
        final String host = uri.getHost() == null ? "127.0.0.1" : uri.getHost();
        final int port;
        if (uri.getPort() == -1) {
            if ("ws".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("wss".equalsIgnoreCase(scheme)) {
                port = 443;
            } else {
                port = -1;
            }
        } else {
            port = uri.getPort();
        }

        if (!"ws".equalsIgnoreCase(scheme) && !"wss".equalsIgnoreCase(scheme)) {
            System.err.println("Only WS(S) is supported.");
            return null;
        }

        final boolean ssl = "wss".equalsIgnoreCase(scheme);
        final SslContext sslCtx;
        if (ssl) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // Connect with V13 (RFC 6455 aka HyBi-17). You can change it to V08 or V00.
            // If you change it to V00, ping is not supported and remember to change
            // HttpResponseDecoder to WebSocketHttpResponseDecoder in the pipeline.
            io.netty.handler.codec.http.cookie.DefaultCookie cookie = new io.netty.handler.codec.http.cookie
                    .DefaultCookie("cid", "tokenValud");
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setSecure(false);

            DefaultHttpHeaders entries = new DefaultHttpHeaders();
            entries.add(HttpHeaderNames.SET_COOKIE, io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode
                    (cookie));
            final WebSocketClientHandler handler =
                    new WebSocketClientHandler(
                            WebSocketClientHandshakerFactory.newHandshaker(
                                    uri, WebSocketVersion.V13, null, true, entries));

            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc(), host, port));
                            }
                            p.addLast(
                                    new HttpClientCodec(),
                                    new HttpObjectAggregator(8192),
                                    WebSocketClientCompressionHandler.INSTANCE,
                                    handler);
                        }
                    });

            Channel ch = b.connect(uri.getHost(), port).sync().channel();
            handler.handshakeFuture().sync();
            return ch;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    public static void main(String[] args) throws Exception {
//        URI uri = new URI(URL);
//        String scheme = uri.getScheme() == null ? "ws" : uri.getScheme();
//        final String host = uri.getHost() == null ? "127.0.0.1" : uri.getHost();
//        final int port;
//        if (uri.getPort() == -1) {
//            if ("ws".equalsIgnoreCase(scheme)) {
//                port = 80;
//            } else if ("wss".equalsIgnoreCase(scheme)) {
//                port = 443;
//            } else {
//                port = -1;
//            }
//        } else {
//            port = uri.getPort();
//        }
//
//        if (!"ws".equalsIgnoreCase(scheme) && !"wss".equalsIgnoreCase(scheme)) {
//            System.err.println("Only WS(S) is supported.");
//            return;
//        }
//
//        final boolean ssl = "wss".equalsIgnoreCase(scheme);
//        final SslContext sslCtx;
//        if (ssl) {
//            sslCtx = SslContextBuilder.forClient()
//                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
//        } else {
//            sslCtx = null;
//        }
//
//        EventLoopGroup group = new NioEventLoopGroup();
//        try {
//            // Connect with V13 (RFC 6455 aka HyBi-17). You can change it to V08 or V00.
//            // If you change it to V00, ping is not supported and remember to change
//            // HttpResponseDecoder to WebSocketHttpResponseDecoder in the pipeline.
//            final WebSocketClientHandler handler =
//                    new WebSocketClientHandler(
//                            WebSocketClientHandshakerFactory.newHandshaker(
//                                    uri, WebSocketVersion.V13, null, true, new DefaultHttpHeaders()));
//
//            Bootstrap b = new Bootstrap();
//            b.group(group)
//                    .channel(NioSocketChannel.class)
//                    .handler(new ChannelInitializer<SocketChannel>() {
//                        @Override
//                        protected void initChannel(SocketChannel ch) {
//                            ChannelPipeline p = ch.pipeline();
//                            if (sslCtx != null) {
//                                p.addLast(sslCtx.newHandler(ch.alloc(), host, port));
//                            }
//                            p.addLast(
//                                    new HttpClientCodec(),
//                                    new HttpObjectAggregator(8192),
//                                    WebSocketClientCompressionHandler.INSTANCE,
//                                    handler);
//                        }
//                    });
//
//            Channel ch = b.connect(uri.getHost(), port).sync().channel();
//            handler.handshakeFuture().sync();

        Channel ch = null;
        try {
            ch = connect();
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String msg = console.readLine();
                if (msg == null) {
                    break;
                } else if ("bye".equals(msg.toLowerCase())) {
                    ch.writeAndFlush(new CloseWebSocketFrame());
                    ch.closeFuture().sync();
                    break;

                } else if ("connect".equals(msg.toLowerCase())) {
//                    ch.writeAndFlush(new CloseWebSocketFrame());
//                    ch.closeFuture().sync();
                    ch = connect();
                } else if ("ping".equals(msg.toLowerCase())) {
                    WebSocketFrame frame = new PingWebSocketFrame(Unpooled.wrappedBuffer(new byte[]{8, 1, 8, 1}));
                    ch.writeAndFlush(frame);

                } else if ("login".equals(msg.toLowerCase())) {
                    Common.LoginRequestMsg.Builder builder = Common.LoginRequestMsg.newBuilder();
                    Common.LoginRequestMsg loginRequestMsg = builder.setPassword("lcl").setUserName("lcl").build();
                    ByteBuf buffer = Unpooled.buffer();
                    byte[] bytes = loginRequestMsg.toByteArray();
                    int length = bytes.length;
                    buffer.writeInt(10001).writeInt(2323232).writeFloat(1.0f).writeInt(length).writeBytes(bytes);
                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
                    ch.writeAndFlush(frame);
                } else if ("bet".equals(msg.toLowerCase())) {

                    Common.BetRequestMsg.Builder builder = Common.BetRequestMsg.newBuilder();
                    Common.BetRequestMsg betRequestMsg = builder.setGameType(10001).setAmt(100).build();
                    ByteBuf buffer = Unpooled.buffer();
                    byte[] bytes = betRequestMsg.toByteArray();
                    buffer.writeInt(10005).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes(bytes);
                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
                    ch.writeAndFlush(frame);
                } else if ("deal".equals(msg.toLowerCase())) {
                    Common.DealRequestMsg.Builder builder = Common.DealRequestMsg.newBuilder();
                    Common.DealRequestMsg dealRequestMsg = builder.setGameType(10001).build();
                    ByteBuf buffer = Unpooled.buffer();
                    byte[] bytes = dealRequestMsg.toByteArray();
                    buffer.writeInt(50003).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes(bytes);
                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
                    ch.writeAndFlush(frame);
                } else if ("raise".equals(msg.toLowerCase())) {
                    GameRegular.RegularRaiseBetRequestMsg.Builder builder = GameRegular.RegularRaiseBetRequestMsg
                            .newBuilder();
                    builder.setTimes(3);
                    GameRegular.RegularRaiseBetRequestMsg regularRaiseBetRequestMsg = builder.build();
                    ByteBuf buffer = Unpooled.buffer();
                    byte[] bytes = regularRaiseBetRequestMsg.toByteArray();
                    buffer.writeInt(50004).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes(bytes);
                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
                    ch.writeAndFlush(frame);
                } else if ("play".equals(msg.toLowerCase())) {
                    for (int i = 0; i < 10; i++) {
                        Common.PlayRequestMsg.Builder builder = Common.PlayRequestMsg.newBuilder();
                        builder.setIsAuto(true);
                        builder.setRolePosition(1);
                        Common.PlayRequestMsg playRequestMsg = builder.build();

                        ByteBuf buffer = Unpooled.buffer();
                        byte[] bytes = playRequestMsg.toByteArray();
                        buffer.writeInt(50006).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes
                                (bytes);

                        WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
                        ch.writeAndFlush(frame);
                    }
                } else if ("guess".equals(msg.toLowerCase())) {
                    GameRegular.RegularGuessSizeRequestMsg.Builder builder = GameRegular.RegularGuessSizeRequestMsg
                            .newBuilder();

                    GameRegular.RegularGuessSizeRequestMsg regularGuessSizeRequestMsg = builder.build();
                    ByteBuf buffer = Unpooled.buffer();
                    byte[] bytes = regularGuessSizeRequestMsg.toByteArray();
                    buffer.writeInt(50007).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes(bytes);
                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
                    ch.writeAndFlush(frame);
                } else if ("clear".equals(msg.toLowerCase())) {
                    GameRegular.RegularClearGameRequestMsg.Builder builder = GameRegular.RegularClearGameRequestMsg
                            .newBuilder();
                    GameRegular.RegularClearGameRequestMsg regularClearGameRequestMsg = builder.build();
                    ByteBuf buffer = Unpooled.buffer();
                    byte[] bytes = regularClearGameRequestMsg.toByteArray();
                    buffer.writeInt(50008).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes(bytes);
                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
                    ch.writeAndFlush(frame);
                } else if ("test".equals(msg.toLowerCase())) {
                    concurrentTest(ch);
                } else {
//                    LoginReq.LoginRequest login = LoginReq.LoginRequest.getDefaultInstance().toBuilder().setPassword
//                            ("password").setUserName(msg).build();
//                    ByteBuf buffer = Unpooled.buffer();
//                    byte[] bytes = login.toByteArray();
//                    buffer.writeInt(1111).writeLong(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes
// (bytes);
//                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
//                    ch.writeAndFlush(frame);
                }
            }
        } finally

        {
            //group.shutdownGracefully();
        }
    }

    public static void concurrentTest(Channel ch) {
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Common.BetRequestMsg.Builder builder = Common.BetRequestMsg.newBuilder();
                    Common.BetRequestMsg betRequestMsg = builder.setAmt(10).setGameType(10003).build();
                    ByteBuf buffer = Unpooled.buffer();
                    byte[] bytes = betRequestMsg.toByteArray();
                    buffer.writeInt(10005).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes(bytes);
                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
                    ch.writeAndFlush(frame);
                }
            });
            System.out.println("first is sent");
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Common.ProfileRequestMsg.Builder builder = Common.ProfileRequestMsg.newBuilder();
                    Common.ProfileRequestMsg profileRequestMsg = builder.build();
                    ByteBuf buffer = Unpooled.buffer();
                    byte[] bytes = profileRequestMsg.toByteArray();
                    buffer.writeInt(10003).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes(bytes);
                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
                    ch.writeAndFlush(frame);
                }
            });
            System.out.println("second is sent");
        }
    }
}