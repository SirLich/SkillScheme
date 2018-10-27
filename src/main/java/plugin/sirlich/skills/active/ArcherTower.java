package main.java.plugin.sirlich.skills.active;

import main.java.plugin.sirlich.SkillScheme;
import main.java.plugin.sirlich.utilities.BlockUtils;
import main.java.plugin.sirlich.core.RpgPlayer;
import main.java.plugin.sirlich.utilities.c;
import main.java.plugin.sirlich.skills.meta.ActiveSkill;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ArcherTower extends ActiveSkill
{
    private static String id = "ArcherTower";
    private static List<Integer> cooldown = getYaml(id).getIntegerList("values.cooldown");
    private static List<Integer> towerLifeSpan = getYaml(id).getIntegerList("values.lifespan");

    public ArcherTower(RpgPlayer rpgPlayer, int level){
        super(rpgPlayer,level,cooldown.get(level));
        setId(id);
        setName("Archer Tower");
        clearDescription();
        setMaxLevel(3);
    }

    private boolean isAir(Location location){
        return location.getWorld().getBlockAt(location).getType().equals(Material.AIR);
    }

    @Override
    public ArrayList<String> getDescription(int level){
        ArrayList<String> lorelines = new ArrayList<String>();
        lorelines.add(c.dgray + "Retreat from the battlefield for a few moment");
        lorelines.add(c.dgray + "in this portable, temporary archer-tower");
        lorelines.add("");
        lorelines.add(c.dgray + "Press " + c.aqua + "F" + c.dgray + " to activate");
        lorelines.add("");
        if(level == 0 || level == getMaxLevel()){
            lorelines.add(c.dgray + "Cooldown: " + c.green + cooldown.get(level)/20 + c.dgray + " seconds");
            lorelines.add(c.dgray + "Lifespan: " + c.green + towerLifeSpan.get(level)/20 + c.dgray + " seconds");
        } else {
            lorelines.add(c.dgray + "Cooldown: " + c.yellow + cooldown.get(level)/20 + c.green + " (+" + (cooldown.get(level + 1)/20 - cooldown.get(level)/20) + ") " + c.dgray + " seconds");
            lorelines.add(c.dgray + "Lifespan: " + c.yellow + towerLifeSpan.get(level)/20 + c.green + " (+" + (towerLifeSpan.get(level + 1)/20 - towerLifeSpan.get(level)/20) + ") " + c.dgray + " seconds");
        }

        return lorelines;
    }

    @Override
    public void onSwap(PlayerSwapHandItemsEvent event){
        if(isCooldown()){return;}
        if(!getRpgPlayer().getPlayer().isOnGround()){
            getRpgPlayer().chat(ChatColor.RED + "You have to be on the ground to use that skill.");
            getRpgPlayer().playSound(Sound.BLOCK_ANVIL_FALL);
            return;
        }
        Player player = event.getPlayer();
        Location location = player.getLocation();

        Location base = location.clone();
        Location base_front = location.clone().add(new Vector(1,0,0));
        Location base_left = location.clone().add(new Vector(0,0,-1));
        Location base_back = location.clone().add(new Vector(-1,0,0));
        Location base_right = location.clone().add(new Vector(0,0,1));
        Location pole1 = location.clone().add(new Vector(0,1,0));
        Location pole2 = location.clone().add(new Vector(0,2,0));

        Location pole3 = location.clone().add(new Vector(0,3,0));

        Location flag_front = location.clone().add(new Vector(1,3,0));
        Location flag_left = location.clone().add(new Vector(0,3,-1));
        Location flag_back = location.clone().add(new Vector(-1,3,0));
        Location flag_right = location.clone().add(new Vector(0,3,1));

        Location pole4 = location.clone().add(new Vector(0,4,0));
        Location top = location.clone().add(new Vector(0,5,0));
        Location top_front = location.clone().add(new Vector(1,5,0));
        Location top_left = location.clone().add(new Vector(0,5,-1));
        Location top_back = location.clone().add(new Vector(-1,5,0));
        Location top_right = location.clone().add(new Vector(0,5,1));
        Location fence_front = location.clone().add(new Vector(1,6,0));
        Location fence_left = location.clone().add(new Vector(0,6,-1));
        Location fence_back = location.clone().add(new Vector(-1,6,0));
        Location fence_right = location.clone().add(new Vector(0,6,1));

        if(isAir(base) &&
                isAir(base) &&
                isAir(base_back) &&
                isAir(base_front) &&
                isAir(base_left) &&
                isAir(base_right) &&
                isAir(pole1) &&
                isAir(pole2) &&
                isAir(pole3) &&
                isAir(pole4) &&
                isAir(top) &&
                isAir(top_back) &&
                isAir(top_front) &&
                isAir(top_left) &&
                isAir(top_right) &&
                isAir(fence_front) &&
                isAir(fence_back) &&
                isAir(fence_left) &&
                isAir(fence_right)){
            player.teleport(top.clone().add(new Vector(0,1,0)));
            new BukkitRunnable() {

                @Override
                public void run() {
                    getRpgPlayer().playSound(Sound.BLOCK_WOOD_BREAK);
                }

            }.runTaskLater(SkillScheme.getInstance(), towerLifeSpan.get(getLevel()) - 40);

            BlockUtils.tempPlaceBlock(Material.LOG,base,towerLifeSpan.get(getLevel()));
            BlockUtils.tempPlaceBlock(Material.WOOD_STAIRS,base_front,towerLifeSpan.get(getLevel()), (byte) 0x1);
            BlockUtils.tempPlaceBlock(Material.WOOD_STAIRS,base_right,towerLifeSpan.get(getLevel()), (byte) 0x3);
            BlockUtils.tempPlaceBlock(Material.WOOD_STAIRS,base_left,towerLifeSpan.get(getLevel()), (byte) 0x2);
            BlockUtils.tempPlaceBlock(Material.WOOD_STAIRS,base_back,towerLifeSpan.get(getLevel()), (byte) 0x0);
            BlockUtils.tempPlaceBlock(Material.COBBLE_WALL,pole1,towerLifeSpan.get(getLevel()));
            BlockUtils.tempPlaceBlock(Material.COBBLE_WALL,pole2,towerLifeSpan.get(getLevel()));
            BlockUtils.tempPlaceBlock(Material.LOG,pole3,towerLifeSpan.get(getLevel()));


            BlockUtils.tempPlaceBlock(Material.WALL_BANNER,flag_front,towerLifeSpan.get(getLevel()) - 5, (byte) 0x5);
            BlockUtils.tempPlaceBlock(Material.WALL_BANNER,flag_left,towerLifeSpan.get(getLevel()) - 5, (byte) 0x6);
            BlockUtils.tempPlaceBlock(Material.WALL_BANNER,flag_back,towerLifeSpan.get(getLevel()) - 5, (byte) 0x4);
            BlockUtils.tempPlaceBlock(Material.WALL_BANNER,flag_right,towerLifeSpan.get(getLevel())- 5, (byte) 0x3);

            BlockUtils.tempPlaceBlock(Material.COBBLE_WALL,pole4,towerLifeSpan.get(getLevel()));

            BlockUtils.tempPlaceBlock(Material.WOOD_STAIRS,top_front,towerLifeSpan.get(getLevel()), (byte) 0x5);
            BlockUtils.tempPlaceBlock(Material.WOOD_STAIRS,top_left,towerLifeSpan.get(getLevel()), (byte) 0x6);
            BlockUtils.tempPlaceBlock(Material.WOOD_STAIRS,top_back,towerLifeSpan.get(getLevel()), (byte) 0x4);
            BlockUtils.tempPlaceBlock(Material.WOOD_STAIRS,top_right,towerLifeSpan.get(getLevel()), (byte) 0x7);
            BlockUtils.tempPlaceBlock(Material.LOG,top,towerLifeSpan.get(getLevel()));
            BlockUtils.tempPlaceBlock(Material.FENCE,fence_front,towerLifeSpan.get(getLevel()));
            BlockUtils.tempPlaceBlock(Material.FENCE,fence_left,towerLifeSpan.get(getLevel()));
            BlockUtils.tempPlaceBlock(Material.FENCE,fence_back,towerLifeSpan.get(getLevel()));
            BlockUtils.tempPlaceBlock(Material.FENCE,fence_right,towerLifeSpan.get(getLevel()));
            refreshCooldown();
        } else {
            getRpgPlayer().chat(ChatColor.RED + "Something appears to be in the way!");
            getRpgPlayer().playSound(Sound.BLOCK_ANVIL_FALL);
        }
    }
}
