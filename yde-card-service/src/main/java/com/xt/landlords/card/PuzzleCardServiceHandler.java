package com.xt.landlords.card;

import com.xt.yde.thrift.card.puzzle.PuzzleCards;
import com.xt.yde.thrift.card.puzzle.PuzzleCardsService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.json.Json;
import ru.trylogic.spring.boot.thrift.annotation.ThriftController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
//import ru.trylogic.spring.boot.thrift.annotation.*;
//import ru.*

/**
 * Created by aleksandr on 01.09.15.
 */
@Component
@ThriftController("/puzzle")
public class PuzzleCardServiceHandler implements PuzzleCardsService.Iface {
    @Autowired
    ApplicationContext applicationContext;

    //wz + 2
    //wz + normal
    //2
    //normal
    Random random4 = new Random(4);
    Random randomNormal = new Random();
    Random randomWz = new Random();
    Random random2 = new Random();

    public List getWZ() {
        List list = map.get(99);
        int i1 = randomWz.nextInt(list.size());
        List<List<Integer>>  o = (List) list.get(i1);//size为4
        return o;
    }

    public List get22() {
        List list22 = map.get(22);
        int index22 = randomWz.nextInt(list22.size());
        List<List<Integer>> listResult22 = (List) list22.get(index22);
        return listResult22;
    }

    public List getNormal(int grade) {
        List list = map.get(grade);
        int index22 = randomNormal.nextInt(list.size());
        List<List<Integer>>  listResult22 = (List) list.get(index22);
        return listResult22;
    }

    @Override
    public PuzzleCards getCards(int grade) throws TException {
        List<List<List<Integer>>> result = new ArrayList<>();
        int i = random4.nextInt(4);
        if (i == 0) {
            //王炸弹
            result.add(getWZ());
            result.add(get22());
        } else if (i == 1) {
            result.add(getWZ());
            result.add(getNormal(grade));
        } else if (i == 2) {
            result.add(get22());
        } else {
            result.add(getNormal(grade));
        }
        PuzzleCards cards = new PuzzleCards();
//        List<List<Integer>> ret = new ArrayList<>();
//        for (List list : result) {
//            for (Object l : list) {
//                System.out.print(l);
//            }
//        }
        cards.setCardId("cardId").setCards(result);
        return cards;
    }


    public List test(String name) throws IOException {
        Resource resource = applicationContext.getResource(String.format("classpath:%s.json", name));

        File file = resource.getFile();
        byte[] buffer = new byte[(int) file.length()];
        FileInputStream is = new FileInputStream(file);

        is.read(buffer, 0, buffer.length);

        is.close();
        String str = new String(buffer);
        List List = Json.decodeValue(str, List.class);
        System.out.println(str);
        return List;

    }

    static HashMap<Integer, List> map = new HashMap<>();

    public void initialize() throws IOException {
        map.put(9, test("2s"));
        map.put(8, test("3s"));
        map.put(7, test("4s"));
        map.put(6, test("232"));
        map.put(5, test("332"));
        map.put(4, test("432"));
        map.put(3, test("4z"));
        map.put(2, test("3z"));
        map.put(1, test("4z"));

        map.put(99, test("wz"));
        map.put(22, test("22"));
    }
}