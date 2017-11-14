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

import com.xt.landlords.card.model.RegularCard17;
import com.xt.landlords.card.model.RegularCard37;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by leo on 17/3/20.
 */
@Mapper
public interface CardRegularMapper {
//    @Select("SELECT * FROM regular_cards_37 WHERE center_id = #{centerId} and prize_level=#{prizeLevel}")
//    RegularCard37 findRegularCard37(@Param("prizeLevel") Integer prizeLevel, @Param("centerId") String centerId);

    @Select("SELECT * FROM regular_cards_37 WHERE center_id = #{centerId} and prize_level=#{bombNums} Limit 1")
    RegularCard37 findRegularCard37(@Param("bombNums") Integer bombNums, @Param("centerId") String centerId);

    @Select("SELECT * FROM regular_cards_37 WHERE id = #{id}")
    RegularCard37 findRegularCard37ById(@Param("id") String id);


    @Select("SELECT * FROM regular_cards_17 where id=#{id}")
    RegularCard17 findRegularCard17ById(@Param("id") String id);

    @Select("SELECT * FROM regular_cards_17 Limit #{random},1")
    RegularCard17 findRegularCard17ByRandom(@Param("random") Integer random);

    @Select("SELECT count(*) FROM regular_cards_17")
    Integer findRegularCard17Count();

}