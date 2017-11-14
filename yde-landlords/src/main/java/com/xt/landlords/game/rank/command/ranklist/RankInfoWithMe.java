package com.xt.landlords.game.rank.command.ranklist;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by leo on 17/10/24.
 */
public class RankInfoWithMe {
    public BigDecimal getMyScore() {
        return myScore;
    }

    public RankInfoWithMe setMyScore(BigDecimal myScore) {
        this.myScore = myScore;
        return this;
    }

    public int getMyRank() {
        return myRank;
    }

    public RankInfoWithMe setMyRank(int myMoney) {
        this.myRank = myMoney;
        return this;
    }

    public long getRemainTime() {
        return remainTime;
    }

    public RankInfoWithMe setRemainTime(long remainTime) {
        this.remainTime = remainTime;
        return this;
    }

    public List<RankInfo> getList() {
        return list;
    }

    public RankInfoWithMe setList(List<RankInfo> list) {
        this.list = list;
        return this;
    }

    private BigDecimal myScore;
    private int myRank;
    private long remainTime;
    private List<RankInfo> list;
}
