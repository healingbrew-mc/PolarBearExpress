package dyn.healingbrew.polarbearexpress.common.behavior;

import dyn.healingbrew.polarbearexpress.Config;
import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.common.capability.provider.TamableEntityProvider;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.player.EntityPlayer;

@SuppressWarnings({"WeakerAccess", "ConstantConditions", "SimplifiableIfStatement"})
public class PlayerPolarBearMountAction {
    public static boolean doWork(EntityPlayer player, EntityPolarBear polarBear, boolean actualWork) {
        if(player.world.isRemote) {
            return false;
        }

        if(player.getHeldItemMainhand().isEmpty()) {
            ITamableEntity tamableEntity = polarBear.getCapability(TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY, null);
            if(tamableEntity == null) {
                return false;
            }

            if(!Config.AllowSharing && player.getPersistentID() != tamableEntity.getUUID()) {
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
