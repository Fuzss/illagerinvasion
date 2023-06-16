package fuzs.illagerinvasion.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

public abstract class IllagerSpellMixin extends SpellcasterIllager {
    protected IllagerSpellMixin(EntityType<? extends SpellcasterIllager> p_33724_, Level p_33725_) {
        super(p_33724_, p_33725_);
    }

    @Mixin(IllagerSpell.class)
    public static abstract class IllagerSpellForgeMixin {

        @SuppressWarnings("ShadowTarget")
        @Shadow
        @Mutable
        private static IllagerSpell[] $VALUES;

//        @SuppressWarnings("InvokerTarget")
//
//        @Accessor("$VALUES")
//        static void setValues(IllagerSpell[] value) {
//            throw new RuntimeException();
//        }
    }

    @Mixin(IllagerSpell.class)
    public interface IllagerSpellForgeMixin2 {

        @Invoker("<init>")
        public static IllagerSpell getValues(String internalName, int ordinalId, int spellId, double spellColorRed, double spellColorGreen, double spellColorBlue) {
            throw new RuntimeException();
        }

//        @Shadow
//        @Mutable
//        @SuppressWarnings("ShadowTarget")
//        private static IllagerSpell[] $VALUES;

//        @SuppressWarnings("AccessorTarget")
//        @Accessor("$VALUES")
//        @Mutable
//        static IllagerSpell[] getValues() {
//            throw new RuntimeException();
//        }
    }
}
