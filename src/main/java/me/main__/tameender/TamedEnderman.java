package me.main__.tameender;

import me.main__.tameender.database.SavedEnderman;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Enderman;

public class TamedEnderman {

    public TamedEnderman(SavedEnderman databaseEntity) {
        this.setDatabaseEntity(databaseEntity);
    }

    private SavedEnderman databaseEntity;

    public final SavedEnderman getDatabaseEntity() {
        return databaseEntity;
    }

    public final void setDatabaseEntity(SavedEnderman databaseEntity) {
        if (databaseEntity == null)
            throw new IllegalArgumentException("databaseEntity was null!");

        this.databaseEntity = databaseEntity;
    }

    public final OfflinePlayer getOwner() {
        return TameEnder.getInstance().getServer().getOfflinePlayer(this.getDatabaseEntity().getPlayerName());
    }

    public final void setOwner(OfflinePlayer player) {
        this.getDatabaseEntity().setPlayerName(player.getName());
    }
    
    public TamedAndSpawnedEnderman linkWithSpawnedEnderman(Enderman e)
    {
        TamedAndSpawnedEnderman ne = new TamedAndSpawnedEnderman(e, getDatabaseEntity());
        return ne;
    }
    
    public boolean isSpawned() {
        return false;
    }
}
