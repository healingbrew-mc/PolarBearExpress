package dyn.healingbrew.polarbearexpress;

import dyn.healingbrew.polarbearexpress.client.ClientProxy;
import dyn.healingbrew.polarbearexpress.common.behavior.BehaviorListener;
import dyn.healingbrew.polarbearexpress.common.capability.CapabilityRegistry;
import dyn.healingbrew.polarbearexpress.common.net.NetworkHandler;
import dyn.healingbrew.polarbearexpress.integration.theoneprobe.TOPIntegration;
import dyn.healingbrew.polarbearexpress.server.ServerProxy;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings({"unused", "WeakerAccess"})
@Mod(modid = PolarBearExpress.MODID, version = PolarBearExpress.VERSION, certificateFingerprint = "ec24659e5fe698bd099dbd6a27ced171932422c5")
public class PolarBearExpress {
    public static final String MODID = "polarbearexpress";
    public static final String VERSION = "@VERSION@";

    @SidedProxy(clientSide = "dyn.healingbrew.polarbearexpress.client.ClientProxy", serverSide = "dyn.healingbrew.polarbearexpress.server.ServerProxy")
    public static ISidedProxy Proxy;

    public static final Logger LOGGER = LogManager.getLogger("healingbrew");

    @EventHandler
    public void init(FMLInitializationEvent event) {
        if (Loader.isModLoaded("theoneprobe")) {
            TOPIntegration.register();
        }
    }

    @EventHandler
    public void pre(FMLPreInitializationEvent event) throws Exception {
        Config.Load(new Configuration(event.getSuggestedConfigurationFile()));
        Proxy.Register();
    }

    @EventHandler
    public void post(FMLPostInitializationEvent event) {
    }
}
