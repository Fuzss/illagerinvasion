package fuzs.illagerinvasion.helper;

import fuzs.illagerinvasion.mixin.SpellcasterIllagerImpl;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.IntFunction;

public abstract class IllagerSpellEnumHelper extends SpellcasterIllager {

    private IllagerSpellEnumHelper(EntityType<? extends SpellcasterIllager> p_33724_, Level p_33725_) {
        super(p_33724_, p_33725_);
    }

    public static IllagerSpell registerIllagerSpell(String internalName, double spellColorRed, double spellColorGreen, double spellColorBlue) {
        try {
            IllagerSpell result = addToEnumValues(IllagerSpell.class, ordinalId -> {
                return SpellcasterIllagerImpl.IllagerSpellForgeAccessor.illagerinvasion$init(internalName, ordinalId, ordinalId, spellColorRed, spellColorGreen, spellColorBlue);
            }, internalName);
            SpellcasterIllagerImpl.IllagerSpellForgeAccessor.illagerinvasion$setById(ByIdMap.continuous(illagerSpell -> {
                return SpellcasterIllagerImpl.IllagerSpellForgeAccessor.class.cast(illagerSpell).illagerinvasion$getId();
            }, SpellcasterIllager.IllagerSpell.values(), ByIdMap.OutOfBoundsStrategy.ZERO));
            return result;
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
                // this only works since we have an additional mixin removing the final flag, without there is no way setting this with just reflection (e.g. unsafe would work)
                field.set(null, modifiedValues);
                return enumValue;
            }
        }
        throw new IllegalAccessException("Could not find enum values field");
    }
}
