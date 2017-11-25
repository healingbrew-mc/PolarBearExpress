package dyn.healingbrew.polarbearexpress.common.net;

import dyn.healingbrew.polarbearexpress.PolarBearExpress;
import dyn.healingbrew.polarbearexpress.common.net.handler.SpawnParticlesHandler;
import dyn.healingbrew.polarbearexpress.common.net.message.SpawnParticlesMessage;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
    public static SimpleNetworkWrapper ChannelClient;

    private static int ChannelClientID = 158385;

    public static void Register() {
        ChannelClient = NetworkRegistry.INSTANCE.newSimpleChannel(PolarBearExpress.MODID);
        ChannelClient.registerMessage(SpawnParticlesHandler.class, SpawnParticlesMessage.class, ChannelClientID++, Side.CLIENT);
    }
}
