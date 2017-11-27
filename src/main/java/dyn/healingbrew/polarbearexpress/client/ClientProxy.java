package dyn.healingbrew.polarbearexpress.client;

import dyn.healingbrew.polarbearexpress.client.behavior.ClientBehaviorListener;
import dyn.healingbrew.polarbearexpress.common.CommonProxy;

public class ClientProxy extends CommonProxy {
    @Override
    public void Register() {
        super.Register();
        ClientBehaviorListener.Register();
    }
}
