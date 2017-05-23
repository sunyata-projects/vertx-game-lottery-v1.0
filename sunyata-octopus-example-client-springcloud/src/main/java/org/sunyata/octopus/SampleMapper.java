//package org.sunyata.octopus;
//
//import info.developerblog.spring.thrift.client.AbstractThriftClientKeyMapper;
//import info.developerblog.spring.thrift.client.pool.ThriftClientKey;
//import org.springframework.stereotype.Component;
//import org.sunyata.octopus.thrift.TGreetingService;
//
//import javax.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author jihor (jihor@ya.ru)
// * Created on 2016-06-14
// */
//@Component
//public class SampleMapper extends AbstractThriftClientKeyMapper {
//
//    protected HashMap<String, ThriftClientKey> mappings = new HashMap<>();
//
//    @PostConstruct
//    public void init() {
//        Map<String, String> mappingsAsStrings = new HashMap<>();
//        mappingsAsStrings.put("key1", "greeting-service");
//        mappingsAsStrings.put("key2", "greeting-service-with-timeouts");
//        mappingsAsStrings.forEach((key, value) -> mappings.put(key, ThriftClientKey.builder()
//                                                                                            .clazz(TGreetingService.Client.class)
//                                                                                            .serviceName(value)
//                                                                                            .path("/api")
//                                                                                            .build()));
//    }
//
//    public HashMap<String, ThriftClientKey> getMappings() {
//        return mappings;
//    }
//}
