package dyn.healingbrew.polarbearexpress.common.behavior;

import dyn.healingbrew.polarbearexpress.common.ai.*;
import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.common.capability.provider.TamableEntityProvider;
import dyn.healingbrew.polarbearexpress.common.net.NetworkHandler;
import dyn.healingbrew.polarbearexpress.common.net.message.TamableCapabilitySyncMessage;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings({"unused", "ConstantConditions"})
public class BehaviorListener {
    public static void Register() {
        MinecraftForge.EVENT_BUS.register(new BehaviorListener());
    }

    @SubscribeEvent
    public void EntitySpawn(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityPolarBear) {
            EntityPolarBear bear = (EntityPolarBear) event.getEntity();

            bear.stepHeight = 1.0F;

            if(event.getEntity().world.isRemote) {
                return;
            }

            EntityAIBase AIMeleeAttack = null;
            for (EntityAITasks.EntityAITaskEntry ai : bear.tasks.taskEntries) {
                if (ai.action.getClass().getSimpleName().contains("AIMeleeAttack")) {
                    AIMeleeAttack = ai.action;
                    break;
                }
            }

            if (AIMeleeAttack == null) {
                System.err.println("Warning: cannot find AIMeleeAttack AI in polar bear");
            }

            bear.tasks.taskEntries.clear();
            bear.tasks.addTask(0, new EntityAISwimming(bear));
            if (AIMeleeAttack != null) {
                bear.tasks.addTask(1, AIMeleeAttack);
            }
            bear.tasks.addTask(2, new AIFollowOwner(bear, 1.0D, 10.0F, 2.0F));
            bear.tasks.addTask(4, new AIWander(bear, 1.0D));
            bear.tasks.addTask(4, new EntityAIFollowParent(bear, 1.25D));
            bear.tasks.addTask(5, new EntityAILookIdle(bear));

            bear.targetTasks.taskEntries.clear();
            bear.targetTasks.addTask(1, new AIDefendOwner(bear));
            bear.targetTasks.addTask(2, new AIListenOwner(bear));
            bear.targetTasks.addTask(3, new AIPolarBearAlert(bear));
            bear.targetTasks.addTask(4, new AIPolarBearPlayerAtk(bear));
        }
    }

    @SubscribeEvent
    public void InteractEvent(PlayerInteractEvent.EntityInteract event) {
        if (event.isCanceled()) {
            return;
        }

        if (event.getTarget() instanceof EntityPolarBear) {
            EntityPolarBear bear = (EntityPolarBear) event.getTarget();
            EntityPlayer player = event.getEntityPlayer();

            ITamableEntity tamableEntity = bear.getCapability(TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY, null);

            if (player instanceof EntityPlayerMP && !player.world.isRemote) {
                NetworkHandler.ChannelClient.sendTo(new TamableCapabilitySyncMessage(tamableEntity, bear), (EntityPlayerMP) player);
            }

            if (PlayerPolarBearMountAction.doWork(player, bear, false)) {
                event.setCanceled(PlayerPolarBearMountAction.doWork(player, bear, true));
            } else if (PlayerPolarBearTameAction.doWork(player, bear, false)) {
                event.setCanceled(PlayerPolarBearTameAction.doWork(player, bear, true));
            }
        }
    }
}
