package com.xt.yde.login.example;

import com.xt.yde.thrift.example.TGreetingService;
import com.xt.yde.thrift.example.TName;
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
public class TGreetingServiceHandler implements TGreetingService.Iface {

    @Override
    public String greet(TName name) throws TException {
        StringBuilder result = new StringBuilder();

        result.append("Hello ");

        if (name.isSetStatus()) {
            result.append(org.springframework.util.StringUtils.capitalize(name.getStatus().name().toLowerCase()));
            result.append(" ");
        }

        result.append(name.getFirstName());
        result.append(" ");
        result.append(name.getSecondName());

        return result.toString();
    }
}
