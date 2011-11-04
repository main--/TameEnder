package me.main__.tameender;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import me.main__.tameender.database.SavedEnderman;
import me.main__.tameender.listeners.TameEnderPlayerListener;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author main()
 */
public class TameEnder extends JavaPlugin {
    private static TameEnder instance;
    
    private TamedEndermanFactory factory;

    public void onEnable() {
        TameEnder.setInstance(this);

        setupDatabase();
        
        factory = new TamedEndermanDefaultFactory(this.getDatabase());

        PluginManager pm = this.getServer().getPluginManager();
        TameEnderPlayerListener playerListener = new TameEnderPlayerListener();
        pm.registerEvent(Type.PLAYER_INTERACT_ENTITY, playerListener, Priority.Normal, this);

        PluginDescriptionFile pdfFile = this.getDescription();
        Util.log(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
    }

    private void setupDatabase() {
        try {
            getDatabase().find(SavedEnderman.class).findRowCount();
        }
        catch (PersistenceException e) {
            Util.log("Installing database!");
            installDDL();
        }
    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        Util.log(pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!");
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(SavedEnderman.class);
        return list;
    }

    public static TameEnder getInstance() {
        return instance;
    }

    public static void setInstance(TameEnder newinstance) {
        instance = newinstance;
    }
    
    public TamedEndermanFactory getFactory() {
        if (factory == null)
            throw new IllegalStateException("Factory is null!");
        
        return factory;
    }
}
