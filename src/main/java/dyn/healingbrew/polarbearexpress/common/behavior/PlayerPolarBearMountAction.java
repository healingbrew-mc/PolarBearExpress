package dyn.healingbrew.polarbearexpress.common.behavior;

import dyn.healingbrew.polarbearexpress.Config;
import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.common.net.NetworkHandler;
import dyn.healingbrew.polarbearexpress.common.net.message.TamableCapabilitySyncMessage;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import static dyn.healingbrew.polarbearexpress.common.capability.provider.TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY;

@SuppressWarnings({"WeakerAccess", "ConstantConditions", "SimplifiableIfStatement"})
public class PlayerPolarBearMountAction {
    public static boolean doWork(EntityPlayer player, EntityPolarBear polarBear, boolean actualWork) {
        if(player.world.isRemote) {
            return false;
        }

        if(player.getHeldItemMainhand().isEmpty()) {
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
