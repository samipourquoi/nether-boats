package io.github.samipourquoi.netherboats.mixin;

import io.github.samipourquoi.netherboats.methods.MoreBoatEntity;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;

@Mixin(BoatEntityRenderer.class)
public class MixinBoatEntityRenderer {
	@Mutable
	@Shadow @Final private static Identifier[] TEXTURES;

	static {
		TEXTURES = new Identifier[]{
				new Identifier("textures/entity/boat/oak.png"),
				new Identifier("textures/entity/boat/spruce.png"),
				new Identifier("textures/entity/boat/birch.png"),
				new Identifier("textures/entity/boat/jungle.png"),
				new Identifier("textures/entity/boat/acacia.png"),
				new Identifier("textures/entity/boat/dark_oak.png"),
				new Identifier("netherboats:textures/entity/boat/crimson.png"),
				new Identifier("netherboats:textures/entity/boat/warped.png")
		};
	}

	/** @author samipourquoi */
	@Overwrite
	public Identifier getTexture(BoatEntity boatEntity) {
		return TEXTURES[((MoreBoatEntity) boatEntity).getMoreBoatType().ordinal()];
	}
}
