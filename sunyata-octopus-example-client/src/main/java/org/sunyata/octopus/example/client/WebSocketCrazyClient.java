//package org.sunyata.octopus.example.client; /**
// * Created by leo on 17/4/14.
// */
//
//import com.xt.yde.protobuf.common.Common;
//import com.xt.yde.protobuf.crazy.Crazy;
//import io.netty.bootstrap.Bootstrap;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.codec.http.DefaultHttpHeaders;
//import io.netty.handler.codec.http.HttpClientCodec;
//import io.netty.handler.codec.http.HttpHeaderNames;
//import io.netty.handler.codec.http.HttpObjectAggregator;
//import io.netty.handler.codec.http.websocketx.*;
//import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
//import io.netty.handler.ssl.SslContext;
//import io.netty.handler.ssl.SslContextBuilder;
//import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
//
//import javax.net.ssl.SSLException;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.atomic.AtomicLong;
//
///**
// * io.netty.handler.codec.http.websocketx.extension
// * Created by leo on 17/4/13.
// */
//public final class WebSocketCrazyClient {
//
//    static final String URL = System.getProperty("url", "ws://127.0.0.1:8000/websocket?token=tokenValuehahaha");
//    private static AtomicLong serialCount = new AtomicLong();
//
//    public static Channel connect() throws SSLException, URISyntaxException {
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
//            return null;
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
//            io.netty.handler.codec.http.cookie.DefaultCookie cookie = new io.netty.handler.codec.http.cookie
//                    .DefaultCookie("cid", "tokenValud");
//            cookie.setPath("/");
//            cookie.setHttpOnly(true);
//            cookie.setSecure(false);
//
//            DefaultHttpHeaders entries = new DefaultHttpHeaders();
//            entries.add(HttpHeaderNames.SET_COOKIE, io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode
//                    (cookie));
//            final WebSocketClientHandler handler =
//                    new WebSocketClientHandler(
//                            WebSocketClientHandshakerFactory.newHandshaker(
//                                    uri, WebSocketVersion.V13, null, true, entries));
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
//            return ch;
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//
//        }
//        return null;
//    }
//
//    public static void main(String[] args) throws Exception {
//        Channel ch = null;
//        try {
//            ch = connect();
//            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
//            while (true) {
//                String msg = console.readLine();
//                if (msg == null) {
//                    break;
//                } else if ("bye".equals(msg.toLowerCase())) {
//                    ch.writeAndFlush(new CloseWebSocketFrame());
//                    ch.closeFuture().sync();
//                    break;
//
//                } else if ("connect".equals(msg.toLowerCase())) {
////                    ch.writeAndFlush(new CloseWebSocketFrame());
////                    ch.closeFuture().sync();
//                    ch = connect();
//                } else if ("ping".equals(msg.toLowerCase())) {
//                    WebSocketFrame frame = new PingWebSocketFrame(Unpooled.wrappedBuffer(new byte[]{8, 1, 8, 1}));
//                    ch.writeAndFlush(frame);
//
//                } else if ("login".equals(msg.toLowerCase())) {
//                    Common.LoginRequestMsg.Builder builder = Common.LoginRequestMsg.newBuilder();
//                    Common.LoginRequestMsg loginRequestMsg = builder.setPassword("lcl").setUserName("lcl").build();
//                    ByteBuf buffer = Unpooled.buffer();
//                    byte[] bytes = loginRequestMsg.toByteArray();
//                    int length = bytes.length;
//                    buffer.writeInt(10001).writeInt(2323232).writeFloat(1.0f).writeInt(length).writeBytes(bytes);
//                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
//                    ch.writeAndFlush(frame);
//                } else if ("bet".equals(msg.toLowerCase())) {
//
//                    Common.BetRequestMsg.Builder builder = Common.BetRequestMsg.newBuilder();
//                    Common.BetRequestMsg betRequestMsg = builder.setGameType(10006).setAmt(100).build();
//                    ByteBuf buffer = Unpooled.buffer();
//                    byte[] bytes = betRequestMsg.toByteArray();
//                    buffer.writeInt(10005).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes(bytes);
//                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
//                    ch.writeAndFlush(frame);
//                } else if ("deal".equals(msg.toLowerCase())) {
//                    Common.DealRequestMsg.Builder builder = Common.DealRequestMsg.newBuilder();
//                    Common.DealRequestMsg dealRequestMsg = builder.setGameType(10006).build();
//                    ByteBuf buffer = Unpooled.buffer();
//                    byte[] bytes = dealRequestMsg.toByteArray();
//                    buffer.writeInt(63004).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes(bytes);
//                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
//                    ch.writeAndFlush(frame);
//                } else if ("drag".equals(msg.toLowerCase())) {
//                    Crazy.CrazyDragCardRequestMsg.Builder builder = Crazy.CrazyDragCardRequestMsg.newBuilder();
//                    builder.setDivideRole(1).setSelectPlace(0).setSendPlace(0);
//                    Crazy.CrazyDragCardRequestMsg crazyDragCardRequestMsg = builder.build();
//                    ByteBuf buffer = Unpooled.buffer();
//                    byte[] bytes = crazyDragCardRequestMsg.toByteArray();
//                    buffer.writeInt(63005).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes
//                            (bytes);
//                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
//                    ch.writeAndFlush(frame);
//
////
//                    builder.setDivideRole(1).setSelectPlace(1).setSendPlace(1);
//                    crazyDragCardRequestMsg = builder.build();
//                    ByteBuf buffer1 = Unpooled.buffer();
//                    byte[] bytes1 = crazyDragCardRequestMsg.toByteArray();
//                    buffer1.writeInt(63005).writeInt(2323232).writeFloat(1.0f).writeInt(bytes1.length).writeBytes
//                            (bytes1);
//                    WebSocketFrame frame1 = new BinaryWebSocketFrame(buffer1);
//                    ch.writeAndFlush(frame1);
//
//
//                    builder.setDivideRole(1).setSelectPlaœce(2).setSendPlace(2);
//                    crazyDragCardRequestMsg = builder.build();
//                    ByteBuf buffer2 = Unpooled.buffer();
//                    byte[] bytes2 = crazyDragCardRequestMsg.toByteArray();
//                    buffer2.writeInt(63005).writeInt(2323232).writeFloat(1.0f).writeInt(bytes2.length).writeBytes
//                            (bytes2);
//                    WebSocketFrame frame2 = new BinaryWebSocketFrame(buffer2);
//                    ch.writeAndFlush(frame2);
//
//
//                    builder.setDivideRole(2).setSelectPlace(3).setSendPlace(0);
//                    crazyDragCardRequestMsg = builder.build();
//                    ByteBuf buffer3 = Unpooled.buffer();
//                    byte[] bytes3 = crazyDragCardRequestMsg.toByteArray();
//                    buffer3.writeInt(63005).writeInt(2323232).writeFloat(1.0f).writeInt(bytes3.length).writeBytes
//                            (bytes3);
//                    WebSocketFrame frame3 = new BinaryWebSocketFrame(buffer3);
//                    ch.writeAndFlush(frame3);
//
//
//                    builder.setDivideRole(3).setSelectPlace(4).setSendPlace(0);
//                    crazyDragCardRequestMsg = builder.build();
//                    ByteBuf buffer4 = Unpooled.buffer();
//                    byte[] bytes4 = crazyDragCardRequestMsg.toByteArray();
//                    buffer4.writeInt(63005).writeInt(2323232).writeFloat(1.0f).writeInt(bytes4.length).writeBytes
//                            (bytes4);
//                    WebSocketFrame frame4 = new BinaryWebSocketFrame(buffer4);
//                    ch.writeAndFlush(frame4);
//
//
//                } else if ("dragover".equals(msg.toLowerCase())) {
//                    Crazy.CrazyDragCardOverRequestMsg.Builder builder = Crazy.CrazyDragCardOverRequestMsg.newBuilder();
//
//                    Crazy.CrazyDragCardOverRequestMsg crazyDragCardOverRequestMsg = builder.build();
//
//                    ByteBuf buffer = Unpooled.buffer();
//                    byte[] bytes = crazyDragCardOverRequestMsg.toByteArray();
//                    buffer.writeInt(63006).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes
//                            (bytes);
//
//                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
//                    ch.writeAndFlush(frame);
//
//                } else if ("enterguess".equals(msg.toLowerCase())) {
//                    Common.EnterDoubleRequestMsg.Builder builder = Common.EnterDoubleRequestMsg.newBuilder();
//                    builder.setType(1);
//                    ByteBuf buffer = Unpooled.buffer();
//                    Common.EnterDoubleRequestMsg enterDoubleRequestMsg = builder.build();
//                    byte[] bytes = enterDoubleRequestMsg.toByteArray();
//                    buffer.writeInt(63007).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes(bytes);
//                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
//                    ch.writeAndFlush(frame);
//                } else if ("guess".equals(msg.toLowerCase())) {
//                    Common.DoubleGuessRequestMsg.Builder builder = Common.DoubleGuessRequestMsg.newBuilder();
//                    ByteBuf buffer = Unpooled.buffer();
//                    Common.DoubleGuessRequestMsg doubleGuessRequestMsg = builder.build();
//                    byte[] bytes = doubleGuessRequestMsg.toByteArray();
//                    buffer.writeInt(63008).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes(bytes);
//                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
//                    ch.writeAndFlush(frame);
//                } else if ("clear".equals(msg.toLowerCase())) {
//                    Common.ClearGameRequestMsg.Builder builder = Common.ClearGameRequestMsg.newBuilder();
//                    Common.ClearGameRequestMsg clearGameRequestMsg = builder.build();
//                    ByteBuf buffer = Unpooled.buffer();
//                    byte[] bytes = clearGameRequestMsg.toByteArray();
//                    buffer.writeInt(63009).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes(bytes);
//                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
//                    ch.writeAndFlush(frame);
//                } else if ("test".equals(msg.toLowerCase())) {
//                    concurrentTest(ch);
//                } else {
////                    LoginReq.LoginRequest login = LoginReq.LoginRequest.getDefaultInstance().toBuilder().setPassword
////                            ("password").setUserName(msg).build();
////                    ByteBuf buffer = Unpooled.buffer();
////                    byte[] bytes = login.toByteArray();
////                    buffer.writeInt(1111).writeLong(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes
//// (bytes);
////                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
////                    ch.writeAndFlush(frame);
//                }
//            }
//        } finally
//
//        {
//            //group.shutdownGracefully();
//        }
//    }
//
//    public static void concurrentTest(Channel ch) {
//        ExecutorService executorService = Executors.newFixedThreadPool(8);
//        for (int i = 0; i < 5; i++) {
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    Common.BetRequestMsg.Builder builder = Common.BetRequestMsg.newBuilder();
//                    Common.BetRequestMsg betRequestMsg = builder.setAmt(10).setGameType(10003).build();
//                    ByteBuf buffer = Unpooled.buffer();
//                    byte[] bytes = betRequestMsg.toByteArray();
//                    buffer.writeInt(10005).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes(bytes);
//                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
//                    ch.writeAndFlush(frame);
//                }
//            });
//            System.out.println("first is sent");
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    Common.ProfileRequestMsg.Builder builder = Common.ProfileRequestMsg.newBuilder();
//                    Common.ProfileRequestMsg profileRequestMsg = builder.build();
//                    ByteBuf buffer = Unpooled.buffer();
//                    byte[] bytes = profileRequestMsg.toByteArray();
//                    buffer.writeInt(10003).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes(bytes);
//                    WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
//                    ch.writeAndFlush(frame);
//                }
//            });
//            System.out.println("second is sent");
//        }
//    }
//}