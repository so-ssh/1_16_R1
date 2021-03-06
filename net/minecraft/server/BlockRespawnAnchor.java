package net.minecraft.server;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

public class BlockRespawnAnchor extends Block {

    public static final BlockStateInteger a = BlockProperties.aC;

    public BlockRespawnAnchor(BlockBase.Info blockbase_info) {
        super(blockbase_info);
        this.j((IBlockData) ((IBlockData) this.blockStateList.getBlockData()).set(BlockRespawnAnchor.a, 0));
    }

    @Override
    public EnumInteractionResult interact(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand enumhand, MovingObjectPositionBlock movingobjectpositionblock) {
        ItemStack itemstack = entityhuman.b(enumhand);

        if (enumhand == EnumHand.MAIN_HAND && !a(itemstack) && a(entityhuman.b(EnumHand.OFF_HAND))) {
            return EnumInteractionResult.PASS;
        } else if (a(itemstack) && h(iblockdata)) {
            a(world, blockposition, iblockdata);
            if (!entityhuman.abilities.canInstantlyBuild) {
                itemstack.subtract(1);
            }

            return EnumInteractionResult.a(world.isClientSide);
        } else if ((Integer) iblockdata.get(BlockRespawnAnchor.a) == 0) {
            return EnumInteractionResult.PASS;
        } else if (!a(world)) {
            if (!world.isClientSide) {
                this.d(iblockdata, world, blockposition);
            }

            return EnumInteractionResult.a(world.isClientSide);
        } else {
            if (!world.isClientSide) {
                EntityPlayer entityplayer = (EntityPlayer) entityhuman;

                if (entityplayer.getSpawnDimension() != world.getDimensionKey() || !entityplayer.getSpawn().equals(blockposition)) {
                    entityplayer.setRespawnPosition(world.getDimensionKey(), blockposition, false, true);
                    world.playSound((EntityHuman) null, (double) blockposition.getX() + 0.5D, (double) blockposition.getY() + 0.5D, (double) blockposition.getZ() + 0.5D, SoundEffects.BLOCK_RESPAWN_ANCHOR_SET_SPAWN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return EnumInteractionResult.SUCCESS;
                }
            }

            return EnumInteractionResult.CONSUME;
        }
    }

    private static boolean a(ItemStack itemstack) {
        return itemstack.getItem() == Items.dq;
    }

    private static boolean h(IBlockData iblockdata) {
        return (Integer) iblockdata.get(BlockRespawnAnchor.a) < 4;
    }

    private static boolean a(BlockPosition blockposition, World world) {
        Fluid fluid = world.getFluid(blockposition);

        if (!fluid.a((Tag) TagsFluid.WATER)) {
            return false;
        } else if (fluid.isSource()) {
            return true;
        } else {
            float f = (float) fluid.e();

            if (f < 2.0F) {
                return false;
            } else {
                Fluid fluid1 = world.getFluid(blockposition.down());

                return !fluid1.a((Tag) TagsFluid.WATER);
            }
        }
    }

    private void d(IBlockData iblockdata, World world, final BlockPosition blockposition) {
        world.a(blockposition, false);
        Stream stream = EnumDirection.EnumDirectionLimit.HORIZONTAL.a();

        blockposition.getClass();
        boolean flag = stream.map(blockposition::shift).anyMatch((blockposition1) -> {
            return a(blockposition1, world);
        });
        final boolean flag1 = flag || world.getFluid(blockposition.up()).a((Tag) TagsFluid.WATER);
        ExplosionDamageCalculator explosiondamagecalculator = new ExplosionDamageCalculator() {
            @Override
            public Optional<Float> a(Explosion explosion, IBlockAccess iblockaccess, BlockPosition blockposition1, IBlockData iblockdata1, Fluid fluid) {
                return blockposition1.equals(blockposition) && flag1 ? Optional.of(Blocks.WATER.getDurability()) : ExplosionDamageCalculatorBlock.INSTANCE.a(explosion, iblockaccess, blockposition1, iblockdata1, fluid);
            }

            @Override
            public boolean a(Explosion explosion, IBlockAccess iblockaccess, BlockPosition blockposition1, IBlockData iblockdata1, float f) {
                return ExplosionDamageCalculatorBlock.INSTANCE.a(explosion, iblockaccess, blockposition1, iblockdata1, f);
            }
        };

        world.createExplosion((Entity) null, DamageSource.a(), explosiondamagecalculator, (double) blockposition.getX() + 0.5D, (double) blockposition.getY() + 0.5D, (double) blockposition.getZ() + 0.5D, 5.0F, true, Explosion.Effect.DESTROY);
    }

    public static boolean a(World world) {
        return world.getDimensionManager().isRespawnAnchorWorks();
    }

    public static void a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        world.setTypeAndData(blockposition, (IBlockData) iblockdata.set(BlockRespawnAnchor.a, (Integer) iblockdata.get(BlockRespawnAnchor.a) + 1), 3);
        world.playSound((EntityHuman) null, (double) blockposition.getX() + 0.5D, (double) blockposition.getY() + 0.5D, (double) blockposition.getZ() + 0.5D, SoundEffects.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
        blockstatelist_a.a(BlockRespawnAnchor.a);
    }

    @Override
    public boolean isComplexRedstone(IBlockData iblockdata) {
        return true;
    }

    public static int a(IBlockData iblockdata, int i) {
        return MathHelper.d((float) ((Integer) iblockdata.get(BlockRespawnAnchor.a) - 0) / 4.0F * (float) i);
    }

    @Override
    public int a(IBlockData iblockdata, World world, BlockPosition blockposition) {
        return a(iblockdata, 15);
    }

    public static Optional<Vec3D> a(EntityTypes<?> entitytypes, IWorldReader iworldreader, BlockPosition blockposition) {
        Iterator iterator = BlockPosition.a(blockposition.b(-1, -1, -1), blockposition.b(1, 1, 1)).iterator();

        Optional optional;

        do {
            if (!iterator.hasNext()) {
                return Optional.empty();
            }

            BlockPosition blockposition1 = (BlockPosition) iterator.next();

            optional = BlockBed.a(entitytypes, iworldreader, blockposition1);
        } while (!optional.isPresent());

        return optional;
    }

    @Override
    public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
        return false;
    }
}
