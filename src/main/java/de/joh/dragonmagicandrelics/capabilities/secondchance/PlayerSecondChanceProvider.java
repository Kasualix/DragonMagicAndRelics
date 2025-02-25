package de.joh.dragonmagicandrelics.capabilities.secondchance;

import de.joh.dragonmagicandrelics.capabilities.secondchance.PlayerSecondChance;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Generate, load and save the NBT data from the player for PlayerSecondChance
 * @see PlayerSecondChance
 * @see de.joh.dragonmagicandrelics.rituals.contexts.PhoenixRitual
 * @see de.joh.dragonmagicandrelics.events.CommonEventHandler
 * @author Joh0210
 */
public class PlayerSecondChanceProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerSecondChance> PLAYER_SECOND_CHANCE = CapabilityManager.get(new CapabilityToken<PlayerSecondChance>() { });

    private PlayerSecondChance secondChance = null;
    private final LazyOptional<PlayerSecondChance> optional = LazyOptional.of(this::createPlayerSecondChance);

    private PlayerSecondChance createPlayerSecondChance() {
        if(this.secondChance == null){
            this.secondChance = new PlayerSecondChance();
        }

        return this.secondChance;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_SECOND_CHANCE){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerSecondChance().saveNBT(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerSecondChance().loadNBT(nbt);
    }
}
