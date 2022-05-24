package net.darkhax.bookshelf.api.util;

import net.darkhax.bookshelf.Constants;
import net.darkhax.bookshelf.mixin.inventory.AccessorCraftingContainer;
import net.darkhax.bookshelf.mixin.inventory.AccessorCraftingMenu;
import net.darkhax.bookshelf.mixin.inventory.AccessorInventoryMenu;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Random;

public interface IInventoryHelper {

    /**
     * Gets the internal menu context that is backing a crafting container.
     *
     * @param container The crafting container to retrieve internal menu context from.
     * @return The internal menu, or null if the menu could not be found.
     */
    @Nullable
    default AbstractContainerMenu getCraftingContainer(CraftingContainer container) {

        return (container instanceof AccessorCraftingContainer accessor) ? accessor.bookshelf$getMenu() : null;
    }

    @Nullable
    default Player getCraftingPlayer(Container container) {

        if (container instanceof Inventory playerInv) {

            return playerInv.player;
        }

        else if (container instanceof CraftingContainer crafting) {

            final AbstractContainerMenu menu = getCraftingContainer(crafting);

            if (menu instanceof AccessorCraftingMenu accessor) {

                return accessor.bookshelf$getPlayer();
            }

            else if (menu instanceof AccessorInventoryMenu accessor) {

                return accessor.bookshelf$getOwner();
            }
        }

        // TODO add some way for dynamic container resolution?
        return null;
    }

    /**
     * Damages an ItemStack by a set amount.
     * <p>
     * ItemStacks with the Unbreakable tag are treated as immune to stack damage as per Minecraft's base spec.
     * <p>
     * ItemStacks with the unbreaking enchantment will have a chance of ignoring the damage.
     * <p>
     * ItemStacks that do not have durability will not be modified.
     * <p>
     * Players in Creative Mode can not damage ItemStacks.
     *
     * @param stack  The ItemStack to damage.
     * @param amount The amount of damage to apply to the item.
     * @param owner  The entity that owns the ItemStack. This is optional but will be used for certain events.
     * @param slot   The slot the ItemStack is in. This is optional and used for the item break animation.
     */
    default ItemStack damageStack(ItemStack stack, int amount, @Nullable LivingEntity owner, @Nullable EquipmentSlot slot) {

        // Only items with durability can be damaged. Items with an Unbreakable tag override damage.
        if (stack.isDamageableItem()) {

            // If an owner is present, use the entity aware version.
            if (owner != null) {

                stack.hurtAndBreak(amount, owner, e -> {

                    if (slot != null) {
                        e.broadcastBreakEvent(slot);
                    }
                });
            }

            // Try to damage the stack directly.
            else if (stack.hurt(amount, Constants.RANDOM, null)) {

                // Destroy the ItemStack when it has no more durability.
                stack.shrink(1);
                stack.setDamageValue(0);
            }
        }

        return stack;
    }

    default NonNullList<ItemStack> keepDamageableItems(CraftingContainer inv, NonNullList<ItemStack> keptItems, int damageAmount) {

        @Nullable final Player player = this.getCraftingPlayer(inv);
        final Random random = player != null ? player.getRandom() : Constants.RANDOM;

        for (int i = 0; i < keptItems.size(); i++) {

            final ItemStack input = inv.getItem(i).copy();

            if (input.getItem().canBeDepleted() || (input.hasTag() && input.getTag().getBoolean("Unbreaking"))) {

                final ItemStack stack = this.damageStack(input, damageAmount, player, null);

                if (!stack.isEmpty()) {

                    keptItems.set(i, stack);
                }
            }
        }

        return keptItems;
    }
}