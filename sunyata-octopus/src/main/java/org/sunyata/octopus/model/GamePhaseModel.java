package org.sunyata.octopus.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.sunyata.octopus.json.Json;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by leo on 17/5/16.
 */
@JsonSerialize()
public class GamePhaseModel<T extends PhaseData> implements Serializable {


    //阶段id
    private String phaseId;
    //游戏实例id
    private String gameInstanceId;
    //阶段名称
    private String phaseName;
    //阶段数据
    private T phaseData;
    //阶段状态
    private PhaseState phaseState;
    //创建时间
    private Timestamp createDateTime;

    public GamePhaseModel setPhaseDataString(String phaseDataString) {
        this.phaseDataString = phaseDataString;
        return this;
    }

    private String phaseDataString;

    public String getPhaseDataString() {
        return phaseDataString;
    }


    //private String phaseDataString;
    //阶段序号
    private int orderBy;

    protected GamePhaseModel() {
    }

    public GamePhaseModel(String gameInstanceId, String phaseName, int orderBy) {
        this.gameInstanceId = gameInstanceId;
        this.orderBy = orderBy;
        this.phaseState = PhaseState.Init;
        this.phaseName = phaseName;
        this.createDateTime = new Timestamp(System.currentTimeMillis());
        this.phaseId = gameInstanceId + "-" + getFixString(this.getOrderBy(), 2);
    }

    public String getPhaseId() {
        return this.phaseId;
    }

    public GamePhaseModel setPhaseId(String phaseId) {
        this.phaseId = phaseId;
        return this;
    }

    public String getGameInstanceId() {
        return gameInstanceId;
    }

    public GamePhaseModel setGameInstanceId(String gameInstanceId) {
        this.gameInstanceId = gameInstanceId;
        return this;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public GamePhaseModel setPhaseName(String phaseName) {
        this.phaseName = phaseName;
        return this;
    }

    public T getPhaseData() {
        return phaseData;
    }

    public GamePhaseModel setPhaseData(T phaseData) {
        this.phaseData = phaseData;
        if (phaseData != null) {
            int length = phaseData.getClass().getDeclaredFields().length;
            if (length > 0) {
                this.phaseDataString = Json.encode(phaseData);
            }
        }
        return this;
    }

    public PhaseState getPhaseState() {
        return phaseState;
    }

    public GamePhaseModel setPhaseState(PhaseState phaseState) {
        this.phaseState = phaseState;
        return this;
    }

    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public GamePhaseModel setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public GamePhaseModel setOrderBy(int orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public String getFixString(Integer order, Integer n) {
        String s = "00" + String.valueOf(order);
        return s.substring(s.length() - n, s.length());
    }
}
