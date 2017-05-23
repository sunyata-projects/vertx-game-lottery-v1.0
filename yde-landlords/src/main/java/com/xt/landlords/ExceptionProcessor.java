package com.xt.landlords;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.sunyata.octopus.OctopusResponse;

/**
 * Created by leo on 17/5/18.
 */
public class ExceptionProcessor {
    public static void process(OctopusResponse response, Throwable throwable) {
        if (throwable instanceof HystrixRuntimeException) {
            response.setErrorCode(CommonCommandErrorCode.RemoteAccessError);
            return;
        }
        response.setErrorCode(CommonCommandErrorCode.InternalError);
    }
}
