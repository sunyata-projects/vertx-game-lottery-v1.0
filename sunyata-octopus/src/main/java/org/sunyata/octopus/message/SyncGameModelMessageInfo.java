//package org.sunyata.octopus.message;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import java.io.Serializable;
//
///**
// * Created by leo on 17/5/10.
// */
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class SyncGameModelMessageInfo implements Serializable {
//    public SerializableGameModel getGameModel() {
//        return gameModel;
//    }
//
//    public SyncGameModelMessageInfo setGameModel(SerializableGameModel gameModel) {
//        this.gameModel = gameModel;
//        return this;
//    }
//
//    //    public int getGameType() {
////        return gameType;
////    }
////
////    public SyncGameModelMessageInfo setGameType(int gameType) {
////        this.gameType = gameType;
////        return this;
////    }
////
////    public String getUserName() {
////        return userName;
////    }
////
////    public SyncGameModelMessageInfo setUserName(String userName) {
////        this.userName = userName;
////        return this;
////    }
////
////    public Timestamp getCreateDateTime() {
////        return createDateTime;
////    }
////
////    public SyncGameModelMessageInfo setCreateDateTime(Timestamp createDateTime) {
////        this.createDateTime = createDateTime;
////        return this;
////    }
////
////    public String getGameInstanceId() {
////        return gameInstanceId;
////    }
////
////    public SyncGameModelMessageInfo setGameInstanceId(String gameInstanceId) {
////        this.gameInstanceId = gameInstanceId;
////        return this;
////    }
////
////    public List<GamePhaseModel> getPhases() {
////        return phases;
////    }
////
////    public SyncGameModelMessageInfo setPhases(List<GamePhaseModel> phases) {
////        this.phases = phases;
////        return this;
////    }
////
////
////    private List<GamePhaseModel> phases = new ArrayList();
////
////    //游戏类型
////    private int gameType;
////    //用户名
////    private String userName;
////    //创建时间
////    private Timestamp createDateTime;
////
////    //游戏唯一标识
////    private String gameInstanceId;
//    private SerializableGameModel gameModel;
//
//}
