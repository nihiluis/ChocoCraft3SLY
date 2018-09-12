package net.slayer5934.chococraft.common.init;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.slayer5934.chococraft.Chococraft;
import net.slayer5934.chococraft.common.items.ItemAbilityFruit;
import net.slayer5934.chococraft.common.items.ItemChocoboSaddle;
import net.slayer5934.chococraft.common.items.ItemChocoboSpawnEgg;
import net.slayer5934.chococraft.common.items.ItemChocopedia;
import net.slayer5934.chococraft.common.items.ItemGysahlGreenSeeds;
import net.slayer5934.chococraft.utils.inject.ClassInjector;
import net.slayer5934.chococraft.utils.inject.InstanceFactoryMethod;
import net.slayer5934.chococraft.utils.registration.IItemModelProvider;

@SuppressWarnings("unused")
@GameRegistry.ObjectHolder(Chococraft.MODID)
@Mod.EventBusSubscriber(modid = Chococraft.MODID)
public class ModItems
{
	@GameRegistry.ObjectHolder("chocobo_saddle")
    @ItemSetupParameters(stackSize = 4)
    public static ItemChocoboSaddle chocoboSaddle;

	@GameRegistry.ObjectHolder("chocobo_spawn_egg")
	public static ItemChocoboSpawnEgg chocoboSpawnEgg;

	@GameRegistry.ObjectHolder("ability_fruit")
	public static ItemAbilityFruit abilityFruit;

    @GameRegistry.ObjectHolder("gysahl_green_seeds")
    public static ItemGysahlGreenSeeds gysahlGreenSeeds;

    @GameRegistry.ObjectHolder("gysahl_green")
    @ItemFoodParameters(amount = 1, saturation = 1)
    public static ItemFood gysahlGreen;
    
    @GameRegistry.ObjectHolder("chocobo_whistle")
    public static Item chocoboWhistle;
    
    @GameRegistry.ObjectHolder("chocobo_feather")
    public static Item chocoboFeather;

    @GameRegistry.ObjectHolder("lovely_gysahl_green")
    public static Item lovelyGysahlGreen;

	@GameRegistry.ObjectHolder("chocobo_drumstick_raw")
    @ItemFoodParameters(amount = 2, saturation = 1, isWolfFood = true)
	public static ItemFood chocoboDrumStickRaw;

	@GameRegistry.ObjectHolder("chocobo_drumstick_cooked")
    @ItemFoodParameters(amount = 4, saturation = 1, isWolfFood = true)
	public static ItemFood chocoboDrumStickCooked;

	@GameRegistry.ObjectHolder("pickled_gysahl_raw")
    @ItemFoodParameters(amount = 1, saturation = 1)
	public static ItemFood pickledGysahlRaw;

	@GameRegistry.ObjectHolder("pickled_gysahl_cooked")
    @ItemFoodParameters(amount = 3, saturation = 1)
	public static ItemFood pickledGysahlCooked;

	@GameRegistry.ObjectHolder("chocopedia")
	public static ItemChocopedia chocopedia;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		for(Field field : ModItems.class.getDeclaredFields())
		{
			if(!Item.class.isAssignableFrom(field.getType())) continue;

			GameRegistry.ObjectHolder objHolder = field.getAnnotation(GameRegistry.ObjectHolder.class);
			if(objHolder == null) continue;

			Item item = ClassInjector.createFromField(field);
			String internalName = objHolder.value();
			item.setRegistryName(internalName);
			item.setTranslationKey(Chococraft.MODID + "." + internalName);
			item.setCreativeTab(Chococraft.creativeTab);
            ItemSetupParameters parameters = field.getAnnotation(ItemSetupParameters.class);
            if(parameters != null)
                applyParameters(item, parameters);
			event.getRegistry().register(item);
		}
	}

    @SubscribeEvent
	public static void onRegisterModels(ModelRegistryEvent event)
    {
        for(Field field : ModItems.class.getDeclaredFields())
        {
            if(!Item.class.isAssignableFrom(field.getType())) continue;
            Item item = ClassInjector.getOrNull(field);
            if(item == null)
			{
				Chococraft.log.error("The field for {} is null! This should not happen!", field.getName());
				continue;
			}
            if(item instanceof IItemModelProvider)
			{
				((IItemModelProvider) item).registerItemModel(item);
			}
            else
			{
				ResourceLocation rl = item.getRegistryName();
				assert rl != null;
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(rl, "inventory"));
			}
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    private @interface ItemFoodParameters
    {
        int amount();
        int saturation();
        boolean isWolfFood() default false;
    }

    @InstanceFactoryMethod
    public static ItemFood createItemFood(ItemFoodParameters parameters)
    {
        return new ItemFood(parameters.amount(), parameters.saturation(), parameters.isWolfFood());
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
	private @interface ItemSetupParameters
    {
        int stackSize() default 64;
    }

	private static void applyParameters(Item item, ItemSetupParameters parameters)
    {
        item.setMaxStackSize(parameters.stackSize());
    }
}
