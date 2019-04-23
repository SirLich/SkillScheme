package main.java.plugin.sirlich.core;

import org.bukkit.entity.Arrow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RpgArrow {

    public static HashMap<UUID, RpgArrow> arrowMap = new HashMap<UUID, RpgArrow>();

    public static RpgArrow getArrow(UUID uuid){
        return arrowMap.get(uuid);
    }

    public static RpgArrow getArrow(Arrow arrow){
        return arrowMap.get(arrow.getUniqueId());
    }

    private ArrayList<String> tags;
    private RpgPlayer shooter;
    private UUID id;

    //Add arrow
    public static void registerArrow(Arrow arrow, RpgPlayer shooter){
        ArrayList<String> tags = new ArrayList<String>();
        RpgArrow rpgArrow = new RpgArrow(arrow.getUniqueId(),shooter,tags);
        arrowMap.put(arrow.getUniqueId(),rpgArrow);
    }



    //Remove arrow
    public static void deregisterArrow(Arrow arrow){
        arrowMap.remove(arrow.getUniqueId());
    }

    //Remove arrow
    public void deregisterSelf(){
        arrowMap.remove(this.id);
    }

    public static void addTag(UUID uuid,String tag){
        RpgArrow.getArrow(uuid).tags.add(tag);
    }

    public void addTag(String tag){
        this.tags.add(tag);
    }
    public static boolean hasArrow(Arrow arrow){
        return arrowMap.containsKey(arrow.getUniqueId());
    }

    public RpgPlayer getShooter(){
        return this.shooter;
    }

    public ArrayList<String> getTags(){
        return tags;
    }

    public UUID getId(){
        return this.id;
    }

    public boolean hasTag(String tag){
        return tags.contains(tag);
    }

    //Handle constructor from both registerArrow types
    public RpgArrow(UUID arrowID,RpgPlayer shooter, ArrayList<String> tags){
        this.id = arrowID;
        this.tags = tags;
        this.shooter = shooter;
    }
}
