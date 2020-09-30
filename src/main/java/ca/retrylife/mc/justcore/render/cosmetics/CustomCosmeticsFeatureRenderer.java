package ca.retrylife.mc.justcore.render.cosmetics;

import java.util.List;

import ca.retrylife.mc.justcore.model.JustPlayer;
import ca.retrylife.mc.justcore.render.PlayerEntityFeatureRenderStep;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class CustomCosmeticsFeatureRenderer extends PlayerEntityFeatureRenderStep {

    public CustomCosmeticsFeatureRenderer(
            FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context,
            List<FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>> appliedFeatures) {
        super(context, appliedFeatures);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
            AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta,
            float animationProgress, float headYaw, float headPitch) {

        // Get the player we are rendering
        JustPlayer player = new JustPlayer(entity.getGameProfile().getId(), entity.getGameProfile().getName());

        // If this player has no cosmetic effects, skip this render step
        if (!CustomPlayerCosmeticsRegistry.getInstance().checkIfPlayerHasAnyEffects(player)) {
            return;
        }

        // Get the player effects
        CosmeticEffect effects = CustomPlayerCosmeticsRegistry.getInstance().getEffectsAppliedToPlayer(player);

        
    }

}