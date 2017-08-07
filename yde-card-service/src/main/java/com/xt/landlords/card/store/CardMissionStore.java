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

package com.xt.landlords.card.store;

import com.xt.landlords.card.model.MissionCard;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by leo on 17/3/20.
 */
@Component
public class CardMissionStore {

    Logger logger = LoggerFactory.getLogger(CardMissionStore.class);

    @Autowired
    SqlSessionTemplate sessionTemplate;

    Random random = new Random();

    public MissionCard getCards(boolean lose, int missionIndex) {
        long startTime = System.currentTimeMillis();   //获取开始时间
        try (SqlSession session = sessionTemplate.getSqlSessionFactory().openSession(true)) {
            CardMissionMapper mapper = session.getMapper(CardMissionMapper.class);
            Integer count = mapper.findMissionCardCount(lose ? 1 : 0);
            int i = random.nextInt(count - 1);
//            MissionCard missionCardsByRandom = mapper.findMissionCardsByRandom(lose ? 1 : 0, i);
            MissionCard missionCardsByRandom = null;
                            missionCardsByRandom = mapper.findMissionCardsByCardId(6595);
//            if (missionIndex == 0) {
//                missionCardsByRandom = mapper.findMissionCardsByCardId(22591);
//            }
// else if (missionIndex == 1) {
//                missionCardsByRandom = mapper.findMissionCardsByCardId(22591);
//
//                //missionCardsByRandom = mapper.findMissionCardsByCardId(22591);
//
//            } else if (missionIndex == 2) {
//                missionCardsByRandom = mapper.findMissionCardsByCardId(22229);
//
//            }
//            else if (missionIndex == 3) {
//                missionCardsByRandom = mapper.findMissionCardsByCardId(22593);
//            }
//            else if (missionIndex == 4) {
//                missionCardsByRandom = mapper.findMissionCardsByCardId(22594);
//            }
//            else{
//                missionCardsByRandom = mapper.findMissionCardsByCardId(22595);
//            }
            return missionCardsByRandom;
        } catch (
                Exception ex
                )

        {
            System.out.println(ex);
            throw ex;
        }
    }
}
