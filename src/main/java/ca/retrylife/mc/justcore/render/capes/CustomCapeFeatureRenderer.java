package ca.retrylife.mc.justcore.render.capes;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.retrylife.mc.justcore.model.CustomAbstractClientPlayerEntity;
import ca.retrylife.mc.justcore.model.JustPlayer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

/**
 * Feature renderer for a custom player cape
 */
public class CustomCapeFeatureRenderer extends CapeFeatureRenderer {
    private Logger logger = LogManager.getLogger(getClass());

    private static final boolean GLOBAL_GLINT = false;

    public CustomCapeFeatureRenderer(
            FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
            AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta,
            float animationProgress, float headYaw, float headPitch) {

        // Get the player we are rendering
        JustPlayer player = new JustPlayer(entity.getGameProfile().getId(), entity.getGameProfile().getName());

        // Rename the entity (for back-compat)
        AbstractClientPlayerEntity newEntity = entity;

        // Get the player cape texture
        Identifier capeIdentifier = newEntity.getCapeTexture();

        // Check if a custom cape exists for this player
        if (CustomPlayerCapeRegistry.getInstance().checkPlayerHasCustomCape(player)
                && CustomPlayerCapeRegistry.getInstance().checkCustomCapeVisibleForPlayer(player)) {
            // Override the entity cape
            capeIdentifier = CustomPlayerCapeRegistry.getInstance().getCapeForPlayer(player).getTexture();
        }

        // Ensure the cape should be rendered right now
        if (newEntity.canRenderCapeTexture() && !newEntity.isInvisible()
                && newEntity.isPartVisible(PlayerModelPart.CAPE) && capeIdentifier != null) {

            // Get the item in the player's internal chest slot
            ItemStack playerChestItem = newEntity.getEquippedStack(EquipmentSlot.CHEST);

            // Skip cape rendering if wearing an elytra
            if (playerChestItem.getItem().equals(Items.ELYTRA)) {
                return;
            }

            // Do render logic
            matrices.push();
            matrices.translate(0.0, 0.0, 0.125);
            double d = MathHelper.lerp(tickDelta, newEntity.prevCapeX, newEntity.capeX)
                    - MathHelper.lerp(tickDelta, newEntity.prevX, newEntity.getX());
            double e = MathHelper.lerp(tickDelta, newEntity.prevCapeY, newEntity.capeY)
                    - MathHelper.lerp(tickDelta, newEntity.prevY, newEntity.getY());
            double m = MathHelper.lerp(tickDelta, newEntity.prevCapeZ, newEntity.capeZ)
                    - MathHelper.lerp(tickDelta, newEntity.prevZ, newEntity.getZ());
            float n = newEntity.prevBodyYaw + (newEntity.bodyYaw - newEntity.prevBodyYaw);
            double o = MathHelper.sin(n * 0.017453292f);
            double p = (-MathHelper.cos(n * 0.017453292f));
            float q = (float) e * 10.0f;
            q = MathHelper.clamp(q, -6.0f, 32.0f);
            float r = (float) (d * o + m * p) * 100.0f;
            r = MathHelper.clamp(r, 0.0f, 150.0f);
            float s = (float) (d * p - m * o) * 100.0f;
            s = MathHelper.clamp(s, -20.0f, 20.0f);
            if (r < 0.0f) {
                r = 0.0f;
            }
            double t = MathHelper.lerp(tickDelta, entity.prevStrideDistance, entity.strideDistance);
            q += MathHelper.sin((MathHelper.lerp(tickDelta, entity.prevHorizontalSpeed, entity.horizontalSpeed) * 6.0f))
                    * 32.0f * t;
            if (entity.isInSneakingPose()) {
                q += 25.0f;
            }
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(6.0f + r / 2.0f + q));
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(s / 2.0f));
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0f - s / 2.0f));
            VertexConsumer vertexConsumer = ItemRenderer.getArmorVertexConsumer(vertexConsumers,
                    RenderLayer.getArmorCutoutNoCull(capeIdentifier), false, GLOBAL_GLINT);
            this.getContextModel().renderCape(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
            matrices.pop();
        }
    }

}