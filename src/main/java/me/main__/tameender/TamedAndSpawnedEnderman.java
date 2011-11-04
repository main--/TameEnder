package me.main__.tameender;

import me.main__.tameender.database.SavedEnderman;

import org.bukkit.entity.Enderman;

public class TamedAndSpawnedEnderman extends TamedEnderman {
    private Enderman enderman;

    public TamedAndSpawnedEnderman(Enderman enderman, SavedEnderman databaseEntity) {
        super(databaseEntity);
        this.setEnderman(enderman);
    }

    public Enderman getEnderman() {
        return enderman;
    }

    public TamedAndSpawnedEnderman linkWithSpawnedEnderman(Enderman e) {
        throw new IllegalStateException("Is already spawned!");
    }

    public boolean isSpawned() {
        return true;
    }

    public void setEnderman(Enderman enderman) {
        if (enderman == null)
            throw new IllegalArgumentException("enderman was null!");

        this.enderman = enderman;
    }
}
