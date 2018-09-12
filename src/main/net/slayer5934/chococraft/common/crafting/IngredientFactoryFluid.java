package net.slayer5934.chococraft.common.crafting;

import javax.annotation.Nonnull;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class IngredientFactoryFluid implements IIngredientFactory
{
    @Nonnull
    @Override
    public Ingredient parse(JsonContext context, JsonObject json)
    {
        String name = JsonUtils.getString(json, "fluid");
        int amount = JsonUtils.getInt(json, "amount", 1000);
        Fluid fluid = FluidRegistry.getFluid(name);
        if(fluid == null)
            throw new JsonSyntaxException("No fluid with name " + name + " was found");
        return new IngredientFluid(fluid, amount);
    }
}
