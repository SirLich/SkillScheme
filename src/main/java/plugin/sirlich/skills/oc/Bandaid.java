package plugin.sirlich.skills.oc;

import plugin.sirlich.core.RpgPlayer;
import plugin.sirlich.skills.meta.ChargeSkill;

public class Bandaid extends ChargeSkill {
    public Bandaid(RpgPlayer rpgPlayer, int level){
        super(rpgPlayer, level, "Bandaid", true, true);
    }

    @Override
    public boolean isCharging(){
        return getRpgPlayer().getPlayer().isOnGround() && getRpgPlayer().getPlayer().isBlocking();
    }

    @Override
    public void onCharge(){
        getRpgPlayer().addHealth(data.getDouble("health_per_charge"));
    }
}
