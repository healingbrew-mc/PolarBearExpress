package dyn.healingbrew.polarbearexpress.behavior;

import dyn.healingbrew.polarbearexpress.Config;
import dyn.healingbrew.polarbearexpress.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.capability.provider.TamableEntityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
public class PlayerPolarBearTameAction {
    private static void TameEffect(boolean success, Entity entity) {
        EnumParticleTypes enumparticletypes = EnumParticleTypes.HEART;

        if (!success) {
            enumparticletypes = EnumParticleTypes.SMOKE_NORMAL;
        }

        for (int i = 0; i < 7; ++i) {
            double d0 = entity.world.rand.nextGaussian() * 0.02D;
            double d1 = entity.world.rand.nextGaussian() * 0.02D;
            double d2 = entity.world.rand.nextGaussian() * 0.02D;
            entity.world.spawnParticle(enumparticletypes, entity.posX + (double) (entity.world.rand.nextFloat() * entity.width * 2.0F) - (double) entity.width, entity.posY + 0.5D + (double) (entity.world.rand.nextFloat() * entity.height), entity.posZ + (double) (entity.world.rand.nextFloat() * entity.width * 2.0F) - (double) entity.width, d0, d1, d2);
        }
    }

    public static boolean doWork(EntityPlayer player, EntityPolarBear polarBear, boolean actualWork) {
        if(player.world.isRemote) {
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
                float chance = Minecraft.getMinecraft().world.rand.nextFloat();
                if(currentChance >= chance) {
                    polarBear.setHealth(polarBear.getMaxHealth());
                    tamableEntity.setOwner(player);
                    TameEffect(true, polarBear);
                } else {
                    tamableEntity.incrementChance();
                    TameEffect(false, polarBear);
                }
            }

            return true;
        }
        return false;
    }
}
