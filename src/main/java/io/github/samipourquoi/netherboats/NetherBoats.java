package io.github.samipourquoi.netherboats;

import io.github.samipourquoi.netherboats.methods.EveryBoatType;
import io.github.samipourquoi.netherboats.methods.MoreBoatItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class NetherBoats implements ModInitializer {
    public static Item WARPED_BOAT = new BoatItem(BoatEntity.Type.OAK, (new Item.Settings()).maxCount(1).group(ItemGroup.TRANSPORTATION));
    public static Item CRIMSON_BOAT = new BoatItem(BoatEntity.Type.OAK, (new Item.Settings()).maxCount(1).group(ItemGroup.TRANSPORTATION));

    static {
        ((MoreBoatItem) Items.OAK_BOAT).setMoreType(EveryBoatType.OAK);
        ((MoreBoatItem) Items.SPRUCE_BOAT).setMoreType(EveryBoatType.SPRUCE);
        ((MoreBoatItem) Items.BIRCH_BOAT).setMoreType(EveryBoatType.BIRCH);
        ((MoreBoatItem) Items.JUNGLE_BOAT).setMoreType(EveryBoatType.JUNGLE);
        ((MoreBoatItem) Items.ACACIA_BOAT).setMoreType(EveryBoatType.ACACIA);
        ((MoreBoatItem) Items.DARK_OAK_BOAT).setMoreType(EveryBoatType.DARK_OAK);
        ((MoreBoatItem) WARPED_BOAT).setMoreType(EveryBoatType.WARPED);
        ((MoreBoatItem) CRIMSON_BOAT).setMoreType(EveryBoatType.CRIMSON);
    }

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("netherboats", "warped_boat"), WARPED_BOAT);
        Registry.register(Registry.ITEM, new Identifier("netherboats", "crimson_boat"), CRIMSON_BOAT);
    }
}
