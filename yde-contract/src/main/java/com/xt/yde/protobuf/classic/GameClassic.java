// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: gameclassic.protobuf

package com.xt.yde.protobuf.classic;

public final class GameClassic {
  private GameClassic() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface SummaryClearGameRequestMsgOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
  }
  /**
   * Protobuf type {@code com.xt.yde.protobuf.classic.SummaryClearGameRequestMsg}
   *
   * <pre>
   *经典赛比大小前结算
   * </pre>
   */
  public static final class SummaryClearGameRequestMsg extends
      com.google.protobuf.GeneratedMessage
      implements SummaryClearGameRequestMsgOrBuilder {
    // Use SummaryClearGameRequestMsg.newBuilder() to construct.
    private SummaryClearGameRequestMsg(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private SummaryClearGameRequestMsg(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final SummaryClearGameRequestMsg defaultInstance;
    public static SummaryClearGameRequestMsg getDefaultInstance() {
      return defaultInstance;
    }

    public SummaryClearGameRequestMsg getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private SummaryClearGameRequestMsg(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.xt.yde.protobuf.classic.GameClassic.internal_static_com_xt_yde_protobuf_classic_SummaryClearGameRequestMsg_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.xt.yde.protobuf.classic.GameClassic.internal_static_com_xt_yde_protobuf_classic_SummaryClearGameRequestMsg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg.class, com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg.Builder.class);
    }

    public static com.google.protobuf.Parser<SummaryClearGameRequestMsg> PARSER =
        new com.google.protobuf.AbstractParser<SummaryClearGameRequestMsg>() {
      public SummaryClearGameRequestMsg parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new SummaryClearGameRequestMsg(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<SummaryClearGameRequestMsg> getParserForType() {
      return PARSER;
    }

    private void initFields() {
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.xt.yde.protobuf.classic.SummaryClearGameRequestMsg}
     *
     * <pre>
     *经典赛比大小前结算
     * </pre>
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsgOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.xt.yde.protobuf.classic.GameClassic.internal_static_com_xt_yde_protobuf_classic_SummaryClearGameRequestMsg_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.xt.yde.protobuf.classic.GameClassic.internal_static_com_xt_yde_protobuf_classic_SummaryClearGameRequestMsg_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg.class, com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg.Builder.class);
      }

      // Construct using com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.xt.yde.protobuf.classic.GameClassic.internal_static_com_xt_yde_protobuf_classic_SummaryClearGameRequestMsg_descriptor;
      }

      public com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg getDefaultInstanceForType() {
        return com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg.getDefaultInstance();
      }

      public com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg build() {
        com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg buildPartial() {
        com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg result = new com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg(this);
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg) {
          return mergeFrom((com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg other) {
        if (other == com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg.getDefaultInstance()) return this;
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameRequestMsg) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      // @@protoc_insertion_point(builder_scope:com.xt.yde.protobuf.classic.SummaryClearGameRequestMsg)
    }

    static {
      defaultInstance = new SummaryClearGameRequestMsg(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:com.xt.yde.protobuf.classic.SummaryClearGameRequestMsg)
  }

  public interface SummaryClearGameResponseMsgOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // required string money = 1;
    /**
     * <code>required string money = 1;</code>
     *
     * <pre>
     * 总钱数
     * </pre>
     */
    boolean hasMoney();
    /**
     * <code>required string money = 1;</code>
     *
     * <pre>
     * 总钱数
     * </pre>
     */
    java.lang.String getMoney();
    /**
     * <code>required string money = 1;</code>
     *
     * <pre>
     * 总钱数
     * </pre>
     */
    com.google.protobuf.ByteString
        getMoneyBytes();
  }
  /**
   * Protobuf type {@code com.xt.yde.protobuf.classic.SummaryClearGameResponseMsg}
   *
   * <pre>
   *经典赛比大小前结算
   * </pre>
   */
  public static final class SummaryClearGameResponseMsg extends
      com.google.protobuf.GeneratedMessage
      implements SummaryClearGameResponseMsgOrBuilder {
    // Use SummaryClearGameResponseMsg.newBuilder() to construct.
    private SummaryClearGameResponseMsg(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private SummaryClearGameResponseMsg(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final SummaryClearGameResponseMsg defaultInstance;
    public static SummaryClearGameResponseMsg getDefaultInstance() {
      return defaultInstance;
    }

    public SummaryClearGameResponseMsg getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private SummaryClearGameResponseMsg(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              money_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.xt.yde.protobuf.classic.GameClassic.internal_static_com_xt_yde_protobuf_classic_SummaryClearGameResponseMsg_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.xt.yde.protobuf.classic.GameClassic.internal_static_com_xt_yde_protobuf_classic_SummaryClearGameResponseMsg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg.class, com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg.Builder.class);
    }

    public static com.google.protobuf.Parser<SummaryClearGameResponseMsg> PARSER =
        new com.google.protobuf.AbstractParser<SummaryClearGameResponseMsg>() {
      public SummaryClearGameResponseMsg parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new SummaryClearGameResponseMsg(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<SummaryClearGameResponseMsg> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required string money = 1;
    public static final int MONEY_FIELD_NUMBER = 1;
    private java.lang.Object money_;
    /**
     * <code>required string money = 1;</code>
     *
     * <pre>
     * 总钱数
     * </pre>
     */
    public boolean hasMoney() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required string money = 1;</code>
     *
     * <pre>
     * 总钱数
     * </pre>
     */
    public java.lang.String getMoney() {
      java.lang.Object ref = money_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          money_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string money = 1;</code>
     *
     * <pre>
     * 总钱数
     * </pre>
     */
    public com.google.protobuf.ByteString
        getMoneyBytes() {
      java.lang.Object ref = money_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        money_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private void initFields() {
      money_ = "";
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasMoney()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getMoneyBytes());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, getMoneyBytes());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.xt.yde.protobuf.classic.SummaryClearGameResponseMsg}
     *
     * <pre>
     *经典赛比大小前结算
     * </pre>
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsgOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.xt.yde.protobuf.classic.GameClassic.internal_static_com_xt_yde_protobuf_classic_SummaryClearGameResponseMsg_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.xt.yde.protobuf.classic.GameClassic.internal_static_com_xt_yde_protobuf_classic_SummaryClearGameResponseMsg_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg.class, com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg.Builder.class);
      }

      // Construct using com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        money_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.xt.yde.protobuf.classic.GameClassic.internal_static_com_xt_yde_protobuf_classic_SummaryClearGameResponseMsg_descriptor;
      }

      public com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg getDefaultInstanceForType() {
        return com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg.getDefaultInstance();
      }

      public com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg build() {
        com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg buildPartial() {
        com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg result = new com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.money_ = money_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg) {
          return mergeFrom((com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg other) {
        if (other == com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg.getDefaultInstance()) return this;
        if (other.hasMoney()) {
          bitField0_ |= 0x00000001;
          money_ = other.money_;
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasMoney()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.xt.yde.protobuf.classic.GameClassic.SummaryClearGameResponseMsg) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // required string money = 1;
      private java.lang.Object money_ = "";
      /**
       * <code>required string money = 1;</code>
       *
       * <pre>
       * 总钱数
       * </pre>
       */
      public boolean hasMoney() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required string money = 1;</code>
       *
       * <pre>
       * 总钱数
       * </pre>
       */
      public java.lang.String getMoney() {
        java.lang.Object ref = money_;
        if (!(ref instanceof java.lang.String)) {
          java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
          money_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string money = 1;</code>
       *
       * <pre>
       * 总钱数
       * </pre>
       */
      public com.google.protobuf.ByteString
          getMoneyBytes() {
        java.lang.Object ref = money_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          money_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string money = 1;</code>
       *
       * <pre>
       * 总钱数
       * </pre>
       */
      public Builder setMoney(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        money_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string money = 1;</code>
       *
       * <pre>
       * 总钱数
       * </pre>
       */
      public Builder clearMoney() {
        bitField0_ = (bitField0_ & ~0x00000001);
        money_ = getDefaultInstance().getMoney();
        onChanged();
        return this;
      }
      /**
       * <code>required string money = 1;</code>
       *
       * <pre>
       * 总钱数
       * </pre>
       */
      public Builder setMoneyBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        money_ = value;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:com.xt.yde.protobuf.classic.SummaryClearGameResponseMsg)
    }

    static {
      defaultInstance = new SummaryClearGameResponseMsg(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:com.xt.yde.protobuf.classic.SummaryClearGameResponseMsg)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_xt_yde_protobuf_classic_SummaryClearGameRequestMsg_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_xt_yde_protobuf_classic_SummaryClearGameRequestMsg_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_xt_yde_protobuf_classic_SummaryClearGameResponseMsg_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_xt_yde_protobuf_classic_SummaryClearGameResponseMsg_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\024gameclassic.protobuf\022\033com.xt.yde.proto" +
      "buf.classic\"\034\n\032SummaryClearGameRequestMs" +
      "g\",\n\033SummaryClearGameResponseMsg\022\r\n\005mone" +
      "y\030\001 \002(\tB\rB\013GameClassic"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_com_xt_yde_protobuf_classic_SummaryClearGameRequestMsg_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_com_xt_yde_protobuf_classic_SummaryClearGameRequestMsg_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_xt_yde_protobuf_classic_SummaryClearGameRequestMsg_descriptor,
              new java.lang.String[] { });
          internal_static_com_xt_yde_protobuf_classic_SummaryClearGameResponseMsg_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_com_xt_yde_protobuf_classic_SummaryClearGameResponseMsg_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_xt_yde_protobuf_classic_SummaryClearGameResponseMsg_descriptor,
              new java.lang.String[] { "Money", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
