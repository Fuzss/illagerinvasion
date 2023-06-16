package fuzs.illagerinvasion.core;

import fuzs.illagerinvasion.mixin.SpellcasterIllagerImpl;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.IntFunction;

public class ForgeAbstractions implements CommonAbstractions {

    @Override
    public Object registerRaiderType(String internalName, EntityType<? extends Raider> entityType, int[] spawnsPerWaveBeforeBonus) {
        return Raid.RaiderType.create(internalName, entityType, spawnsPerWaveBeforeBonus);
    }

    @Override
    public Object registerIllagerSpell(String internalName, double spellColorRed, double spellColorGreen, double spellColorBlue) {
        return actuallyRegisterIllagerSpell(internalName, spellColorRed, spellColorGreen, spellColorBlue);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Enum<T>> T actuallyRegisterIllagerSpell(String internalName, double spellColorRed, double spellColorGreen, double spellColorBlue) {
        try {
            Class<T> enumMainClass = (Class<T>) Class.forName("net.minecraft.world.entity.monster.SpellcasterIllager$IllagerSpell");
            return addToEnumValues(enumMainClass, ordinalId -> {
                return (T) SpellcasterIllagerImpl.IllagerSpellForgeAccessor.getValues(internalName, ordinalId, ordinalId, spellColorRed, spellColorGreen, spellColorBlue);
            }, internalName);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Enum<T>> T addToEnumValues(Class<T> enumMainClass, IntFunction<T> enumValueFactory, String internalName) throws ReflectiveOperationException {
        // don't test for final, some mixin accessor might make the field mutable, removing the flag (we actually do it in this case)
        final int valuesFieldModifiers = Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_SYNTHETIC;
        for (Field field : enumMainClass.getDeclaredFields()) {
            // we don't go looking for $VALUES field by name as Proguard might be messing with that
            if (field.getType().isArray() && (field.getModifiers() & valuesFieldModifiers) == valuesFieldModifiers) {
                field.setAccessible(true);
                T[] values = (T[]) field.get(null);
                for (T value : values) {
                    if (value.name().equals(internalName)) {
                        throw new IllegalArgumentException(String.format("%s already exists in enum class %s", internalName, enumMainClass.getName()));
                    }
                }
                T[] modifiedValues = Arrays.copyOf(values, values.length + 1);
                T enumValue = enumValueFactory.apply(modifiedValues.length - 1);
                modifiedValues[modifiedValues.length - 1] = enumValue;
                field.set(null, modifiedValues);
                return enumValue;
            }
        }
        throw new IllegalAccessException("Could not find enum values field");
    }
}
