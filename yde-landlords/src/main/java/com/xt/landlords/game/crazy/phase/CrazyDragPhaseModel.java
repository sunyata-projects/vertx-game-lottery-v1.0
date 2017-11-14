package com.xt.landlords.game.crazy.phase;

import org.sunyata.octopus.model.GamePhaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 17/5/18.
 */
public class CrazyDragPhaseModel extends GamePhaseModel<CrazyDragPhaseData> {
    public CrazyDragPhaseModel(String gameInstanceId, String phaseName, int orderBy) {
        super(gameInstanceId, phaseName, orderBy);
    }

    public CrazyDragPhaseModel() {

    }

    public List<Integer> drag(int divideRole, List<Integer> selectPlace, List<Integer> sendPlace, CrazyDealPhaseData
            crazyDealPhaseData) {
        CrazyDragPhaseData phaseData = this.getPhaseData();
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < selectPlace.size(); i++) {
            CrazyDragPhaseDataItem item = new CrazyDragPhaseDataItem()
                    .setDivideRole(divideRole)
                    .setSelectPlace(selectPlace.get(i))
                    .setSendPlace(sendPlace.get(i));
            if (divideRole == 1) {
                int count = (int) (this.getPhaseData().getItems().stream().filter(p -> p.getDivideRole() == 1).count());
                Integer cardIndex = crazyDealPhaseData.getCenterThreeCard().get(count);
                result.add(cardIndex);
                item.setSelectCard(cardIndex);
            } else if (divideRole == 3) {
                result.add(crazyDealPhaseData.getLeftOneCard());
                item.setSelectCard(crazyDealPhaseData.getLeftOneCard());
            } else {
                result.add(crazyDealPhaseData.getRightOneCard());
                item.setSelectCard(crazyDealPhaseData.getRightOneCard());
            }
            phaseData.addDataItem(item);
        }
        return result;
    }
}
