package dyn.healingbrew.polarbearexpress.ai;

import dyn.healingbrew.polarbearexpress.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.capability.provider.TamableEntityProvider;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.monster.EntityPolarBear;

@SuppressWarnings({"ConstantConditions", "WeakerAccess", "NullableProblems"})
public class AIPolarBearAlert extends EntityAIHurtByTarget {
    EntityCreature entity;

    public AIPolarBearAlert(EntityCreature entity) {
        super(entity, false);
        this.entity = entity;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        super.startExecuting();

        if (entity.isChild()) {
            this.alertOthers();
            this.resetTask();
        }
    }

    public ITamableEntity getCapability() {
        return entity.getCapability(TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY, null);
    }

    public ITamableEntity getCapability(EntityCreature creature) {
        return creature.getCapability(TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY, null);
    }

    protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn) {
        if(getCapability(creatureIn) != null && !entityLivingBaseIn.isEntityEqual(getCapability(creatureIn).getOwner())) {
            if (creatureIn instanceof EntityPolarBear && !creatureIn.isChild()) {
                super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);
            }
        }
    }
}