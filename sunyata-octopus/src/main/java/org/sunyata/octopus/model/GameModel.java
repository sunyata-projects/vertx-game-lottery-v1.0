package org.sunyata.octopus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 17/4/27.
 */
public abstract class GameModel implements Serializable {

    //游戏类型
    private int gameType;
    //用户名
    private String userName;
    //创建时间
    private Timestamp createDateTime;

    //游戏唯一标识
    private String gameInstanceId;

    private List<GamePhaseModel> phases = new ArrayList();


    public GameModel(int gameType, String gameInstanceId) {
        this.gameInstanceId = gameInstanceId;
        this.gameType = gameType;
        this.createDateTime = new Timestamp(System.currentTimeMillis());
    }

    protected GameModel() {
    }

    public int getGameType() {
        return gameType;
    }

    public GameModel setGameType(int gameType) {
        this.gameType = gameType;
        return this;
    }


    public String getUserName() {
        return userName;
    }

    public GameModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public GameModel setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public String getGameInstanceId() {
        return gameInstanceId;
    }

    public GameModel setGameInstanceId(String gameInstanceId) {
        this.gameInstanceId = gameInstanceId;
        return this;
    }


    public void addPhase(GamePhaseModel gamePhaseModel) {
        phases.add(gamePhaseModel);
    }

    @JsonIgnore
    public int getPhaseCount() {
        return phases.size();
    }

    public GamePhaseModel getPhase(String phaseName) {
        GamePhaseModel gamePhaseModel = this.phases.stream().filter(p -> p.getPhaseName().equalsIgnoreCase(phaseName))
                .findFirst().orElse(null);
        return gamePhaseModel;
    }


    public List<GamePhaseModel> getPhases() {
        return phases;
    }

    public GameModel setPhases(List<GamePhaseModel> phases) {
        this.phases = phases;
        return this;
    }

    @JsonIgnore()
    public abstract Object getBetEvent();


    @JsonIgnore()
    public abstract Object getInitState();

    @JsonIgnore
    public abstract boolean needBreakPlay();

    @JsonIgnore
    public abstract boolean isGameOver();

}
