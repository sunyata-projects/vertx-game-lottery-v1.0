package com.xt.landlords.game.crazy.phase;

import org.sunyata.octopus.model.PhaseData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 17/6/2.
 */
public class CrazyGuessSizePhaseData extends PhaseData {

    public List<CrazyGuessSizePhaseDataItem> getItems() {
        return items;
    }

    public CrazyGuessSizePhaseData setItems(List<CrazyGuessSizePhaseDataItem> items) {
        this.items = items;
        return this;
    }
    private List<CrazyGuessSizePhaseDataItem> items = new ArrayList<>();

    public void addDataItem(CrazyGuessSizePhaseDataItem crazyDragPhaseDataItem) {
        items.add(crazyDragPhaseDataItem);
    }
}
