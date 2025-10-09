package fuzs.illagerinvasion.neoforge.data.client;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.init.ModSoundEvents;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.neoforge.api.client.data.v2.AbstractSoundProvider;
import net.minecraft.sounds.SoundEvents;

public class ModSoundProvider extends AbstractSoundProvider {

    public ModSoundProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addSounds() {
        this.add(ModSoundEvents.HORN_OF_SIGHT_SOUND_EVENT.value(), sound(SoundEvents.RAID_HORN.value()));
        this.add(ModSoundEvents.LOST_CANDLE_FIND_ORE_SOUND_EVENT.value(), sound(SoundEvents.BELL_RESONATE));
        this.add(ModSoundEvents.ARCHIVIST_DEATH_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/archivist/death1")),
                sound(IllagerInvasion.id("mob/archivist/death2")),
                sound(IllagerInvasion.id("mob/archivist/death3")));
        this.add(ModSoundEvents.ARCHIVIST_HURT_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/archivist/hurt1")),
                sound(IllagerInvasion.id("mob/archivist/hurt2")),
                sound(IllagerInvasion.id("mob/archivist/hurt3")));
        this.add(ModSoundEvents.ARCHIVIST_AMBIENT_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/archivist/idle1")),
                sound(IllagerInvasion.id("mob/archivist/idle2")),
                sound(IllagerInvasion.id("mob/archivist/idle3")));
        this.add(ModSoundEvents.BASHER_CELEBRATE_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/basher/celebrate")));
        this.add(ModSoundEvents.BASHER_DEATH_SOUND_EVENT.value(), sound(IllagerInvasion.id("mob/basher/death")));
        this.add(ModSoundEvents.BASHER_HURT_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/basher/hurt1")),
                sound(IllagerInvasion.id("mob/basher/hurt2")),
                sound(IllagerInvasion.id("mob/basher/hurt3")));
        this.add(ModSoundEvents.BASHER_AMBIENT_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/basher/idle1")),
                sound(IllagerInvasion.id("mob/basher/idle2")),
                sound(IllagerInvasion.id("mob/basher/idle3")));
        this.add(ModSoundEvents.FIRECALLER_CAST_SOUND_EVENT.value(), sound(IllagerInvasion.id("mob/firecaller/cast")));
        this.add(ModSoundEvents.FIRECALLER_DEATH_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/firecaller/death1")),
                sound(IllagerInvasion.id("mob/firecaller/death2")));
        this.add(ModSoundEvents.FIRECALLER_HURT_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/firecaller/hurt1")),
                sound(IllagerInvasion.id("mob/firecaller/hurt2")),
                sound(IllagerInvasion.id("mob/firecaller/hurt3")));
        this.add(ModSoundEvents.FIRECALLER_AMBIENT_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/firecaller/idle1")),
                sound(IllagerInvasion.id("mob/firecaller/idle2")),
                sound(IllagerInvasion.id("mob/firecaller/idle3")),
                sound(IllagerInvasion.id("mob/firecaller/idle4")));
        this.add(ModSoundEvents.ILLAGER_BRUTE_DEATH_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/illager_brute/death1")));
        this.add(ModSoundEvents.ILLAGER_BRUTE_HURT_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/illager_brute/hurt1")),
                sound(IllagerInvasion.id("mob/illager_brute/hurt2")),
                sound(IllagerInvasion.id("mob/illager_brute/hurt3")));
        this.add(ModSoundEvents.ILLAGER_BRUTE_AMBIENT_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/illager_brute/idle1")),
                sound(IllagerInvasion.id("mob/illager_brute/idle2")),
                sound(IllagerInvasion.id("mob/illager_brute/idle3")),
                sound(IllagerInvasion.id("mob/illager_brute/idle4")),
                sound(IllagerInvasion.id("mob/illager_brute/idle5")));
        this.add(ModSoundEvents.INVOKER_BIG_CAST_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/invoker/big_cast")));
        this.add(ModSoundEvents.INVOKER_COMPLETE_CAST_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/invoker/completecast")));
        this.add(ModSoundEvents.INVOKER_DEATH_SOUND_EVENT.value(), sound(IllagerInvasion.id("mob/invoker/death")));
        this.add(ModSoundEvents.INVOKER_FANGS_CAST_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/invoker/fangs_cast")));
        this.add(ModSoundEvents.INVOKER_FANGS_SOUND_EVENT.value(), sound(IllagerInvasion.id("mob/invoker/fangs")));
        this.add(ModSoundEvents.INVOKER_HURT_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/invoker/hurt1")),
                sound(IllagerInvasion.id("mob/invoker/hurt2")));
        this.add(ModSoundEvents.INVOKER_AMBIENT_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/invoker/idle1")),
                sound(IllagerInvasion.id("mob/invoker/idle2")),
                sound(IllagerInvasion.id("mob/invoker/idle3")),
                sound(IllagerInvasion.id("mob/invoker/idle4")));
        this.add(ModSoundEvents.INVOKER_SHIELD_BREAK_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/invoker/shield_break")));
        this.add(ModSoundEvents.INVOKER_SHIELD_CREATE_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/invoker/shield_create")));
        this.add(ModSoundEvents.INVOKER_SUMMON_CAST_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/invoker/summon_cast")));
        this.add(ModSoundEvents.INVOKER_TELEPORT_CAST_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/invoker/teleport_cast")));
        this.add(ModSoundEvents.NECROMANCER_SUMMON_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/necromancer/necroraise")));
        this.add(ModSoundEvents.PROVOKER_CELEBRATE_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/provoker/celebrate")));
        this.add(ModSoundEvents.PROVOKER_DEATH_SOUND_EVENT.value(), sound(IllagerInvasion.id("mob/provoker/death")));
        this.add(ModSoundEvents.PROVOKER_HURT_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/provoker/hurt1")),
                sound(IllagerInvasion.id("mob/provoker/hurt2")),
                sound(IllagerInvasion.id("mob/provoker/hurt3")));
        this.add(ModSoundEvents.PROVOKER_AMBIENT_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/provoker/idle1")),
                sound(IllagerInvasion.id("mob/provoker/idle2")),
                sound(IllagerInvasion.id("mob/provoker/idle3")));
        this.add(ModSoundEvents.SORCERER_CAST_SOUND_EVENT.value(), sound(IllagerInvasion.id("mob/sorcerer/cast")));
        this.add(ModSoundEvents.SORCERER_CELEBRATE_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/sorcerer/celebrate")));
        this.add(ModSoundEvents.SORCERER_COMPLETE_CAST_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/sorcerer/completecast")));
        this.add(ModSoundEvents.SORCERER_DEATH_SOUND_EVENT.value(), sound(IllagerInvasion.id("mob/sorcerer/death1")));
        this.add(ModSoundEvents.SORCERER_HURT_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/sorcerer/hurt1")),
                sound(IllagerInvasion.id("mob/sorcerer/hurt2")),
                sound(IllagerInvasion.id("mob/sorcerer/hurt3")));
        this.add(ModSoundEvents.SORCERER_AMBIENT_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/sorcerer/idle1")),
                sound(IllagerInvasion.id("mob/sorcerer/idle2")),
                sound(IllagerInvasion.id("mob/sorcerer/idle3")),
                sound(IllagerInvasion.id("mob/sorcerer/idle4")));
        this.add(ModSoundEvents.SURRENDERED_CHARGE_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/surrendered/charge")));
        this.add(ModSoundEvents.SURRENDERED_DEATH_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/surrendered/death")));
        this.add(ModSoundEvents.SURRENDERED_HURT_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/surrendered/hurt1")),
                sound(IllagerInvasion.id("mob/surrendered/hurt2")),
                sound(IllagerInvasion.id("mob/surrendered/hurt3")));
        this.add(ModSoundEvents.SURRENDERED_AMBIENT_SOUND_EVENT.value(),
                sound(IllagerInvasion.id("mob/surrendered/ambient1")),
                sound(IllagerInvasion.id("mob/surrendered/ambient2")),
                sound(IllagerInvasion.id("mob/surrendered/ambient3")));
    }
}
