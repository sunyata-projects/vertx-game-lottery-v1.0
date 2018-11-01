package com.xt.card.model;

/**
 * Created by leo on 17/6/2.
 */
public class MissionCard {
    public String getId() {
        return id;
    }

    public MissionCard setId(String id) {
        this.id = id;
        return this;
    }



    public String getC_right() {
        return c_right;
    }

    public MissionCard setC_right(String c_right) {
        this.c_right = c_right;
        return this;
    }

    public String getC_left() {
        return c_left;
    }

    public MissionCard setC_left(String c_left) {
        this.c_left = c_left;
        return this;
    }

    public String getC_under() {
        return c_under;
    }

    public MissionCard setC_under(String c_under) {
        this.c_under = c_under;
        return this;
    }


    public String getC_center() {
        return c_center;
    }

    public MissionCard setC_center(String c_center) {
        this.c_center = c_center;
        return this;
    }

    public int getPrize_level() {
        return prize_level;
    }

    public MissionCard setPrize_level(int prize_level) {
        this.prize_level = prize_level;
        return this;
    }

    private String id;
    private String c_center;
    private int prize_level;


    private String c_right;
    private String c_left;
    private String c_under;
}
