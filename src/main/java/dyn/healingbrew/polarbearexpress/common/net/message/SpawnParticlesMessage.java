package dyn.healingbrew.polarbearexpress.common.net.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.nio.charset.Charset;

@SuppressWarnings("unused")
public class SpawnParticlesMessage implements IMessage {
    public int entity;
    public int particle;

    public SpawnParticlesMessage() {}
    public SpawnParticlesMessage(Entity entity, int particle) {
        this.entity = entity.getEntityId();
        this.particle = particle;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.particle = buf.readInt();
        this.entity = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.particle);
        buf.writeInt(this.entity);
    }
}
