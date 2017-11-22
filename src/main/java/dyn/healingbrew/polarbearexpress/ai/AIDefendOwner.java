package dyn.healingbrew.polarbearexpress.ai;

import dyn.healingbrew.polarbearexpress.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.capability.provider.TamableEntityProvider;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;

@SuppressWarnings({"ConstantConditions", "WeakerAccess", "SimplifiableIfStatement"})
public class AIDefendOwner extends EntityAITarget {
    EntityCreature tameable;
    EntityLivingBase attacker;
    private int timestamp;

    public AIDefendOwner(EntityCreature theDefendingTameableIn) {
        super(theDefendingTameableIn, false);
        this.tameable = theDefendingTameableIn;
        this.setMutexBits(1);
    }

    public ITamableEntity getCapability() {
        return tameable.getCapability(TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY, null);
    }

    public static boolean shouldAttackEntity(EntityLivingBase target, EntityLivingBase owner) {
        if (!(target instanceof EntityCreeper) && !(target instanceof EntityGhast)) {
            if (target instanceof EntityWolf) {
                EntityWolf entitywolf = (EntityWolf) target;

                if (entitywolf.isTamed() && entitywolf.getOwner() == owner) {
                    return false;
                }
            }

            if(target instanceof EntityPolarBear) {
                return false;
            }

            if (target instanceof EntityPlayer && owner instanceof EntityPlayer && !((EntityPlayer) owner).canAttackPlayer((EntityPlayer) target)) {
                return false;
            } else {
                return !(target instanceof AbstractHorse) || !((AbstractHorse) target).isTame();
            }
        } else {
            return false;
        }
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (this.getCapability() == null) {
            return false;
        } else if (this.getCapability().getUUID() == null) {
            return false;
        } else {
            EntityLivingBase entitylivingbase = this.getCapability().getOwner();

            if (entitylivingbase == null) {
                return false;
            } else {
                this.attacker = entitylivingbase.getRevengeTarget();
                int i = entitylivingbase.getRevengeTimer();
                return i != this.timestamp && this.isSuitableTarget(this.attacker, false) && shouldAttackEntity(this.attacker, entitylivingbase);
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.attacker);
        EntityLivingBase entitylivingbase = this.getCapability().getOwner();

        if (entitylivingbase != null) {
            this.timestamp = entitylivingbase.getRevengeTimer();
        }

        super.startExecuting();
    }
}