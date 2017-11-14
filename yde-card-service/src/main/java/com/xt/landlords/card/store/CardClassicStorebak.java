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

import com.xt.landlords.card.model.ClassicCard;
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
public class CardClassicStorebak {

    Logger logger = LoggerFactory.getLogger(CardClassicStorebak.class);

    @Autowired
    SqlSessionTemplate sessionTemplate;

    Random random = new Random();

    public ClassicCard getCards(int prizeLevel) {
        long startTime = System.currentTimeMillis();   //获取开始时间
        if (prizeLevel == 8) {
            prizeLevel = 0;
        }
        if (prizeLevel == 6) {
            ClassicCard card = null;
            card = new ClassicCard();
            card
                    .setId("-1")
                    .setC_center("53,52,51,38,25,12,50,37,24,49,48,47,46,45,44,43,42")
                    .setC_under("41,40,39")
                    .setC_left("0,1,2,3,4,5,6,7,8,9,10,11,13,14,15,16,17")
                    .setC_right("18,19,20,21,22,23,26,27,28,29,30,31,32,33,34,35,36");
            return card;
        }
        try (SqlSession session = sessionTemplate.getSqlSessionFactory().openSession(true)) {
            CardClassicMapper mapper = session.getMapper(CardClassicMapper.class);
            Integer count = mapper.findClassicCardCount(prizeLevel);
            int i = random.nextInt(count - 1);
            ClassicCard ClassicCardsByRandom = mapper.findClassicCardsByRandom(prizeLevel, i);
//            MissionCard missionCardsByRandom = null;
//                            missionCardsByRandom = mapper.findMissionCardsByCardId(6595);
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
            return ClassicCardsByRandom;
        } catch (
                Exception ex
                )

        {
            System.out.println(ex);
            throw ex;
        }
    }

    public ClassicCard getCardsById(String id) {
        long startTime = System.currentTimeMillis();   //获取开始时间
        try (SqlSession session = sessionTemplate.getSqlSessionFactory().openSession(true)) {
            CardClassicMapper mapper = session.getMapper(CardClassicMapper.class);
            ClassicCard missionCardsByRandom = null;
            missionCardsByRandom = mapper.findClassicCardsByCardId(id);
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
