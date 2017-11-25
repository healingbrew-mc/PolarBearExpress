package dyn.healingbrew.polarbearexpress.common.net.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

@SuppressWarnings("unused")
public class SpawnParticlesMessage implements IMessage {
    public int entity;
    public boolean success;

    public SpawnParticlesMessage() {}
    public SpawnParticlesMessage(Entity entity, boolean success) {
        this.entity = entity.getEntityId();
        this.success = success;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.success = buf.readBoolean();
        this.entity = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.success);
        buf.writeInt(this.entity);
    }
}
