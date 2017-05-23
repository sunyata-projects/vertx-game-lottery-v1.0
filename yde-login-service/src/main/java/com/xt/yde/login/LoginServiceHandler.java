package com.xt.yde.login;

import com.xt.yde.thrift.login.AckLoginMsg;
import com.xt.yde.thrift.login.LoginMsg;
import com.xt.yde.thrift.login.LoginResult;
import com.xt.yde.thrift.login.LoginService;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;
import ru.trylogic.spring.boot.thrift.annotation.ThriftController;
//import ru.trylogic.spring.boot.thrift.annotation.*;
//import ru.*

/**
 * Created by aleksandr on 01.09.15.
 */
@Component
@ThriftController("/api")
public class LoginServiceHandler implements LoginService.Iface {

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
        return new AckLoginMsg().setCode("000").setCoin(111).setDisplayName("lcl").setResult(LoginResult.ET_TYPE1);
    }
}
