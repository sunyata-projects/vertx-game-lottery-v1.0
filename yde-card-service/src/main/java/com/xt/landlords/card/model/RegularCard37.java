package com.xt.landlords.card.model;

/**
 * Created by leo on 17/6/2.
 */
public class RegularCard37 {
    public String getId() {
        return id;
    }

    public RegularCard37 setId(String id) {
        this.id = id;
        return this;
    }



    public String getC_right() {
        return c_right;
    }

    public RegularCard37 setC_right(String c_right) {
        this.c_right = c_right;
        return this;
    }

    public String getC_left() {
        return c_left;
    }

    public RegularCard37 setC_left(String c_left) {
        this.c_left = c_left;
        return this;
    }

    public String getC_under() {
        return c_under;
    }

    public RegularCard37 setC_under(String c_under) {
        this.c_under = c_under;
        return this;
    }


    public String getCenter_id() {
        return center_id;
    }

    public RegularCard37 setCenter_id(String center_id) {
        this.center_id = center_id;
        return this;
    }

    public int getPrize_level() {
        return prize_level;
    }

    public RegularCard37 setPrize_level(int prize_level) {
        this.prize_level = prize_level;
        return this;
    }

    private String id;
    private String center_id;
    private int prize_level;


    private String c_right;
    private String c_left;
    private String c_under;
}
