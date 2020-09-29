package ca.retrylife.mc.justcore.render.capes;

import ca.retrylife.mc.justcore.model.CustomAbstractClientPlayerEntity;
import ca.retrylife.mc.justcore.model.JustPlayer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;

/**
 * Feature renderer for a custom player cape
 */
public class CustomCapeFeatureRenderer extends CapeFeatureRenderer {

    public CustomCapeFeatureRenderer(
            FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
            AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta,
            float animationProgress, float headYaw, float headPitch) {

        // Get the player we are rendering
        JustPlayer player = new JustPlayer(entity.getUuid());

        // Build a new CustomAbstractClientPlayerEntity to modify
        CustomAbstractClientPlayerEntity newEntity = new CustomAbstractClientPlayerEntity(entity.clientWorld,
                entity.getGameProfile());

        // Check if a custom cape exists for this player
        if (CustomPlayerCapeRegistry.getInstance().checkPlayerHasCustomCape(player)
                && CustomPlayerCapeRegistry.getInstance().checkCustomCapeVisibleForPlayer(player)) {
            // Override the entity cape
            newEntity.setCustomCapeTexture(CustomPlayerCapeRegistry.getInstance().getCapeForPlayer(player));
        }

        // Call the Mojang cape renderer
        super.render(matrices, vertexConsumers, light, newEntity, limbAngle, limbDistance, tickDelta, animationProgress,
                headYaw, headPitch);
    }

}