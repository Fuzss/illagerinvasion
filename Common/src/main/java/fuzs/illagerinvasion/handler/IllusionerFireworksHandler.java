package fuzs.illagerinvasion.handler;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import net.minecraft.nbt.TagParser;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

public class IllusionerFireworksHandler {
    private static final String TAG_FIREWORKS = "{Flight:3,Explosions:[{Type:1,Flicker:0,Trail:0,Colors:[I;2437522],FadeColors:[I;2437522]},{Type:1,Flicker:0,Trail:0,Colors:[I;8073150],FadeColors:[I;8073150]},{Type:1,Flicker:0,Trail:0,Colors:[I;3887386],FadeColors:[I;3887386]}]}";

    public static EventResult onEntityLoad(Entity entity, ServerLevel level, @Nullable MobSpawnType spawnType) {
        if (entity instanceof Arrow arrow && arrow.getOwner() instanceof Illusioner illusioner) {
            if (illusioner.getRandom().nextInt(3) == 0 && level.getEntitiesOfClass(Player.class, illusioner.getBoundingBox().inflate(4.0), EntitySelector.NO_CREATIVE_OR_SPECTATOR).isEmpty()) {
                ItemStack stack = new ItemStack(Items.FIREWORK_ROCKET);
                try {
                    stack.addTagElement("Fireworks", TagParser.parseTag(TAG_FIREWORKS));
                } catch (CommandSyntaxException ignored) {
                    return EventResult.PASS;
                }
                FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(level, stack, illusioner, illusioner.getX(), illusioner.getEyeY() - 0.1, illusioner.getZ(), true);
                fireworkRocketEntity.setDeltaMovement(arrow.getDeltaMovement());
                fireworkRocketEntity.setYRot(arrow.getYRot());
                fireworkRocketEntity.setXRot(arrow.getXRot());
                fireworkRocketEntity.yRotO = arrow.yRotO;
                fireworkRocketEntity.xRotO = arrow.xRotO;
                level.addFreshEntity(fireworkRocketEntity);
                return EventResult.INTERRUPT;
            }
        }
        return EventResult.PASS;
    }
}
