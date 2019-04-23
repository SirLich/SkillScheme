package main.java.plugin.sirlich.skills.active;

import main.java.plugin.sirlich.core.RpgPlayer;
import main.java.plugin.sirlich.skills.meta.PrimedSkill;
import main.java.plugin.sirlich.utilities.c;
import org.bukkit.Sound;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Random;

public class BladeOfConfusion extends PrimedSkill {
    public BladeOfConfusion(RpgPlayer rpgPlayer, int level){
        super(rpgPlayer, level,"BladeOfConfusion");
    }

    @Override
    public ArrayList<String> getDescription(int level){
        ArrayList<String> lorelines = new ArrayList<String>();
        lorelines.add(c.dgray + "Blade of confusion sets pitch/yaw random");
        return lorelines;
    }

    @Override
    public void onSwordRightClick(PlayerInteractEvent event){
        attemptPrime();
    }

    @Override
    public void onSwordMeleeAttackOther(EntityDamageByEntityEvent event){
        if(primed){
            primed = false;
            getRpgPlayer().playSound(Sound.BLOCK_IRON_TRAPDOOR_CLOSE);
            Random rand = new Random();

            //Sort of magic-numbery. Set the random from -180 to 180, which is the max for yaw
            event.getEntity().getLocation().setYaw(-180 + rand.nextFloat() * (360));

            //Sort of magic-numbery. Set the random from -90 to 90, which is the max for pitch
            event.getEntity().getLocation().setPitch(-90 + rand.nextFloat() * (180));
            refreshCooldown();
        }
    }
}

