package com.xt.landlords.game.rank.command;

import com.xt.landlords.AbstractGameControllerCommandHandler;
import com.xt.landlords.Commands;
import com.xt.landlords.ExceptionProcessor;
import com.xt.yde.protobuf.rank.GameRank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.RankHistoryList)
public class RankHistoryListCommandHandler extends AbstractGameControllerCommandHandler {
    class CommandErrorCode {

    }

    Logger logger = LoggerFactory.getLogger(RankHistoryListCommandHandler.class);

    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getStringDate(int i) throws ParseException {
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - i);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        return dft.format(date.getTime());
    }

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            GameRank.HistoryRankListResponseMsg.Builder builder = GameRank.HistoryRankListResponseMsg.newBuilder();
            //long secondsLeftToday = 86400 - DateUtils.getFragmentInSeconds(Calendar.getInstance(), Calendar.DATE);
            for (int j = 0; j < 10; j++) {
                GameRank.HistoryRankListRow.Builder historyRow = GameRank.HistoryRankListRow.newBuilder();
                historyRow.setTime(getStringDate(j));
                for (int i = 0; i < 3; i++) {
                    GameRank.RankListData.Builder b = GameRank.RankListData.newBuilder();
                    b.setMoney(new BigDecimal(30 - i).toPlainString());
                    b.setScore(new BigDecimal(30 - i).multiply(new BigDecimal("10")).intValue());
                    b.setDisplayName("玩家" + i);
                    GameRank.RankListData build = b.build();
                    historyRow.addItem(build);
                }
                builder.addItem(historyRow);
            }
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

