package io.github.samipourquoi.netherboats.mixin;

import io.github.samipourquoi.netherboats.EveryBoatType;
import io.github.samipourquoi.netherboats.methods.MoreBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MixinEntity {
	@Shadow private boolean invulnerable;
	private Entity dis;

	@Inject(method = "<init>", at = @At("HEAD"))
	public void Entity(EntityType<?> type, World world, CallbackInfo ci) {
		this.dis = ((Entity) (Object) this);
	}

	/** @author samipourquoi */
	@Overwrite
	public boolean isInvulnerableTo(DamageSource damageSource) {
		boolean epicBoat = false;
		if (this.dis instanceof BoatEntity) {
			EveryBoatType type = ((MoreBoatEntity) this).getMoreBoatType();
			if ((type == EveryBoatType.WARPED || type == EveryBoatType.CRIMSON) && damageSource.isFire()) {
				epicBoat = true;
			}
		}
		return this.invulnerable && damageSource != DamageSource.OUT_OF_WORLD && !damageSource.isSourceCreativePlayer();
	}
}
