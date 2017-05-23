package com.xt.yde.job.message;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xt.yde.job.GameModelStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunyata.octopus.json.DecodeException;
import org.sunyata.octopus.message.ComplexMessageInfo;
import org.sunyata.octopus.message.MessageInfoType;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.model.GamePhaseModel;
import org.sunyata.octopus.model.PhaseState;

/**
 * Created by leo on 17/5/10.
 */

@Service
public class RetryProcessMessageService {
    final Logger logger = LoggerFactory.getLogger(RetryProcessMessageService.class);

    public RetryProcessMessageService() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Autowired()
    GameModelStore gameModelStore;
    ObjectMapper objectMapper = new ObjectMapper();

    //@Retryable(value = {Exception.class}, maxAttempts = 5, backoff = @Backoff(delay = 10000l, multiplier = 2))
    public void process(ComplexMessageInfo jobInfo) throws Exception {
        try {
            assert jobInfo != null;
            MessageInfoType messageInfoType = MessageInfoType.from(jobInfo.getJobInfoType());
            if (messageInfoType == MessageInfoType.SyncGameModel) {
                String bodyJsonString = jobInfo.getBodyJsonString();
                DefaultGameModel info = objectMapper.readValue(bodyJsonString, DefaultGameModel.class);
                saveToDb(info);
            } else if (messageInfoType == MessageInfoType.SyncLoginLog) {
                String bodyJsonString = jobInfo.getBodyJsonString();
            }
        } catch (DecodeException ex) {
            throw ex;
        } catch (Exception ex) {
            //logger.error("消息处理失败:" + ExceptionUtils.getStackTrace(ex));
            throw ex;
        }
    }

    private void saveToDb(GameModel info) {
        //Game game1 = new Game(info.getGameType(), info.getGameInstanceId());
        //game1.setUserName(info.getUserName()).setCreateDateTime(info.getCreateDateTime());
        GameModel gameModel = info;
        int count = gameModel.getPhases().size();
        ;
        if (count == 0) {
            logger.error("gameModel非法,不存在任何一个游戏阶段");
            return;
        }
        if (count == 1) {
            GamePhaseModel phase = gameModel.getPhases().stream().filter(p -> p.getOrderBy() == count).findFirst
                    ().orElse(null);
            if (phase.getPhaseState() != PhaseState.Success) {
                logger.error("阶段没有执行成功,无需存储");
            } else {
                gameModelStore.create(gameModel, phase);
            }
        } else if (count > 0) {
            GamePhaseModel gamePhaseModel = gameModel.getPhases().get(count - 1);
            if (gamePhaseModel.getPhaseState() != PhaseState.Success) {
                logger.error("阶段没有执行成功,无需存储");
            } else {
                gameModelStore.addPhase(gamePhaseModel);
            }
        }
    }
}


