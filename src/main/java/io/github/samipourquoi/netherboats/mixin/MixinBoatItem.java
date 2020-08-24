package io.github.samipourquoi.netherboats.mixin;

import io.github.samipourquoi.netherboats.methods.EveryBoatType;
import io.github.samipourquoi.netherboats.methods.MoreBoatItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatItem.class)
public abstract class MixinBoatItem extends Item implements MoreBoatItem {
	@Shadow @Final private BoatEntity.Type type;
	private EveryBoatType moreType;

	public MixinBoatItem(Settings settings) {
		super(settings);
	}

	@Override
	public void setMoreType(EveryBoatType type) {
		this.moreType = type;
	}

	@Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/BoatEntity;setBoatType(Lnet/minecraft/entity/vehicle/BoatEntity$Type;)V"))
	public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		System.out.println(this.moreType);
		System.out.println(this.type);
	}
}
