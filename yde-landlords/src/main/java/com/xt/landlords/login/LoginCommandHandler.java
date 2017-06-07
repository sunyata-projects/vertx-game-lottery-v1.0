package com.xt.landlords.login;

import com.google.protobuf.InvalidProtocolBufferException;
import com.xt.landlords.*;
import com.xt.landlords.event.LoginEventMessage;
import com.xt.yde.protobuf.common.Common;
import com.xt.yde.thrift.login.AckLoginMsg;
import com.xt.yde.thrift.login.LoginMsg;
import com.xt.yde.thrift.login.LoginResult;
import com.xt.yde.thrift.login.LoginService;
import info.developerblog.spring.thrift.annotation.ThriftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.*;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.store.Store;
import org.sunyata.octopus.store.StoreFactory;

/**
 * Created by leo on 17/4/18.
 */
@Component(value = Commands.Login)
public class LoginCommandHandler extends AbstractCommandHandler {

    Logger logger = LoggerFactory.getLogger(LoginCommandHandler.class);

    @ThriftClient(serviceId = "yde-login-service", path = "/api")
    LoginService.Client loginService;

    @Autowired
    StoreFactory storeFactory;

    @Autowired
    StoreManager storeManager;

    @Autowired
    GameManager gameManager;

    class CommandErrorCode {
        public final static int LoginError = 1011;
    }

    //public static final AttributeKey<String> KEY_USER_ID = AttributeKey.newInstance("USER_ID");

    @Override
    public boolean onExecuteBefore(OctopusRequest request, OctopusResponse response) {
        return true;
    }

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception,
            InvalidProtocolBufferException {

        Common.LoginRequestMsg loginRequest = Common.LoginRequestMsg.parseFrom(request.getMessage().getBody());
        storeManager.storeGameModel(loginRequest.getUserName(), null);
        GameManager.onGameOver(loginRequest.getUserName());
        try {
            AckLoginMsg loginRet = loginService.login(new LoginMsg(loginRequest.getUserName(), loginRequest.getPassword
                    ()));

            if (!loginRet.getCode().equals("000") || !loginRet.getResult().equals(LoginResult.ET_TYPE1)) {
                //logger.debug("取用户验证信息不正确:"+ackLoginD.toString());
                logger.info("用户{}登录失败", loginRequest.getUserName());
                response.setErrorCode(CommandErrorCode.LoginError).writeAndFlush();
                return;
            }
        } catch (Exception ex) {
            logger.error("处理用户登录时发生错误{}", loginRequest.getUserName(), org.apache.commons.lang3.exception
                    .ExceptionUtils.getStackTrace(ex));
            response.setErrorCode(CommandErrorCode.LoginError).writeAndFlush();
            return;
        }

        try {
            //用户是否在本地
            boolean playerOnLocal = false;
            //当前用户状态(在线,离线)
            UserState userStatusFromCache = storeManager.getUserStatusFromCache(loginRequest.getUserName());
            Session session = SessionManager.getSession(loginRequest.getUserName());
            if (session != null) {//用户在本地登录,先踢人,接管session
                playerOnLocal = true;
                gameManager.kickPlayer(loginRequest.getUserName());
                request.getContext().channel().attr(Login.KEY_USER_ID).set(loginRequest.getUserName());
                session.setHandlerContext(request.getContext());
            }

            if (userStatusFromCache == UserState.OnLine && session == null) {//在线并且用户在其它Server
                playerOnLocal = false;
                //踢人(其它gameServer)
                EventBus.getDistributePubsubStore().publish(LoginEventMessage.EventType, new LoginEventMessage
                        (loginRequest.getUserName()).setRequest(request));
            }

            if (!playerOnLocal) {//如果用户没有在线或用户在其它Server,即用户在本机没有登录
                //创建session
                createSession(loginRequest.getUserName(), request);
                //发布本地登录事件
                EventBus.getLocalPubsubStore().publish(LoginEventMessage.EventType, new LoginEventMessage(loginRequest
                        .getUserName
                                ()).setRequest(request));
            }
            GameModel gameModelFromCache = storeManager.getGameModelFromCache(loginRequest.getUserName());

            Common.LoginResponseMsg.Builder builder = Common.LoginResponseMsg.newBuilder();
            if (gameModelFromCache != null && gameModelFromCache.needBreakPlay()) {//用户需要断线重玩
                builder.setNeedBreakPlay(true);
            } else {
                builder.setNeedBreakPlay(false);
                storeManager.storeGameModel(loginRequest.getUserName(), null);
            }
            Common.LoginResponseMsg loginResponseMsg = builder.build();
            response.setBody(loginResponseMsg.toByteArray()).writeAndFlush();
        } catch (Exception ex) {
            response.setErrorCode(CommandErrorCode.LoginError).writeAndFlush();
            throw ex;
        } finally {
        }

    }

    @Override
    public boolean isAsync() {
        return true;
    }

    private void createSession(String userName, OctopusRequest request) throws Exception {
        //存储channel上下文中,当前登录的用户名称
        request.getContext().channel().attr(Login.KEY_USER_ID).set(userName);
        //初始化session
        Store store = storeFactory.createStore(userName);
        RedisSession session = SessionManager.newInstance(userName, store, request.getContext());
        long now = System.currentTimeMillis();
        session.setCreationTime(now);
        session.setLastAccessedTime(now);
        session.setUser(new User(userName));
        request.setSession(session);
        //设置用户在线
        storeManager.storeUserStatus(userName, UserState.OnLine);
    }
}
