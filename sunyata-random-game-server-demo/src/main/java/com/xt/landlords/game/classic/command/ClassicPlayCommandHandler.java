package com.xt.landlords.game.classic.command;

import com.xt.ai.AIServiceHandler;
import com.xt.landlords.*;
import com.xt.landlords.exception.InvalidPlayException;
import com.xt.landlords.exception.InvalidRoleException;
import com.xt.landlords.game.classic.GameClassicEvent;
import com.xt.landlords.game.classic.GameClassicModel;
import com.xt.landlords.game.classic.GameClassicState;
import com.xt.landlords.game.classic.phase.ClassicPlayPhaseData;
import com.xt.landlords.game.classic.phase.ClassicPlayPhaseDataItem;
import com.xt.landlords.game.classic.phase.ClassicPlayPhaseModel;
import com.xt.landlords.game.phase.DealPhaseData;
import com.xt.landlords.game.phase.DealPhaseModel;
import com.xt.landlords.statemachine.GameController;
import com.xt.landlords.utils.Utility;
import com.xt.yde.protobuf.common.Common;
import com.xt.yde.thrift.ai.CheckCards;
import com.xt.yde.thrift.ai.ShowCards;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.ImmutableState;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.ClassicPlay)
public class ClassicPlayCommandHandler extends AbstractGameControllerCommandHandler {
    class CommandErrorCode {

    }

    Logger logger = LoggerFactory.getLogger(ClassicPlayCommandHandler.class);
    //    @ThriftClient(serviceId = "yde-ai-service", path = "/ai")
//    AIService.Client aiService;
    @Autowired
    AIServiceHandler aiService;

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            if (!this.canAccpet(request, GameClassicEvent.Play)) {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            GameController gameController = GameManager.getGameController(request.getSession().getCurrentUser()
                    .getName());
            if (gameController == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            GameClassicModel gameModel = (GameClassicModel) gameController.getGameModel();

            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }

            Common.PlayRequestMsg playRequestMsg = Common.PlayRequestMsg.parseFrom(request.getMessage().getBody());
            boolean isAuto = playRequestMsg.getIsAuto();//是否托管
            Integer position = playRequestMsg.getRolePosition();//玩家位置,1地主,2 左边农民,3右边农民
            List<Integer> playCards = playRequestMsg.getCardsList();//出的牌,如果是机器人或托管,则忽略此值

            ClassicPlayPhaseModel playPhaseModel = gameModel.addOrUpdatePlayPhase();
            ClassicPlayPhaseData phaseData = playPhaseModel.getPhaseData();
//            if (phaseData.getNextPosition() != position) {
//                throw new InvalidRoleException("角色位置错乱");
//            }
            ClassicPlayPhaseDataItem lastItem = phaseData.getLastDataItem();
            ClassicPlayPhaseDataItem item = new ClassicPlayPhaseDataItem();

            if (lastItem == null) {//此次为第一次出牌
                DealPhaseModel dealPhaseModel = (DealPhaseModel) gameModel.getPhase(GameClassicState.Deal.getValue());
                DealPhaseData dealPhaseData = dealPhaseModel.getPhaseData();
                item.setCenterCards(new ArrayList<>(dealPhaseData.getCenterCard()))
                        .setRightCards(new ArrayList<>(dealPhaseData.getRightCard()))
                        .setLeftCards(new ArrayList<>(dealPhaseData.getLeftCard()));
                item.getCenterCards().addAll(dealPhaseData.getDarkCard());

            } else {
                item
                        .setCenterCards(new ArrayList<>(lastItem.getCenterCards()))
                        .setRightCards(new ArrayList<>(lastItem.getRightCards()))
                        .setLeftCards(new ArrayList<>(lastItem.getLeftCards()));
//                        .setLastPlace(lastItem.getNowPlace())
//                        .setLastCards(lastItem.getShowCards())
//                        .setNowPlace(lastItem.getNextPosition())
//                        .setCurrentBombNumbers(lastItem.getCurrentBombNumbers());
            }
            item.setAuto(isAuto);
            if (!isAuto) {
                item.setShowCards(playCards);
            }
            phaseData.setNowPlace(phaseData.getNextPosition());//设置当前出牌位置为下一个
            play(item, phaseData);
//            if (item.getShowCards().size() == 0 && lastItem != null) {
//                item.setShowCards(lastItem.getShowCards());
//            }
            phaseData.addPhaseDataItem(item);
            gameController.fire(GameClassicEvent.Play, gameModel);
            Common.PlayResponseMsg.Builder builder = Common.PlayResponseMsg.newBuilder();
            builder
                    .addAllCenter(item.getCenterCards())
                    .addAllRight(item.getRightCards())
                    .addAllLeft(item.getLeftCards())
                    .setIfEnd(phaseData.isIfEnd())
                    .setNextPerson(phaseData.getNextPosition())
                    .setBomNums(phaseData.getCurrentBombNumbers())
                    .addAllCards(phaseData.getPlayCards())
                    .setRolePosition(phaseData.getNowPlace());
            ImmutableState currentRawState = gameController.getCurrentRawState();
            logger.info("{}:currentState:{}", this.getClass().getName(), currentRawState);
            response.setBody(builder.build().toByteArray());
            logger.info("position:{},cards:{}", phaseData.getNextPosition(), phaseData.getPlayCards());
        } catch (Exception ex) {
            ExceptionProcessor.process(response, ex);
        } finally {
            response.writeAndFlush();
        }
    }

    public void play(ClassicPlayPhaseDataItem item, ClassicPlayPhaseData phaseData) throws TException,
            InvalidPlayException,
            InvalidRoleException {
        boolean isAuto = item.isAuto();
        int placeRole = phaseData.getNowPlace(); //玩家角色 1地主 2右边农民 3左边农民
        List<Integer> cardsList = null;//
        if (item.getShowCards() != null) {
            cardsList = new ArrayList<>(item.getShowCards());
        }
        ;
        if (isAuto && placeRole == 1) {
            ShowCards showCards = new ShowCards();
            showCards.setNowPlace(placeRole);
            showCards.setCenterCards(item.getCenterCards());
            showCards.setLeftCards(item.getLeftCards());
            showCards.setRightCards(item.getRightCards());
            showCards.setLastCards(phaseData.getLastCards());
            showCards.setLastPlace(phaseData.getLastPlace());
            showCards.setCurrentBombNumber(phaseData.getCurrentBombNumbers());
            showCards.setAiVersionFlag(0);
//            if(player.getCurrentAwardType() == 0){
//                showCards.setTotalBombNumber(-1); //接口要求 当奖为0的时候，总炸弹数传-1
//            }else{
//                int bombs=this.useLevelForBombs(player.getCurrentAwardType());
//                showCards.setTotalBombNumber(bombs);
//            }

            List<Integer> putList = new ArrayList<Integer>();
            long ts = System.currentTimeMillis();
            putList = aiService.playCards(showCards);
            logger.debug("Common Auto Ai playCards cost time :" + (System.currentTimeMillis() - ts) + "ms .");
            cardsList = putList;
        }
        boolean isWin = false;
        if (placeRole == 1) {//地主出牌
            CheckCards checkCards = new CheckCards();
            checkCards.setLastCards(phaseData.getLastCards());
            checkCards.setLastPlace(phaseData.getLastPlace());
            checkCards.setNowPlace(placeRole);
            checkCards.setPlayCards(cardsList);
            int flag;
            long ts = System.currentTimeMillis();
            flag = aiService.checkCards(checkCards);
            logger.debug("Common Ai checkCards cost time :" + (System.currentTimeMillis() - ts) + "ms .");

            //出牌判定 是否符合规则
            if (flag == 1) {
                logger.debug("card is right");
            } else {
                //logger.error("地主出牌非法"+player.getPaiId()+"所验证牌型"+checkCards.toString());
                //return false;
                throw new InvalidPlayException("地方出牌非法");
            }

//            int bomNums = Utility.decideBomNums(cardsList);//所出的牌是否包含炸弹
//            item.setCurrentBombNumbers(item.getCurrentBombNumbers() + bomNums);

//            for (Integer cardValue : cardsList) {
//                if (item.getCenterCards().contains(cardValue)) {
//                    item.getCenterCards().remove(cardValue);
//                }
//            }
//
//            if (item.getCenterCards().size() == 0) {
//                item.setIfEnd(true);
//                isWin = true;
//            }
//            item.setNextPosition(2);
            isWin = syncCards(phaseData, item, cardsList, placeRole);
        } else if (placeRole == 2 || placeRole == 3) {
            //牌库取牌
            ShowCards showCards = new ShowCards();
            showCards.setNowPlace(placeRole);
            showCards.setCenterCards(item.getCenterCards());
            showCards.setLeftCards(item.getLeftCards());
            showCards.setRightCards(item.getRightCards());
            showCards.setLastCards(phaseData.getLastCards());
            showCards.setLastPlace(phaseData.getLastPlace());
            showCards.setCurrentBombNumber(phaseData.getCurrentBombNumbers());
            showCards.setTotalBombNumber(0);
//            if (player.getCurrentAwardType() == 0) {
//                showCards.setTotalBombNumber(-1);
//                showCards.setAiVersionFlag(0);
//            } else {
//                int bombs = this.useLevelForBombs(player.getCurrentAwardType());
//                showCards.setTotalBombNumber(bombs);
//                int flag = player.getPaiId().substring(0, 2) == "C-L" ? 0 : 1;
//                showCards.setAiVersionFlag(flag);
//            }

            List<Integer> putList = new ArrayList<Integer>();

            long start = System.currentTimeMillis();
            putList = aiService.playCards(showCards);
            long end = System.currentTimeMillis();
            logger.debug(" Common Ai get cards time is :" + (end - start));
            cardsList = putList;
            logger.info("placeRole:" + placeRole + " ,  cardsList = " + cardsList);
            syncCards(phaseData, item, cardsList, placeRole);
        } else {
            //错误
            logger.error("出牌过程 ，出现错误！出现非法角色！");
            throw new InvalidRoleException("出牌过程 ，出现错误！出现非法角色！");
        }
        //重置上一出牌人
        if (cardsList.size() > 0) {
            phaseData.setLastPlace(placeRole);
            phaseData.setLastCards(cardsList);
        } else {
        }
        phaseData.setWin(isWin);
    }

    public boolean syncCards(ClassicPlayPhaseData phaseData, ClassicPlayPhaseDataItem item, List<Integer> cardList,
                             Integer placeRole) {
        List<Integer> willSyncCards = null;
        if (placeRole == 3) {
            willSyncCards = item.getLeftCards();
            phaseData.setNextPosition(1);
        } else if (placeRole == 2) {
            willSyncCards = item.getRightCards();
            phaseData.setNextPosition(3);
        } else {
            willSyncCards = item.getCenterCards();
            phaseData.setNextPosition(2);
        }

        for (Integer cardValue : cardList) {
            if (willSyncCards.contains(cardValue)) {
                willSyncCards.remove(cardValue);
            }
        }
        boolean result = false;
        if (willSyncCards.size() == 0) {
            phaseData.setIfEnd(true);
            if (placeRole == 1) {
                result = true;
            }
        }
        phaseData.setPlayCards(cardList);
        int bomNums = Utility.decideBomNums(cardList);//所出的牌是否包含炸弹
        phaseData.setCurrentBombNumbers(phaseData.getCurrentBombNumbers() + bomNums);
        return result;
    }
//    public ClassicPlayPhaseDataItem generatorPlayDataItem(GameClassicModel gameModel) {
//
//        return item;
//    }

    @Override
    public boolean isAsync() {
        return false;
    }
}

