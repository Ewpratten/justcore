package ca.retrylife.mc.justcore.mixins;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ca.retrylife.mc.justcore.render.capes.CustomCapeFeatureRenderer;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin
        extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public PlayerEntityRendererMixin(EntityRenderDispatcher dispatcher,
            PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(dispatcher, model, shadowRadius);
    }

    @Inject(method = { "<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Z)V" }, at = @At("RETURN"))
    private void ConstructorMixinPlayerEntityRenderer(EntityRenderDispatcher dispatcher, boolean bl,
            CallbackInfo info) {

        // Since we are overriding all cape rendering with a custom renderer, we can
        // remove the Mojang cape renderer.
        this.features.removeIf(renderer -> renderer instanceof CapeFeatureRenderer);

        // Construct a custom cape renderer
        CustomCapeFeatureRenderer capeRenderer = new CustomCapeFeatureRenderer(this);

        // Add the new cape renderer as a feature
        this.addFeature(capeRenderer);
    }

}