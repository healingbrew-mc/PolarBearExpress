package dyn.healingbrew.polarbearexpress.common.capability;

import dyn.healingbrew.polarbearexpress.common.capability.data.TamableEntityNBT;
import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TamableEntityCapability implements Capability.IStorage<ITamableEntity> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ITamableEntity> capability, ITamableEntity instance, EnumFacing side) {
        return TamableEntityNBT.save(instance);
    }

    @Override
    public void readNBT(Capability<ITamableEntity> capability, ITamableEntity instance, EnumFacing side, NBTBase nbt) {
        TamableEntityNBT.load(nbt, instance);
    }
}
