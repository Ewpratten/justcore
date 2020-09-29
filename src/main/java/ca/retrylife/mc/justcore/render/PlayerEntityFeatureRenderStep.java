package ca.retrylife.mc.justcore.render;

import java.util.List;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

/**
 * PlayerEntityFeatureRenderStep represents one rendering step in the chain of
 * steps needed to render a player on screen.
 */
public abstract class PlayerEntityFeatureRenderStep
        extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    private List<FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>> appliedFeatures;

    /**
     * Create a PlayerEntityFeatureRenderStep
     * 
     * @param context         Rendering context
     * @param appliedFeatures List of applied features in the rendering chain
     */
    public PlayerEntityFeatureRenderStep(
            FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context,
            List<FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>> appliedFeatures) {
        super(context);

        // Save the list of applied features
        this.appliedFeatures = appliedFeatures;
    }

    /**
     * Get the list of applied features in the rendering chain
     * 
     * @return List of features
     */
    public List<FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>> getAllRenderStepsForPlayer() {
        return this.appliedFeatures;
    }

}