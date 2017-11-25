package dyn.healingbrew.polarbearexpress.common.capability;

import dyn.healingbrew.polarbearexpress.PolarBearExpress;
import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.common.capability.impl.TamableEntity;
import dyn.healingbrew.polarbearexpress.common.capability.provider.TamableEntityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings({"WeakerAccess", "unused"})
public class CapabilityRegistry {
    public static void Register() {
        CapabilityManager.INSTANCE.register(ITamableEntity.class, new TamableEntityCapability(), TamableEntity.class);
        MinecraftForge.EVENT_BUS.register(new CapabilityRegistry());
    }

    public static final ResourceLocation TAMABLE_ENTITY_CAPABILITY = new ResourceLocation(PolarBearExpress.MODID, "tamable_entity_capability");

    @SubscribeEvent
    public void AttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof EntityPolarBear) {
            event.addCapability(TAMABLE_ENTITY_CAPABILITY, new TamableEntityProvider());
        }
    }
}
