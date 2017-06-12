package com.xt.landlords.card;

import com.xt.yde.thrift.card.eliminate.EliminateCards;
import com.xt.yde.thrift.card.eliminate.EliminateCardsService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.json.Json;
import ru.trylogic.spring.boot.thrift.annotation.ThriftController;

import java.io.*;
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
@ThriftController("/eliminate")
public class EliminateCardServiceHandler implements EliminateCardsService.Iface {
    @Autowired
    ApplicationContext applicationContext;

    //wz + 2
    //wz + normal
    //2
    //normal

    Random randomWz = new Random();


    public List getWZ() {
        List list = map.get(9);
        int i1 = randomWz.nextInt(list.size());
        List<List<Integer>> o = (List) list.get(i1);//size为4
        return o;
    }

    @Override
    public EliminateCards getCards(int awardLevel, int bombNum) throws TException {
        List<List<Integer>> result = new ArrayList<>();
        result = getWZ();
        EliminateCards cards = new EliminateCards();
        cards.setCardId("cardId").setCards(result);
        return cards;
    }


    public List readJson(String name) throws IOException {
        Resource resource = applicationContext.getResource(String.format("classpath:%s.json", name));

//        File file = resource.getFile();
//        byte[] buffer = new byte[(int) file.length()];
//        FileInputStream is = new FileInputStream(file);
//
//        is.read(buffer, 0, buffer.length);
//
//        is.close();
//        String str = new String(buffer);
        InputStream inputStream = resource.getInputStream();
        String str = convertStreamToStream(inputStream);
        List List = Json.decodeValue(str, List.class);
        System.out.println(str);
        return List;

    }

    public String convertStreamToStream(InputStream is) throws IOException {
        InputStreamReader isr = null;
        BufferedReader br = null;

        StringBuilder sb = new StringBuilder();
        String content;
        try {
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            while ((content = br.readLine()) != null) {
                sb.append(content);
            }
        } catch (IOException ioe) {
            System.out.println("IO Exception occurred");
            ioe.printStackTrace();
        } finally {
            isr.close();
            br.close();
        }
        String mystring = sb.toString();
        System.out.println(mystring);
        return mystring;
    }

    static HashMap<Integer, List> map = new HashMap<>();

    public void initialize() throws IOException {
        map.put(9, readJson("eliminate"));
    }


}