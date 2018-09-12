package net.slayer5934.chococraft.common.entities.properties;

import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.network.datasync.DataSerializers;
import net.slayer5934.chococraft.utils.GenericByteEnumSerializer;

public class EntityDataSerializers
{
    public final static DataSerializer<ChocoboColor> CHOCOBO_COLOR = new GenericByteEnumSerializer<>(ChocoboColor.values());
    public final static DataSerializer<SaddleType> BAG_TYPE = new GenericByteEnumSerializer<>(SaddleType.values());
    public final static DataSerializer<MovementType> MOVEMENT_TYPE = new GenericByteEnumSerializer<>(MovementType.values());

    public static void init()
    {
        DataSerializers.registerSerializer(CHOCOBO_COLOR);
        DataSerializers.registerSerializer(BAG_TYPE);
        DataSerializers.registerSerializer(MOVEMENT_TYPE);
    }
}
