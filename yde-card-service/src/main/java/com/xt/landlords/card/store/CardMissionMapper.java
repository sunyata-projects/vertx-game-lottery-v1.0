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
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by leo on 17/3/20.
 */
@Mapper
public interface CardMissionMapper {
    @Select("SELECT * FROM mission_cards where lose = #{lose} Limit #{random},1")
    MissionCard findMissionCardsByRandom(@Param("lose") Integer lose, @Param("random") Integer random);

    @Select("SELECT * FROM mission_cards where id = #{id}")
    MissionCard findMissionCardsByCardId(@Param("id") String id);

    @Select("SELECT count(*) FROM mission_cards where lose = #{lose}")
    Integer findMissionCardCount(@Param("lose") int lose);

}