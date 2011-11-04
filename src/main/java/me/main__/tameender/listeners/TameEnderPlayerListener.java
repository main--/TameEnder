package me.main__.tameender.listeners;

import me.main__.tameender.TameEnder;
import me.main__.tameender.TamedAndSpawnedEnderman;
import me.main__.tameender.TamedEnderman;
import me.main__.tameender.database.SavedEnderman;
import me.main__.tameender.event.EndermanTameEvent;

import org.bukkit.Material;
/*
 * We are waiting for this one:
 * https://github.com/Bukkit/Bukkit/pull/400
 */
//import org.bukkit.EntityEffect;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;

public class TameEnderPlayerListener extends PlayerListener {
    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.isCancelled())
            return;

        if (event.getRightClicked() instanceof Enderman) {
            Enderman enderman = (Enderman) event.getRightClicked();
            Player player = event.getPlayer();

            if (player.getItemInHand().getType() == Material.DIAMOND) {
                player.getInventory().removeItem(new ItemStack(Material.DIAMOND));
                // so he had the diamonds. Let's see if he can convince the enderman to go with him ;)
                boolean tameSuccess = true;
                double chance = Math.random();
                if (chance > 0.5) {
                    EndermanTameEvent etevent = new EndermanTameEvent(enderman, player);
                    TameEnder.getInstance().getServer().getPluginManager().callEvent(etevent);
                    if (etevent.isCancelled())
                        // the event has been cancelled, for whatever reasons. sorry, player :(
                        tameSuccess = false;
                }
                else {
                    tameSuccess = false;
                }

                if (tameSuccess) {
                    // yay, let's tame the enderman! :D
                    enderman.setCarriedMaterial(Material.AIR.getNewData((byte) 0));

                    TamedEnderman e = null;
                    try {
                        e = TameEnder.getInstance().getFactory().getNewOne(player.getName());
                    }
                    catch (IllegalStateException ex) {
                        player.sendMessage("Try again later.");
                        // TODO this is rather unfair since he doesn't get his diamond back
                    }
                    TamedAndSpawnedEnderman tase = e.linkWithSpawnedEnderman(enderman);
                    TameEnder.getInstance().getFactory().save(tase);

                    // pay...?

                    /*
                     * We are waiting for this one: https://github.com/Bukkit/Bukkit/pull/400
                     */
                    // event.getRightClicked().playEffect(EntityEffect.WOLF_HEARTS); //a client-mod will enable this for endermen

                }
                else {
                    // sorry, but taming failed!

                    /*
                     * We are waiting for this one: https://github.com/Bukkit/Bukkit/pull/400
                     */
                    // event.getRightClicked().playEffect(EntityEffect.WOLF_SMOKE); //a client-mod will enable this for endermen
                    return;
                }
            }
            else {
                // sorry, we need the diamonds :D
                return;
            }
        }
    }
}
