package ca.retrylife.mc.justcore.render.cosmetics;

import ca.retrylife.mc.justcore.render.cosmetics.templates.GlowingEffect;
import ca.retrylife.mc.justcore.render.cosmetics.templates.InvisibleEffect;

/**
 * Cosmetic effect tracking for a player
 */
public class CosmeticEffect {

    // If the player is glowing
    public boolean isGlowing;

    // If the player is invisible
    public boolean isInvisible;

    /**
     * Adds the effects of another CosmeticEffect to this one
     * 
     * @param other Other effects
     */
    public void apply(CosmeticEffect other) {
        this.isGlowing = (other.isGlowing) ? true : this.isGlowing;
        this.isInvisible = (other.isInvisible) ? true : this.isInvisible;
    }

    /**
     * Removes any CosmeticEffect effects that are enabled in both this and the
     * other object
     * 
     * @param other Other effects
     */
    public void remove(CosmeticEffect other) {
        this.isGlowing = (other.isGlowing) ? false : this.isGlowing;
        this.isInvisible = (other.isInvisible) ? false : this.isInvisible;
    }

    /**
     * Get if any effects are present
     * 
     * @return Has any effect
     */
    public boolean hasAnyEffect() {
        return this.isGlowing || this.isInvisible;
    }

    /**
     * Convert from an effect name to the actual effect
     * 
     * @param name Effect name
     * @return Effect
     */
    public static CosmeticEffect stringNameToEffectObject(String name) {
        switch (name) {
            case "glowing":
                return new GlowingEffect();
            case "invisible":
                return new InvisibleEffect();
            default:
                return new CosmeticEffect();
        }
    }
}