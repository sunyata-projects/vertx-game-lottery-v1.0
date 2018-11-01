package com.xt.landlords.game.phase;

import org.sunyata.octopus.model.PhaseData;

import java.util.List;

/**
 * Created by leo on 17/6/2.
 */
public class DealPhaseData extends PhaseData {
    public String getCardId() {
        return cardId;
    }

    public DealPhaseData setCardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    private String cardId;
    private List<Integer> darkCard;//可选,底牌数组
    private List<Integer> centerCard; //可选,当前玩家的手牌,不包括底牌
    private List<Integer> leftCard; //可选,左边玩家的手牌,不包括底牌
    private List<Integer> rightCard; //可选,右边玩家的手牌,不包括底牌

    private Integer totalBombNumbers;//总的炸弹数量

    public Integer getTotalBombNumbers() {
        return totalBombNumbers;
    }

    public DealPhaseData setTotalBombNumbers(Integer totalBombNumbers) {
        this.totalBombNumbers = totalBombNumbers;
        return this;
    }



    public List<Integer> getDarkCard() {
        return darkCard;
    }

    public DealPhaseData setDarkCard(List<Integer> darkCard) {
        this.darkCard = darkCard;
        return this;
    }

    public List<Integer> getCenterCard() {
        return centerCard;
    }

    public DealPhaseData setCenterCard(List<Integer> centerCard) {
        this.centerCard = centerCard;
        return this;
    }

    public List<Integer> getLeftCard() {
        return leftCard;
    }

    public DealPhaseData setLeftCard(List<Integer> leftCard) {
        this.leftCard = leftCard;
        return this;
    }

    public List<Integer> getRightCard() {
        return rightCard;
    }

    public DealPhaseData setRightCard(List<Integer> rightCard) {
        this.rightCard = rightCard;
        return this;
    }
}
