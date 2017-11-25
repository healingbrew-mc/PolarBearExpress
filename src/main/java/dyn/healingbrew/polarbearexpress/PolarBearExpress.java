package dyn.healingbrew.polarbearexpress;

import dyn.healingbrew.polarbearexpress.common.behavior.BehaviorListener;
import dyn.healingbrew.polarbearexpress.common.capability.CapabilityRegistry;
import dyn.healingbrew.polarbearexpress.common.net.NetworkHandler;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@SuppressWarnings({"unused", "WeakerAccess"})
@Mod(modid = PolarBearExpress.MODID, version = PolarBearExpress.VERSION)
public class PolarBearExpress
{
    public static final String MODID = "polarbearexpress";
    public static final String VERSION = "@VERSION@";

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }

    @EventHandler
    public void pre(FMLPreInitializationEvent event) throws Exception
    {
        Config.Load(new Configuration(event.getSuggestedConfigurationFile()));
        CapabilityRegistry.Register();
        NetworkHandler.Register();
        BehaviorListener.Register();
    }

    @EventHandler
    public void post(FMLPostInitializationEvent event)
    {
    }
}
