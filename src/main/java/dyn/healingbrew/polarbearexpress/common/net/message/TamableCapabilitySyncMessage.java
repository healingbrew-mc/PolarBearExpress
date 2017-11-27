package dyn.healingbrew.polarbearexpress.common.net.message;

import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import static dyn.healingbrew.polarbearexpress.common.capability.provider.TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY;
import static dyn.healingbrew.polarbearexpress.common.capability.CapabilityRegistry.TAMABLE_ENTITY_CAPABILITY_IO;

public class TamableCapabilitySyncMessage implements IMessage {
    public NBTTagCompound tag;
    public int id;

    public TamableCapabilitySyncMessage() {}
    public TamableCapabilitySyncMessage(ITamableEntity tamableEntity, EntityPolarBear polarBear) {
        this.tag = (NBTTagCompound) TAMABLE_ENTITY_CAPABILITY_IO.writeNBT(TAMABLE_ENTITY_CAPABILITY, tamableEntity, null);
        this.id = polarBear.getEntityId();
    }
    public TamableCapabilitySyncMessage(NBTBase tag, int dimension, int id) {
        this.tag = (NBTTagCompound)tag;
        this.id = id;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        tag = ByteBufUtils.readTag(buf);
        if(tag == null) {
            return;
        }
        id = tag.getInteger("NET_ID");
        tag.removeTag("NET_ID");
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if(tag == null) {
            return;
        }
        tag.setInteger("NET_ID", id);
        ByteBufUtils.writeTag(buf, tag);
        tag.removeTag("NET_ID");
    }
}
