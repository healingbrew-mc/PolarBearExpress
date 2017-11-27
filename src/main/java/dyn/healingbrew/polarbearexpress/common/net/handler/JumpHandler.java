package dyn.healingbrew.polarbearexpress.common.net.handler;

import dyn.healingbrew.polarbearexpress.common.net.message.JumpMessage;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class JumpHandler implements IMessageHandler<JumpMessage, IMessage> {
    @Override
    public IMessage onMessage(JumpMessage message, MessageContext ctx) {
        if(message.player == null) {
            return null;
        }
        ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
            if(message.player.isRiding() && message.player.getRidingEntity() instanceof EntityPolarBear) {
                ((EntityPolarBear) message.player.getRidingEntity()).getJumpHelper().setJumping();
            }
        });
        return null;
    }
}