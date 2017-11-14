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

import com.xt.landlords.card.model.RankCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by leo on 17/3/20.
 */
@Mapper
public interface CardRankMapper {
    @Select("SELECT * FROM rank_cards where prize_level = #{prize_level} Limit #{random},1")
    RankCard findRankCardsByRandom(@Param("prize_level") Integer prizeLevel, @Param("random") Integer random);

    @Select("SELECT * FROM rank_cards where id = #{id}")
    RankCard findRankCardsByCardId(@Param("id") String id);

    @Select("SELECT count(*) FROM rank_cards where prize_level = #{prize_level}")
    Integer findRankCardCount(@Param("prize_level") int prizeLevel);

}