package com.xt.landlords.example;

import com.xt.landlords.AbstractCommandHandler;
import org.springframework.stereotype.Component;

/**
 * Created by leo on 17/4/18.
 */
@Component("8888")
public class TestCommandHandler extends AbstractCommandHandler {
//    @Override
//    public void run(OctopusRequest request, OctopusResponse response) {
//        response.getMessage().setBody("test".getBytes());
//        response.writeAndFlush();
//    }

    @Override
    public boolean isAsync() {
        return true;
    }

}