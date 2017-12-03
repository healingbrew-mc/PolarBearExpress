package dyn.healingbrew.polarbearexpress.common.behavior;

import dyn.healingbrew.polarbearexpress.Config;
import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.common.capability.provider.TamableEntityProvider;
import dyn.healingbrew.polarbearexpress.common.net.handler.SpawnParticlesHandler;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
public class PlayerPolarBearTameAction {

    public static boolean doWork(EntityPlayer player, EntityPolarBear polarBear, boolean actualWork) {
        if(player.world.isRemote) {
            return false;
        }

        if(polarBear.isChild() && !Config.AllowChildTaming) {
            return false;
        }

        if(player.getHeldItemMainhand().getItem().getRegistryName().equals(Config.TamingItem)) {
            ITamableEntity tamableEntity = polarBear.getCapability(TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY, null);

            if(tamableEntity == null) {
                return false;
            }

            if(tamableEntity.getUUID() != null) {
                return false;
            }

            if(!actualWork) {
                return true;
            }

            float currentChance = tamableEntity.getChance();
            float currentAttempt = tamableEntity.getAttempts();
            tamableEntity.incrementAttempt();
            if(!player.isCreative()) {
                player.getHeldItemMainhand().setCount(player.getHeldItemMainhand().getCount() - 1);
            }

            if(currentAttempt >= Config.MinimumAttempts) {
                float chance = player.getEntityWorld().rand.nextFloat();
                if(currentChance >= chance) {
                    polarBear.setHealth(polarBear.getMaxHealth());
                    tamableEntity.setOwner(player);
                    SpawnParticlesHandler.DoEffect(EnumParticleTypes.HEART.getParticleID(), polarBear);
                } else {
                    tamableEntity.incrementChance();
                    SpawnParticlesHandler.DoEffect(EnumParticleTypes.SMOKE_NORMAL.getParticleID(), polarBear);
                }
            }

            return true;
        }
        return false;
    }
}
