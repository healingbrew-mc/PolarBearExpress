package dyn.healingbrew.polarbearexpress.behavior;

import dyn.healingbrew.polarbearexpress.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.capability.provider.TamableEntityProvider;
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

            if(player.getPersistentID() != tamableEntity.getUUID()) {
                return false;
            }

            if(!actualWork) {
                return true;
            }

            return player.startRiding(polarBear);
        }
        return false;
    }
}
