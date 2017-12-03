package dyn.healingbrew.polarbearexpress.integration.theoneprobe;

import com.google.common.base.Function;
import dyn.healingbrew.polarbearexpress.PolarBearExpress;
import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.common.capability.provider.TamableEntityProvider;
import javafx.scene.control.TextFormatter;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import javax.annotation.Nullable;

public class TOPIntegration  implements Function<ITheOneProbe, Void> {

    public static ITheOneProbe probe;

    private static boolean registered;

    public static void register() {
        if (registered) {
            return;
        }

        registered = true;
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "com.gendeathrow.hatchery.core.theoneprobe.TheOneProbeSupport$GetTheOneProbe");
    }

    @Nullable
    @Override
    public Void apply(@Nullable ITheOneProbe input) {
        probe = input;

        PolarBearExpress.LOGGER.info("Enabling The One Probe integration");

        probe.registerEntityProvider(new IProbeInfoEntityProvider() {
            @Override
            public String getID() {
                return PolarBearExpress.MODID + ":top:entity";
            }

            @Override
            public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, Entity entity, IProbeHitEntityData data) {

                if(entity.hasCapability(TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY, null)) {
                    ITamableEntity tamableEntity = entity.getCapability(TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY, null);
                    if(tamableEntity == null) {
                        return;
                    }

                    if(tamableEntity.getUUID() == null) {
                        probeInfo.text(TextFormatting.RED + "Wild");
                    } else {
                        probeInfo.text(String.format("Owner: %s", tamableEntity.getName()));
                    }

                    if(tamableEntity.getSitting()) {
                        probeInfo.text("Sitting");
                    }
                }
            }
        });

        return null;
    }
}
