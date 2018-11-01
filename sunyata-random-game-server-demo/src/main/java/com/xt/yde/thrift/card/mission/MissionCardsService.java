/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xt.yde.thrift.card.mission;

import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.server.AbstractNonblockingServer.AsyncFrameBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Generated;
import java.util.*;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-07-13")
public class MissionCardsService {

  public interface Iface {

    /**
     * 获取发牌信息
     * lose：是否必输入
     *
     * @param lose
     * @param missionIndex
     */
    public MissionCards getCards(boolean lose, int missionIndex) throws TException;

  }

  public interface AsyncIface {

    public void getCards(boolean lose, int missionIndex, AsyncMethodCallback resultHandler) throws TException;

  }

  public static class Client extends org.apache.thrift.TServiceClient implements Iface {
    public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(org.apache.thrift.protocol.TProtocol prot)
    {
      super(prot, prot);
    }

    public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
      super(iprot, oprot);
    }

    public MissionCards getCards(boolean lose, int missionIndex) throws TException
    {
      send_getCards(lose, missionIndex);
      return recv_getCards();
    }

    public void send_getCards(boolean lose, int missionIndex) throws TException
    {
      getCards_args args = new getCards_args();
      args.setLose(lose);
      args.setMissionIndex(missionIndex);
      sendBase("getCards", args);
    }

    public MissionCards recv_getCards() throws TException
    {
      getCards_result result = new getCards_result();
      receiveBase(result, "getCards");
      if (result.isSetSuccess()) {
        return result.success;
      }
      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "getCards failed: unknown result");
    }

  }
  public static class AsyncClient extends org.apache.thrift.async.TAsyncClient implements AsyncIface {
    public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
      private org.apache.thrift.async.TAsyncClientManager clientManager;
      private org.apache.thrift.protocol.TProtocolFactory protocolFactory;
      public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
    }

    public void getCards(boolean lose, int missionIndex, AsyncMethodCallback resultHandler) throws TException {
      checkReady();
      getCards_call method_call = new getCards_call(lose, missionIndex, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class getCards_call extends org.apache.thrift.async.TAsyncMethodCall {
      private boolean lose;
      private int missionIndex;
      public getCards_call(boolean lose, int missionIndex, AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.lose = lose;
        this.missionIndex = missionIndex;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("getCards", org.apache.thrift.protocol.TMessageType.CALL, 0));
        getCards_args args = new getCards_args();
        args.setLose(lose);
        args.setMissionIndex(missionIndex);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public MissionCards getResult() throws TException {
        if (getState() != State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_getCards();
      }
    }

  }

  public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift.TProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class.getName());
    public Processor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
    }

    protected Processor(I iface, Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends Iface> Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> getProcessMap(Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      processMap.put("getCards", new getCards());
      return processMap;
    }

    public static class getCards<I extends Iface> extends org.apache.thrift.ProcessFunction<I, getCards_args> {
      public getCards() {
        super("getCards");
      }

      public getCards_args getEmptyArgsInstance() {
        return new getCards_args();
      }

      protected boolean isOneway() {
        return false;
      }

      public getCards_result getResult(I iface, getCards_args args) throws TException {
        getCards_result result = new getCards_result();
        result.success = iface.getCards(args.lose, args.missionIndex);
        return result;
      }
    }

  }

  public static class AsyncProcessor<I extends AsyncIface> extends org.apache.thrift.TBaseAsyncProcessor<I> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncProcessor.class.getName());
    public AsyncProcessor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>>()));
    }

    protected AsyncProcessor(I iface, Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends AsyncIface> Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase,?>> getProcessMap(Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      processMap.put("getCards", new getCards());
      return processMap;
    }

    public static class getCards<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, getCards_args, MissionCards> {
      public getCards() {
        super("getCards");
      }

      public getCards_args getEmptyArgsInstance() {
        return new getCards_args();
      }

      public AsyncMethodCallback<MissionCards> getResultHandler(final AsyncFrameBuffer fb, final int seqid) {
        final org.apache.thrift.AsyncProcessFunction fcall = this;
        return new AsyncMethodCallback<MissionCards>() {
          public void onComplete(MissionCards o) {
            getCards_result result = new getCards_result();
            result.success = o;
            try {
              fcall.sendResponse(fb,result, org.apache.thrift.protocol.TMessageType.REPLY,seqid);
              return;
            } catch (Exception e) {
              LOGGER.error("Exception writing to internal frame buffer", e);
            }
            fb.close();
          }
          public void onError(Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TBase msg;
            getCards_result result = new getCards_result();
            {
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = (org.apache.thrift.TBase)new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
            }
            try {
              fcall.sendResponse(fb,msg,msgType,seqid);
              return;
            } catch (Exception ex) {
              LOGGER.error("Exception writing to internal frame buffer", ex);
            }
            fb.close();
          }
        };
      }

      protected boolean isOneway() {
        return false;
      }

      public void start(I iface, getCards_args args, AsyncMethodCallback<MissionCards> resultHandler) throws TException {
        iface.getCards(args.lose, args.missionIndex,resultHandler);
      }
    }

  }

  public static class getCards_args implements org.apache.thrift.TBase<getCards_args, getCards_args._Fields>, java.io.Serializable, Cloneable, Comparable<getCards_args>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("getCards_args");

    private static final org.apache.thrift.protocol.TField LOSE_FIELD_DESC = new org.apache.thrift.protocol.TField("lose", org.apache.thrift.protocol.TType.BOOL, (short)1);
    private static final org.apache.thrift.protocol.TField MISSION_INDEX_FIELD_DESC = new org.apache.thrift.protocol.TField("missionIndex", org.apache.thrift.protocol.TType.I32, (short)2);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new getCards_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new getCards_argsTupleSchemeFactory());
    }

    public boolean lose; // required
    public int missionIndex; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      LOSE((short)1, "lose"),
      MISSION_INDEX((short)2, "missionIndex");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // LOSE
            return LOSE;
          case 2: // MISSION_INDEX
            return MISSION_INDEX;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    private static final int __LOSE_ISSET_ID = 0;
    private static final int __MISSIONINDEX_ISSET_ID = 1;
    private byte __isset_bitfield = 0;
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.LOSE, new org.apache.thrift.meta_data.FieldMetaData("lose", org.apache.thrift.TFieldRequirementType.DEFAULT,
              new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
      tmpMap.put(_Fields.MISSION_INDEX, new org.apache.thrift.meta_data.FieldMetaData("missionIndex", org.apache.thrift.TFieldRequirementType.DEFAULT,
              new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(getCards_args.class, metaDataMap);
    }

    public getCards_args() {
    }

    public getCards_args(
            boolean lose,
            int missionIndex)
    {
      this();
      this.lose = lose;
      setLoseIsSet(true);
      this.missionIndex = missionIndex;
      setMissionIndexIsSet(true);
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public getCards_args(getCards_args other) {
      __isset_bitfield = other.__isset_bitfield;
      this.lose = other.lose;
      this.missionIndex = other.missionIndex;
    }

    public getCards_args deepCopy() {
      return new getCards_args(this);
    }

    @Override
    public void clear() {
      setLoseIsSet(false);
      this.lose = false;
      setMissionIndexIsSet(false);
      this.missionIndex = 0;
    }

    public boolean isLose() {
      return this.lose;
    }

    public getCards_args setLose(boolean lose) {
      this.lose = lose;
      setLoseIsSet(true);
      return this;
    }

    public void unsetLose() {
      __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LOSE_ISSET_ID);
    }

    /** Returns true if field lose is set (has been assigned a value) and false otherwise */
    public boolean isSetLose() {
      return EncodingUtils.testBit(__isset_bitfield, __LOSE_ISSET_ID);
    }

    public void setLoseIsSet(boolean value) {
      __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LOSE_ISSET_ID, value);
    }

    public int getMissionIndex() {
      return this.missionIndex;
    }

    public getCards_args setMissionIndex(int missionIndex) {
      this.missionIndex = missionIndex;
      setMissionIndexIsSet(true);
      return this;
    }

    public void unsetMissionIndex() {
      __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MISSIONINDEX_ISSET_ID);
    }

    /** Returns true if field missionIndex is set (has been assigned a value) and false otherwise */
    public boolean isSetMissionIndex() {
      return EncodingUtils.testBit(__isset_bitfield, __MISSIONINDEX_ISSET_ID);
    }

    public void setMissionIndexIsSet(boolean value) {
      __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MISSIONINDEX_ISSET_ID, value);
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
        case LOSE:
          if (value == null) {
            unsetLose();
          } else {
            setLose((Boolean)value);
          }
          break;

        case MISSION_INDEX:
          if (value == null) {
            unsetMissionIndex();
          } else {
            setMissionIndex((Integer)value);
          }
          break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
        case LOSE:
          return isLose();

        case MISSION_INDEX:
          return getMissionIndex();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
        case LOSE:
          return isSetLose();
        case MISSION_INDEX:
          return isSetMissionIndex();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof getCards_args)
        return this.equals((getCards_args)that);
      return false;
    }

    public boolean equals(getCards_args that) {
      if (that == null)
        return false;

      boolean this_present_lose = true;
      boolean that_present_lose = true;
      if (this_present_lose || that_present_lose) {
        if (!(this_present_lose && that_present_lose))
          return false;
        if (this.lose != that.lose)
          return false;
      }

      boolean this_present_missionIndex = true;
      boolean that_present_missionIndex = true;
      if (this_present_missionIndex || that_present_missionIndex) {
        if (!(this_present_missionIndex && that_present_missionIndex))
          return false;
        if (this.missionIndex != that.missionIndex)
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      List<Object> list = new ArrayList<Object>();

      boolean present_lose = true;
      list.add(present_lose);
      if (present_lose)
        list.add(lose);

      boolean present_missionIndex = true;
      list.add(present_missionIndex);
      if (present_missionIndex)
        list.add(missionIndex);

      return list.hashCode();
    }

    @Override
    public int compareTo(getCards_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetLose()).compareTo(other.isSetLose());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetLose()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lose, other.lose);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetMissionIndex()).compareTo(other.isSetMissionIndex());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetMissionIndex()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.missionIndex, other.missionIndex);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("getCards_args(");
      boolean first = true;

      sb.append("lose:");
      sb.append(this.lose);
      first = false;
      if (!first) sb.append(", ");
      sb.append("missionIndex:");
      sb.append(this.missionIndex);
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws TException {
      // check for required fields
      // check for sub-struct validity
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
        __isset_bitfield = 0;
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class getCards_argsStandardSchemeFactory implements SchemeFactory {
      public getCards_argsStandardScheme getScheme() {
        return new getCards_argsStandardScheme();
      }
    }

    private static class getCards_argsStandardScheme extends StandardScheme<getCards_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, getCards_args struct) throws TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
            break;
          }
          switch (schemeField.id) {
            case 1: // LOSE
              if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
                struct.lose = iprot.readBool();
                struct.setLoseIsSet(true);
              } else {
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // MISSION_INDEX
              if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                struct.missionIndex = iprot.readI32();
                struct.setMissionIndexIsSet(true);
              } else {
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, getCards_args struct) throws TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        oprot.writeFieldBegin(LOSE_FIELD_DESC);
        oprot.writeBool(struct.lose);
        oprot.writeFieldEnd();
        oprot.writeFieldBegin(MISSION_INDEX_FIELD_DESC);
        oprot.writeI32(struct.missionIndex);
        oprot.writeFieldEnd();
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class getCards_argsTupleSchemeFactory implements SchemeFactory {
      public getCards_argsTupleScheme getScheme() {
        return new getCards_argsTupleScheme();
      }
    }

    private static class getCards_argsTupleScheme extends TupleScheme<getCards_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, getCards_args struct) throws TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetLose()) {
          optionals.set(0);
        }
        if (struct.isSetMissionIndex()) {
          optionals.set(1);
        }
        oprot.writeBitSet(optionals, 2);
        if (struct.isSetLose()) {
          oprot.writeBool(struct.lose);
        }
        if (struct.isSetMissionIndex()) {
          oprot.writeI32(struct.missionIndex);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, getCards_args struct) throws TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(2);
        if (incoming.get(0)) {
          struct.lose = iprot.readBool();
          struct.setLoseIsSet(true);
        }
        if (incoming.get(1)) {
          struct.missionIndex = iprot.readI32();
          struct.setMissionIndexIsSet(true);
        }
      }
    }

  }

  public static class getCards_result implements org.apache.thrift.TBase<getCards_result, getCards_result._Fields>, java.io.Serializable, Cloneable, Comparable<getCards_result>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("getCards_result");

    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.STRUCT, (short)0);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new getCards_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new getCards_resultTupleSchemeFactory());
    }

    public MissionCards success; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      SUCCESS((short)0, "success");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 0: // SUCCESS
            return SUCCESS;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT,
              new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, MissionCards.class)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(getCards_result.class, metaDataMap);
    }

    public getCards_result() {
    }

    public getCards_result(
            MissionCards success)
    {
      this();
      this.success = success;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public getCards_result(getCards_result other) {
      if (other.isSetSuccess()) {
        this.success = new MissionCards(other.success);
      }
    }

    public getCards_result deepCopy() {
      return new getCards_result(this);
    }

    @Override
    public void clear() {
      this.success = null;
    }

    public MissionCards getSuccess() {
      return this.success;
    }

    public getCards_result setSuccess(MissionCards success) {
      this.success = success;
      return this;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
        case SUCCESS:
          if (value == null) {
            unsetSuccess();
          } else {
            setSuccess((MissionCards)value);
          }
          break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
        case SUCCESS:
          return getSuccess();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
        case SUCCESS:
          return isSetSuccess();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof getCards_result)
        return this.equals((getCards_result)that);
      return false;
    }

    public boolean equals(getCards_result that) {
      if (that == null)
        return false;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      List<Object> list = new ArrayList<Object>();

      boolean present_success = true && (isSetSuccess());
      list.add(present_success);
      if (present_success)
        list.add(success);

      return list.hashCode();
    }

    @Override
    public int compareTo(getCards_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(other.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, other.success);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("getCards_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws TException {
      // check for required fields
      // check for sub-struct validity
      if (success != null) {
        success.validate();
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class getCards_resultStandardSchemeFactory implements SchemeFactory {
      public getCards_resultStandardScheme getScheme() {
        return new getCards_resultStandardScheme();
      }
    }

    private static class getCards_resultStandardScheme extends StandardScheme<getCards_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, getCards_result struct) throws TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
            break;
          }
          switch (schemeField.id) {
            case 0: // SUCCESS
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.success = new MissionCards();
                struct.success.read(iprot);
                struct.setSuccessIsSet(true);
              } else {
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, getCards_result struct) throws TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.success != null) {
          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
          struct.success.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class getCards_resultTupleSchemeFactory implements SchemeFactory {
      public getCards_resultTupleScheme getScheme() {
        return new getCards_resultTupleScheme();
      }
    }

    private static class getCards_resultTupleScheme extends TupleScheme<getCards_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, getCards_result struct) throws TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetSuccess()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetSuccess()) {
          struct.success.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, getCards_result struct) throws TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.success = new MissionCards();
          struct.success.read(iprot);
          struct.setSuccessIsSet(true);
        }
      }
    }

  }

}
