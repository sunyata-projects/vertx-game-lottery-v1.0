/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xt.yde.thrift.regular;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import javax.annotation.Generated;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
/**
 * 发牌信息
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-06-02")
public class RegularCards implements org.apache.thrift.TBase<RegularCards, RegularCards._Fields>, java.io.Serializable, Cloneable, Comparable<RegularCards> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RegularCards");

  private static final org.apache.thrift.protocol.TField CARD_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("cardId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField CENTER_FIELD_DESC = new org.apache.thrift.protocol.TField("center", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField LEFT_FIELD_DESC = new org.apache.thrift.protocol.TField("left", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField RIGHT_FIELD_DESC = new org.apache.thrift.protocol.TField("right", org.apache.thrift.protocol.TType.LIST, (short)4);
  private static final org.apache.thrift.protocol.TField UNDER_FIELD_DESC = new org.apache.thrift.protocol.TField("under", org.apache.thrift.protocol.TType.LIST, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new RegularCardsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new RegularCardsTupleSchemeFactory());
  }

  public String cardId; // required
  public List<Integer> center; // required
  public List<Integer> left; // required
  public List<Integer> right; // required
  public List<Integer> under; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CARD_ID((short)1, "cardId"),
    CENTER((short)2, "center"),
    LEFT((short)3, "left"),
    RIGHT((short)4, "right"),
    UNDER((short)5, "under");

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
        case 1: // CARD_ID
          return CARD_ID;
        case 2: // CENTER
          return CENTER;
        case 3: // LEFT
          return LEFT;
        case 4: // RIGHT
          return RIGHT;
        case 5: // UNDER
          return UNDER;
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
    tmpMap.put(_Fields.CARD_ID, new org.apache.thrift.meta_data.FieldMetaData("cardId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CENTER, new org.apache.thrift.meta_data.FieldMetaData("center", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32))));
    tmpMap.put(_Fields.LEFT, new org.apache.thrift.meta_data.FieldMetaData("left", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32))));
    tmpMap.put(_Fields.RIGHT, new org.apache.thrift.meta_data.FieldMetaData("right", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32))));
    tmpMap.put(_Fields.UNDER, new org.apache.thrift.meta_data.FieldMetaData("under", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RegularCards.class, metaDataMap);
  }

  public RegularCards() {
  }

  public RegularCards(
    String cardId,
    List<Integer> center,
    List<Integer> left,
    List<Integer> right,
    List<Integer> under)
  {
    this();
    this.cardId = cardId;
    this.center = center;
    this.left = left;
    this.right = right;
    this.under = under;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RegularCards(RegularCards other) {
    if (other.isSetCardId()) {
      this.cardId = other.cardId;
    }
    if (other.isSetCenter()) {
      List<Integer> __this__center = new ArrayList<Integer>(other.center);
      this.center = __this__center;
    }
    if (other.isSetLeft()) {
      List<Integer> __this__left = new ArrayList<Integer>(other.left);
      this.left = __this__left;
    }
    if (other.isSetRight()) {
      List<Integer> __this__right = new ArrayList<Integer>(other.right);
      this.right = __this__right;
    }
    if (other.isSetUnder()) {
      List<Integer> __this__under = new ArrayList<Integer>(other.under);
      this.under = __this__under;
    }
  }

  public RegularCards deepCopy() {
    return new RegularCards(this);
  }

  @Override
  public void clear() {
    this.cardId = null;
    this.center = null;
    this.left = null;
    this.right = null;
    this.under = null;
  }

  public String getCardId() {
    return this.cardId;
  }

  public RegularCards setCardId(String cardId) {
    this.cardId = cardId;
    return this;
  }

  public void unsetCardId() {
    this.cardId = null;
  }

  /** Returns true if field cardId is set (has been assigned a value) and false otherwise */
  public boolean isSetCardId() {
    return this.cardId != null;
  }

  public void setCardIdIsSet(boolean value) {
    if (!value) {
      this.cardId = null;
    }
  }

  public int getCenterSize() {
    return (this.center == null) ? 0 : this.center.size();
  }

  public java.util.Iterator<Integer> getCenterIterator() {
    return (this.center == null) ? null : this.center.iterator();
  }

  public void addToCenter(int elem) {
    if (this.center == null) {
      this.center = new ArrayList<Integer>();
    }
    this.center.add(elem);
  }

  public List<Integer> getCenter() {
    return this.center;
  }

  public RegularCards setCenter(List<Integer> center) {
    this.center = center;
    return this;
  }

  public void unsetCenter() {
    this.center = null;
  }

  /** Returns true if field center is set (has been assigned a value) and false otherwise */
  public boolean isSetCenter() {
    return this.center != null;
  }

  public void setCenterIsSet(boolean value) {
    if (!value) {
      this.center = null;
    }
  }

  public int getLeftSize() {
    return (this.left == null) ? 0 : this.left.size();
  }

  public java.util.Iterator<Integer> getLeftIterator() {
    return (this.left == null) ? null : this.left.iterator();
  }

  public void addToLeft(int elem) {
    if (this.left == null) {
      this.left = new ArrayList<Integer>();
    }
    this.left.add(elem);
  }

  public List<Integer> getLeft() {
    return this.left;
  }

  public RegularCards setLeft(List<Integer> left) {
    this.left = left;
    return this;
  }

  public void unsetLeft() {
    this.left = null;
  }

  /** Returns true if field left is set (has been assigned a value) and false otherwise */
  public boolean isSetLeft() {
    return this.left != null;
  }

  public void setLeftIsSet(boolean value) {
    if (!value) {
      this.left = null;
    }
  }

  public int getRightSize() {
    return (this.right == null) ? 0 : this.right.size();
  }

  public java.util.Iterator<Integer> getRightIterator() {
    return (this.right == null) ? null : this.right.iterator();
  }

  public void addToRight(int elem) {
    if (this.right == null) {
      this.right = new ArrayList<Integer>();
    }
    this.right.add(elem);
  }

  public List<Integer> getRight() {
    return this.right;
  }

  public RegularCards setRight(List<Integer> right) {
    this.right = right;
    return this;
  }

  public void unsetRight() {
    this.right = null;
  }

  /** Returns true if field right is set (has been assigned a value) and false otherwise */
  public boolean isSetRight() {
    return this.right != null;
  }

  public void setRightIsSet(boolean value) {
    if (!value) {
      this.right = null;
    }
  }

  public int getUnderSize() {
    return (this.under == null) ? 0 : this.under.size();
  }

  public java.util.Iterator<Integer> getUnderIterator() {
    return (this.under == null) ? null : this.under.iterator();
  }

  public void addToUnder(int elem) {
    if (this.under == null) {
      this.under = new ArrayList<Integer>();
    }
    this.under.add(elem);
  }

  public List<Integer> getUnder() {
    return this.under;
  }

  public RegularCards setUnder(List<Integer> under) {
    this.under = under;
    return this;
  }

  public void unsetUnder() {
    this.under = null;
  }

  /** Returns true if field under is set (has been assigned a value) and false otherwise */
  public boolean isSetUnder() {
    return this.under != null;
  }

  public void setUnderIsSet(boolean value) {
    if (!value) {
      this.under = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CARD_ID:
      if (value == null) {
        unsetCardId();
      } else {
        setCardId((String)value);
      }
      break;

    case CENTER:
      if (value == null) {
        unsetCenter();
      } else {
        setCenter((List<Integer>)value);
      }
      break;

    case LEFT:
      if (value == null) {
        unsetLeft();
      } else {
        setLeft((List<Integer>)value);
      }
      break;

    case RIGHT:
      if (value == null) {
        unsetRight();
      } else {
        setRight((List<Integer>)value);
      }
      break;

    case UNDER:
      if (value == null) {
        unsetUnder();
      } else {
        setUnder((List<Integer>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CARD_ID:
      return getCardId();

    case CENTER:
      return getCenter();

    case LEFT:
      return getLeft();

    case RIGHT:
      return getRight();

    case UNDER:
      return getUnder();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CARD_ID:
      return isSetCardId();
    case CENTER:
      return isSetCenter();
    case LEFT:
      return isSetLeft();
    case RIGHT:
      return isSetRight();
    case UNDER:
      return isSetUnder();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof RegularCards)
      return this.equals((RegularCards)that);
    return false;
  }

  public boolean equals(RegularCards that) {
    if (that == null)
      return false;

    boolean this_present_cardId = true && this.isSetCardId();
    boolean that_present_cardId = true && that.isSetCardId();
    if (this_present_cardId || that_present_cardId) {
      if (!(this_present_cardId && that_present_cardId))
        return false;
      if (!this.cardId.equals(that.cardId))
        return false;
    }

    boolean this_present_center = true && this.isSetCenter();
    boolean that_present_center = true && that.isSetCenter();
    if (this_present_center || that_present_center) {
      if (!(this_present_center && that_present_center))
        return false;
      if (!this.center.equals(that.center))
        return false;
    }

    boolean this_present_left = true && this.isSetLeft();
    boolean that_present_left = true && that.isSetLeft();
    if (this_present_left || that_present_left) {
      if (!(this_present_left && that_present_left))
        return false;
      if (!this.left.equals(that.left))
        return false;
    }

    boolean this_present_right = true && this.isSetRight();
    boolean that_present_right = true && that.isSetRight();
    if (this_present_right || that_present_right) {
      if (!(this_present_right && that_present_right))
        return false;
      if (!this.right.equals(that.right))
        return false;
    }

    boolean this_present_under = true && this.isSetUnder();
    boolean that_present_under = true && that.isSetUnder();
    if (this_present_under || that_present_under) {
      if (!(this_present_under && that_present_under))
        return false;
      if (!this.under.equals(that.under))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_cardId = true && (isSetCardId());
    list.add(present_cardId);
    if (present_cardId)
      list.add(cardId);

    boolean present_center = true && (isSetCenter());
    list.add(present_center);
    if (present_center)
      list.add(center);

    boolean present_left = true && (isSetLeft());
    list.add(present_left);
    if (present_left)
      list.add(left);

    boolean present_right = true && (isSetRight());
    list.add(present_right);
    if (present_right)
      list.add(right);

    boolean present_under = true && (isSetUnder());
    list.add(present_under);
    if (present_under)
      list.add(under);

    return list.hashCode();
  }

  @Override
  public int compareTo(RegularCards other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCardId()).compareTo(other.isSetCardId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCardId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cardId, other.cardId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCenter()).compareTo(other.isSetCenter());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCenter()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.center, other.center);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLeft()).compareTo(other.isSetLeft());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLeft()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.left, other.left);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRight()).compareTo(other.isSetRight());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRight()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.right, other.right);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUnder()).compareTo(other.isSetUnder());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUnder()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.under, other.under);
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
    StringBuilder sb = new StringBuilder("RegularCards(");
    boolean first = true;

    sb.append("cardId:");
    if (this.cardId == null) {
      sb.append("null");
    } else {
      sb.append(this.cardId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("center:");
    if (this.center == null) {
      sb.append("null");
    } else {
      sb.append(this.center);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("left:");
    if (this.left == null) {
      sb.append("null");
    } else {
      sb.append(this.left);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("right:");
    if (this.right == null) {
      sb.append("null");
    } else {
      sb.append(this.right);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("under:");
    if (this.under == null) {
      sb.append("null");
    } else {
      sb.append(this.under);
    }
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
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class RegularCardsStandardSchemeFactory implements SchemeFactory {
    public RegularCardsStandardScheme getScheme() {
      return new RegularCardsStandardScheme();
    }
  }

  private static class RegularCardsStandardScheme extends StandardScheme<RegularCards> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, RegularCards struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CARD_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.cardId = iprot.readString();
              struct.setCardIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CENTER
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.center = new ArrayList<Integer>(_list0.size);
                int _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = iprot.readI32();
                  struct.center.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setCenterIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // LEFT
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list3 = iprot.readListBegin();
                struct.left = new ArrayList<Integer>(_list3.size);
                int _elem4;
                for (int _i5 = 0; _i5 < _list3.size; ++_i5)
                {
                  _elem4 = iprot.readI32();
                  struct.left.add(_elem4);
                }
                iprot.readListEnd();
              }
              struct.setLeftIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // RIGHT
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list6 = iprot.readListBegin();
                struct.right = new ArrayList<Integer>(_list6.size);
                int _elem7;
                for (int _i8 = 0; _i8 < _list6.size; ++_i8)
                {
                  _elem7 = iprot.readI32();
                  struct.right.add(_elem7);
                }
                iprot.readListEnd();
              }
              struct.setRightIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // UNDER
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list9 = iprot.readListBegin();
                struct.under = new ArrayList<Integer>(_list9.size);
                int _elem10;
                for (int _i11 = 0; _i11 < _list9.size; ++_i11)
                {
                  _elem10 = iprot.readI32();
                  struct.under.add(_elem10);
                }
                iprot.readListEnd();
              }
              struct.setUnderIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, RegularCards struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.cardId != null) {
        oprot.writeFieldBegin(CARD_ID_FIELD_DESC);
        oprot.writeString(struct.cardId);
        oprot.writeFieldEnd();
      }
      if (struct.center != null) {
        oprot.writeFieldBegin(CENTER_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, struct.center.size()));
          for (int _iter12 : struct.center)
          {
            oprot.writeI32(_iter12);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.left != null) {
        oprot.writeFieldBegin(LEFT_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, struct.left.size()));
          for (int _iter13 : struct.left)
          {
            oprot.writeI32(_iter13);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.right != null) {
        oprot.writeFieldBegin(RIGHT_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, struct.right.size()));
          for (int _iter14 : struct.right)
          {
            oprot.writeI32(_iter14);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.under != null) {
        oprot.writeFieldBegin(UNDER_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, struct.under.size()));
          for (int _iter15 : struct.under)
          {
            oprot.writeI32(_iter15);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RegularCardsTupleSchemeFactory implements SchemeFactory {
    public RegularCardsTupleScheme getScheme() {
      return new RegularCardsTupleScheme();
    }
  }

  private static class RegularCardsTupleScheme extends TupleScheme<RegularCards> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, RegularCards struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetCardId()) {
        optionals.set(0);
      }
      if (struct.isSetCenter()) {
        optionals.set(1);
      }
      if (struct.isSetLeft()) {
        optionals.set(2);
      }
      if (struct.isSetRight()) {
        optionals.set(3);
      }
      if (struct.isSetUnder()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetCardId()) {
        oprot.writeString(struct.cardId);
      }
      if (struct.isSetCenter()) {
        {
          oprot.writeI32(struct.center.size());
          for (int _iter16 : struct.center)
          {
            oprot.writeI32(_iter16);
          }
        }
      }
      if (struct.isSetLeft()) {
        {
          oprot.writeI32(struct.left.size());
          for (int _iter17 : struct.left)
          {
            oprot.writeI32(_iter17);
          }
        }
      }
      if (struct.isSetRight()) {
        {
          oprot.writeI32(struct.right.size());
          for (int _iter18 : struct.right)
          {
            oprot.writeI32(_iter18);
          }
        }
      }
      if (struct.isSetUnder()) {
        {
          oprot.writeI32(struct.under.size());
          for (int _iter19 : struct.under)
          {
            oprot.writeI32(_iter19);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, RegularCards struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.cardId = iprot.readString();
        struct.setCardIdIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list20 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, iprot.readI32());
          struct.center = new ArrayList<Integer>(_list20.size);
          int _elem21;
          for (int _i22 = 0; _i22 < _list20.size; ++_i22)
          {
            _elem21 = iprot.readI32();
            struct.center.add(_elem21);
          }
        }
        struct.setCenterIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list23 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, iprot.readI32());
          struct.left = new ArrayList<Integer>(_list23.size);
          int _elem24;
          for (int _i25 = 0; _i25 < _list23.size; ++_i25)
          {
            _elem24 = iprot.readI32();
            struct.left.add(_elem24);
          }
        }
        struct.setLeftIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list26 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, iprot.readI32());
          struct.right = new ArrayList<Integer>(_list26.size);
          int _elem27;
          for (int _i28 = 0; _i28 < _list26.size; ++_i28)
          {
            _elem27 = iprot.readI32();
            struct.right.add(_elem27);
          }
        }
        struct.setRightIsSet(true);
      }
      if (incoming.get(4)) {
        {
          org.apache.thrift.protocol.TList _list29 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, iprot.readI32());
          struct.under = new ArrayList<Integer>(_list29.size);
          int _elem30;
          for (int _i31 = 0; _i31 < _list29.size; ++_i31)
          {
            _elem30 = iprot.readI32();
            struct.under.add(_elem30);
          }
        }
        struct.setUnderIsSet(true);
      }
    }
  }

}

