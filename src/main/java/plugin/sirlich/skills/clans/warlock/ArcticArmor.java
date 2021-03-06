package plugin.sirlich.skills.clans.warlock;

import org.bukkit.event.player.PlayerDropItemEvent;
import plugin.sirlich.core.RpgPlayer;
import plugin.sirlich.skills.meta.ManaSkill;
import plugin.sirlich.utilities.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class ArcticArmor extends ManaSkill
{
    private static String id = "ArcticArmor";

    public ArcticArmor(RpgPlayer rpgPlayer, int level){
        super(rpgPlayer,level,"ArcticArmor");
    }

    @Override
    public void onTick(){
        for(Block block : BlockUtils.getNearbyBlocks(getRpgPlayer().getPlayer().getLocation(), data.getInt("radius"))){
            if(block.getType() != Material.AIR && block.getType() != Material.ICE && block.getLightLevel() >= 0){
                BlockUtils.tempPlaceBlock(Material.ICE,block.getLocation(),data.getInt("ice_duration"));
            }
        }
    }

    @Override
    public void onSwordDrop(PlayerDropItemEvent entityEvent){
        toggleStatus();
    }
}
