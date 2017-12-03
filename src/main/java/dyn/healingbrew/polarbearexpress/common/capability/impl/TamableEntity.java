package dyn.healingbrew.polarbearexpress.common.capability.impl;

import dyn.healingbrew.polarbearexpress.Config;
import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import jline.internal.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.UUID;

public class TamableEntity implements ITamableEntity {
    private UUID player = null;
    private String playerName = null;
    private float chance = Config.ChancePerAttempt;
    private int attempts;
    private EntityPlayer cachedPlayer = null;
    private boolean sitting = false;

    @Override
    public void setOwner(EntityPlayer player) {
        this.player = player.getPersistentID();
        this.playerName = player.getName();
    }

    @Override
    public void setOwner(UUID player, String name) {
        this.player = player;
        this.playerName = name;
    }

    @Override
    public void incrementChance() {
        this.chance += Config.IncreaseOddsWithAttempt;
    }

    @Override
    public void incrementAttempt() {
        this.attempts += 1;
    }

    @Override
    public void setChance(float chance) {
        this.chance = chance;
    }

    @Override
    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    @Override
    public void setSitting(boolean sitting) {
        this.sitting = sitting;
    }

    @Override
    public EntityPlayer getOwner() {
        if(cachedPlayer == null) {
            cachedPlayer = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getPlayerEntityByUUID(player);
        }
        return cachedPlayer;
    }

    @Override
    public String getName() {
        return playerName;
    }

    @Override
    @Nullable
    public UUID getUUID() {
        return player;
    }

    @Override
    public float getChance() {
        return Math.max(this.chance, Config.ChancePerAttempt);
    }

    @Override
    public int getAttempts() {
        return this.attempts;
    }

    @Override
    public boolean getSitting() {
        return this.sitting;
    }
}
