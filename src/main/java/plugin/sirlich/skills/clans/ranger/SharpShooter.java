package plugin.sirlich.skills.clans.ranger;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import plugin.sirlich.core.RpgPlayer;
import plugin.sirlich.core.RpgProjectile;
import plugin.sirlich.skills.meta.TickingSkill;
import plugin.sirlich.utilities.Color;

public class SharpShooter extends TickingSkill {
    /*
    Config values:
    bonus_damage_per_charge: double
    max_charges: int
    base_millis: int
    per_level_millis: int

    xliff:

    sounds:
    on_miss
    on_hit
     */

    private int charges = 0;
    private long lastHit;

    public SharpShooter(RpgPlayer rpgPlayer, int level){
        super(rpgPlayer, level, "SharpShooter");
    }

    public void handleArrowHit(){
        if(charges < data.getInt("max_charges")){
            charges = charges + 1;
            getRpgPlayer().tell(Color.green + getName() + Color.dgray + " charges: " + Color.green + charges);
        }
        getRpgPlayer().getPlayer().playSound(getRpgPlayer().getPlayer().getLocation(), data.getSound("on_hit"),1.0f,2.0f * charges/data.getInt("max_charges"));
    }

    public void resetCharges(){
        if(charges != 0){
            getRpgPlayer().playSound(data.getSound("on_miss"));
            getRpgPlayer().tell(Color.red + getName() + Color.dgray + " charges has been reset.");
        }
        charges = 0;
    }

    @Override
    public void onBowFire(EntityShootBowEvent event){
        if(isSilenced()){return;};
        Arrow arrow = (Arrow) event.getProjectile();
        RpgProjectile.addTag(arrow.getUniqueId(),"SHARP_SHOOTER");
    }

    @Override
    public void onArrowHitEntity(EntityDamageByEntityEvent event){
        lastHit = System.currentTimeMillis();
        Projectile projectile = (Projectile) event.getDamager();
        RpgProjectile rpgProjectile = RpgProjectile.getProjectile(projectile.getUniqueId());
        if(rpgProjectile.hasTag("SHARP_SHOOTER")){
            handleArrowHit();
            event.setDamage(event.getDamage() + charges * data.getDouble("bonus_damage_per_charge"));
        }
    }

    @Override
    public void onTick(){
        if (System.currentTimeMillis() >= lastHit + (data.getInt("base_millis") + data.getInt("per_level_millis"))) {
            resetCharges();
        }
    }
}
