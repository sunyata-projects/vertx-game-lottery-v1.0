//package com.xt.yde.job.model;
//
//import org.sunyata.octopus.json.Json;
//import org.sunyata.octopus.model.GamePhaseModel;
//import org.sunyata.octopus.model.PhaseState;
//
//import java.io.Serializable;
//import java.sql.Timestamp;
//
///**
// * Created by leo on 17/5/16.
// */
//public class Phase implements Serializable {
//
//
//    //阶段id
//    private String phaseId;
//    //游戏实例id
//    private String gameInstanceId;
//    //阶段名称
//    private String phaseName;
//    //阶段数据
//    private Object phaseData;
//    //阶段状态
//    private PhaseState phaseState;
//    //创建时间
//    private Timestamp createDateTime;
//
//    //阶段序号
//    private int orderBy;
//
//    protected Phase() {
//    }
//
//
//    private String phaseDataString;
//
//    public Phase(String gameInstanceId, String phaseName, int orderBy) {
//        this.gameInstanceId = gameInstanceId;
//        this.orderBy = orderBy;
//        this.phaseState = PhaseState.Init;
//        this.phaseName = phaseName;
//        this.createDateTime = new Timestamp(System.currentTimeMillis());
//        this.phaseId = gameInstanceId + "-" + getFixString(this.getOrderBy(), 2);
//    }
//
//    public String getPhaseDataString() {
//        return Json.encode(phaseData);
//    }
//
//    public Phase setPhaseDataString(String phaseDataString) {
//        this.phaseDataString = phaseDataString;
//        return this;
//    }
//
//    public String getPhaseId() {
//        return this.phaseId;
//    }
//
//    public Phase setPhaseId(String phaseId) {
//        this.phaseId = phaseId;
//        return this;
//    }
//
//    public String getGameInstanceId() {
//        return gameInstanceId;
//    }
//
//    public Phase setGameInstanceId(String gameInstanceId) {
//        this.gameInstanceId = gameInstanceId;
//        return this;
//    }
//
//    public String getPhaseName() {
//        return phaseName;
//    }
//
//    public Phase setPhaseName(String phaseName) {
//        this.phaseName = phaseName;
//        return this;
//    }
//
//    public Object getPhaseData() {
//        return phaseData;
//    }
//
//    public Phase setPhaseData(Object phaseData) {
//        this.phaseData = phaseData;
//        return this;
//    }
//
//    public PhaseState getPhaseState() {
//        return phaseState;
//    }
//
//    public Phase setPhaseState(PhaseState phaseState) {
//        this.phaseState = phaseState;
//        return this;
//    }
//
//    public Timestamp getCreateDateTime() {
//        return createDateTime;
//    }
//
//    public Phase setCreateDateTime(Timestamp createDateTime) {
//        this.createDateTime = createDateTime;
//        return this;
//    }
//
//    public int getOrderBy() {
//        return orderBy;
//    }
//
//    public Phase setOrderBy(int orderBy) {
//        this.orderBy = orderBy;
//        return this;
//    }
//
//    public String getFixString(Integer order, Integer n) {
//        String s = "00" + String.valueOf(order);
//        return s.substring(s.length() - n, s.length());
//    }
//
//    public static Phase fromPhaseModel(GamePhaseModel phaseModel) {
//        String phaseDataString = Json.encode(phaseModel.getPhaseData());
//        return new Phase(phaseModel.getGameInstanceId(), phaseModel.getPhaseName(), phaseModel.getOrderBy())
//                .setPhaseData(phaseDataString).setPhaseId(phaseModel.getPhaseId()).setPhaseState(phaseModel
//                        .getPhaseState()).setCreateDateTime(phaseModel.getCreateDateTime()).setOrderBy
//                        (phaseModel.getOrderBy());
//    }
//}
