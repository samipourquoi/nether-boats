package io.github.samipourquoi.netherboats.mixin;

import io.github.samipourquoi.netherboats.methods.EveryBoatType;
import io.github.samipourquoi.netherboats.methods.MoreBoatType;
import io.github.samipourquoi.netherboats.NetherBoats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BoatEntity.class)
public abstract class MixinBoatEntity extends Entity implements MoreBoatType {
	@Shadow @Final private static TrackedData<Integer> BOAT_TYPE;

	@Shadow @Final private static TrackedData<Integer> DAMAGE_WOBBLE_TICKS;

	@Shadow @Final private static TrackedData<Integer> DAMAGE_WOBBLE_SIDE;

	@Shadow @Final private static TrackedData<Float> DAMAGE_WOBBLE_STRENGTH;

	@Shadow @Final private static TrackedData<Boolean> LEFT_PADDLE_MOVING;

	@Shadow @Final private static TrackedData<Boolean> RIGHT_PADDLE_MOVING;

	@Shadow @Final private static TrackedData<Integer> BUBBLE_WOBBLE_TICKS;

	@Shadow public abstract BoatEntity.Type getBoatType();

	public MixinBoatEntity(EntityType<?> type, World world) {
		super(type, world);
	}

	/** @author samipourquoi */
	@Overwrite
	public void initDataTracker() {
		this.dataTracker.startTracking(DAMAGE_WOBBLE_TICKS, 0);
		this.dataTracker.startTracking(DAMAGE_WOBBLE_SIDE, 1);
		this.dataTracker.startTracking(DAMAGE_WOBBLE_STRENGTH, 0.0F);
		this.dataTracker.startTracking(BOAT_TYPE, EveryBoatType.OAK.ordinal());
		this.dataTracker.startTracking(LEFT_PADDLE_MOVING, false);
		this.dataTracker.startTracking(RIGHT_PADDLE_MOVING, false);
		this.dataTracker.startTracking(BUBBLE_WOBBLE_TICKS, 0);
	}

	@Override
	public void setMoreBoatType(EveryBoatType type) {
		this.dataTracker.set(BOAT_TYPE, type.ordinal());
	}

	/** @author samipourquoi */
    @Overwrite
    public Item asItem() {
        switch(((MoreBoatType) this).getMoreBoatType()) {
            case OAK:
            default:
                return Items.OAK_BOAT;
            case SPRUCE:
                return Items.SPRUCE_BOAT;
            case BIRCH:
                return Items.BIRCH_BOAT;
            case JUNGLE:
                return Items.JUNGLE_BOAT;
            case ACACIA:
                return Items.ACACIA_BOAT;
            case DARK_OAK:
                return Items.DARK_OAK_BOAT;
            case WARPED:
                return NetherBoats.WARPED_BOAT;
            case CRIMSON:
                return NetherBoats.CRIMSON_BOAT;
        }
    }

    /** @author samipourquoi */
    @Overwrite
	public void readCustomDataFromTag(CompoundTag tag) {
		if (tag.contains("Type", 8)) {
			((MoreBoatType) this).setMoreBoatType(EveryBoatType.getType(tag.getString("Type")));
		}
	}

	/** @author samipourquoi */
	@Override
	public EveryBoatType getMoreBoatType() {
		return EveryBoatType.getType((Integer) this.dataTracker.get(BOAT_TYPE));
	}
}
