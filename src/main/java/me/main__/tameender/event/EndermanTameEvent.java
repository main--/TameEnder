package me.main__.tameender.event;

import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Enderman;
import org.bukkit.event.entity.EntityTameEvent;

@SuppressWarnings("serial")
public class EndermanTameEvent extends EntityTameEvent {

    public EndermanTameEvent(Enderman ender, AnimalTamer owner) {
        super(ender, owner);

        // TODO something...?
    }
}
