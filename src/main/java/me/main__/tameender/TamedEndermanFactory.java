package me.main__.tameender;

import java.util.Collection;

public interface TamedEndermanFactory {

    /**
     * Creates a new {@link TamedEnderman} for a given player.
     * 
     * @param playerName The player
     * @return The new {@link TamedEnderman}
     */
    public abstract TamedEnderman getNewOne(String playerName);

    /**
     * @return All {@link TamedEnderman} of a given player.
     */
    public abstract Collection<TamedEnderman> getAll(String playerName);

    /**
     * Save a {@link TamedEnderman}.
     * 
     * @param ender The {@link TamedEnderman} that should be saved.
     */
    public abstract void save(TamedEnderman ender);

    /**
     * Write the items to whatever storage.
     */
    public abstract void flush();

    /**
     * Remove all items.
     */
    public abstract void clear();

    /**
     * Temporarily forbid creation of new items.
     */
    public abstract void pauseCreation();

    /**
     * Resume creation of new items.
     */
    public abstract void resumeCreation();

}