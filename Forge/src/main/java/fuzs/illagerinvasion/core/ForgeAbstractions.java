package fuzs.illagerinvasion.core;

import fuzs.illagerinvasion.mixin.IllagerSpellMixin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ForgeAbstractions implements CommonAbstractions {

    @Override
    public Object registerRaiderType(String internalName, EntityType<? extends Raider> entityType, int[] spawnsPerWaveBeforeBonus) {
        return Raid.RaiderType.create(internalName, entityType, spawnsPerWaveBeforeBonus);
    }

    @Override
    public Object registerIllagerSpell(String internalName, double spellColorRed, double spellColorGreen, double spellColorBlue) {
        return registerIllagerSpellInternal(internalName, spellColorRed, spellColorGreen, spellColorBlue);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Enum<T>> T registerIllagerSpellInternal(String internalName, double spellColorRed, double spellColorGreen, double spellColorBlue) {
        try {
            Class<T> aClass = (Class<T>) Class.forName("net.minecraft.world.entity.monster.SpellcasterIllager$IllagerSpell");
            int ordinalId = addToEnumValues(aClass).length;
            T enumValue = (T) IllagerSpellMixin.IllagerSpellForgeMixin2.getValues(internalName, ordinalId, ordinalId, spellColorRed, spellColorGreen, spellColorBlue);
            addToEnumValues(aClass, enumValue, internalName);
            return enumValue;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T extends Enum<T>> int addToEnumValues(Class<T> enumMainClass, T enumValue, String internalName) throws ReflectiveOperationException {
        final int valuesFieldModifiers = Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_SYNTHETIC;
        for (Field field : enumMainClass.getDeclaredFields()) {
            // we don't go looking for $VALUES field by name as Proguard might be messing with that
            if (field.getType().isArray() && (field.getModifiers() & valuesFieldModifiers) == valuesFieldModifiers) {
                field.setAccessible(true);
                // does not work in Java 12+ due to private field members of Field.class no longer being accessible via reflection
//                Field modifiers = field.getClass().getDeclaredField("modifiers");
//                modifiers.setAccessible(true);
//                modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                T[] values = (T[]) field.get(null);
                for (T value : values) {
                    if (value.name().equals(internalName)) {
                        throw new IllegalArgumentException(String.format("%s already exists in enum class %s", internalName, enumMainClass.getName()));
                    }
                }
                T[] modifiedValues = Arrays.copyOf(values, values.length + 1);
                modifiedValues[modifiedValues.length - 1] = enumValue;
                // use more unsafe hacks here since modifying final fields no longer works via reflection
                field.set(null, modifiedValues);
//                setStaticObjectField(field, modifiedValues);
                return values.length;
            }
        }
        throw new IllegalAccessException("Could not find enum values field");
    }

    private static <T extends Enum<T>> T[] addToEnumValues(Class<T> enumMainClass) throws ReflectiveOperationException {
        final int valuesFieldModifiers = Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_SYNTHETIC;
        for (Field field : enumMainClass.getDeclaredFields()) {
            // we don't go looking for $VALUES field by name as Proguard might be messing with that
            if (field.getType().isArray() && (field.getModifiers() & valuesFieldModifiers) == valuesFieldModifiers) {
                field.setAccessible(true);
                return (T[]) field.get(null);
            }
        }
        throw new IllegalAccessException("Could not find enum values field");
    }
}
