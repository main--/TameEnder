package me.main__.tameender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.main__.tameender.database.SavedEnderman;

import com.avaje.ebean.EbeanServer;

public class TamedEndermanDefaultFactory implements TamedEndermanFactory {
    private final Map<Integer, TamedEnderman> tamedendermen;
    private final EbeanServer database;

    private int nextId = -1;
    private boolean canCreate = true;

    public TamedEndermanDefaultFactory(EbeanServer database) {
        this.tamedendermen = new HashMap<Integer, TamedEnderman>();
        this.database = database;
    }

    /**
     * {@inheritDoc}
     */
    public TamedEnderman getNewOne(String playerName) throws IllegalStateException {
        if (!canCreate)
            throw new IllegalStateException("Creation paused!");

        SavedEnderman dbentity = new SavedEnderman(getNextId(), playerName);
        TamedEnderman thisEnderman = new TamedEnderman(dbentity);
        this.tamedendermen.put(thisEnderman.getDatabaseEntity().getId(), thisEnderman);

        return thisEnderman;
    }

    private int getNextId() {
        if (nextId == -1) {
            // we need to recalc it
            recalcNextId();
        }
        else if (tamedendermen.containsKey(nextId)) {
            // not so very nice ^^
            recalcNextId();
        }

        return nextId++;
    }

    public void recalcNextId() {
        for (int i = 0; true; i++) {
            if (!tamedendermen.containsKey(i)) {
                nextId = i;
                break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public Collection<TamedEnderman> getAll(String playerName) {
        List<TamedEnderman> ret = new ArrayList<TamedEnderman>();

        for (TamedEnderman e : tamedendermen.values()) {
            if (e.getOwner().getName() == playerName)
                ret.add(e);
        }

        return ret;
    }

    /**
     * {@inheritDoc}
     */
    public void save(TamedEnderman ender) {
        this.tamedendermen.put(ender.getDatabaseEntity().getId(), ender);
    }

    /**
     * {@inheritDoc}
     */
    public void flush() {
        for (TamedEnderman e : tamedendermen.values()) {
            database.save(e.getDatabaseEntity());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void clear() {
        this.tamedendermen.clear();
    }

    /**
     * {@inheritDoc}
     */
    public void pauseCreation() {
        canCreate = false;
    }

    /**
     * {@inheritDoc}
     */
    public void resumeCreation() {
        canCreate = true;
    }
}
