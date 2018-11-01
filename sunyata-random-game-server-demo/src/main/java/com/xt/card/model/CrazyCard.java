package com.xt.card.model;

/**
 * Created by leo on 17/6/2.
 */
public class CrazyCard extends RegularCard17 {
    private String id;
    private String c_center;
    private String c_center_three;
    private String c_right;
    private String c_right_one;
    private String c_left;
    private String c_left_one;
    private int bomb_nums;
    private int prize_level;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public CrazyCard setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getC_center() {
        return c_center;
    }

    @Override
    public CrazyCard setC_center(String c_center) {
        this.c_center = c_center;
        return this;
    }

    public String getC_center_three() {
        return c_center_three;
    }

    public CrazyCard setC_center_three(String c_center_three) {
        this.c_center_three = c_center_three;
        return this;
    }

    public String getC_right() {
        return c_right;
    }

    public CrazyCard setC_right(String c_right) {
        this.c_right = c_right;
        return this;
    }

    public String getC_right_one() {
        return c_right_one;
    }

    public CrazyCard setC_right_one(String c_right_one) {
        this.c_right_one = c_right_one;
        return this;
    }

    public String getC_left() {
        return c_left;
    }

    public CrazyCard setC_left(String c_left) {
        this.c_left = c_left;
        return this;
    }

    public String getC_left_one() {
        return c_left_one;
    }

    public CrazyCard setC_left_one(String c_left_one) {
        this.c_left_one = c_left_one;
        return this;
    }

    public int getBomb_nums() {
        return bomb_nums;
    }

    public CrazyCard setBomb_nums(int bomb_nums) {
        this.bomb_nums = bomb_nums;
        return this;
    }

    public int getPrize_level() {
        return prize_level;
    }

    public CrazyCard setPrize_level(int prize_level) {
        this.prize_level = prize_level;
        return this;
    }
}
