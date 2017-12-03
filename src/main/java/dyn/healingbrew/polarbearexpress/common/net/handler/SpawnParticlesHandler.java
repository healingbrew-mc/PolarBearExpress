package dyn.healingbrew.polarbearexpress.common.net.handler;

import dyn.healingbrew.polarbearexpress.common.net.NetworkHandler;
import dyn.healingbrew.polarbearexpress.common.net.message.SpawnParticlesMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings("unused")
public class SpawnParticlesHandler implements IMessageHandler<SpawnParticlesMessage, IMessage> {
    public static void DoEffect(int particle, Entity entity) {
        World world = entity.getEntityWorld();
        if(!world.isRemote) {
            NetworkHandler.ChannelClient.sendToAllAround(new SpawnParticlesMessage(entity, particle), new NetworkRegistry.TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 6));
            return;
        }

        EnumParticleTypes enumparticletypes = EnumParticleTypes.getParticleFromId(particle);

        if(enumparticletypes == null) {
            enumparticletypes = EnumParticleTypes.DRAGON_BREATH;
        }

        for (int i = 0; i < 7; ++i) {
            double d0 = world.rand.nextGaussian() * 0.02D;
            double d1 = world.rand.nextGaussian() * 0.02D;
            double d2 = world.rand.nextGaussian() * 0.02D;
            world.spawnParticle(enumparticletypes, entity.posX + (double) (world.rand.nextFloat() * entity.width * 2.0F) - (double) entity.width, entity.posY + 0.5D + (double) (world.rand.nextFloat() * entity.height), entity.posZ + (double) (world.rand.nextFloat() * entity.width * 2.0F) - (double) entity.width, d0, d1, d2);
        }
    }

    @Override
    public IMessage onMessage(SpawnParticlesMessage message, MessageContext ctx) {
        Minecraft minecraft = Minecraft.getMinecraft();
        Entity entity = minecraft.player.world.getEntityByID(message.entity);  // should be local world anyway
        if(entity == null) {
            return null;
        }
        minecraft.addScheduledTask(() -> DoEffect(message.particle, entity));
        return null;
    }
}
