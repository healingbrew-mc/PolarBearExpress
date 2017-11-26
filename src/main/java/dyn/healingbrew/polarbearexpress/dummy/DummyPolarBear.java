package dyn.healingbrew.polarbearexpress.dummy;

import dyn.healingbrew.polarbearexpress.core.HBMethod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.world.World;

import javax.annotation.Nullable;

// used to get bytecode

public class DummyPolarBear extends EntityPolarBear {
    public DummyPolarBear(World worldIn) {
        super(worldIn);
    }

    @Override
    public boolean canBeSteered() {
        return true;
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        Entity s = super.getControllingPassenger();
        return s == null ? HBMethod.getControllingPassenger(this) : s;
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        if(!HBMethod.travel(this, strafe, vertical, forward)) {
            super.travel(strafe, vertical, forward);
        }
    }
}
