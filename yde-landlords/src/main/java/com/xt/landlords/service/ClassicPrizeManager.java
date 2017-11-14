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
public class ClassicPrizeManager {
    List<RankPrizeLevelInfo> Infos = new ArrayList<>();

    public ClassicPrizeManager() {


        Infos.add(new RankPrizeLevelInfo().setPrizeLevel(1).setMoney(new BigDecimal("10")).setBombNumbers(6));
        Infos.add(new RankPrizeLevelInfo().setPrizeLevel(2).setMoney(new BigDecimal("3.0")).setBombNumbers(4));
        Infos.add(new RankPrizeLevelInfo().setPrizeLevel(3).setMoney(new BigDecimal("2.0")).setBombNumbers(2));
        Infos.add(new RankPrizeLevelInfo().setPrizeLevel(4).setMoney(new BigDecimal("1.5")).setBombNumbers(0));
        Infos.add(new RankPrizeLevelInfo().setPrizeLevel(5).setMoney(new BigDecimal("0")).setBombNumbers(0));
    }

    RankPrizeLevelInfo getRankPrizeLevelInfo(int prizeLevel) {
        RankPrizeLevelInfo rankPrizeLevelInfo = Infos.stream().filter(p -> p.getPrizeLevel() ==
                prizeLevel).findFirst().orElse(null);
        return rankPrizeLevelInfo;
    }

    public TurnResultInfo getTurnMoneyAndScore(int betAmt, float prizeLevel, boolean isWin, Integer
            currentBombNumbers) {
        RankPrizeLevelInfo rankPrizeLevelInfo = Infos.stream().filter(p -> p.getPrizeLevel() ==
                prizeLevel).findFirst().orElse(null);
        BigDecimal haha = new BigDecimal(betAmt).divide(new BigDecimal("1"));
        BigDecimal totalMoney = rankPrizeLevelInfo.getMoney().multiply(haha);
        RankPrizeLevelInfo maxRankPrizeLevelInfo = null;
        if (isWin) {
            maxRankPrizeLevelInfo = Infos.stream().filter(p -> currentBombNumbers >= p
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
        BigDecimal subtract = totalMoney.subtract(maxRankPrizeLevelInfo.getMoney().multiply(haha));
        if (subtract.compareTo(BigDecimal.ZERO) == -1) {
            subtract = BigDecimal.ZERO;
        }
        return new TurnResultInfo().setMoney(subtract);
    }

    public BigDecimal getScore(float prizeLevel) {
        RankPrizeLevelInfo rankPrizeLevelInfo = Infos.stream().filter(p -> p.getPrizeLevel() ==
                prizeLevel).findFirst().orElse(null);
        return rankPrizeLevelInfo.getScore();
    }

    public BigDecimal getTotalScore(List<TicketResult> ticketResults) {
        List<Integer> collect = ticketResults.stream().map(p -> (int) p.getPrizeLevel()).collect(Collectors.toList());
        int score = Infos.stream().filter(p -> collect.contains(p
                .getPrizeLevel())).mapToInt(p -> p.getScore().intValue()).sum();
        return new BigDecimal(score);
    }

    public BigDecimal getTotalMoney(List<TicketResult> ticketResults) {
        List<Integer> collect = ticketResults.stream().map(p -> (int) p.getPrizeLevel()).collect(Collectors.toList());
        int money = Infos.stream().filter(p -> collect.contains(p
                .getPrizeLevel())).mapToInt(p -> p.getMoney().intValue()).sum();
        return new BigDecimal(money);
    }
}
