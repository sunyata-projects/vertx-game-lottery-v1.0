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

import com.xt.landlords.card.model.PuzzleCard;
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
public class CardPuzzleStore {

    Logger logger = LoggerFactory.getLogger(CardPuzzleStore.class);

    @Autowired
    SqlSessionTemplate sessionTemplate;

    Random random = new Random();

    public PuzzleCard getWZ() {
        long startTime = System.currentTimeMillis();   //获取开始时间
        try (SqlSession session = sessionTemplate.getSqlSessionFactory().openSession(true)) {
            CardPuzzleMapper mapper = session.getMapper(CardPuzzleMapper.class);
            Integer count = mapper.findWZCardCount();
            int i = random.nextInt(count - 1);
            PuzzleCard missionCardsByRandom = mapper.findWZCardsByRandom(i);
            return missionCardsByRandom;
        } catch (Exception ex) {
            System.out.println(ex);
            throw ex;
        }
    }

    public PuzzleCard get22() {
        long startTime = System.currentTimeMillis();   //获取开始时间
        try (SqlSession session = sessionTemplate.getSqlSessionFactory().openSession(true)) {
            CardPuzzleMapper mapper = session.getMapper(CardPuzzleMapper.class);
            Integer count = mapper.find22CardCount();
            int i = random.nextInt(count - 1);
            PuzzleCard missionCardsByRandom = mapper.find22CardsByRandom(i);
            return missionCardsByRandom;
        } catch (Exception ex) {
            System.out.println(ex);
            throw ex;
        }
    }


    public PuzzleCard getNormal(int grade) {
        long startTime = System.currentTimeMillis();   //获取开始时间
        try (SqlSession session = sessionTemplate.getSqlSessionFactory().openSession(true)) {
            CardPuzzleMapper mapper = session.getMapper(CardPuzzleMapper.class);
            Integer count = mapper.findNormalCount(grade);
            int i = random.nextInt(count - 1);
            PuzzleCard missionCardsByRandom = mapper.findNormalCardsByRandom(grade, i);
            return missionCardsByRandom;
        } catch (Exception ex) {
            System.out.println(ex);
            throw ex;
        }
    }
}
