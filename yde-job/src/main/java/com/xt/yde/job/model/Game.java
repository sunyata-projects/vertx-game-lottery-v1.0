//package com.xt.yde.job.model;
//
//import org.sunyata.octopus.model.GameModel;
//
//import java.io.Serializable;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by leo on 17/4/27.
// */
//public class Game implements Serializable {
//
//    //游戏类型
//    private int gameType;
//    //用户名
//    private String userName;
//    //创建时间
//    private Timestamp createDateTime;
//
//    //游戏唯一标识
//    private String gameInstanceId;
//
//    private List<Phase> phases = new ArrayList();
//
//
//    public Game(int gameType, String gameInstanceId) {
//        this.gameInstanceId = gameInstanceId;
//        this.gameType = gameType;
//        this.createDateTime = new Timestamp(System.currentTimeMillis());
//    }
//
//    protected Game() {
//    }
//
//    public int getGameType() {
//        return gameType;
//    }
//
//    public Game setGameType(int gameType) {
//        this.gameType = gameType;
//        return this;
//    }
//
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public Game setUserName(String userName) {
//        this.userName = userName;
//        return this;
//    }
//
//    public Timestamp getCreateDateTime() {
//        return createDateTime;
//    }
//
//    public Game setCreateDateTime(Timestamp createDateTime) {
//        this.createDateTime = createDateTime;
//        return this;
//    }
//
//    public String getGameInstanceId() {
//        return gameInstanceId;
//    }
//
//    public Game setGameInstanceId(String gameInstanceId) {
//        this.gameInstanceId = gameInstanceId;
//        return this;
//    }
//
//
//    public void addPhase(Phase gamePhaseModel) {
//        phases.add(gamePhaseModel);
//    }
//
//    public int getPhaseCount() {
//        return phases.size();
//    }
//
//    public Phase getPhase(String phaseName) {
//        Phase gamePhaseModel = this.phases.stream().filter(p -> phaseName.equalsIgnoreCase(phaseName))
//                .findFirst().orElse(null);
//        return gamePhaseModel;
//    }
//
//
//    public List<Phase> getPhases() {
//        return phases;
//    }
//
//    public Game setPhases(List<Phase> phases) {
//        this.phases = phases;
//        return this;
//    }
//    public static Game fromGameModel(GameModel gameModel){
//        return new Game(gameModel.getGameType(), gameModel.getGameInstanceId()).setCreateDateTime
//                (gameModel.getCreateDateTime()).setUserName(gameModel.getUserName());
//
//    }
//}
