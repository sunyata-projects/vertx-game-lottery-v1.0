package com.xt.landlords;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.store.Store;
import org.sunyata.octopus.store.StoreFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by leo on 17/5/16.
 */
@Component
public class StoreManager {
    @Autowired
    StoreFactory factory;

    static ConcurrentMap<String, Store> map = new ConcurrentHashMap<>();

    public Store getStore(String identity) {
        if (!map.containsKey(identity)) {
            synchronized (map) {
                Store store = factory.createStore(identity);
                map.put(identity, store);
                return store;
            }
        }
        return map.getOrDefault(identity, null);
    }

    public void set(String identity, String key, Object object) {
        Store store = getStore(identity);
        store.set(key, object);
    }

    public void removeFromLocal(String identity) {
        map.remove(identity);
    }

    public GameModel getGameModelFromCache(String userName) {
        GameModel result = null;
        Object o = getStore(userName).get(StoreKeys.GameModel);
        if (o != null) {
            result = (GameModel) o;
        }
        return result;
    }

    public UserState getUserStatusFromCache(String userName) {
        String result = null;
        Object o = getStore(userName).get(StoreKeys.UserStatus);
        if (o != null) {
            result = (String) o;
        }
        if (StringUtils.isEmpty(result)) {
            return UserState.OffLine;
        }
        UserState userState = UserState.valueOf(result);
        return userState;
    }

    public void storeUserStatus(String userName, UserState userStatus) {
        getStore(userName).set(StoreKeys.UserStatus, userStatus.getValue());
    }

    public void storeGameModel(String userName, GameModel gameModel) {
        getStore(userName).set(StoreKeys.GameModel, gameModel);
    }
}
