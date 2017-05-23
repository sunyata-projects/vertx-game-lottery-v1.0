namespace java com.lianzhong.loginserver.thrift

/**
  * 登录信息
  */
struct LoginMsg {
  1: string userId,
  2: string cert
}

enum LoginResult {
  ET_UNKNOWN = 0, //未知错误
  ET_TYPE1 = 1, // 登陆成功
  ET_TYPE2 = 2, // 账号名称不合法
  ET_TYPE3 = 3, // 解析证书错误
  ET_TYPE4 = 4  // 账号证书不合法
}

/**
  * 登录应答信息
  */
struct AckLoginMsg {
  1: LoginResult result,
  2: string code,
  3: string userId,
  4: string displayName,
  5: string avatar,
  6: i64 coin
}

service LoginService {
  AckLoginMsg login(1:LoginMsg loginMsg);
}