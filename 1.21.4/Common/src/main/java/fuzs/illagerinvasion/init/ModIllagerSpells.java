package fuzs.illagerinvasion.init;

import fuzs.extensibleenums.api.v2.BuiltInEnumFactories;
import fuzs.illagerinvasion.IllagerInvasion;
import net.minecraft.world.entity.monster.SpellcasterIllager;

public class ModIllagerSpells {
    public static final SpellcasterIllager.IllagerSpell ENCHANT_ILLAGER_SPELL = registerIllagerSpell("enchant",
            121,
            161,
            161);
    public static final SpellcasterIllager.IllagerSpell CONJURE_FLAMES_ILLAGER_SPELL = registerIllagerSpell(
            "conjure_flames",
            194,
            41,
            36);
    public static final SpellcasterIllager.IllagerSpell CONJURE_TELEPORT_ILLAGER_SPELL = registerIllagerSpell(
            "conjure_teleport",
            64,
            35,
            81);
    public static final SpellcasterIllager.IllagerSpell NECRO_RAISE_ILLAGER_SPELL = registerIllagerSpell("necro_raise",
            78,
            73,
            52);
    public static final SpellcasterIllager.IllagerSpell CONJURE_SKULL_BOLT_ILLAGER_SPELL = registerIllagerSpell(
            "conjure_skull_bolt",
            44,
            53,
            26);
    public static final SpellcasterIllager.IllagerSpell PROVOKE_ILLAGER_SPELL = registerIllagerSpell("provoke",
            235,
            123,
            109);

    private static SpellcasterIllager.IllagerSpell registerIllagerSpell(String internalName, int redColor, int greenColor, int blueColor) {
        return BuiltInEnumFactories.INSTANCE.createIllagerSpell(IllagerInvasion.id(internalName),
                redColor / 255.0,
                greenColor / 255.0,
                blueColor / 255.0);
    }

    public static void bootstrap() {
        // NO-OP
    }
}
