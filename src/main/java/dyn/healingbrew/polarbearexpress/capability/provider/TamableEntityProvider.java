package dyn.healingbrew.polarbearexpress.capability.provider;

import dyn.healingbrew.polarbearexpress.capability.generic.ITamableEntity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class TamableEntityProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(ITamableEntity.class)
    public static final Capability<ITamableEntity> TAMABLE_ENTITY_CAPABILITY = null;

    private ITamableEntity instance = TAMABLE_ENTITY_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == TAMABLE_ENTITY_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == TAMABLE_ENTITY_CAPABILITY ? TAMABLE_ENTITY_CAPABILITY.cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return TAMABLE_ENTITY_CAPABILITY.getStorage().writeNBT(TAMABLE_ENTITY_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        TAMABLE_ENTITY_CAPABILITY.getStorage().readNBT(TAMABLE_ENTITY_CAPABILITY, this.instance, null, nbt);
    }
}
