package dyn.healingbrew.polarbearexpress.common.behavior;

import dyn.healingbrew.polarbearexpress.Config;
import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.player.EntityPlayer;

import static dyn.healingbrew.polarbearexpress.common.capability.provider.TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY;

@SuppressWarnings({"WeakerAccess", "ConstantConditions", "SimplifiableIfStatement"})
public class PlayerPolarBearMountAction {
    public static boolean doWork(EntityPlayer player, EntityPolarBear polarBear, boolean actualWork) {
        if(player.world.isRemote) {
            return false;
        }

        if(player.getHeldItemMainhand().isEmpty() && !player.isSneaking()) {
            ITamableEntity tamableEntity = polarBear.getCapability(TAMABLE_ENTITY_CAPABILITY, null);
            if(tamableEntity == null) {
                return false;
            }

            if(!Config.AllowSharing && player.getPersistentID().compareTo(tamableEntity.getUUID()) != 0) {
                return false;
            }

            if(!actualWork) {
                return true;
            }

            player.rotationYaw = polarBear.rotationYaw;
            player.rotationPitch = polarBear.rotationPitch;

            return player.startRiding(polarBear);
        }
        return false;
    }
}
