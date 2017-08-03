package com.xt.landlords.card;

import com.xt.landlords.card.model.PuzzleCard;
import com.xt.landlords.card.store.CardPuzzleStore;
import com.xt.yde.thrift.card.puzzle.PuzzleCards;
import com.xt.yde.thrift.card.puzzle.PuzzleCardsService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.sunyata.octopus.json.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//import ru.trylogic.spring.boot.thrift.annotation.*;
//import ru.*

/**
 * Created by aleksandr on 01.09.15.
 */
@Service
//@ThriftController("/puzzle")
public class PuzzleCardServiceHandler implements PuzzleCardsService.Iface {
    Logger logger = LoggerFactory.getLogger(EliminateCardServiceHandler.class);
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    CardPuzzleStore cardPuzzleStore;
    //wz + 2
    //wz + normal
    //2
    //normal
//    Random random4 = new Random(4);
//    Random randomNormal = new Random();
//    Random randomWz = new Random();
//    Random random2 = new Random();

//    public List getWZ() {
//        List list = map.get(99);
//        int i1 = randomWz.nextInt(list.size());
//        List<List<Integer>> o = (List) list.get(i1);//size为4
//        return o;
//    }

    public List getWZ() {
//        List list = map.get(99);
//        int i1 = randomWz.nextInt(list.size());
//        List<List<Integer>> o = (List) list.get(i1);//size为4
//        return o;
        PuzzleCard wz = cardPuzzleStore.getWZ();
        return Json.decodeValue(wz.getCards(), List.class);
//        List<String> stringList = Arrays.asList(wz.getCards().split(","));
//        return stringList.stream().map(Integer::valueOf).collect(Collectors.toList());
    }

    public List get22() {
        PuzzleCard wz = cardPuzzleStore.get22();
        return Json.decodeValue(wz.getCards(), List.class);
    }


//    public List get22() {
//        List list22 = map.get(22);
//        int index22 = randomWz.nextInt(list22.size());
//        List<List<Integer>> listResult22 = (List) list22.get(index22);
//        return listResult22;
//    }
//
//    public List getNormal(int grade) {
//        List list = map.get(grade);
//        int index22 = randomNormal.nextInt(list.size());
//        List<List<Integer>> listResult22 = (List) list.get(index22);
//        return listResult22;
//    }


    public List getNormal(int grade) {
        PuzzleCard wz = cardPuzzleStore.getNormal(grade);
        return Json.decodeValue(wz.getCards(), List.class);
    }


    public int nextInt(int from, int to) {
        int max = to;
        int min = from;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    public boolean is25Percent() {
        int i = nextInt(1, 4);
        return i == 1;
    }

    @Override
    public PuzzleCards getCards(int grade) throws TException {
        logger.info("grade:{}", grade);
        List<List<List<Integer>>> result = new ArrayList<>();
        if (is25Percent()) {
            result.add(getWZ());
        }
        result.add(get22());
        //result.add(getNormal(grade));

//        if (is25Percent()) {//25机率是用22来通过抽红包的方式来发奖
//            result.add(get22());
//        } else {
//            result.add(getNormal(grade));
//        }


//        int i = random4.nextInt(4);
//        if (i == 0) {
//            //王炸弹
//            result.add(getWZ());
//            result.add(get22());
//        } else if (i == 1) {
//            result.add(getWZ());
//            result.add(getNormal(grade));
//        } else if (i == 2) {
//            result.add(get22());
//        } else {
//            result.add(getNormal(grade));
//        }
        PuzzleCards cards = new PuzzleCards();
//        List<List<Integer>> ret = new ArrayList<>();
//        for (List list : result) {
//            for (Object l : list) {
//                System.out.print(l);
//            }
//        }
        cards.setCardId("cardId").setCards(result);
        logger.info("获取牌库成功");
        return cards;
    }


//    public List test(String name) throws IOException {
//        Resource resource = applicationContext.getResource(String.format("classpath:%s.json", name));
//
//        InputStream is = resource.getInputStream();
//
////        File file = resource.getFile();
////        byte[] buffer = new byte[(int) file.length()];
////        FileInputStream is = new FileInputStream(file);
//
////        StringWriter writer = new StringWriter();
////        int copy = IOUtils.copy(is, writer, "UTF-8");
////        String theString = writer.toString();
//        String str = convertStreamToStream(is);
//        List List = Json.decodeValue(str, List.class);
//        return List;
//
//    }
//
//    public String convertStreamToStream(InputStream is) throws IOException {
//        InputStreamReader isr = null;
//        BufferedReader br = null;
//
//        StringBuilder sb = new StringBuilder();
//        String content;
//        try {
//            isr = new InputStreamReader(is);
//            br = new BufferedReader(isr);
//            while ((content = br.readLine()) != null) {
//                sb.append(content);
//            }
//        } catch (IOException ioe) {
//            System.out.println("IO Exception occurred");
//            ioe.printStackTrace();
//        } finally {
//            isr.close();
//            br.close();
//        }
//        String mystring = sb.toString();
//        //System.out.println(mystring);
//        return mystring;
//    }

//    static HashMap<Integer, List> map = new HashMap<>();

    public void initialize() throws IOException {
//        map.put(9, test("2s"));
//        map.put(8, test("3s"));
//        map.put(7, test("4s"));
//        map.put(6, test("232"));
//        map.put(5, test("332"));
//        map.put(4, test("432"));
//        map.put(3, test("4z"));
//        map.put(2, test("3z"));
//        map.put(1, test("4z"));
//
//        map.put(99, test("wz"));
//        map.put(22, test("22"));
    }
}