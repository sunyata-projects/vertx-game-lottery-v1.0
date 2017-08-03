package com.xt.landlords.card;

import com.xt.landlords.card.util.CardIdUtil;
import com.xt.landlords.card.util.MyId;
import com.xt.landlords.card.util.WinLevelUtil;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service
public class AppDBCards {

    @Autowired
    SqlSessionTemplate sessionTemplate;
    final static Logger log = LoggerFactory.getLogger(AppDBCards.class);
    public static boolean chooseCardsFlag = true;// false顺序取，true随机取
//	public static Properties conf = AppUtil.readFile(AppDBCards.class, "conf.properties");

    public static Map<Integer, Map<Integer, CardIdUtil>> c_bombLevelCardIdMap = new HashMap<Integer, Map<Integer,
            CardIdUtil>>();
    public static Map<Integer, CardIdUtil> f_cardIdMap = new HashMap<Integer, CardIdUtil>();

    // @Override
    // public void initProvider() throws InstantiationException,
    // IllegalAccessException {
    // ProviderPool.init(DBCardsService.Iface.class, DBCardsServiceImpl.class,
    // "DBCardsService");
    // }
//	public void initProvider(DubboProviderConfig providerConfig) throws InstantiationException,
// IllegalAccessException {
//		ProviderPool.init(DBCardsService.Iface.class, DBCardsServiceImpl.class, "DBCardsService", providerConfig);
//	}


    public void initAfter() {
        WinLevelUtil.initWinLevelMap();
    }

    public void getChooseCardsFlag() {
        // 查库
        AppDBCards.chooseCardsFlag = true;
    }

    public void initialize() {
		initCardId();
        initAfter();
    }

    public void initCardId() {
        SqlSession sqlSession = null;
        try {
            for (int i = 0; i <= 7; i++) {
                Map<Integer, CardIdUtil> c_levelCardIdMap = new HashMap<Integer, CardIdUtil>();
                for (int j = 0; j <= 15; j++) {// 难度等级
                    try {
                        List<MyId> mids = new ArrayList<MyId>();
                        if (i < 7) {
                            Object[] params = {i, j};
                            sqlSession = sessionTemplate.getSqlSessionFactory().openSession(true);
                            //sqlSession = MyBatisUtil.getSqlSession(true);
                            String statement = "com.lianzhong.dbcardsserver.T_C_W_N.initCardId";
                            mids = sqlSession.selectList(statement, params);
                        } else {
                            //sqlSession = MyBatisUtil.getSqlSession(true);
                            sqlSession = sessionTemplate.getSqlSessionFactory().openSession(true);
                            String statement = "com.lianzhong.dbcardsserver.T_C_L_0.initCardId";// TODO
                            mids = sqlSession.selectList(statement, j);
                        }
                        if (mids != null && mids.size() > 0) {
                            System.out.println(mids.get(0).getId());
                            CardIdUtil cu = new CardIdUtil();
                            cu.setIdList(mids);
                            cu.setLen(mids.size());
                            c_levelCardIdMap.put(j, cu);
                            log.info("bomb = {}, c_levelCardIdMap.get({}) = {}", i, j,
                                    c_levelCardIdMap.get(j).toString());
                        }
                    } catch (Exception e) {
                        log.error("something wrong:", e);
                    } finally {
                        sqlSession.close();
                    }
                }
                AppDBCards.c_bombLevelCardIdMap.put(i, c_levelCardIdMap);// 输的id为７
            }
            for (int i = 0; i <= 8; i++) {
                List<MyId> mids = new ArrayList<MyId>();
                //sqlSession = MyBatisUtil.getSqlSession(true);
                sqlSession = sessionTemplate.getSqlSessionFactory().openSession(true);
                String statement = "com.lianzhong.dbcardsserver.T_F_N.getT_F_ID";
                mids = sqlSession.selectList(statement, i);

                CardIdUtil cu = new CardIdUtil();

                cu.setIdList(mids);
                cu.setLen(mids.size());
                AppDBCards.f_cardIdMap.put(i, cu);
                log.info("f_cardIdMap.get({}) = {}", i, f_cardIdMap.get(i).toString());
            }
        } catch (Exception e) {
            log.error("something wrong:", e);
        } finally {
            sqlSession.close();
        }
    }

//    public static void main(String[] args) throws TException {
//        initServer();
//        log.info("服务器版本为：{}, 对应AI版本为：{}；", 1683, 1489);
//
//    }

//    public static void initServer() {
//        try {
//            AppDBCards adbc = new AppDBCards();
//            adbc.initCardId();
//            adbc.initAfter();
//        } catch (Exception e) {
//            log.error("something wrong:", e);
//        }
//    }
}
