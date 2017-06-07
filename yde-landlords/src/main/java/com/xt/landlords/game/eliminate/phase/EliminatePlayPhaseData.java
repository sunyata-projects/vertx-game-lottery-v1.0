package com.xt.landlords.game.eliminate.phase;

import org.sunyata.octopus.model.PhaseData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by leo on 17/5/16.
 */
public class EliminatePlayPhaseData extends PhaseData {

    public List<EliminatePlayPhaseDataItem> getItems() {
        return items;
    }

    public EliminatePlayPhaseData setItems(List<EliminatePlayPhaseDataItem> items) {
        this.items = items;
        return this;
    }

    private List<EliminatePlayPhaseDataItem> items = new ArrayList<>();

    public EliminatePlayPhaseData addItem(int betGamePoint, int awardGamePoint, int doubleKingCount, boolean
            isOver) {
//        private int betGamePoint;//本次投注点数
//        private int awardGamePoint;//本次投注完成后,赢得的点数
//        private int doubleKingCount = 0;//本次投注意完成后,双王的数量,即游戏进度
        EliminatePlayPhaseDataItem eliminatePlayPhaseDataItem = new EliminatePlayPhaseDataItem().setAwardGamePoint
                (awardGamePoint).setBetGamePoint(betGamePoint)
                .setTotalDoubleKingCount(doubleKingCount).setOver(isOver);
        eliminatePlayPhaseDataItem.setOrderBy(items.size() + 1);
        items.add(eliminatePlayPhaseDataItem);
        return this;
    }

    public EliminatePlayPhaseDataItem getLastPlayPhaseDataItem() {
        EliminatePlayPhaseDataItem eliminatePlayPhaseDataItem = items.stream().max(Comparator.comparing
                (EliminatePlayPhaseDataItem::getOrderBy)).orElse(null);
        return eliminatePlayPhaseDataItem;
    }

    public EliminatePlayPhaseDataItem getPenultimateDataItem() {
        EliminatePlayPhaseDataItem lastPlayPhaseDataItem = getLastPlayPhaseDataItem();
        if (lastPlayPhaseDataItem != null) {
            int orderBy = lastPlayPhaseDataItem.getOrderBy() - 1;
            EliminatePlayPhaseDataItem eliminatePlayPhaseDataItem = items.stream().filter(p -> p.getOrderBy() ==
                    orderBy)
                    .findFirst().orElse(null);
            return eliminatePlayPhaseDataItem;
        }
        return null;
    }

    public EliminatePlayPhaseData addItem(EliminatePlayPhaseDataItem item) {
        item.setOrderBy(items.size() + 1);
        items.add(item);
        return this;
    }
}