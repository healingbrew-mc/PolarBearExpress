package dyn.healingbrew.polarbearexpress.common.net.handler;

import dyn.healingbrew.polarbearexpress.common.net.message.TamableCapabilitySyncMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import static dyn.healingbrew.polarbearexpress.common.capability.CapabilityRegistry.TAMABLE_ENTITY_CAPABILITY_IO;
import static dyn.healingbrew.polarbearexpress.common.capability.provider.TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY;

@SuppressWarnings("unused")
public class TamableCapabilitySyncHandler implements IMessageHandler<TamableCapabilitySyncMessage, IMessage> {
    @Override
    public IMessage onMessage(TamableCapabilitySyncMessage message, MessageContext ctx) {
        Minecraft minecraft = Minecraft.getMinecraft();
        Entity entity = minecraft.player.world.getEntityByID(message.id); // should be local world anyway
        if(entity == null) {
            return null;
        }
        minecraft.addScheduledTask(() -> TAMABLE_ENTITY_CAPABILITY_IO.readNBT(TAMABLE_ENTITY_CAPABILITY, entity.getCapability(TAMABLE_ENTITY_CAPABILITY, null), null, message.tag));
        return null;
    }
}
