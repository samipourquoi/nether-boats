package io.github.samipourquoi.netherboats.mixin;

import io.github.samipourquoi.netherboats.EveryBoatType;
import io.github.samipourquoi.netherboats.methods.MoreBoatItem;
import io.github.samipourquoi.netherboats.methods.MoreBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

@Mixin(BoatItem.class)
public abstract class MixinBoatItem extends Item implements MoreBoatItem {
	@Shadow @Final private BoatEntity.Type type;
	@Shadow @Final private static Predicate<Entity> RIDERS;
	private EveryBoatType moreType;

	public MixinBoatItem(Settings settings) {
		super(settings);
	}

	@Override
	public void setMoreType(EveryBoatType type) {
		this.moreType = type;
	}

	/** @author samipourquoi */
	@Overwrite
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		HitResult hitResult = rayTrace(world, user, RayTraceContext.FluidHandling.ANY);
		if (hitResult.getType() == HitResult.Type.MISS) {
			return TypedActionResult.pass(itemStack);
		} else {
			Vec3d vec3d = user.getRotationVec(1.0F);
			double d = 5.0D;
			List<Entity> list = world.getOtherEntities(user, user.getBoundingBox().stretch(vec3d.multiply(5.0D)).expand(1.0D), RIDERS);
			if (!list.isEmpty()) {
				Vec3d vec3d2 = user.getCameraPosVec(1.0F);
				Iterator var11 = list.iterator();

				while(var11.hasNext()) {
					Entity entity = (Entity)var11.next();
					Box box = entity.getBoundingBox().expand((double)entity.getTargetingMargin());
					if (box.contains(vec3d2)) {
						return TypedActionResult.pass(itemStack);
					}
				}
			}

			if (hitResult.getType() == HitResult.Type.BLOCK) {
				BoatEntity boatEntity = new BoatEntity(world, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z);
				boatEntity.setBoatType(this.type);
				((MoreBoatEntity) boatEntity).setMoreBoatType(this.moreType);
				boatEntity.yaw = user.yaw;
				if (!world.doesNotCollide(boatEntity, boatEntity.getBoundingBox().expand(-0.1D))) {
					return TypedActionResult.fail(itemStack);
				} else {
					if (!world.isClient) {
						world.spawnEntity(boatEntity);
						if (!user.abilities.creativeMode) {
							itemStack.decrement(1);
						}
					}

					user.incrementStat(Stats.USED.getOrCreateStat(this));
					return TypedActionResult.method_29237(itemStack, world.isClient());
				}
			} else {
				return TypedActionResult.pass(itemStack);
			}
		}
	}

	/*
	Couldn't get this shit to work with mixins

	@Inject(
		method = "use",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/vehicle/BoatEntity;setBoatType(Lnet/minecraft/entity/vehicle/BoatEntity$Type;)V"
		),
		locals = LocalCapture.PRINT
	)
	public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		System.out.println(this.moreType);
	}
	 */
}
