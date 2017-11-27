package dyn.healingbrew.polarbearexpress.common.ai;

import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.common.capability.provider.TamableEntityProvider;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIWander;

@SuppressWarnings("ConstantConditions")
public class AIWander extends EntityAIWander {
    public AIWander(EntityCreature creatureIn, double speedIn) {
        super(creatureIn, speedIn);
    }

    public ITamableEntity getCapability() {
        return entity.getCapability(TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY, null);
    }

    @Override
    public boolean shouldExecute() {
        if(super.shouldExecute()) {
            if (this.getCapability().getUUID() == null) {
                return true;
            }

            EntityLivingBase entitylivingbase = this.getCapability().getOwner();

            if(entitylivingbase == null) {
                return true;
            }

            if (entity.isBeingRidden()) {
                return false;
            } else if (entity.getLeashed()) {
                return false;
            }

            return true;
        }

        return false;
    }
}
