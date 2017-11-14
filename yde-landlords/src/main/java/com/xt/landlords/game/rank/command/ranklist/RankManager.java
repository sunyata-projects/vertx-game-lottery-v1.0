package com.xt.landlords.game.rank.command.ranklist;

import org.apache.commons.lang.time.DateUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by leo on 17/10/24.
 */
public class RankManager {

    private static HashMap<String, List<RankInfo>> userRoundDetails = new HashMap<>();
    private static List<RankInfo> rankList = new ArrayList<>();

    static {
        rankList.add(new RankInfo().setDisplayName("玩家" + "0").setScore(new BigDecimal("200")).setMoney(new
                BigDecimal("300000")));
        rankList.add(new RankInfo().setDisplayName("玩家" + "1").setScore(new BigDecimal("198")).setMoney(new
                BigDecimal("200000")));
        rankList.add(new RankInfo().setDisplayName("玩家" + "2").setScore(new BigDecimal("196")).setMoney(new
                BigDecimal("100000")));
        for (int i = 0; i < 27; i++) {
            rankList.add(new RankInfo().setDisplayName("玩家" + (i + 3)).setScore(new BigDecimal(194 - i * 4)).setMoney
                    (BigDecimal.ZERO));

        }
    }

    public static RankInfoWithMe getRankList(String userName) {
        RankInfo rankInfo = rankList.stream().filter(p -> p.getDisplayName().equals(userName)).findFirst().orElse(null);
        int i = rankList.indexOf(rankInfo);
        BigDecimal myScore = BigDecimal.ZERO;
        BigDecimal myMoney = BigDecimal.ZERO;
        if (rankInfo != null) {
            myScore = rankInfo.getScore();
            myMoney = rankInfo.getMoney();
        } else {
            if (userRoundDetails.containsKey(userName)) {
                List<RankInfo> rankInfos = userRoundDetails.get(userName);
                for (int j = 0; j < rankInfos.size(); j++) {
                    myMoney = myMoney.add(rankInfos.get(j).getMoney());
                    myScore = myScore.add(rankInfos.get(j).getScore());
                }
            }
        }
        long secondsLeftToday = 86400 - DateUtils.getFragmentInSeconds(Calendar.getInstance(), Calendar.DATE);
        if(i==-1) {
            return new RankInfoWithMe().setList(rankList).setMyRank(rankList.size() + 1).setMyScore(myScore).setRemainTime
                    (secondsLeftToday);
        }else{
            return new RankInfoWithMe().setList(rankList).setMyRank(i + 1).setMyScore(myScore).setRemainTime
                    (secondsLeftToday);
        }
    }

    public static void saveRoundInfo(String userName, BigDecimal money, BigDecimal score) {
        RankInfo rankInfo = new RankInfo().setDisplayName(userName).setMoney(money).setScore(score);
        if (userRoundDetails.containsKey(userName)) {
            List<RankInfo> rankInfos = userRoundDetails.get(userName);
            rankInfos.add(rankInfo);
        } else {
            List<RankInfo> objects = new ArrayList<>();
            objects.add(rankInfo);
            userRoundDetails.put(userName, objects);
        }
        List<RankInfo> rankInfos = userRoundDetails.get(userName);
        if (rankInfos.size() > 3) {//删除最小的
            RankInfo minRankInfo = rankInfos.stream().min(new Comparator<RankInfo>() {
                @Override
                public int compare(RankInfo o1, RankInfo o2) {
                    return o1.getMoney().subtract(o2.getMoney()).intValue();
                }
            }).orElse(null);
            rankInfos.remove(minRankInfo);
        }

        //更新排行榜
        BigDecimal totalMoney = BigDecimal.ZERO;
        BigDecimal totalScore = BigDecimal.ZERO;
        for (int i = 0; i < rankInfos.size(); i++) {
            totalMoney = totalMoney.add(rankInfos.get(i).getMoney());
            totalScore = totalScore.add(rankInfos.get(i).getScore());
        }
        //把当前用户的排名数据删除掉
        RankInfo rankInfoForUserName = rankList.stream().filter(p -> p.getDisplayName().equals(userName)).findFirst()
                .orElse(null);
        if (rankInfoForUserName != null) {
            rankList.remove(rankInfoForUserName);
        }
        //把当前用户的排名数据插入到列表中
        rankList.add(new RankInfo().setDisplayName(userName).setMoney(totalMoney).setScore(totalScore));

        if (rankList.size() > 30) {
            //删除最小的
            RankInfo minRankInfo = rankList.stream().min(new Comparator<RankInfo>() {
                @Override
                public int compare(RankInfo o1, RankInfo o2) {
                    int i = o1.getScore().subtract(o2.getScore()).intValue();
                    if (i == 0) {
                        return o1.getMoney().subtract(o2.getMoney()).intValue();
                    } else {
                        return i;
                    }
                }
            }).orElse(null);
            rankList.remove(minRankInfo);
        }
        //重新排序
        rankList = rankList.stream().sorted(((o2, o1) -> {
            if (Objects.equals(o1.getScore(), o2.getScore())) {
                return o1.getMoney().subtract(o2.getMoney()).intValue();
            } else {
                return o1.getScore().subtract(o2.getScore()).intValue();
            }
        })).collect(Collectors.toList());

    }
}
