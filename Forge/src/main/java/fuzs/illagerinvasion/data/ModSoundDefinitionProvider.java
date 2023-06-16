package fuzs.illagerinvasion.data;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractSoundDefinitionProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModSoundDefinitionProvider extends AbstractSoundDefinitionProvider {

    public ModSoundDefinitionProvider(PackOutput packOutput, String modId, ExistingFileHelper fileHelper) {
        super(packOutput, modId, fileHelper);
    }

    @Override
    public void registerSounds() {
        this.add(ModRegistry.HORN_OF_SIGHT_SOUND_EVENT.get(), sound(this.id("item/horn_of_sight/blow")));
        this.add(ModRegistry.LOST_CANDLE_COAL_SOUND_EVENT.get(), sound(this.id("item/lost_candle/coal")));
        this.add(ModRegistry.LOST_CANDLE_COPPER_SOUND_EVENT.get(), sound(this.id("item/lost_candle/copper")));
        this.add(ModRegistry.LOST_CANDLE_DIAMOND_SOUND_EVENT.get(), sound(this.id("item/lost_candle/diamond")));
        this.add(ModRegistry.LOST_CANDLE_GOLD_SOUND_EVENT.get(), sound(this.id("item/lost_candle/gold")));
        this.add(ModRegistry.LOST_CANDLE_IRON_SOUND_EVENT.get(), sound(this.id("item/lost_candle/iron")));
        this.add(ModRegistry.ARCHIVIST_DEATH_SOUND_EVENT.get(), sound(this.id("mob/archivist/death1")), sound(this.id("mob/archivist/death2")), sound(this.id("mob/archivist/death3")));
        this.add(ModRegistry.ARCHIVIST_HURT_SOUND_EVENT.get(), sound(this.id("mob/archivist/hurt1")), sound(this.id("mob/archivist/hurt2")), sound(this.id("mob/archivist/hurt3")));
        this.add(ModRegistry.ARCHIVIST_AMBIENT_SOUND_EVENT.get(), sound(this.id("mob/archivist/idle1")), sound(this.id("mob/archivist/idle2")), sound(this.id("mob/archivist/idle3")));
        this.add(ModRegistry.BASHER_CELEBRATE_SOUND_EVENT.get(), sound(this.id("mob/basher/celebrate")));
        this.add(ModRegistry.BASHER_DEATH_SOUND_EVENT.get(), sound(this.id("mob/basher/death")));
        this.add(ModRegistry.BASHER_HURT_SOUND_EVENT.get(), sound(this.id("mob/basher/hurt1")), sound(this.id("mob/basher/hurt2")), sound(this.id("mob/basher/hurt3")));
        this.add(ModRegistry.BASHER_AMBIENT_SOUND_EVENT.get(), sound(this.id("mob/basher/idle1")), sound(this.id("mob/basher/idle2")), sound(this.id("mob/basher/idle3")));
        this.add(ModRegistry.FIRECALLER_CAST_SOUND_EVENT.get(), sound(this.id("mob/firecaller/cast")));
        this.add(ModRegistry.FIRECALLER_DEATH_SOUND_EVENT.get(), sound(this.id("mob/firecaller/death1")), sound(this.id("mob/firecaller/death2")));
        this.add(ModRegistry.FIRECALLER_HURT_SOUND_EVENT.get(), sound(this.id("mob/firecaller/hurt1")), sound(this.id("mob/firecaller/hurt2")), sound(this.id("mob/firecaller/hurt3")));
        this.add(ModRegistry.FIRECALLER_AMBIENT_SOUND_EVENT.get(), sound(this.id("mob/firecaller/idle1")), sound(this.id("mob/firecaller/idle2")), sound(this.id("mob/firecaller/idle3")), sound(this.id("mob/firecaller/idle4")));
        this.add(ModRegistry.ILLAGER_BRUTE_DEATH_SOUND_EVENT.get(), sound(this.id("mob/illager_brute/death1")));
        this.add(ModRegistry.ILLAGER_BRUTE_HURT_SOUND_EVENT.get(), sound(this.id("mob/illager_brute/hurt1")), sound(this.id("mob/illager_brute/hurt2")), sound(this.id("mob/illager_brute/hurt3")));
        this.add(ModRegistry.ILLAGER_BRUTE_AMBIENT_SOUND_EVENT.get(), sound(this.id("mob/illager_brute/idle1")), sound(this.id("mob/illager_brute/idle2")), sound(this.id("mob/illager_brute/idle3")), sound(this.id("mob/illager_brute/idle4")), sound(this.id("mob/illager_brute/idle5")));
        this.add(ModRegistry.INVOKER_BIG_CAST_SOUND_EVENT.get(), sound(this.id("mob/invoker/big_cast")));
        this.add(ModRegistry.INVOKER_COMPLETE_CAST_SOUND_EVENT.get(), sound(this.id("mob/invoker/completecast")));
        this.add(ModRegistry.INVOKER_DEATH_SOUND_EVENT.get(), sound(this.id("mob/invoker/death")));
        this.add(ModRegistry.INVOKER_FANGS_CAST_SOUND_EVENT.get(), sound(this.id("mob/invoker/fangs_cast")));
        this.add(ModRegistry.INVOKER_FANGS_SOUND_EVENT.get(), sound(this.id("mob/invoker/fangs")));
        this.add(ModRegistry.INVOKER_HURT_SOUND_EVENT.get(), sound(this.id("mob/invoker/hurt1")), sound(this.id("mob/invoker/hurt2")));
        this.add(ModRegistry.INVOKER_AMBIENT_SOUND_EVENT.get(), sound(this.id("mob/invoker/idle1")), sound(this.id("mob/invoker/idle2")), sound(this.id("mob/invoker/idle3")), sound(this.id("mob/invoker/idle4")));
        this.add(ModRegistry.INVOKER_SHIELD_BREAK_SOUND_EVENT.get(), sound(this.id("mob/invoker/shield_break")));
        this.add(ModRegistry.INVOKER_SHIELD_CREATE_SOUND_EVENT.get(), sound(this.id("mob/invoker/shield_create")));
        this.add(ModRegistry.INVOKER_SUMMON_CAST_SOUND_EVENT.get(), sound(this.id("mob/invoker/summon_cast")));
        this.add(ModRegistry.INVOKER_TELEPORT_CAST_SOUND_EVENT.get(), sound(this.id("mob/invoker/teleport_cast")));
        this.add(ModRegistry.NECROMANCER_SUMMON_SOUND_EVENT.get(), sound(this.id("mob/necromancer/necroraise")));
        this.add(ModRegistry.PROVOKER_CELEBRATE_SOUND_EVENT.get(), sound(this.id("mob/provoker/celebrate")));
        this.add(ModRegistry.PROVOKER_DEATH_SOUND_EVENT.get(), sound(this.id("mob/provoker/death")));
        this.add(ModRegistry.PROVOKER_HURT_SOUND_EVENT.get(), sound(this.id("mob/provoker/hurt1")), sound(this.id("mob/provoker/hurt2")), sound(this.id("mob/provoker/hurt3")));
        this.add(ModRegistry.PROVOKER_AMBIENT_SOUND_EVENT.get(), sound(this.id("mob/provoker/idle1")), sound(this.id("mob/provoker/idle2")), sound(this.id("mob/provoker/idle3")));
        this.add(ModRegistry.SORCERER_CAST_SOUND_EVENT.get(), sound(this.id("mob/sorcerer/cast")));
        this.add(ModRegistry.SORCERER_CELEBRATE_SOUND_EVENT.get(), sound(this.id("mob/sorcerer/celebrate")));
        this.add(ModRegistry.SORCERER_COMPLETE_CAST_SOUND_EVENT.get(), sound(this.id("mob/sorcerer/completecast")));
        this.add(ModRegistry.SORCERER_DEATH_SOUND_EVENT.get(), sound(this.id("mob/sorcerer/death1")));
        this.add(ModRegistry.SORCERER_HURT_SOUND_EVENT.get(), sound(this.id("mob/sorcerer/hurt1")), sound(this.id("mob/sorcerer/hurt2")), sound(this.id("mob/sorcerer/hurt3")));
        this.add(ModRegistry.SORCERER_AMBIENT_SOUND_EVENT.get(), sound(this.id("mob/sorcerer/idle1")), sound(this.id("mob/sorcerer/idle2")), sound(this.id("mob/sorcerer/idle3")), sound(this.id("mob/sorcerer/idle4")));
        this.add(ModRegistry.SURRENDERED_CHARGE_SOUND_EVENT.get(), sound(this.id("mob/surrendered/charge")));
        this.add(ModRegistry.SURRENDERED_DEATH_SOUND_EVENT.get(), sound(this.id("mob/surrendered/death")));
        this.add(ModRegistry.SURRENDERED_HURT_SOUND_EVENT.get(), sound(this.id("mob/surrendered/hurt1")), sound(this.id("mob/surrendered/hurt2")), sound(this.id("mob/surrendered/hurt3")));
        this.add(ModRegistry.SURRENDERED_AMBIENT_SOUND_EVENT.get(), sound(this.id("mob/surrendered/ambient1")), sound(this.id("mob/surrendered/ambient2")), sound(this.id("mob/surrendered/ambient3")));
    }
}
