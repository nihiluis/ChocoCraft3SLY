package net.slayer5934.chococraft.common.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slayer5934.chococraft.Chococraft;
import net.slayer5934.chococraft.client.renderer.entities.EntityRendererChocobo;
import net.slayer5934.chococraft.common.entities.EntityChocobo;

@Mod.EventBusSubscriber(modid = Chococraft.MODID)
public class ModEntities
{
    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event)
    {
        // NOTE: We cannot use event.getRegistry() for our registration here, because it does only half the registration
        // This is a limitation in the forge registry system :/
        ResourceLocation chocoboName = new ResourceLocation(Chococraft.MODID, "chocobo");
        EntityRegistry.registerModEntity(chocoboName,
                EntityChocobo.class, "chocobo", 0, Chococraft.getInstance(), 64, 1, false);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRegisterModels(ModelRegistryEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityChocobo.class, EntityRendererChocobo::new);
    }
}
