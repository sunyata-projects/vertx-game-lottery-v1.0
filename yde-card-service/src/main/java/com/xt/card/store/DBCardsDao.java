package com.xt.card.store;

import com.xt.card.AppDBCards;
import com.xt.card.util.CardIdUtil;
import com.xt.yde.thrift.card.Cards;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DBCardsDao {

    @Autowired
    SqlSessionTemplate sessionTemplate;
    final static Logger log = LoggerFactory.getLogger(DBCardsDao.class);

    public Object[] getCards(String gameType, String gameResult, int bombNum, int grade) {
        // log.info("游戏类型{}，由于牌库因素，转换为C", sGameType);
        gameType = "C";
        Object[] arrays = null;
        SqlSession sqlSession = null;

        if (StrUtil.notBlank(gameType, gameResult)) {

            int cardMapId = -1;
            if (gameResult.equals("L")) {
                cardMapId = 7;
            } else if (gameResult.equals("W")) {
                cardMapId = bombNum;
            }
            if (cardMapId == -1) {
                return null;
            }

            //根据难度等级取真实等级，
            int actualGrade = -1;
            CardIdUtil ciu = null;//AppDBCards.c_cardIdMap.get(cardMapId);
            for (int j = grade; j >= 0; j--) {//在无当前等级时先向简单等级找
                ciu = AppDBCards.c_bombLevelCardIdMap.get(cardMapId).get(j);
                if (ciu != null) {
                    actualGrade = j;
                    log.info("grade={}, actualGrade={}", grade, actualGrade);
                    break;
                }
            }

            if (ciu == null || actualGrade < 0) {//无当前等级和简单等级时，向最近的较难等级找
                for (int j = grade + 1; j <= 15; j++) {
                    ciu = AppDBCards.c_bombLevelCardIdMap.get(cardMapId).get(j);
                    if (ciu != null) {
                        actualGrade = j;
                        log.info("grade={}, actualGrade={}", grade, actualGrade);
                        break;
                    }
                }
            }
            if (ciu == null || actualGrade < 0) {//最后一直没找到就返回错误
                return null;
            }
            int index = -1;
            if (AppDBCards.chooseCardsFlag) {//true随机取
                index = (new Random()).nextInt(ciu.getLen());
            } else {//false顺序取
                index = ciu.getAtomicInteger().getAndIncrement() % ciu.getLen();
            }

            String cardId = ciu.getIdList().get(index).toString();

            Object[] params = {gameType.toLowerCase(), gameResult.toLowerCase(), bombNum, cardId, actualGrade};
            log.info("getCards.....");
            try {
                long t1 = System.currentTimeMillis();
                //sqlSession = MyBatisUtil.getSqlSession(true);
                sqlSession = sessionTemplate.getSqlSessionFactory().openSession(true);
                String statement = "com.lianzhong.dbcardsserver.T_N_N_N.getCards";
                CardsBean cards = sqlSession.selectOne(statement, params);
                if (cards != null) {
                    arrays = new Object[]{cards.getId(), cards.getC_center(), cards.getC_right(), cards.getC_left(),
                            cards.getC_under()};
                }
//				arrays = DB.query2Array(sql.toString());
                long t2 = System.currentTimeMillis();
                log.info("getCards, spend = {}ms", (t2 - t1));
            } catch (Exception e) {
                log.error("getCards error!\n", e);
            } finally {
                sqlSession.close();
            }
        }

        return arrays;
    }

    public Object[] getFCards(int bombNum) {
        Object[] arrays = null;
        SqlSession sqlSession = null;

        int cardMapId = bombNum;
        CardIdUtil ciu = AppDBCards.f_cardIdMap.get(cardMapId);
        if (ciu == null) {
            return null;
        }
        int index = -1;
        if (AppDBCards.chooseCardsFlag) {//true随机取
            index = (new Random()).nextInt(ciu.getLen());
        } else {//false顺序取
            index = ciu.getAtomicInteger().getAndIncrement() % ciu.getLen();
        }
        String cardId = ciu.getIdList().get(index).toString();

        Object[] params = {bombNum, cardId};
        log.info("getCardsByBombNum......");
        try {
            long t1 = System.currentTimeMillis();
            //sqlSession = MyBatisUtil.getSqlSession(true);

            String statement = "com.lianzhong.dbcardsserver.T_N_N_N.getCardsByBombNum";
            CardsBean cards = sqlSession.selectOne(statement, params);
            if (cards != null) {
                arrays = new Object[]{cards.getId(), cards.getC_center(), cards.getC_right(), cards.getC_left(),
						cards.getC_under()};
            }
            long t2 = System.currentTimeMillis();
            log.info("getCardsByBombNum, spend = {}ms", (t2 - t1));
        } catch (Exception e) {
            log.error("getCardsByBombNum error!\n", e);
        } finally {
            sqlSession.close();
        }
        return arrays;
    }

    public Object[] getCardsById(String gameType, String winFlag,
                                 String bombFlag, String cardId) {
        log.info("getCardsById: gameType={}, winFlag={}, bombFlag={}, cardId={}", gameType, winFlag, bombFlag, cardId);
        SqlSession sqlSession = null;

        Object[] arrays = null;

        StringBuilder tableName = new StringBuilder("T_")
                .append(gameType)
                .append("_");
        if (gameType.equals("F") || gameType == "F") {
            //疯狂赛不加输赢标记W/L
        } else {
            tableName.append(winFlag).append("_");
        }

        if (bombFlag.equals("E") || bombFlag == "E") {//输的牌，统一从T_C_L_0表中取，其它的表不用了
            bombFlag = "0";
        }
        tableName.append(bombFlag);

        Object[] params = {tableName.toString().toLowerCase(), cardId};
        log.info("getCardsById......");

        try {
            long t1 = System.currentTimeMillis();
            //sqlSession = MyBatisUtil.getSqlSession(true);
            sqlSession = sessionTemplate.getSqlSessionFactory().openSession(true);
            String statement = "com.lianzhong.dbcardsserver.T_N_N_N.getCardsById";
            CardsBean cards = sqlSession.selectOne(statement, params);
            if (cards != null) {
                arrays = new Object[]{cards.getId(), cards.getC_center(), cards.getC_right(), cards.getC_left(), cards.getC_under()};
            }
            long t2 = System.currentTimeMillis();
            log.info("getCardsById, spend = {}ms", (t2 - t1));
        } catch (Exception e) {
            log.error("getCardsById error!\n", e);
        }

        return arrays;
    }

}
