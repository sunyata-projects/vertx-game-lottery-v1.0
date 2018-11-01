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

import com.xt.card.model.PuzzleCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by leo on 17/3/20.
 */
@Mapper
public interface CardPuzzleMapper {
//    @Select("SELECT * FROM mission_cards where lose = #{lose} Limit #{random},1")
//    MissionCard findMissionCardsByRandom(@Param("lose") Integer lose, @Param("random") Integer random);
//
//    @Select("SELECT count(*) FROM mission_cards where lose = #{lose}")
//    Integer findMissionCardCount(@Param("lose") int lose);

    @Select("SELECT count(*) FROM puzzle_nuke_cards")
    Integer findWZCardCount();

    @Select("SELECT * FROM puzzle_nuke_cards Limit #{random},1")
    PuzzleCard findWZCardsByRandom(@Param("random") int random);

    @Select("SELECT count(*) FROM puzzle_two_cards where two_number>1")
    Integer find22CardCount();

    @Select("SELECT * FROM puzzle_two_cards  where two_number>1  Limit #{random},1")
    PuzzleCard find22CardsByRandom(@Param("random") int random);


    @Select("SELECT count(*) FROM puzzle_cards where prize_level=#{grade}")
    Integer findNormalCount(@Param("grade") int grade);

    @Select("SELECT * FROM puzzle_cards where prize_level = #{grade} Limit #{random},1")
    PuzzleCard findNormalCardsByRandom(@Param("grade") int grade, @Param("random")int random);
}