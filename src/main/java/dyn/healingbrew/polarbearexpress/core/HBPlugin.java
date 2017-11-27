package dyn.healingbrew.polarbearexpress.core;

import dyn.healingbrew.polarbearexpress.PolarBearExpress;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import static net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;

import javax.annotation.Nullable;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
@MCVersion(MinecraftForge.MC_VERSION)
public class HBPlugin implements IFMLLoadingPlugin {
    @Override
    public String[] getASMTransformerClass() {
        return new String[] { HBTransformer.class.getName() };
    }

    @Override
    public String getModContainerClass() {
        return HBCore.class.getName();
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    public static boolean Deobf;

    @Override
    public void injectData(Map<String, Object> data) {
        Deobf = (boolean)data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    public static class HBCore extends DummyModContainer {
        public HBCore() {
            super(new ModMetadata());

            ModMetadata meta = this.getMetadata();
            meta.modId = getModId();
            meta.name = getName();
            meta.version = getVersion();
        }

        @Override
        public String getModId() {
            return PolarBearExpress.MODID + "-core";
        }

        @Override
        public String getVersion() {
            return PolarBearExpress.VERSION;
        }

        @Override
        public String getDisplayVersion() {
            return PolarBearExpress.VERSION;
        }

        @Override
        public String getName() {
            return "PolarBearExpress Core";
        }
    }
}
