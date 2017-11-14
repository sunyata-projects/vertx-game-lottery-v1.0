package com.xt.landlords.game.crazy.phase;

import org.sunyata.octopus.model.PhaseData;

/**
 * Created by leo on 17/6/2.
 */
public class CrazyDragPhaseDataItem extends PhaseData {

    private int divideRole ; //分派的角色 1,地主 ,2右农民,3 左农民
    private int selectPlace; //0~4 底牌从左到右的位置

    public int getSelectCard() {
        return selectCard;
    }

    public CrazyDragPhaseDataItem setSelectCard(int selectCard) {
        this.selectCard = selectCard;
        return this;
    }

    private int selectCard;//具体的牌面
    private int sendPlace ;//每次拖拽牌拽到的位置，地主为0,1,2 农民都为0

    public int getDivideRole() {
        return divideRole;
    }

    public CrazyDragPhaseDataItem setDivideRole(int divideRole) {
        this.divideRole = divideRole;
        return this;
    }

    public int getSelectPlace() {
        return selectPlace;
    }

    public CrazyDragPhaseDataItem setSelectPlace(int selectPlace) {
        this.selectPlace = selectPlace;
        return this;
    }

    public int getSendPlace() {
        return sendPlace;
    }

    public CrazyDragPhaseDataItem setSendPlace(int sendPlace) {
        this.sendPlace = sendPlace;
        return this;
    }
}
