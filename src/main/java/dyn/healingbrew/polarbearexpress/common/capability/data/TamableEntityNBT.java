package dyn.healingbrew.polarbearexpress.common.capability.data;

import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class TamableEntityNBT {
    public static NBTBase save(ITamableEntity instance) {
        NBTTagCompound compound = new NBTTagCompound();
        if(instance.getUUID() != null) {
            compound.setUniqueId("owner", instance.getUUID());
            compound.setString("owner_name", instance.getName());
        }
        compound.setInteger("attempts", instance.getAttempts());
        compound.setFloat("chance", instance.getChance());
        compound.setBoolean("sitting", instance.getSitting());
        return compound;
    }

    public static void load(NBTBase nbt, ITamableEntity instance) {
        if(nbt instanceof NBTTagCompound) {
            NBTTagCompound compound = (NBTTagCompound) nbt;
            if(compound.hasKey("ownerMost") && compound.hasKey("ownerLeast") && compound.hasKey("owner_name")) {
                instance.setOwner(compound.getUniqueId("owner"), compound.getString("owner_name"));
            }
            if(compound.hasKey("attempts")) {
                instance.setAttempts(compound.getInteger("attempts"));
            }
            if(compound.hasKey("chance")) {
                instance.setChance(compound.getFloat("chance"));
            }
            if(compound.hasKey("sitting")) {
                instance.setSitting(compound.getBoolean("sitting"));
            }
        }
    }
}
