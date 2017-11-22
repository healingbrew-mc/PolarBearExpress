package dyn.healingbrew.polarbearexpress.net;

import dyn.healingbrew.polarbearexpress.PolarBearExpress;
import dyn.healingbrew.polarbearexpress.net.handler.SpawnParticlesHandler;
import dyn.healingbrew.polarbearexpress.net.message.SpawnParticlesMessage;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
    public static SimpleNetworkWrapper ChannelClient;

    private static int ChannelClientID;

    public static void Register() {
        ChannelClient = NetworkRegistry.INSTANCE.newSimpleChannel(PolarBearExpress.MODID);
        ChannelClient.registerMessage(SpawnParticlesHandler.class, SpawnParticlesMessage.class, ChannelClientID++, Side.CLIENT);
    }
}
