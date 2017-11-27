package dyn.healingbrew.polarbearexpress.common.behavior;

import dyn.healingbrew.polarbearexpress.Config;
import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.common.capability.provider.TamableEntityProvider;
import dyn.healingbrew.polarbearexpress.common.net.NetworkHandler;
import dyn.healingbrew.polarbearexpress.common.net.message.SpawnParticlesMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
public class PlayerPolarBearTameAction {
    public static void TameEffect(boolean success, Entity entity) {
        World world = entity.getEntityWorld();
        if(!world.isRemote) {
            NetworkHandler.ChannelClient.sendToAllAround(new SpawnParticlesMessage(entity, success), new NetworkRegistry.TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 6));
            return;
        }

        EnumParticleTypes enumparticletypes = EnumParticleTypes.HEART;

        if (!success) {
            enumparticletypes = EnumParticleTypes.SMOKE_NORMAL;
        }

        for (int i = 0; i < 7; ++i) {
            double d0 = world.rand.nextGaussian() * 0.02D;
            double d1 = world.rand.nextGaussian() * 0.02D;
            double d2 = world.rand.nextGaussian() * 0.02D;
            world.spawnParticle(enumparticletypes, entity.posX + (double) (world.rand.nextFloat() * entity.width * 2.0F) - (double) entity.width, entity.posY + 0.5D + (double) (world.rand.nextFloat() * entity.height), entity.posZ + (double) (world.rand.nextFloat() * entity.width * 2.0F) - (double) entity.width, d0, d1, d2);
        }
    }

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
                player.getHeldItemMainhand().setCount(player.getHeldItemMainhand().getCount());
            }

            if(currentAttempt >= Config.MinimumAttempts) {
                float chance = player.getEntityWorld().rand.nextFloat();
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
