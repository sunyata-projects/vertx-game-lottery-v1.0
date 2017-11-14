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

import com.xt.landlords.card.model.CrazyCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by leo on 17/3/20.
 */
@Component
public class CardCrazyStore {
    Logger logger = LoggerFactory.getLogger(CardCrazyStore.class);
    @Autowired
    CardCrazyMapper mapper;
    Random random = new Random();
    public boolean is50Percent() {
        int i = nextInt(1, 2);
        return i == 1;
    }

    public int nextInt(int from, int to) {
        int max = to;
        int min = from;
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
    public CrazyCard getCards(int prizeLevel) {
        logger.info("疯狂赛PrizeLevel:{}", prizeLevel);
        if(prizeLevel==8){
            if(is50Percent()){
                prizeLevel = 9;
            }
        }
        Integer c = mapper.getCardsCount(prizeLevel);
        int i = random.nextInt(c - 1);
        CrazyCard cards = mapper.getCards(prizeLevel, i);
        logger.info("疯狂赛牌库Id:{}", cards.getId());
        return cards;
    }


    public CrazyCard getCardsById(String id) {
        logger.info("疯狂赛id:{}", id);
        CrazyCard cards = mapper.getCardsById(id);
        logger.info("疯狂赛牌库Id:{}", cards.getId());
        return cards;
    }
}
