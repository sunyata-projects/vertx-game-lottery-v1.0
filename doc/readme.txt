类别  状态码 状态说明
成功  0   成功
系统  90
网络  80
业务  600001

yde-landlord-port:
yde-discover-service:
yde-config-service:
yde-login-service-port:28001
yde-card-service-port:28002
yde-ai-service-port:28003
yde-job-service-port:None

yde-quark-service-port:28009


java -jar yde-discover-service-1.0.0.jar ----spring.profiles.active=demo --eureka.client.serviceUrl
.defaultZone=http://172.21.120.227:16001/eureka/

模块列表：

AIServer:处理AI打牌逻辑，即轮到AI出牌，AI决定是否出牌及出什么样的牌
牌库Server：通过奖等（档）确定发给用户的牌
交易系统：负责与彩票引擎的交易（如投注、兑换等）
GameServer:游戏逻辑服务器。处理用户消息，并负责与AI、牌库、交易系统通讯更新当前游戏状态，返回用户正确的请求
JobServer:处理异步落盘任务，用户登录记录、用户游戏记录等