package com.xt.landlords.game.classic.phase;

import org.sunyata.octopus.model.PhaseData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 17/6/2.
 */
public class ClassicGuessSizePhaseData extends PhaseData {

    public List<ClassicGuessSizePhaseDataItem> getItems() {
        return items;
    }

    public ClassicGuessSizePhaseData setItems(List<ClassicGuessSizePhaseDataItem> items) {
        this.items = items;
        return this;
    }
    private List<ClassicGuessSizePhaseDataItem> items = new ArrayList<>();

    public void addDataItem(ClassicGuessSizePhaseDataItem crazyDragPhaseDataItem) {
        items.add(crazyDragPhaseDataItem);
    }
}
