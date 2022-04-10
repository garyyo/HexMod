package at.petrak.hexcasting.datagen;

import at.petrak.hexcasting.datagen.lootmods.HexLootModifiers;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class HexDataGenerators {
    @SubscribeEvent
    public static void generateData(GatherDataEvent ev) {
        DataGenerator gen = ev.getGenerator();
        ExistingFileHelper efh = ev.getExistingFileHelper();
        if (ev.includeClient()) {
            gen.addProvider(new HexItemModels(gen, efh));
            gen.addProvider(new HexBlockStatesAndModels(gen, efh));
        }
        if (ev.includeServer()) {
            HexBlockTagProvider blockTagProvider = new HexBlockTagProvider(gen, efh);
            gen.addProvider(new HexRecipes(gen));
            gen.addProvider(new HexLootModifiers(gen));
            gen.addProvider(new HexAdvancements(gen, efh));
            gen.addProvider(blockTagProvider);
            gen.addProvider(new HexItemTagProvider(gen, blockTagProvider, efh));
            gen.addProvider(new HexLootTables(gen));
        }
    }
}