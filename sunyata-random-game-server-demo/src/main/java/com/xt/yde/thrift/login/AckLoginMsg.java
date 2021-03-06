/**
 * Autogenerated by Thrift Compiler (0.9.2)
 * <p>
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *
 * @generated
 */
package com.xt.yde.thrift.login;

import org.apache.thrift.EncodingUtils;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import javax.annotation.Generated;
import java.util.*;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
/**
 * 登录应答信息
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-10-28")
public class AckLoginMsg implements org.apache.thrift.TBase<AckLoginMsg, AckLoginMsg._Fields>, java.io.Serializable,
        Cloneable, Comparable<AckLoginMsg> {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct
            ("AckLoginMsg");

    private static final org.apache.thrift.protocol.TField RESULT_FIELD_DESC = new org.apache.thrift.protocol.TField
            ("result", org.apache.thrift.protocol.TType.I32, (short) 1);
    private static final org.apache.thrift.protocol.TField CODE_FIELD_DESC = new org.apache.thrift.protocol.TField
            ("code", org.apache.thrift.protocol.TType.STRING, (short) 2);
    private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField
            ("userId", org.apache.thrift.protocol.TType.STRING, (short) 3);
    private static final org.apache.thrift.protocol.TField DISPLAY_NAME_FIELD_DESC = new org.apache.thrift.protocol
            .TField("displayName", org.apache.thrift.protocol.TType.STRING, (short) 4);
    private static final org.apache.thrift.protocol.TField AVATAR_FIELD_DESC = new org.apache.thrift.protocol.TField
            ("avatar", org.apache.thrift.protocol.TType.STRING, (short) 5);
    private static final org.apache.thrift.protocol.TField COIN_FIELD_DESC = new org.apache.thrift.protocol.TField
            ("coin", org.apache.thrift.protocol.TType.I64, (short) 6);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>,
            SchemeFactory>();

    static {
        schemes.put(StandardScheme.class, new AckLoginMsgStandardSchemeFactory());
        schemes.put(TupleScheme.class, new AckLoginMsgTupleSchemeFactory());
    }

    /**
     *
     * @see LoginResult
     */
    public LoginResult result; // required
    public String code; // required
    public String userId; // required
    public String displayName; // required
    public String avatar; // required
    public long coin; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
        /**
         *
         * @see LoginResult
         */
        RESULT((short) 1, "result"),
        CODE((short) 2, "code"),
        USER_ID((short) 3, "userId"),
        DISPLAY_NAME((short) 4, "displayName"),
        AVATAR((short) 5, "avatar"),
        COIN((short) 6, "coin");

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
            switch (fieldId) {
                case 1: // RESULT
                    return RESULT;
                case 2: // CODE
                    return CODE;
                case 3: // USER_ID
                    return USER_ID;
                case 4: // DISPLAY_NAME
                    return DISPLAY_NAME;
                case 5: // AVATAR
                    return AVATAR;
                case 6: // COIN
                    return COIN;
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
    private static final int __COIN_ISSET_ID = 0;
    private byte __isset_bitfield = 0;
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

    static {
        Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift
                .meta_data.FieldMetaData>(_Fields.class);
        tmpMap.put(_Fields.RESULT, new org.apache.thrift.meta_data.FieldMetaData("result", org.apache.thrift
                .TFieldRequirementType.DEFAULT,
                new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, LoginResult
                        .class)));
        tmpMap.put(_Fields.CODE, new org.apache.thrift.meta_data.FieldMetaData("code", org.apache.thrift
                .TFieldRequirementType.DEFAULT,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("userId", org.apache.thrift
                .TFieldRequirementType.DEFAULT,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.DISPLAY_NAME, new org.apache.thrift.meta_data.FieldMetaData("displayName", org.apache
                .thrift.TFieldRequirementType.DEFAULT,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.AVATAR, new org.apache.thrift.meta_data.FieldMetaData("avatar", org.apache.thrift
                .TFieldRequirementType.DEFAULT,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.COIN, new org.apache.thrift.meta_data.FieldMetaData("coin", org.apache.thrift
                .TFieldRequirementType.DEFAULT,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
        metaDataMap = Collections.unmodifiableMap(tmpMap);
        org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(AckLoginMsg.class, metaDataMap);
    }

    public AckLoginMsg() {
    }

    public AckLoginMsg(
            LoginResult result,
            String code,
            String userId,
            String displayName,
            String avatar,
            long coin) {
        this();
        this.result = result;
        this.code = code;
        this.userId = userId;
        this.displayName = displayName;
        this.avatar = avatar;
        this.coin = coin;
        setCoinIsSet(true);
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public AckLoginMsg(AckLoginMsg other) {
        __isset_bitfield = other.__isset_bitfield;
        if (other.isSetResult()) {
            this.result = other.result;
        }
        if (other.isSetCode()) {
            this.code = other.code;
        }
        if (other.isSetUserId()) {
            this.userId = other.userId;
        }
        if (other.isSetDisplayName()) {
            this.displayName = other.displayName;
        }
        if (other.isSetAvatar()) {
            this.avatar = other.avatar;
        }
        this.coin = other.coin;
    }

    public AckLoginMsg deepCopy() {
        return new AckLoginMsg(this);
    }

    @Override
    public void clear() {
        this.result = null;
        this.code = null;
        this.userId = null;
        this.displayName = null;
        this.avatar = null;
        setCoinIsSet(false);
        this.coin = 0;
    }

    /**
     *
     * @see LoginResult
     */
    public LoginResult getResult() {
        return this.result;
    }

    /**
     *
     * @see LoginResult
     */
    public AckLoginMsg setResult(LoginResult result) {
        this.result = result;
        return this;
    }

    public void unsetResult() {
        this.result = null;
    }

    /** Returns true if field result is set (has been assigned a value) and false otherwise */
    public boolean isSetResult() {
        return this.result != null;
    }

    public void setResultIsSet(boolean value) {
        if (!value) {
            this.result = null;
        }
    }

    public String getCode() {
        return this.code;
    }

    public AckLoginMsg setCode(String code) {
        this.code = code;
        return this;
    }

    public void unsetCode() {
        this.code = null;
    }

    /** Returns true if field code is set (has been assigned a value) and false otherwise */
    public boolean isSetCode() {
        return this.code != null;
    }

    public void setCodeIsSet(boolean value) {
        if (!value) {
            this.code = null;
        }
    }

    public String getUserId() {
        return this.userId;
    }

    public AckLoginMsg setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public void unsetUserId() {
        this.userId = null;
    }

    /** Returns true if field userId is set (has been assigned a value) and false otherwise */
    public boolean isSetUserId() {
        return this.userId != null;
    }

    public void setUserIdIsSet(boolean value) {
        if (!value) {
            this.userId = null;
        }
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public AckLoginMsg setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public void unsetDisplayName() {
        this.displayName = null;
    }

    /** Returns true if field displayName is set (has been assigned a value) and false otherwise */
    public boolean isSetDisplayName() {
        return this.displayName != null;
    }

    public void setDisplayNameIsSet(boolean value) {
        if (!value) {
            this.displayName = null;
        }
    }

    public String getAvatar() {
        return this.avatar;
    }

    public AckLoginMsg setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void unsetAvatar() {
        this.avatar = null;
    }

    /** Returns true if field avatar is set (has been assigned a value) and false otherwise */
    public boolean isSetAvatar() {
        return this.avatar != null;
    }

    public void setAvatarIsSet(boolean value) {
        if (!value) {
            this.avatar = null;
        }
    }

    public long getCoin() {
        return this.coin;
    }

    public AckLoginMsg setCoin(long coin) {
        this.coin = coin;
        setCoinIsSet(true);
        return this;
    }

    public void unsetCoin() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __COIN_ISSET_ID);
    }

    /** Returns true if field coin is set (has been assigned a value) and false otherwise */
    public boolean isSetCoin() {
        return EncodingUtils.testBit(__isset_bitfield, __COIN_ISSET_ID);
    }

    public void setCoinIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __COIN_ISSET_ID, value);
    }

    public void setFieldValue(_Fields field, Object value) {
        switch (field) {
            case RESULT:
                if (value == null) {
                    unsetResult();
                } else {
                    setResult((LoginResult) value);
                }
                break;

            case CODE:
                if (value == null) {
                    unsetCode();
                } else {
                    setCode((String) value);
                }
                break;

            case USER_ID:
                if (value == null) {
                    unsetUserId();
                } else {
                    setUserId((String) value);
                }
                break;

            case DISPLAY_NAME:
                if (value == null) {
                    unsetDisplayName();
                } else {
                    setDisplayName((String) value);
                }
                break;

            case AVATAR:
                if (value == null) {
                    unsetAvatar();
                } else {
                    setAvatar((String) value);
                }
                break;

            case COIN:
                if (value == null) {
                    unsetCoin();
                } else {
                    setCoin((Long) value);
                }
                break;

        }
    }

    public Object getFieldValue(_Fields field) {
        switch (field) {
            case RESULT:
                return getResult();

            case CODE:
                return getCode();

            case USER_ID:
                return getUserId();

            case DISPLAY_NAME:
                return getDisplayName();

            case AVATAR:
                return getAvatar();

            case COIN:
                return Long.valueOf(getCoin());

        }
        throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
        if (field == null) {
            throw new IllegalArgumentException();
        }

        switch (field) {
            case RESULT:
                return isSetResult();
            case CODE:
                return isSetCode();
            case USER_ID:
                return isSetUserId();
            case DISPLAY_NAME:
                return isSetDisplayName();
            case AVATAR:
                return isSetAvatar();
            case COIN:
                return isSetCoin();
        }
        throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (that instanceof AckLoginMsg)
            return this.equals((AckLoginMsg) that);
        return false;
    }

    public boolean equals(AckLoginMsg that) {
        if (that == null)
            return false;

        boolean this_present_result = true && this.isSetResult();
        boolean that_present_result = true && that.isSetResult();
        if (this_present_result || that_present_result) {
            if (!(this_present_result && that_present_result))
                return false;
            if (!this.result.equals(that.result))
                return false;
        }

        boolean this_present_code = true && this.isSetCode();
        boolean that_present_code = true && that.isSetCode();
        if (this_present_code || that_present_code) {
            if (!(this_present_code && that_present_code))
                return false;
            if (!this.code.equals(that.code))
                return false;
        }

        boolean this_present_userId = true && this.isSetUserId();
        boolean that_present_userId = true && that.isSetUserId();
        if (this_present_userId || that_present_userId) {
            if (!(this_present_userId && that_present_userId))
                return false;
            if (!this.userId.equals(that.userId))
                return false;
        }

        boolean this_present_displayName = true && this.isSetDisplayName();
        boolean that_present_displayName = true && that.isSetDisplayName();
        if (this_present_displayName || that_present_displayName) {
            if (!(this_present_displayName && that_present_displayName))
                return false;
            if (!this.displayName.equals(that.displayName))
                return false;
        }

        boolean this_present_avatar = true && this.isSetAvatar();
        boolean that_present_avatar = true && that.isSetAvatar();
        if (this_present_avatar || that_present_avatar) {
            if (!(this_present_avatar && that_present_avatar))
                return false;
            if (!this.avatar.equals(that.avatar))
                return false;
        }

        boolean this_present_coin = true;
        boolean that_present_coin = true;
        if (this_present_coin || that_present_coin) {
            if (!(this_present_coin && that_present_coin))
                return false;
            if (this.coin != that.coin)
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        List<Object> list = new ArrayList<Object>();

        boolean present_result = true && (isSetResult());
        list.add(present_result);
        if (present_result)
            list.add(result.getValue());

        boolean present_code = true && (isSetCode());
        list.add(present_code);
        if (present_code)
            list.add(code);

        boolean present_userId = true && (isSetUserId());
        list.add(present_userId);
        if (present_userId)
            list.add(userId);

        boolean present_displayName = true && (isSetDisplayName());
        list.add(present_displayName);
        if (present_displayName)
            list.add(displayName);

        boolean present_avatar = true && (isSetAvatar());
        list.add(present_avatar);
        if (present_avatar)
            list.add(avatar);

        boolean present_coin = true;
        list.add(present_coin);
        if (present_coin)
            list.add(coin);

        return list.hashCode();
    }

    @Override
    public int compareTo(AckLoginMsg other) {
        if (!getClass().equals(other.getClass())) {
            return getClass().getName().compareTo(other.getClass().getName());
        }

        int lastComparison = 0;

        lastComparison = Boolean.valueOf(isSetResult()).compareTo(other.isSetResult());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetResult()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.result, other.result);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetCode()).compareTo(other.isSetCode());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetCode()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.code, other.code);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetUserId()).compareTo(other.isSetUserId());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetUserId()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userId, other.userId);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetDisplayName()).compareTo(other.isSetDisplayName());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetDisplayName()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.displayName, other.displayName);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetAvatar()).compareTo(other.isSetAvatar());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetAvatar()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.avatar, other.avatar);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetCoin()).compareTo(other.isSetCoin());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetCoin()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.coin, other.coin);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        return 0;
    }

    public _Fields fieldForId(int fieldId) {
        return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
        schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
        schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AckLoginMsg(");
        boolean first = true;

        sb.append("result:");
        if (this.result == null) {
            sb.append("null");
        } else {
            sb.append(this.result);
        }
        first = false;
        if (!first) sb.append(", ");
        sb.append("code:");
        if (this.code == null) {
            sb.append("null");
        } else {
            sb.append(this.code);
        }
        first = false;
        if (!first) sb.append(", ");
        sb.append("userId:");
        if (this.userId == null) {
            sb.append("null");
        } else {
            sb.append(this.userId);
        }
        first = false;
        if (!first) sb.append(", ");
        sb.append("displayName:");
        if (this.displayName == null) {
            sb.append("null");
        } else {
            sb.append(this.displayName);
        }
        first = false;
        if (!first) sb.append(", ");
        sb.append("avatar:");
        if (this.avatar == null) {
            sb.append("null");
        } else {
            sb.append(this.avatar);
        }
        first = false;
        if (!first) sb.append(", ");
        sb.append("coin:");
        sb.append(this.coin);
        first = false;
        sb.append(")");
        return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
        // check for required fields
        // check for sub-struct validity
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        try {
            write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport
                    (out)));
        } catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        try {
            // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the
          // default constructor.
            __isset_bitfield = 0;
            read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport
                    (in)));
        } catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    private static class AckLoginMsgStandardSchemeFactory implements SchemeFactory {
        public AckLoginMsgStandardScheme getScheme() {
            return new AckLoginMsgStandardScheme();
        }
    }

    private static class AckLoginMsgStandardScheme extends StandardScheme<AckLoginMsg> {

        public void read(org.apache.thrift.protocol.TProtocol iprot, AckLoginMsg struct) throws org.apache.thrift
                .TException {
            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while (true) {
                schemeField = iprot.readFieldBegin();
                if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (schemeField.id) {
                    case 1: // RESULT
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.result = com.xt.yde.thrift.login.LoginResult.findByValue(iprot.readI32());
                            struct.setResultIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 2: // CODE
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.code = iprot.readString();
                            struct.setCodeIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 3: // USER_ID
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.userId = iprot.readString();
                            struct.setUserIdIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 4: // DISPLAY_NAME
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.displayName = iprot.readString();
                            struct.setDisplayNameIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 5: // AVATAR
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.avatar = iprot.readString();
                            struct.setAvatarIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 6: // COIN
                        if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
                            struct.coin = iprot.readI64();
                            struct.setCoinIsSet(true);
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

        public void write(org.apache.thrift.protocol.TProtocol oprot, AckLoginMsg struct) throws org.apache.thrift.TException {
            struct.validate();

            oprot.writeStructBegin(STRUCT_DESC);
            if (struct.result != null) {
                oprot.writeFieldBegin(RESULT_FIELD_DESC);
                oprot.writeI32(struct.result.getValue());
                oprot.writeFieldEnd();
            }
            if (struct.code != null) {
                oprot.writeFieldBegin(CODE_FIELD_DESC);
                oprot.writeString(struct.code);
                oprot.writeFieldEnd();
            }
            if (struct.userId != null) {
                oprot.writeFieldBegin(USER_ID_FIELD_DESC);
                oprot.writeString(struct.userId);
                oprot.writeFieldEnd();
            }
            if (struct.displayName != null) {
                oprot.writeFieldBegin(DISPLAY_NAME_FIELD_DESC);
                oprot.writeString(struct.displayName);
                oprot.writeFieldEnd();
            }
            if (struct.avatar != null) {
                oprot.writeFieldBegin(AVATAR_FIELD_DESC);
                oprot.writeString(struct.avatar);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldBegin(COIN_FIELD_DESC);
            oprot.writeI64(struct.coin);
            oprot.writeFieldEnd();
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

    }

    private static class AckLoginMsgTupleSchemeFactory implements SchemeFactory {
        public AckLoginMsgTupleScheme getScheme() {
            return new AckLoginMsgTupleScheme();
        }
    }

    private static class AckLoginMsgTupleScheme extends TupleScheme<AckLoginMsg> {

        @Override
        public void write(org.apache.thrift.protocol.TProtocol prot, AckLoginMsg struct) throws org.apache.thrift.TException {
            TTupleProtocol oprot = (TTupleProtocol) prot;
            BitSet optionals = new BitSet();
            if (struct.isSetResult()) {
                optionals.set(0);
            }
            if (struct.isSetCode()) {
                optionals.set(1);
            }
            if (struct.isSetUserId()) {
                optionals.set(2);
            }
            if (struct.isSetDisplayName()) {
                optionals.set(3);
            }
            if (struct.isSetAvatar()) {
                optionals.set(4);
            }
            if (struct.isSetCoin()) {
                optionals.set(5);
            }
            oprot.writeBitSet(optionals, 6);
            if (struct.isSetResult()) {
                oprot.writeI32(struct.result.getValue());
            }
            if (struct.isSetCode()) {
                oprot.writeString(struct.code);
            }
            if (struct.isSetUserId()) {
                oprot.writeString(struct.userId);
            }
            if (struct.isSetDisplayName()) {
                oprot.writeString(struct.displayName);
            }
            if (struct.isSetAvatar()) {
                oprot.writeString(struct.avatar);
            }
            if (struct.isSetCoin()) {
                oprot.writeI64(struct.coin);
            }
        }

        @Override
        public void read(org.apache.thrift.protocol.TProtocol prot, AckLoginMsg struct) throws org.apache.thrift.TException {
            TTupleProtocol iprot = (TTupleProtocol) prot;
            BitSet incoming = iprot.readBitSet(6);
            if (incoming.get(0)) {
                struct.result = com.xt.yde.thrift.login.LoginResult.findByValue(iprot.readI32());
                struct.setResultIsSet(true);
            }
            if (incoming.get(1)) {
                struct.code = iprot.readString();
                struct.setCodeIsSet(true);
            }
            if (incoming.get(2)) {
                struct.userId = iprot.readString();
                struct.setUserIdIsSet(true);
            }
            if (incoming.get(3)) {
                struct.displayName = iprot.readString();
                struct.setDisplayNameIsSet(true);
            }
            if (incoming.get(4)) {
                struct.avatar = iprot.readString();
                struct.setAvatarIsSet(true);
            }
            if (incoming.get(5)) {
                struct.coin = iprot.readI64();
                struct.setCoinIsSet(true);
            }
        }
    }

}

