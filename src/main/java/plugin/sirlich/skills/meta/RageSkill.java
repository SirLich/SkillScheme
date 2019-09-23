package plugin.sirlich.skills.meta;

import plugin.sirlich.SkillScheme;
import plugin.sirlich.core.RpgPlayer;
import plugin.sirlich.utilities.c;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class RageSkill extends CooldownSkill{

    //Defaults
    private Material headBlock;
    private boolean enraged;
    private boolean endRageEarly = false;
    private ItemStack oldHeadblock;

    private Sound currentlyEnragedSound = Sound.ANVIL_BREAK;
    private String currentlyEnragedText = c.red + getName() + c.dgray + "  is already active.";
    private Sound becomeEnragedSound = Sound.COW_HURT;
    private String becomeEnragedText = c.dgray + "You activate "+ c.green + this.getName();

    private Sound stoppedRagingSound = Sound.FIRE_IGNITE;
    private String stoppedRagingText = c.red + getName() + c.dgray + " has expired.";


    public RageSkill(RpgPlayer rpgPlayer, int level, String id, Material headBlock){
        super(rpgPlayer,level,id);
        this.headBlock = headBlock;
        this.enraged = false;
    }

    public void onEnrage(){

    }

    public void onRageExpire(){

    }

    public boolean attemptRage(){
        if(isSilenced()){return false;}
        if(enraged){
            getRpgPlayer().tell(currentlyEnragedText);
            getRpgPlayer().playSound(currentlyEnragedSound);
            return false;
        } else {
            if(isCooldown()){
                return false;
            } else {

                //Set rage
                enraged = true;
                onEnrage();

                //Play media
                getRpgPlayer().tell(becomeEnragedText);
                getRpgPlayer().playSound(becomeEnragedSound);

                //Set Helmet
                oldHeadblock = getRpgPlayer().getPlayer().getInventory().getHelmet();
                getRpgPlayer().getPlayer().getInventory().setHelmet(new ItemStack(headBlock));

                //Set enraged countdown
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if(endRageEarly){
                            endRageEarly = false;
                        } else {
                            endRage();
                        }
                    }

                }.runTaskLater(SkillScheme.getInstance(), data.getInt("duration"));
                return true;
            }
        }
    }

    public void endRageEarly(){
        if(isEnraged()){
            this.endRageEarly = true;
            endRage();
        }
    }

    private void endRage(){
        //Set Raging
        enraged = false;
        onRageExpire();

        //Set Helmet
        getRpgPlayer().getPlayer().getInventory().setHelmet(oldHeadblock);

        //Play media
        getRpgPlayer().tell(stoppedRagingText);
        getRpgPlayer().playSound(stoppedRagingSound);

        //Refresh cooldown:
        refreshCooldown();
    }

    public boolean isEnraged() {
        return enraged;
    }

    public void setHeadBlock(Material headBlock) {
        this.headBlock = headBlock;
    }

    public void setCurrentlyEnragedSound(Sound currentlyEnragedSound) {
        this.currentlyEnragedSound = currentlyEnragedSound;
    }

    public void setCurrentlyEnragedText(String currentlyEnragedText) {
        this.currentlyEnragedText = currentlyEnragedText;
    }

    public void setBecomeEnragedSound(Sound becomeEnragedSound) {
        this.becomeEnragedSound = becomeEnragedSound;
    }

    public void setBecomeEnragedText(String becomeEnragedText) {
        this.becomeEnragedText = becomeEnragedText;
    }

    public void setStoppedRagingSound(Sound stoppedRagingSound) {
        this.stoppedRagingSound = stoppedRagingSound;
    }

    public void setStoppedRagingText(String stoppedRagingText) {
        this.stoppedRagingText = stoppedRagingText;
    }
}
