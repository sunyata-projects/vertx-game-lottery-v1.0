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

package com.xt.card.store;

import com.xt.card.model.EliminateCard;
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
public class CardEliminateStore {

    Logger logger = LoggerFactory.getLogger(CardEliminateStore.class);

    @Autowired
    SqlSessionTemplate sessionTemplate;

    Random random = new Random();

    public EliminateCard getCards(int prizeLevel, int bombNums) {
        try (SqlSession session = sessionTemplate.getSqlSessionFactory().openSession(true)) {
            CardEliminateMapper mapper = session.getMapper(CardEliminateMapper.class);
            Integer cardsCount = mapper.getCardsCount(bombNums, prizeLevel);
            int i = random.nextInt(cardsCount - 1);
            EliminateCard cards = mapper.getCards(bombNums, prizeLevel, i);
            return cards;
        } catch (Exception ex) {
            System.out.println(ex);
            throw ex;
        }
    }
}
