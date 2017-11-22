package dyn.healingbrew.polarbearexpress.behavior;

import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
public class BehaviorListener {
    public static void Register() {
        MinecraftForge.EVENT_BUS.register(new BehaviorListener());
    }

    @SubscribeEvent
    public void EntitySpawn(EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof EntityPolarBear) {
            EntityPolarBear bear = (EntityPolarBear) event.getEntity();
            bear.tasks.taskEntries.clear();
            bear.targetTasks.taskEntries.clear();
        }
    }

    @SubscribeEvent
    public void InteractEvent(PlayerInteractEvent.EntityInteract event) {
        if(event.isCanceled()) {
            return;
        }

        if(event.getTarget() instanceof EntityPolarBear) {
            EntityPolarBear bear = (EntityPolarBear) event.getTarget();
            EntityPlayer player = event.getEntityPlayer();

            if(PlayerPolarBearMountAction.doWork(player, bear, false)) {
                event.setCanceled(PlayerPolarBearMountAction.doWork(player, bear, true));
            } else if(PlayerPolarBearTameAction.doWork(player, bear, false)) {
                event.setCanceled(PlayerPolarBearTameAction.doWork(player, bear, true));
            }
        }
    }
}
