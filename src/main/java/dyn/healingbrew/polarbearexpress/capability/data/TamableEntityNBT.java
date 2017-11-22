package dyn.healingbrew.polarbearexpress.capability.data;

import dyn.healingbrew.polarbearexpress.capability.generic.ITamableEntity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class TamableEntityNBT {
    public static NBTBase save(ITamableEntity instance) {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setUniqueId("owner", instance.getUUID());
        compound.setString("owner_name", instance.getName());
        compound.setInteger("attempts", instance.getAttempts());
        compound.setFloat("chance", instance.getChance());
        return compound;
    }

    public static void load(NBTBase nbt, ITamableEntity instance) {
        if(nbt instanceof NBTTagCompound) {
            NBTTagCompound compound = (NBTTagCompound) nbt;
            instance.setOwner(compound.getUniqueId("owner"), compound.getString("owner_name"));
            instance.setAttempts(compound.getInteger("attempts"));
            instance.setChance(compound.getFloat("chance"));
        }
    }
}
