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

package com.xt.yde.job;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/3/20.
 */
@Component
public class GameModelStore {

    Logger logger = LoggerFactory.getLogger(GameModelStore.class);

    @Autowired
    SqlSessionTemplate sessionTemplate;


    public void create(GameModel instance, GamePhaseModel gamePhaseModel) {
        long startTime = System.currentTimeMillis();   //获取开始时间
        try (SqlSession session = sessionTemplate.getSqlSessionFactory().openSession(false)) {
            GameModelMapper mapper = session.getMapper(GameModelMapper.class);
            mapper.createGameModel(instance);
            mapper.addPhase(gamePhaseModel);
            session.commit();
        } catch (Exception ex) {
            System.out.println(ex);
            throw ex;
        }
    }

    public void addPhase(GamePhaseModel gamePhaseModel) {
        long startTime = System.currentTimeMillis();   //获取开始时间
        try (SqlSession session = sessionTemplate.getSqlSessionFactory().openSession(true)) {
            GameModelMapper mapper = session.getMapper(GameModelMapper.class);
            mapper.addPhase(gamePhaseModel);
            mapper.updateGameModel(gamePhaseModel.getGameInstanceId(), gamePhaseModel.getPhaseName());
        } catch (Exception ex) {
            System.out.println(ex);
            throw ex;
        }
    }
}
