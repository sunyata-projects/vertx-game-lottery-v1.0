package com.xt.landlords.service;

import com.xt.landlords.game.phase.TicketResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by leo on 17/9/5.
 */
@Component
public class RankPrizeManager {
    List<RankPrizeLevelInfo> rankPrizeLevelInfos = new ArrayList<>();

    public RankPrizeManager() {


        rankPrizeLevelInfos.add(new RankPrizeLevelInfo().setPrizeLevel(1).setScore(new BigDecimal("100")).setMoney
                (new BigDecimal("100")).setBombNumbers(6));
        rankPrizeLevelInfos.add(new RankPrizeLevelInfo().setPrizeLevel(2).setScore(new BigDecimal("64")).setMoney
                (new BigDecimal("50")).setBombNumbers(5));
        rankPrizeLevelInfos.add(new RankPrizeLevelInfo().setPrizeLevel(3).setScore(new BigDecimal("32")).setMoney
                (new BigDecimal("10")).setBombNumbers(4));
        rankPrizeLevelInfos.add(new RankPrizeLevelInfo().setPrizeLevel(4).setScore(new BigDecimal("16")).setMoney
                (new BigDecimal("5")).setBombNumbers(3));
        rankPrizeLevelInfos.add(new RankPrizeLevelInfo().setPrizeLevel(5).setScore(new BigDecimal("8")).setMoney
                (new BigDecimal("3")).setBombNumbers(2));
        rankPrizeLevelInfos.add(new RankPrizeLevelInfo().setPrizeLevel(6).setScore(new BigDecimal("4")).setMoney
                (new BigDecimal("1.5")).setBombNumbers(1));
        rankPrizeLevelInfos.add(new RankPrizeLevelInfo().setPrizeLevel(7).setScore(new BigDecimal("2")).setMoney
                (new BigDecimal("1")).setBombNumbers(0));
        rankPrizeLevelInfos.add(new RankPrizeLevelInfo().setPrizeLevel(8).setScore(new BigDecimal("0")).setMoney
                (new BigDecimal("0")).setBombNumbers(0));
    }

    RankPrizeLevelInfo getRankPrizeLevelInfo(int prizeLevel) {
        RankPrizeLevelInfo rankPrizeLevelInfo = rankPrizeLevelInfos.stream().filter(p -> p.getPrizeLevel() ==
                prizeLevel).findFirst().orElse(null);
        return rankPrizeLevelInfo;
    }

    public TurnResultInfo getTurnMoneyAndScore(float prizeLevel, boolean isWin, Integer currentBombNumbers, BigDecimal
            betAmt) {
        BigDecimal times = betAmt.divide(new BigDecimal("3"));
        RankPrizeLevelInfo rankPrizeLevelInfo = rankPrizeLevelInfos.stream().filter(p -> p.getPrizeLevel() ==
                prizeLevel).findFirst().orElse(null);
        BigDecimal totalMoney = rankPrizeLevelInfo.getMoney().multiply(times);
        BigDecimal totalMcore = rankPrizeLevelInfo.getScore();
        RankPrizeLevelInfo maxRankPrizeLevelInfo = null;
        if (isWin) {
            maxRankPrizeLevelInfo = rankPrizeLevelInfos.stream().filter(p -> currentBombNumbers >= p
                    .getBombNumbers()).max(new Comparator<RankPrizeLevelInfo>() {
                @Override
                public int compare(RankPrizeLevelInfo o1, RankPrizeLevelInfo o2) {
                    return o1.getBombNumbers() - o2.getBombNumbers();
                }
            }).orElse(null);
        } else {
            maxRankPrizeLevelInfo = new RankPrizeLevelInfo().setPrizeLevel(-1).setMoney(BigDecimal.ZERO).setScore
                    (BigDecimal.ZERO);
        }
        return new TurnResultInfo().setMoney(totalMoney.subtract(maxRankPrizeLevelInfo.getMoney().multiply(times)))
                .setScore
                        (totalMcore.subtract(maxRankPrizeLevelInfo.getScore()));
    }

    public BigDecimal getScore(float prizeLevel) {
        RankPrizeLevelInfo rankPrizeLevelInfo = rankPrizeLevelInfos.stream().filter(p -> p.getPrizeLevel() ==
                prizeLevel).findFirst().orElse(null);
        return rankPrizeLevelInfo.getScore();
    }

    public BigDecimal getTotalScore(List<TicketResult> ticketResults) {
        BigDecimal totalScore = BigDecimal.ZERO;
        List<Integer> collect = ticketResults.stream().map(p -> (int) p.getPrizeLevel()).collect(Collectors.toList());
        for (int i = 0; i < collect.size(); i++) {
            int prizeLevel = collect.get(i);
            RankPrizeLevelInfo rankPrizeLevelInfo = rankPrizeLevelInfos.stream().filter(p -> prizeLevel == p
                    .getPrizeLevel()).findFirst().orElse(null);
            totalScore = totalScore.add(rankPrizeLevelInfo.getScore());
        }
        return totalScore;
    }

    public BigDecimal getTotalMoney(List<TicketResult> ticketResults, BigDecimal betAmt) {
        List<Integer> collect = ticketResults.stream().map(p -> (int) p.getPrizeLevel()).collect(Collectors.toList());
        //int money = rankPrizeLevelInfos.stream().filter(p -> collect.contains(p.getPrizeLevel())).mapToInt(p -> p
        //.getMoney().intValue()).sum();
        BigDecimal times = betAmt.divide(new BigDecimal("3"));
        BigDecimal money = BigDecimal.ZERO;
        for (int i = 0; i < collect.size(); i++) {
            Integer prizeLevel = collect.get(i);
            RankPrizeLevelInfo rankPrizeLevelInfo = rankPrizeLevelInfos.stream().filter(p -> p.getPrizeLevel() ==
                    prizeLevel).findFirst().orElse(null);
            if (rankPrizeLevelInfo != null) {
                money = money.add(rankPrizeLevelInfo.getMoney().multiply(times));
            }
        }
        return money;
    }
}
