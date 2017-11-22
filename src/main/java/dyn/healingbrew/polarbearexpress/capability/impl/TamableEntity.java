package dyn.healingbrew.polarbearexpress.capability.impl;

import dyn.healingbrew.polarbearexpress.Config;
import dyn.healingbrew.polarbearexpress.capability.generic.ITamableEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class TamableEntity implements ITamableEntity {
    private UUID player = null;
    private String playerName = null;
    private float chance;
    private int attempts;
    private EntityPlayer cachedPlayer = null;

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
    public EntityPlayer getOwner() {
        if(cachedPlayer == null) {
            cachedPlayer = Minecraft.getMinecraft().world.getPlayerEntityByUUID(player);
        }
        return cachedPlayer;
    }

    @Override
    public String getName() {
        return playerName;
    }

    @Override
    public UUID getUUID() {
        return player;
    }

    @Override
    public float getChance() {
        return this.chance;
    }

    @Override
    public int getAttempts() {
        return this.attempts;
    }
}
