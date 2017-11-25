package dyn.healingbrew.polarbearexpress.core;

import net.minecraftforge.common.MinecraftForge;
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
        return null;
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
}
