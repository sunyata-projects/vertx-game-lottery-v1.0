package com.xt.landlords;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sunyata.octopus.OctopusResponse;

/**
 * Created by leo on 17/5/18.
 */
public class ExceptionProcessor {
    static Logger logger = LoggerFactory.getLogger(ExceptionProcessor.class);

    public static void process(OctopusResponse response, Throwable throwable) {
        if (throwable instanceof HystrixRuntimeException) {
            response.setErrorCode(CommonCommandErrorCode.RemoteAccessError);
            logger.error(ExceptionUtils.getStackTrace(throwable));
            return;
        }
        response.setErrorCode(CommonCommandErrorCode.InternalError);
    }
}
