package dyn.healingbrew.polarbearexpress.client.behavior;

import dyn.healingbrew.polarbearexpress.common.net.NetworkHandler;
import dyn.healingbrew.polarbearexpress.common.net.message.JumpMessage;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
public class ClientBehaviorListener {

    public static void Register() {
        MinecraftForge.EVENT_BUS.register(new ClientBehaviorListener());
    }

    @SubscribeEvent
    public void LUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        if(!event.getEntity().world.isRemote) {
            return;
        }

        if(event.getEntityLiving() instanceof EntityPlayer) {
            if(event.getEntity() instanceof EntityPlayerSP) {
                EntityPlayerSP player = (EntityPlayerSP) event.getEntityLiving();

                if (player.isRiding() && player.movementInput.jump && player.getRidingEntity() instanceof EntityPolarBear) {
                    NetworkHandler.ChannelClient.sendToServer(new JumpMessage(player));
                }
            }
        }
    }
}
