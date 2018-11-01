/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xt.yde.thrift.card.puzzle;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import javax.annotation.Generated;
import java.util.*;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
/**
 * 发牌信息
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-05-19")
public class PuzzleCards implements org.apache.thrift.TBase<PuzzleCards, PuzzleCards._Fields>, java.io.Serializable, Cloneable, Comparable<PuzzleCards> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PuzzleCards");

  private static final org.apache.thrift.protocol.TField CARD_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("cardId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField CARDS_FIELD_DESC = new org.apache.thrift.protocol.TField("cards", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PuzzleCardsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PuzzleCardsTupleSchemeFactory());
  }

  public String cardId; // required
  public List<List<List<Integer>>> cards; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CARD_ID((short)1, "cardId"),
    CARDS((short)2, "cards");

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
        case 2: // CARDS
          return CARDS;
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
    tmpMap.put(_Fields.CARDS, new org.apache.thrift.meta_data.FieldMetaData("cards", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
                new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
                    new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32))))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PuzzleCards.class, metaDataMap);
  }

  public PuzzleCards() {
  }

  public PuzzleCards(
    String cardId,
    List<List<List<Integer>>> cards)
  {
    this();
    this.cardId = cardId;
    this.cards = cards;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PuzzleCards(PuzzleCards other) {
    if (other.isSetCardId()) {
      this.cardId = other.cardId;
    }
    if (other.isSetCards()) {
      List<List<List<Integer>>> __this__cards = new ArrayList<List<List<Integer>>>(other.cards.size());
      for (List<List<Integer>> other_element : other.cards) {
        List<List<Integer>> __this__cards_copy = new ArrayList<List<Integer>>(other_element.size());
        for (List<Integer> other_element_element : other_element) {
          List<Integer> __this__cards_copy_copy = new ArrayList<Integer>(other_element_element);
          __this__cards_copy.add(__this__cards_copy_copy);
        }
        __this__cards.add(__this__cards_copy);
      }
      this.cards = __this__cards;
    }
  }

  public PuzzleCards deepCopy() {
    return new PuzzleCards(this);
  }

  @Override
  public void clear() {
    this.cardId = null;
    this.cards = null;
  }

  public String getCardId() {
    return this.cardId;
  }

  public PuzzleCards setCardId(String cardId) {
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

  public int getCardsSize() {
    return (this.cards == null) ? 0 : this.cards.size();
  }

  public java.util.Iterator<List<List<Integer>>> getCardsIterator() {
    return (this.cards == null) ? null : this.cards.iterator();
  }

  public void addToCards(List<List<Integer>> elem) {
    if (this.cards == null) {
      this.cards = new ArrayList<List<List<Integer>>>();
    }
    this.cards.add(elem);
  }

  public List<List<List<Integer>>> getCards() {
    return this.cards;
  }

  public PuzzleCards setCards(List<List<List<Integer>>> cards) {
    this.cards = cards;
    return this;
  }

  public void unsetCards() {
    this.cards = null;
  }

  /** Returns true if field cards is set (has been assigned a value) and false otherwise */
  public boolean isSetCards() {
    return this.cards != null;
  }

  public void setCardsIsSet(boolean value) {
    if (!value) {
      this.cards = null;
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

    case CARDS:
      if (value == null) {
        unsetCards();
      } else {
        setCards((List<List<List<Integer>>>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CARD_ID:
      return getCardId();

    case CARDS:
      return getCards();

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
    case CARDS:
      return isSetCards();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof PuzzleCards)
      return this.equals((PuzzleCards)that);
    return false;
  }

  public boolean equals(PuzzleCards that) {
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

    boolean this_present_cards = true && this.isSetCards();
    boolean that_present_cards = true && that.isSetCards();
    if (this_present_cards || that_present_cards) {
      if (!(this_present_cards && that_present_cards))
        return false;
      if (!this.cards.equals(that.cards))
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

    boolean present_cards = true && (isSetCards());
    list.add(present_cards);
    if (present_cards)
      list.add(cards);

    return list.hashCode();
  }

  @Override
  public int compareTo(PuzzleCards other) {
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
    lastComparison = Boolean.valueOf(isSetCards()).compareTo(other.isSetCards());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCards()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cards, other.cards);
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
    StringBuilder sb = new StringBuilder("PuzzleCards(");
    boolean first = true;

    sb.append("cardId:");
    if (this.cardId == null) {
      sb.append("null");
    } else {
      sb.append(this.cardId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("cards:");
    if (this.cards == null) {
      sb.append("null");
    } else {
      sb.append(this.cards);
    }
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class PuzzleCardsStandardSchemeFactory implements SchemeFactory {
    public PuzzleCardsStandardScheme getScheme() {
      return new PuzzleCardsStandardScheme();
    }
  }

  private static class PuzzleCardsStandardScheme extends StandardScheme<PuzzleCards> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PuzzleCards struct) throws TException {
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
          case 2: // CARDS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.cards = new ArrayList<List<List<Integer>>>(_list0.size);
                List<List<Integer>> _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  {
                    org.apache.thrift.protocol.TList _list3 = iprot.readListBegin();
                    _elem1 = new ArrayList<List<Integer>>(_list3.size);
                    List<Integer> _elem4;
                    for (int _i5 = 0; _i5 < _list3.size; ++_i5)
                    {
                      {
                        org.apache.thrift.protocol.TList _list6 = iprot.readListBegin();
                        _elem4 = new ArrayList<Integer>(_list6.size);
                        int _elem7;
                        for (int _i8 = 0; _i8 < _list6.size; ++_i8)
                        {
                          _elem7 = iprot.readI32();
                          _elem4.add(_elem7);
                        }
                        iprot.readListEnd();
                      }
                      _elem1.add(_elem4);
                    }
                    iprot.readListEnd();
                  }
                  struct.cards.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setCardsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PuzzleCards struct) throws TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.cardId != null) {
        oprot.writeFieldBegin(CARD_ID_FIELD_DESC);
        oprot.writeString(struct.cardId);
        oprot.writeFieldEnd();
      }
      if (struct.cards != null) {
        oprot.writeFieldBegin(CARDS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.LIST, struct.cards.size()));
          for (List<List<Integer>> _iter9 : struct.cards)
          {
            {
              oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.LIST, _iter9.size()));
              for (List<Integer> _iter10 : _iter9)
              {
                {
                  oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, _iter10.size()));
                  for (int _iter11 : _iter10)
                  {
                    oprot.writeI32(_iter11);
                  }
                  oprot.writeListEnd();
                }
              }
              oprot.writeListEnd();
            }
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PuzzleCardsTupleSchemeFactory implements SchemeFactory {
    public PuzzleCardsTupleScheme getScheme() {
      return new PuzzleCardsTupleScheme();
    }
  }

  private static class PuzzleCardsTupleScheme extends TupleScheme<PuzzleCards> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PuzzleCards struct) throws TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetCardId()) {
        optionals.set(0);
      }
      if (struct.isSetCards()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetCardId()) {
        oprot.writeString(struct.cardId);
      }
      if (struct.isSetCards()) {
        {
          oprot.writeI32(struct.cards.size());
          for (List<List<Integer>> _iter12 : struct.cards)
          {
            {
              oprot.writeI32(_iter12.size());
              for (List<Integer> _iter13 : _iter12)
              {
                {
                  oprot.writeI32(_iter13.size());
                  for (int _iter14 : _iter13)
                  {
                    oprot.writeI32(_iter14);
                  }
                }
              }
            }
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PuzzleCards struct) throws TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.cardId = iprot.readString();
        struct.setCardIdIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list15 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.LIST, iprot.readI32());
          struct.cards = new ArrayList<List<List<Integer>>>(_list15.size);
          List<List<Integer>> _elem16;
          for (int _i17 = 0; _i17 < _list15.size; ++_i17)
          {
            {
              org.apache.thrift.protocol.TList _list18 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.LIST, iprot.readI32());
              _elem16 = new ArrayList<List<Integer>>(_list18.size);
              List<Integer> _elem19;
              for (int _i20 = 0; _i20 < _list18.size; ++_i20)
              {
                {
                  org.apache.thrift.protocol.TList _list21 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, iprot.readI32());
                  _elem19 = new ArrayList<Integer>(_list21.size);
                  int _elem22;
                  for (int _i23 = 0; _i23 < _list21.size; ++_i23)
                  {
                    _elem22 = iprot.readI32();
                    _elem19.add(_elem22);
                  }
                }
                _elem16.add(_elem19);
              }
            }
            struct.cards.add(_elem16);
          }
        }
        struct.setCardsIsSet(true);
      }
    }
  }

}

