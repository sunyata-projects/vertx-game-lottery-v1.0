package org.sunyata.octopus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    public abstract boolean getCompleted();


//    //最后成功状态名称
//    private String lastSuccessStateName;

//    private boolean isGameOver = false;

//    public GameModel setGameOver(boolean gameOver) {
//        isGameOver = gameOver;
//        return this;
//    }

    public String getLastSuccessStateName() {
        List<GamePhaseModel> phases = this.getPhases();
        if (phases.size() > 0) {
            GamePhaseModel gamePhaseModel = phases.stream().filter(p -> p.getPhaseState() == PhaseState.Success).max
                    (Comparator.comparing
                            (GamePhaseModel::getOrderBy)).orElse(null);
            if (gamePhaseModel != null) {
                return gamePhaseModel.getPhaseName();
            }
        }
        return "Init";

    }


    protected List<GamePhaseModel> phases = new ArrayList();


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


    public void addOrUpdatePhase(GamePhaseModel gamePhaseModel) {
        GamePhaseModel phase = getPhase(gamePhaseModel.getPhaseName());
        if (phase != null) {
            phase.setPhaseData(gamePhaseModel.getPhaseData());
            phase.setPhaseState(gamePhaseModel.getPhaseState());
        } else {
            phases.add(gamePhaseModel);
        }
    }

    @JsonIgnore
    public int getPhaseCount() {
        return phases.size();
    }

    public GamePhaseModel getPhase(String phaseName) {
        Optional<GamePhaseModel> first = this.phases.stream().filter(p -> p.getPhaseName().equalsIgnoreCase(phaseName))
                .findFirst();
        GamePhaseModel gamePhaseModel = first.orElse(null);
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
    public abstract Object getFirstEvent();


    @JsonIgnore()
    public abstract Object getInitState();

    @JsonIgnore
    public abstract boolean needBreakPlay();

    @JsonIgnore
    public abstract Object getLastSuccessState();



}
