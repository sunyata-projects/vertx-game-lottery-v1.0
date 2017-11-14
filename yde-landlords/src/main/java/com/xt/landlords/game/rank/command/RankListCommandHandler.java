package com.xt.landlords.game.rank.command;

import com.xt.landlords.AbstractGameControllerCommandHandler;
import com.xt.landlords.Commands;
import com.xt.landlords.ExceptionProcessor;
import com.xt.landlords.game.rank.command.ranklist.RankInfo;
import com.xt.landlords.game.rank.command.ranklist.RankInfoWithMe;
import com.xt.landlords.game.rank.command.ranklist.RankManager;
import com.xt.yde.protobuf.rank.GameRank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;

import java.util.Objects;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.RankList)
public class RankListCommandHandler extends AbstractGameControllerCommandHandler {
    class CommandErrorCode {

    }

    Logger logger = LoggerFactory.getLogger(RankListCommandHandler.class);

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            GameRank.RankListResponseMsg.Builder builder = GameRank.RankListResponseMsg.newBuilder();
//            long secondsLeftToday = 86400 - DateUtils.getFragmentInSeconds(Calendar.getInstance(), Calendar.DATE);
//            for (int i = 0; i < 30; i++) {
//                GameRank.RankListData.Builder b = GameRank.RankListData.newBuilder();
//                b.setMoney(new BigDecimal(30 - i).toPlainString());
//                b.setScore(new BigDecimal(30 - i).multiply(new BigDecimal("10")).intValue());
//                b.setDisplayName("玩家" + i);
//                GameRank.RankListData build = b.build();
//                builder.addItem(build);
//            }
//            builder.setMyrank(2).setRemaintime(secondsLeftToday).setMyscore(29);
            RankInfoWithMe rankList = RankManager.getRankList(request.getSession().getCurrentUser().getName());
            int oldRank = 1;
            int oldScore = rankList.getList().get(0).getScore().intValue();
            for (int i = 0; i < rankList.getList().size(); i++) {
                RankInfo rankInfo = rankList.getList().get(i);
                GameRank.RankListData.Builder b = GameRank.RankListData.newBuilder();

                if (!Objects.equals(oldScore, rankInfo.getScore().intValue())) {
                    oldRank = oldRank + 1;
                }
                b.setRank(oldRank);
                if (oldRank == 1) {
                    b.setMoney("300000");
                } else if (oldRank == 2) {
                    b.setMoney("200000");
                } else if (oldRank == 3) {
                    b.setMoney("100000");
                } else {
                    b.setMoney("0");
                }


                oldScore = rankInfo.getScore().intValue();
                b.setScore(rankInfo.getScore().intValue());
                b.setDisplayName(rankInfo.getDisplayName());

                GameRank.RankListData build = b.build();
                builder.addItem(build);
            }
            builder.setMyrank(rankList.getMyRank()).setRemaintime(rankList.getRemainTime()).setMyscore(rankList
                    .getMyScore().intValue());
            response.setBody(builder.build().toByteArray());
        } catch (Exception ex) {
            ExceptionProcessor.process(response, ex);
        } finally {
            response.writeAndFlush();
        }
    }


    @Override
    public boolean isAsync() {
        return false;
    }
}

