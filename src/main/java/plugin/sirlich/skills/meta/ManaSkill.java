package main.java.plugin.sirlich.skills.meta;

import main.java.plugin.sirlich.core.RpgPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ManaSkill extends Skill{
    private Material headBlock;
    private boolean active;

    public ManaSkill(RpgPlayer rpgPlayer, int level, String id, Material headBlock){
        super(rpgPlayer,level,id);
        this.headBlock = headBlock;
    }

    public void ActivateSkill(){
        this.getRpgPlayer().getPlayer().getInventory().setHelmet(new ItemStack(headBlock));
        this.active = true;
        getRpgPlayer().setModifierActive(true);
    }

    public void DeactivateSkill(){
        this.getRpgPlayer().getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR));
        this.active = false;
        getRpgPlayer().setModifierActive(false);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}