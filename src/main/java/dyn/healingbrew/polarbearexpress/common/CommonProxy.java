package dyn.healingbrew.polarbearexpress.common;

import dyn.healingbrew.polarbearexpress.ISidedProxy;
import dyn.healingbrew.polarbearexpress.common.behavior.BehaviorListener;
import dyn.healingbrew.polarbearexpress.common.capability.CapabilityRegistry;
import dyn.healingbrew.polarbearexpress.common.net.NetworkHandler;

public class CommonProxy implements ISidedProxy {
    @Override
    public void Register() {
        NetworkHandler.Register();
        CapabilityRegistry.Register();
        BehaviorListener.Register();
    }
}
