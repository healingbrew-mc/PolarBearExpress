package dyn.healingbrew.polarbearexpress.common.ai;

import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.common.capability.provider.TamableEntityProvider;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

@SuppressWarnings("ConstantConditions")
public class AIListenOwner extends EntityAITarget {
    EntityCreature tameable;
    EntityLivingBase attacker;
    private int timestamp;

    public AIListenOwner(EntityCreature theEntityTameableIn) {
        super(theEntityTameableIn, false);
        this.tameable = theEntityTameableIn;
        this.setMutexBits(1);
    }

    public ITamableEntity getCapability() {
        return tameable.getCapability(TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY, null);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (this.getCapability() == null) {
            return false;
        } else if (this.getCapability().getUUID() == null) {
            return false;
        } else if (this.getCapability().getSitting()) {
            return false;
        } else {
            EntityLivingBase entitylivingbase = this.getCapability().getOwner();

            if (entitylivingbase == null) {
                return false;
            } else {
                this.attacker = entitylivingbase.getLastAttackedEntity();
                int i = entitylivingbase.getLastAttackedEntityTime();
                return i != this.timestamp && this.isSuitableTarget(this.attacker, false) && AIDefendOwner.shouldAttackEntity(this.attacker, entitylivingbase);
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
            this.timestamp = entitylivingbase.getLastAttackedEntityTime();
        }

        super.startExecuting();
    }
}