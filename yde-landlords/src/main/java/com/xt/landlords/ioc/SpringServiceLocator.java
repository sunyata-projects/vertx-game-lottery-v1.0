/*
 *
 *
 *  * Copyright (c) 2017 Leo Lee(lichl.1980@163.com).
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  * use this file except in compliance with the License. You may obtain a copy
 *  * of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  * License for the specific language governing permissions and limitations
 *  * under the License.
 *  *
 *
 */

package com.xt.landlords.ioc;

import org.sunyata.octopus.CommandHandler;
import org.sunyata.octopus.MethodHandlerLocator;
import org.sunyata.octopus.OctopusRequest;

/**
 * Created by leo on 17/3/15.
 */
public class SpringServiceLocator implements MethodHandlerLocator {


    @Override
    public CommandHandler getMethodHandler(OctopusRequest request) {
        Object bean = SpringIocUtil.getBean(String.valueOf(request.getMessage().getCmd()), CommandHandler.class);
        if (bean != null) {
            return (CommandHandler) bean;
        }
        return null;
    }
}
