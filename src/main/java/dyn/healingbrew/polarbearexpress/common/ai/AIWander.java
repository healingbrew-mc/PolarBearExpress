package dyn.healingbrew.polarbearexpress.common.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;

public class AIWander extends EntityAIWander {
    public AIWander(EntityCreature creatureIn, double speedIn) {
        super(creatureIn, speedIn);
    }

    @Override
    public boolean shouldExecute() {
        if(super.shouldExecute()) {
            return !this.entity.getLeashed();
        }
        return false;
    }
}
