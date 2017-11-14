package com.xt.landlords.game.rank.command;

import com.xt.landlords.AbstractGameControllerCommandHandler;
import com.xt.landlords.Commands;
import com.xt.landlords.ExceptionProcessor;
import com.xt.yde.protobuf.rank.GameRank;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;

import java.util.Calendar;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.RankRemainTime)
public class RankRemainTimeCommandHandler extends AbstractGameControllerCommandHandler {
    class CommandErrorCode {

    }

    Logger logger = LoggerFactory.getLogger(RankRemainTimeCommandHandler.class);
    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            GameRank.RankRemaintimeResponseMsg.Builder builder = GameRank.RankRemaintimeResponseMsg.newBuilder();
            //long milliSecondsLeftToday = 86400000 - DateUtils.getFragmentInMilliseconds(Calendar.getInstance(),
            //      Calendar.DATE);
            long secondsLeftToday = 86400 - DateUtils.getFragmentInSeconds(Calendar.getInstance(), Calendar.DATE);
            builder.setRemaintime(secondsLeftToday);
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

