package dyn.healingbrew.polarbearexpress.common.net.handler;

import dyn.healingbrew.polarbearexpress.common.behavior.PlayerPolarBearTameAction;
import dyn.healingbrew.polarbearexpress.common.net.message.SpawnParticlesMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings("unused")
public class SpawnParticlesHandler implements IMessageHandler<SpawnParticlesMessage, IMessage> {
    @Override
    public IMessage onMessage(SpawnParticlesMessage message, MessageContext ctx) {
        Minecraft minecraft = Minecraft.getMinecraft();

        Entity entity = minecraft.world.getEntityByID(message.entity);

        minecraft.addScheduledTask(() -> PlayerPolarBearTameAction.TameEffect(message.success, entity));
        return null;
    }
}
