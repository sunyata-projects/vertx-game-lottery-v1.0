cd /Users/apple/work/java/sunyata-octopus/sunyata-random-game-server-demo/src/main/resources/protobuf
/usr/local/protobuf/bin/protoc --java_out=/Users/apple/work/java/sunyata-octopus/sunyata-random-game-server-demo//src/main/java/ common.protobuf


接口说明:

app与gameserver之间通讯采用长连接的WebSocket,应用层通讯协议采用protobuf,版本为2.5.0
每一个请求单独声明一个请求协议
针对只返回成功或失败的应答，不单独设计protobuf协议
消息码共5位XXXXX
通用请求消息码:10XXX,其中后三位由消息本身自定义
游戏内相关的消息码50-80
    常规赛:50
    积分比赛:51
    消除:52
    拼图:53
登录
LoginReqMsg.cmd = 10001
下注
BetReqMsg.cmd = 10
发牌
DealReqMsg.cmd =
加注
RaiseReqMsg.cmd =
暗牌
DarkReqMsg.cmd
出牌
PlayReqMsg.cmd
