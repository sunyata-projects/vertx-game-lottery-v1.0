package com.xt.landlords.game.crazy.phase;

import org.sunyata.octopus.model.PhaseData;

import java.util.List;

/**
 * Created by leo on 17/6/2.
 */
public class CrazyDealPhaseData extends PhaseData {
    public String getCardId() {
        return cardId;
    }

    public CrazyDealPhaseData setCardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public List<Integer> getCenterThreeCard() {
        return centerThreeCard;
    }

    public CrazyDealPhaseData setCenterThreeCard(List<Integer> centerThreeCard) {
        this.centerThreeCard = centerThreeCard;
        return this;
    }

    public List<Integer> getCenterCard() {
        return centerCard;
    }

    public CrazyDealPhaseData setCenterCard(List<Integer> centerCard) {
        this.centerCard = centerCard;
        return this;
    }

    public List<Integer> getLeftCard() {
        return leftCard;
    }

    public CrazyDealPhaseData setLeftCard(List<Integer> leftCard) {
        this.leftCard = leftCard;
        return this;
    }

    public List<Integer> getRightCard() {
        return rightCard;
    }

    public CrazyDealPhaseData setRightCard(List<Integer> rightCard) {
        this.rightCard = rightCard;
        return this;
    }

    public Integer getLeftOneCard() {
        return leftOneCard;
    }

    public CrazyDealPhaseData setLeftOneCard(Integer leftOneCard) {
        this.leftOneCard = leftOneCard;
        return this;
    }

    public Integer getRightOneCard() {
        return rightOneCard;
    }

    public CrazyDealPhaseData setRightOneCard(Integer rightOneCard) {
        this.rightOneCard = rightOneCard;
        return this;
    }

    public Integer getBombNumbers() {
        return bombNumbers;
    }

    public CrazyDealPhaseData setBombNumbers(Integer bombNumbers) {
        this.bombNumbers = bombNumbers;
        return this;
    }

    private String cardId;
    private List<Integer> centerThreeCard;//
    private List<Integer> centerCard; //
    private List<Integer> leftCard; //
    private List<Integer> rightCard; //

    private Integer leftOneCard;
    private Integer rightOneCard;

    private Integer bombNumbers;//总的炸弹数量

}
