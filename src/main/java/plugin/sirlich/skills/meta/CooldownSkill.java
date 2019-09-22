package plugin.sirlich.skills.meta;

import plugin.sirlich.SkillScheme;
import plugin.sirlich.core.RpgPlayer;
import plugin.sirlich.utilities.c;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class CooldownSkill extends Skill
{
    //List of all cooldown values. This is used for Inventory Displays.
    private List<Integer> cooldownValues;

    //Current cooldown by level
    private int cooldownValue;

    private boolean cooldown;
    private long lastUsed;

    private Sound cooldownSound = Sound.CLICK;
    private Sound rechargeSound = Sound.CHEST_CLOSE;
    private String cooldownText = c.red + getName() + c.dgray + " can be used again in ";
    private String rechargeText = c.green + getName() + c.dgray + " has been recharged.";


    private double calculateCooldownLeft(){
        return Math.round((((cooldownValue / 20.00) - (System.currentTimeMillis() - lastUsed) / 1000.00) * 100.00) / 100.00);
    }

    public CooldownSkill(RpgPlayer rpgPlayer, int level, String id){
        super(rpgPlayer,level,id);
        this.cooldownValues = getYaml(id).getIntegerList("values.cooldown");
        this.cooldownValue = cooldownValues.get(getLevel());
        this.cooldown = false;
    }

    public boolean isCooldown(){
        if(cooldown) {
            playCooldownMedia();
        }
        return cooldown;
    }

    public void playCooldownMedia(){
        getRpgPlayer().playSound(cooldownSound);
        getRpgPlayer().tell(cooldownText + c.green + calculateCooldownLeft() + c.dgray + " seconds");
    }

    public void playRechargeMedia(){
        getRpgPlayer().playSound(rechargeSound);
        getRpgPlayer().tell(rechargeText);
    }

    public void setCooldown(boolean state){
        this.cooldown = state;
    }
    public void refreshCooldown(){
        this.cooldown = true;
        this.lastUsed = System.currentTimeMillis();
        new BukkitRunnable() {

            @Override
            public void run() {
                cooldown = false;
                playRechargeMedia();
            }

        }.runTaskLater(SkillScheme.getInstance(), cooldownValue);
    }

    public Sound getCooldownSound()
    {
        return cooldownSound;
    }

    public void setCooldownSound(Sound cooldownSound)
    {
        this.cooldownSound = cooldownSound;
    }


    public Sound getRechargeSound()
    {
        return rechargeSound;
    }

    public void setRechargeSound(Sound rechargeSound)
    {
        this.rechargeSound = rechargeSound;
    }

    public int getCooldown()
    {
        return cooldownValue;
    }

    public int getCooldown(int level){
        return cooldownValues.get(level);
    }

    public String getCooldownText() {
        return cooldownText;
    }

    public void setCooldownText(String cooldownText) {
        this.cooldownText = cooldownText;
    }

    public String getRechargeText() {
        return rechargeText;
    }

    public void setRechargeText(String rechargeText) {
        this.rechargeText = rechargeText;
    }
}
