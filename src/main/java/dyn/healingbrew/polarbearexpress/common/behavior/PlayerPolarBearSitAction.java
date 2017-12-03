package dyn.healingbrew.polarbearexpress.common.behavior;

import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.common.net.handler.SpawnParticlesHandler;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;

import static dyn.healingbrew.polarbearexpress.common.capability.provider.TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY;

@SuppressWarnings({"WeakerAccess", "ConstantConditions", "SimplifiableIfStatement"})
public class PlayerPolarBearSitAction {
    public static boolean doWork(EntityPlayer player, EntityPolarBear polarBear, boolean actualWork) {
        if(player.world.isRemote) {
            return false;
        }

        if(player.getHeldItemMainhand().isEmpty() && player.isSneaking()) {
            ITamableEntity tamableEntity = polarBear.getCapability(TAMABLE_ENTITY_CAPABILITY, null);
            if(tamableEntity == null) {
                return false;
            }

            if(player.getPersistentID().compareTo(tamableEntity.getUUID()) != 0) {
                return false;
            }

            if(!actualWork) {
                return true;
            }

            boolean isSitting = tamableEntity.getSitting();
            tamableEntity.setSitting(!isSitting);
            polarBear.playLivingSound();
            SpawnParticlesHandler.DoEffect(isSitting ? EnumParticleTypes.NOTE.getParticleID() : EnumParticleTypes.SLIME.getParticleID(), polarBear);

            return true;
        }
        return false;
    }
}
