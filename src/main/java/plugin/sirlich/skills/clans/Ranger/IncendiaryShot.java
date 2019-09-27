package plugin.sirlich.skills.clans.Ranger;

import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import plugin.sirlich.core.RpgPlayer;
import plugin.sirlich.skills.meta.PrimedSkill;

public class IncendiaryShot extends PrimedSkill {
    public IncendiaryShot(RpgPlayer rpgPlayer, int level){
        super(rpgPlayer,level,"IncendiaryShot");
    }

    @Override
    public void onBowLeftClick(PlayerInteractEvent event){
        attemptPrime();
    }

    @Override
    public void onBowFire(EntityShootBowEvent event){
        if(isSilenced()){return;};
        if(primed){
            primed = false;
            event.getProjectile().setFireTicks(data.getInt("burn_duration"));
            refreshCooldown();
        }
    }
}