package com.xt.landlords.game.regular.phase;

import org.sunyata.octopus.model.PhaseData;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by leo on 17/5/16.
 */
public class RegularPlayPhaseDataItem extends PhaseData {
    public RegularPlayPhaseDataItem() {
        this.createDateTime = new Timestamp(System.currentTimeMillis());
    }

    public List<Integer> getCenterCards() {
        return centerCards;
    }

    public RegularPlayPhaseDataItem setCenterCards(List<Integer> centerCards) {
        this.centerCards = centerCards;
        return this;
    }

    public List<Integer> getRightCards() {
        return rightCards;
    }

    public RegularPlayPhaseDataItem setRightCards(List<Integer> rightCards) {
        this.rightCards = rightCards;
        return this;
    }

    public List<Integer> getLeftCards() {
        return leftCards;
    }

    public RegularPlayPhaseDataItem setLeftCards(List<Integer> leftCards) {
        this.leftCards = leftCards;
        return this;
    }
//
//    public List<Integer> getLastCards() {
//        return lastCards;
//    }
//
//    public RegularPlayPhaseDataItem setLastCards(List<Integer> lastCards) {
//        this.lastCards = lastCards;
//        return this;
//    }
//
//    public Integer getLastPlace() {
//        return lastPlace;
//    }
//
//    public RegularPlayPhaseDataItem setLastPlace(Integer lastPlace) {
//        this.lastPlace = lastPlace;
//        return this;
//    }
//
//    public Integer getNowPlace() {
//        return nowPlace;
//    }
//
//    public RegularPlayPhaseDataItem setNowPlace(Integer nowPlace) {
//        this.nowPlace = nowPlace;
//        return this;
//    }
//
//    public Integer getTotalBombNumber() {
//        return totalBombNumber;
//    }
//
//    public RegularPlayPhaseDataItem setTotalBombNumber(Integer totalBombNumber) {
//        this.totalBombNumber = totalBombNumber;
//        return this;
//    }

    public Integer getAiVersionFlag() {
        return aiVersionFlag;
    }

    public RegularPlayPhaseDataItem setAiVersionFlag(Integer aiVersionFlag) {
        this.aiVersionFlag = aiVersionFlag;
        return this;
    }

    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public RegularPlayPhaseDataItem setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

//    public Integer getCurrentBombNumbers() {
//        return currentBombNumbers;
//    }
//
//    public RegularPlayPhaseDataItem setCurrentBombNumbers(Integer currentBombNumbers) {
//        this.currentBombNumbers = currentBombNumbers;
//        return this;
//    }
//
//    public Integer getNextPosition() {
//        return nextPosition;
//    }
//
//    public RegularPlayPhaseDataItem setNextPosition(Integer nextPosition) {
//        this.nextPosition = nextPosition;
//        return this;
//    }
//
//    public boolean isIfEnd() {
//        return ifEnd;
//    }
//
//    public RegularPlayPhaseDataItem setIfEnd(boolean ifEnd) {
//        this.ifEnd = ifEnd;
//        return this;
//    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public RegularPlayPhaseDataItem setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public RegularPlayPhaseDataItem setAuto(boolean auto) {
        isAuto = auto;
        return this;
    }

    public List<Integer> getShowCards() {
        return showCards;
    }

    public RegularPlayPhaseDataItem setShowCards(List<Integer> showCards) {
        this.showCards = showCards;
        return this;
    }

    //    1: list<i32> centerCards, //地主剩余牌
//    2: list<i32> rightCards, //右侧农民剩余牌
//    3: list<i32> leftCards, //左侧农民剩余牌
//    4: list<i32> lastCards, //上家出牌
//    5: i32 lastPlace, //上一出牌人位置，1地主 2右边农民 3左边农民
//    6: i32 nowPlace, //当前出牌人位置，1地主 2右边农民 3左边农民
//    7: i32 totalBombNumber, //总炸弹数，地主输-1，赢具体炸弹数
//    8: i32 currentBombNumber, //当前打出的炸弹数
//    9: i32 aiVersionFlag  //AI版本标志，0－难版本（输），1－简单版本（赢）
    private List<Integer> centerCards;
    private List<Integer> rightCards;
    private List<Integer> leftCards;
    private Integer aiVersionFlag = 1;//AI版本标志,0－难版本（输），1－简单版本（赢）
    private boolean isAuto;//是否托管
    private List<Integer> showCards;//牌桌上的上一个玩家或上上玩家出的牌,需要你大过他的牌,有可能是自己出的牌
    private Integer orderBy;
    private Timestamp createDateTime;

//    private List<Integer> lastCards;//上一个出牌位置所出的牌
//    private Integer lastPlace = 1;//上一个出牌人位置
//    private Integer nowPlace = 1;//当前出牌人位置
//    private Integer nextPosition = 1;//下一个位置
//    private Integer currentBombNumbers = 0;//当前炸弹数量
//    private boolean ifEnd = false;//是否结束
//    private Integer totalBombNumber = 0;//总炸弹数量

}