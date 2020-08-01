package net.darkhax.bookshelf.serialization;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

public class Serializers {
    
    public static final ISerializer<ResourceLocation> RESOURCE_LOCATION = SerializerResourceLocation.SERIALIZER;
    public static final ISerializer<EnchantmentData> ENCHANTMENT_DATA = SerializerEnchantmentData.SERIALIZER;
    
    public static final ISerializer<Block> BLOCK = new SerializerForgeRegistry<>(ForgeRegistries.BLOCKS);
    public static final ISerializer<Fluid> FLUID = new SerializerForgeRegistry<>(ForgeRegistries.FLUIDS);
    public static final ISerializer<Item> ITEM = new SerializerForgeRegistry<>(ForgeRegistries.ITEMS);
    public static final ISerializer<Effect> EFFECT = new SerializerForgeRegistry<>(ForgeRegistries.POTIONS);
    public static final ISerializer<Biome> BIOME = new SerializerForgeRegistry<>(ForgeRegistries.BIOMES);
    public static final ISerializer<SoundEvent> SOUND = new SerializerForgeRegistry<>(ForgeRegistries.SOUND_EVENTS);
    public static final ISerializer<Potion> POTION = new SerializerForgeRegistry<>(ForgeRegistries.POTION_TYPES);
    public static final ISerializer<Enchantment> ENCHANTMENT = new SerializerForgeRegistry<>(ForgeRegistries.ENCHANTMENTS);
    public static final ISerializer<EntityType<?>> ENTITY = new SerializerForgeRegistry<>(ForgeRegistries.ENTITIES);
    public static final ISerializer<TileEntityType<?>> TILE_ENTITY = new SerializerForgeRegistry<>(ForgeRegistries.TILE_ENTITIES);
    public static final ISerializer<ParticleType<?>> PARTICLE = new SerializerForgeRegistry<>(ForgeRegistries.PARTICLE_TYPES);
    public static final ISerializer<ContainerType<?>> CONTAINER = new SerializerForgeRegistry<>(ForgeRegistries.CONTAINERS);
    public static final ISerializer<PaintingType> PAINTING = new SerializerForgeRegistry<>(ForgeRegistries.PAINTING_TYPES);
    public static final ISerializer<IRecipeSerializer<?>> RECIPE_SERIALIZER = new SerializerForgeRegistry<>(ForgeRegistries.RECIPE_SERIALIZERS);
    public static final ISerializer<Attribute> ATTRIBUTE = new SerializerForgeRegistry<>(ForgeRegistries.ATTRIBUTES);
    public static final ISerializer<StatType<?>> STAT = new SerializerForgeRegistry<>(ForgeRegistries.STAT_TYPES);
    public static final ISerializer<VillagerProfession> PROFESSION = new SerializerForgeRegistry<>(ForgeRegistries.PROFESSIONS);
    public static final ISerializer<PointOfInterestType> POINT_OF_INTEREST = new SerializerForgeRegistry<>(ForgeRegistries.POI_TYPES);
}