package dyn.healingbrew.polarbearexpress.capability.generic;

import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nullable;
import java.util.UUID;

@SuppressWarnings("unused")
public interface ITamableEntity {
    void setOwner(EntityPlayer player);
    void setOwner(UUID player, String name);
    void incrementChance();
    void incrementAttempt();
    void setChance(float chance);
    void setAttempts(int attempts);

    EntityPlayer getOwner();
    String getName();
    @Nullable
    UUID getUUID();
    float getChance();
    int getAttempts();
}
