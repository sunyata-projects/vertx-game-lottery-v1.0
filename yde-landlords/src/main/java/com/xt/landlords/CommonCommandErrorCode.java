package com.xt.landlords;

/**
 * 常规错误
 */
public class CommonCommandErrorCode {
    //远程访问错误
    public final static int RemoteAccessError = 1001;
    //当前游戏不能接受此命令(如没有下注就调用出牌命令)
    public final static int CanNotAcceptEventException = 1002;
    //没有登录
    public final static int NotLoginException = 1003;
    //不能重复下注
    public final static int CanNotRepeatBet = 1004;
    //内部错误
    public final static int InternalError = 1099;
}
