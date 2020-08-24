package io.github.samipourquoi.netherboats;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.vehicle.BoatEntity;

/**
 * Enum of boat block types.<br>
 * We can't inject new properties in an enum with Mixin,
 * so we must create a new enum.
 *
 * @author samipourquoi
 * @see BoatEntity.Type
 */
public enum EveryBoatType {
    OAK(Blocks.OAK_PLANKS, "oak"),
    SPRUCE(Blocks.SPRUCE_PLANKS, "spruce"),
    BIRCH(Blocks.BIRCH_PLANKS, "birch"),
    JUNGLE(Blocks.JUNGLE_PLANKS, "jungle"),
    ACACIA(Blocks.ACACIA_PLANKS, "acacia"),
    DARK_OAK(Blocks.DARK_OAK_PLANKS, "dark_oak"),
    CRIMSON(Blocks.CRIMSON_PLANKS, "crimson"),
    WARPED(Blocks.WARPED_PLANKS, "warped");

    private final String name;
    private final Block baseBlock;

    private EveryBoatType(Block baseBlock, String name) {
        this.name = name;
        this.baseBlock = baseBlock;
    }

    public String getName() {
        return this.name;
    }

    public Block getBaseBlock() {
        return this.baseBlock;
    }

    public String toString() {
        return this.name;
    }

    public static EveryBoatType getType(int i) {
        EveryBoatType[] types = values();
        if (i < 0 || i >= types.length) {
            i = 0;
        }

        return types[i];
    }

    public static EveryBoatType getType(String string) {
        EveryBoatType[] types = values();

        for(int i = 0; i < types.length; ++i) {
            if (types[i].getName().equals(string)) {
                return types[i];
            }
        }

        return types[0];
    }
}
