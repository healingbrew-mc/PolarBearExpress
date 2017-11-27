package dyn.healingbrew.polarbearexpress.common.net.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.UUID;

public class JumpMessage implements IMessage {
    public EntityPlayer player;

    public JumpMessage() {}
    public JumpMessage(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int dimension = buf.readInt();
        this.player = DimensionManager.getWorld(dimension).getPlayerEntityByUUID(new UUID(buf.readLong(), buf.readLong()));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(player.dimension);
        buf.writeLong(player.getPersistentID().getMostSignificantBits());
        buf.writeLong(player.getPersistentID().getLeastSignificantBits());
    }
}
