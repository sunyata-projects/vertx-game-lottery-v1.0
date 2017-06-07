package com.xt.landlords.game.regular.breakplay;

import com.xt.landlords.AbstractAuthCommandHandler;
import com.xt.landlords.service.MoneyBetService;
import com.xt.landlords.Commands;
import com.xt.landlords.StoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;
import org.sunyata.octopus.Session;
import org.sunyata.octopus.model.GameModel;

/**
 * Created by leo on 17/4/18.
 */
@Component(Commands.RegularBreakPlay)
public class BreakPlayCommandHandler extends AbstractAuthCommandHandler {
    class CommandErrorCode {
        public final static int RemoteError = 1011;
        public final static int NotExistBreakPlayGameError = 1012;
    }

    @Autowired
    MoneyBetService moneyBetService;

    @Autowired
    StoreManager storeManager;

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            Session session = request.getSession();
            GameModel gameModelFromCache = storeManager.getGameModelFromCache(session.getCurrentUser().getName());
            if (gameModelFromCache == null) {
                response.setErrorCode(CommandErrorCode.NotExistBreakPlayGameError);
                return;
            }
        } catch (Exception ex) {
            response.setErrorCode(CommandErrorCode.RemoteError);
        } finally {
            response.writeAndFlush();
        }
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}
