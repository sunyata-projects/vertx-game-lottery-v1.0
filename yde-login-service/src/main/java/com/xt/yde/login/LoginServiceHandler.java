package com.xt.yde.login;

import com.xt.yde.thrift.login.AckLoginMsg;
import com.xt.yde.thrift.login.LoginMsg;
import com.xt.yde.thrift.login.LoginResult;
import com.xt.yde.thrift.login.LoginService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
//import ru.trylogic.spring.boot.thrift.annotation.*;
//import ru.*

/**
 * Created by aleksandr on 01.09.15.
 */
@Service
//@ThriftController("/api")
public class LoginServiceHandler implements LoginService.Iface {

    Logger logger = LoggerFactory.getLogger(LoginServiceHandler.class);
//    @Override
//    public String greet(TName name) throws TException {
//        StringBuilder result = new StringBuilder();
//
//        result.append("Hello ");
//
//        if (name.isSetStatus()) {
//            result.append(org.springframework.util.StringUtils.capitalize(name.getStatus().name().toLowerCase()));
//            result.append(" ");
//        }
//
//        result.append(name.getFirstName());
//        result.append(" ");
//        result.append(name.getSecondName());
//
//        return result.toString();
//    }

    @Override
    public AckLoginMsg login(LoginMsg loginMsg) throws TException {
        logger.info("处理登录请求:userName->{},password->{}", loginMsg.getUserId(), loginMsg.getCert());
        return new AckLoginMsg().setCode("000").setCoin(111).setDisplayName("lcl").setResult(LoginResult.ET_TYPE1);
    }
}
