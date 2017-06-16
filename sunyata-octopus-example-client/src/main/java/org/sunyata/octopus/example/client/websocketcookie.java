package org.sunyata.octopus.example.client;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslHandler;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;

class CustomWebSocketServerProtocolHandshakeHandler extends ChannelInboundHandlerAdapter {

    private final String websocketPath;
    private final String subprotocols;
    private final boolean allowExtensions;
    private final int maxFramePayloadSize;
    private final boolean allowMaskMismatch;
    static final MethodHandle setHandshakerMethod = getSetHandshakerMethod();
    static final MethodHandle forbiddenHttpRequestResponderMethod = getForbiddenHttpRequestResponderMethod();

    static MethodHandle getSetHandshakerMethod() {
        try {
            Method method = WebSocketServerProtocolHandler.class.getDeclaredMethod("setHandshaker"
                    , Channel.class
                    , WebSocketServerHandshaker.class
            );
            method.setAccessible(true);

            return MethodHandles.lookup().unreflect(method);
        } catch (Throwable e) {
            // Should never happen
            e.printStackTrace();
            System.exit(5);
            return null;
        }
    }

    static MethodHandle getForbiddenHttpRequestResponderMethod() {
        try {
            Method method = WebSocketServerProtocolHandler.class.getDeclaredMethod("forbiddenHttpRequestResponder");
            method.setAccessible(true);

            return MethodHandles.lookup().unreflect(method);
        } catch (Throwable e) {
            // Should never happen
            e.printStackTrace();
            System.exit(6);
            return null;
        }
    }

    public CustomWebSocketServerProtocolHandshakeHandler(String websocketPath, String subprotocols,
                                                         boolean allowExtensions, int maxFrameSize, boolean
                                                                 allowMaskMismatch) {
        this.websocketPath = websocketPath;
        this.subprotocols = subprotocols;
        this.allowExtensions = allowExtensions;
        maxFramePayloadSize = maxFrameSize;
        this.allowMaskMismatch = allowMaskMismatch;
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest req = (FullHttpRequest) msg;
        if (!websocketPath.equals(req.uri())) {
            ctx.fireChannelRead(msg);
            return;
        }

        try {
            if (req.method() != HttpMethod.GET) {
                sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
                return;
            }

            final WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                    getWebSocketLocation(ctx.pipeline(), req, websocketPath), subprotocols,
                    allowExtensions, maxFramePayloadSize, allowMaskMismatch);
            final WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {


                Channel channel = ctx.channel();
                final ChannelFuture handshakeFuture = handshaker.handshake(channel, req, getResponseHeaders(req),
                        channel.newPromise());

                handshakeFuture.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            ctx.fireExceptionCaught(future.cause());
                        } else {
                            ctx.fireUserEventTriggered(
                                    WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE);
                        }
                    }
                });


                try {
                    setHandshakerMethod.invokeExact(ctx.channel(), handshaker);

                    ChannelHandler handler = (ChannelHandler) forbiddenHttpRequestResponderMethod.invokeExact();
                    ctx.pipeline().replace(this, "WS403Responder", handler);

                } catch (Throwable e) {
                    // Should never happen
                    e.printStackTrace();
                    System.exit(7);
                }
            }
        } finally {
            req.release();
        }
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static String getWebSocketLocation(ChannelPipeline cp, HttpRequest req, String path) {
        String protocol = "ws";
        if (cp.get(SslHandler.class) != null) {
            // SSL in use so use Secure WebSockets
            protocol = "wss";
        }
        return protocol + "://" + req.headers().get(HttpHeaderNames.HOST) + path;
    }

    private static HttpHeaders getResponseHeaders(FullHttpRequest req) {
        final String cookieName = "cid";
        final DefaultHttpHeaders httpHeaders = new DefaultHttpHeaders();

        String connectionID = null;
        String cookieString = req.headers().get(HttpHeaderNames.COOKIE);
        if (cookieString != null && cookieString.length() > 0) {
            Set<io.netty.handler.codec.http.cookie.Cookie> cookies = ServerCookieDecoder.LAX.decode(cookieString);
            for (io.netty.handler.codec.http.cookie.Cookie cookie : cookies) {
                if (cookieName.equalsIgnoreCase(cookie.name())) {
                    connectionID = cookie.value();
                    break;
                }
            }
        }
        if (connectionID == null || connectionID.length() < 16 || connectionID.length() > 50) {
            connectionID = UUID.randomUUID().toString().replaceAll("-", "");
        }


        io.netty.handler.codec.http.cookie.DefaultCookie cookie = new io.netty.handler.codec.http.cookie
                .DefaultCookie("cid", connectionID);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);

        httpHeaders.add(HttpHeaderNames.SET_COOKIE, io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode
                (cookie));
        return httpHeaders;
    }
}