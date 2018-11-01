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

import com.xt.card.model.RegularCard17;
import com.xt.card.model.RegularCard37;
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
public class CardRegularStore {

    Logger logger = LoggerFactory.getLogger(CardRegularStore.class);

    @Autowired
    SqlSessionTemplate sessionTemplate;

    Random random = new Random();

    public RegularCard17 getCard17WithRandom() {
//        long startTime = System.currentTimeMillis();   //获取开始时间
        try (SqlSession session = sessionTemplate.getSqlSessionFactory().openSession(true)) {
            CardRegularMapper mapper = session.getMapper(CardRegularMapper.class);
            Integer regularCard17Count = mapper.findRegularCard17Count();
            int i = random.nextInt(regularCard17Count - 1);
            RegularCard17 regularCard17 = mapper.findRegularCard17ByRandom(i);
            return regularCard17;
        } catch (Exception ex) {
            System.out.println(ex);
            throw ex;
        }
    }

    public RegularCard17 getCard17ById(String id) {
//        long startTime = System.currentTimeMillis();   //获取开始时间
        try (SqlSession session = sessionTemplate.getSqlSessionFactory().openSession(true)) {
            CardRegularMapper mapper = session.getMapper(CardRegularMapper.class);
            RegularCard17 regularCard17 = mapper.findRegularCard17ById(id);
            return regularCard17;
        } catch (Exception ex) {
            System.out.println(ex);
            throw ex;
        }
    }

    public RegularCard37 getCard37(int bombNums, String centerId) {
        try (SqlSession session = sessionTemplate.getSqlSessionFactory().openSession(false)) {
            CardRegularMapper mapper = session.getMapper(CardRegularMapper.class);
            RegularCard37 regularCard37 = mapper.findRegularCard37(bombNums, centerId);
            return regularCard37;
        } catch (Exception ex) {
            System.out.println(ex);
            throw ex;
        }
    }

    public RegularCard37 getCard37ById(String id) {
        try (SqlSession session = sessionTemplate.getSqlSessionFactory().openSession(false)) {
            CardRegularMapper mapper = session.getMapper(CardRegularMapper.class);
            RegularCard37 regularCard37 = mapper.findRegularCard37ById(id);
            return regularCard37;
        } catch (Exception ex) {
            System.out.println(ex);
            throw ex;
        }
    }
}
