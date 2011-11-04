package me.main__.tameender.database;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bukkit.inventory.ItemStack;

import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name = "tameender_endermen")
public class SavedEnderman {

    public SavedEnderman() {
    }

    public SavedEnderman(int id, String playerName) {
        this.id = id;
        this.playerName = playerName;
    }

    @Id
    private Integer id;

    @NotNull
    private String playerName;

    private ItemStack carriedItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public ItemStack getCarriedItems() {
        return carriedItems;
    }

    public void setCarriedItems(ItemStack carriedItems) {
        this.carriedItems = carriedItems;
    }
}
