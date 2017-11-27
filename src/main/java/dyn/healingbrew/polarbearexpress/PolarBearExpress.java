package dyn.healingbrew.polarbearexpress;

import dyn.healingbrew.polarbearexpress.client.ClientProxy;
import dyn.healingbrew.polarbearexpress.common.behavior.BehaviorListener;
import dyn.healingbrew.polarbearexpress.common.capability.CapabilityRegistry;
import dyn.healingbrew.polarbearexpress.common.net.NetworkHandler;
import dyn.healingbrew.polarbearexpress.server.ServerProxy;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@SuppressWarnings({"unused", "WeakerAccess"})
@Mod(modid = PolarBearExpress.MODID, version = PolarBearExpress.VERSION, certificateFingerprint = "d0ff4e3ee6e9a9ab7d68a1993bee4674341b3201")
public class PolarBearExpress
{
    public static final String MODID = "polarbearexpress";
    public static final String VERSION = "@VERSION@";

    @SidedProxy(clientSide = "dyn.healingbrew.polarbearexpress.client.ClientProxy", serverSide = "dyn.healingbrew.polarbearexpress.server.ServerProxy")
    public static ISidedProxy Proxy;

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }

    @EventHandler
    public void pre(FMLPreInitializationEvent event) throws Exception
    {
        Config.Load(new Configuration(event.getSuggestedConfigurationFile()));
        Proxy.Register();
    }

    @EventHandler
    public void post(FMLPostInitializationEvent event)
    {
    }
}
