package dyn.healingbrew.polarbearexpress.common.ai;

import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.common.capability.provider.TamableEntityProvider;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.player.EntityPlayer;

@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class AIPolarBearPlayerAtk extends EntityAINearestAttackableTarget<EntityPlayer> {
    EntityCreature entity;

    public AIPolarBearPlayerAtk(EntityCreature entity) {
        super(entity, EntityPlayer.class, 20, true, true, null);
        this.entity = entity;
    }

    public ITamableEntity getCapability() {
        return entity.getCapability(TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY, null);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (entity.isChild()) {
            return false;
        } else {
            if (getCapability() != null && getCapability().getUUID() != null) {
                return false;
            }
            if (super.shouldExecute()) {
                for (EntityPolarBear entitypolarbear : entity.world.getEntitiesWithinAABB(EntityPolarBear.class, entity.getEntityBoundingBox().grow(8.0D, 4.0D, 8.0D))) {
                    if (entitypolarbear.isChild()) {
                        return true;
                    }
                }
            }

            entity.setAttackTarget(null);
            return false;
        }
    }

    protected double getTargetDistance() {
        return super.getTargetDistance() * 0.5D;
    }
}