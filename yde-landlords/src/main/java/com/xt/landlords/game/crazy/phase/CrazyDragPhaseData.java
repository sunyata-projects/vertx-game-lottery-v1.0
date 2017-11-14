package com.xt.landlords.game.crazy.phase;

import org.sunyata.octopus.model.PhaseData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 17/6/2.
 */
public class CrazyDragPhaseData extends PhaseData {

    public List<CrazyDragPhaseDataItem> getItems() {
        return items;
    }

    public CrazyDragPhaseData setItems(List<CrazyDragPhaseDataItem> items) {
        this.items = items;
        return this;
    }
    private List<CrazyDragPhaseDataItem> items = new ArrayList<>();

    public void addDataItem(CrazyDragPhaseDataItem crazyDragPhaseDataItem) {
        items.add(crazyDragPhaseDataItem);
    }
}
