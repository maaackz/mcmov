package com.maaackz.mcmov.mixin.client;

import com.maaackz.mcmov.sound.SoundManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Set;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    private static final Set<BlockPos> activeFurnaces = new HashSet<>();

    @Inject(method = "tick", at = @At("HEAD"))
    private static void tick(World world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci) {

        ItemStack inputStack = blockEntity.getStack(0);
        ItemStack fuelStack = blockEntity.getStack(1);

        boolean hasRawChicken = inputStack.getItem() == Items.CHICKEN;
        boolean hasLavaFuel = fuelStack.getItem() == Items.LAVA_BUCKET || fuelStack.getItem() == Items.BUCKET;

        boolean shouldPlay = hasRawChicken && hasLavaFuel;

        if (shouldPlay) {
            if (!activeFurnaces.contains(pos)) {
                activeFurnaces.add(pos);
                SoundManager.playItemSound("steve.lava.chicken");
            }
        } else {
            if (activeFurnaces.contains(pos)) {
                activeFurnaces.remove(pos);
                SoundManager.stopItemSound("steve.lava.chicken");
            }
        }
    }
}
