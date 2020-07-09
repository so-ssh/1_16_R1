package net.minecraft.server;

import com.mojang.serialization.Codec;

public class WorldGenBuriedTreasure extends StructureGenerator<WorldGenBuriedTreasureConfiguration> {

    public WorldGenBuriedTreasure(Codec<WorldGenBuriedTreasureConfiguration> codec) {
        super(codec);
    }

    protected boolean a(ChunkGenerator chunkgenerator, WorldChunkManager worldchunkmanager, long i, SeededRandom seededrandom, int j, int k, BiomeBase biomebase, ChunkCoordIntPair chunkcoordintpair, WorldGenBuriedTreasureConfiguration worldgenburiedtreasureconfiguration) {
        seededrandom.a(i, j, k, 10387320);
        return seededrandom.nextFloat() < worldgenburiedtreasureconfiguration.b;
    }

    @Override
    public StructureGenerator.a<WorldGenBuriedTreasureConfiguration> a() {
        return WorldGenBuriedTreasure.a::new;
    }

    public static class a extends StructureStart<WorldGenBuriedTreasureConfiguration> {

        public a(StructureGenerator<WorldGenBuriedTreasureConfiguration> structuregenerator, int i, int j, StructureBoundingBox structureboundingbox, int k, long l) {
            super(structuregenerator, i, j, structureboundingbox, k, l);
        }

        public void a(ChunkGenerator chunkgenerator, DefinedStructureManager definedstructuremanager, int i, int j, BiomeBase biomebase, WorldGenBuriedTreasureConfiguration worldgenburiedtreasureconfiguration) {
            int k = i * 16;
            int l = j * 16;
            BlockPosition blockposition = new BlockPosition(k + 9, 90, l + 9);

            this.b.add(new WorldGenBuriedTreasurePieces.a(blockposition));
            this.b();
        }

        @Override
        public BlockPosition a() {
            return new BlockPosition((this.f() << 4) + 9, 0, (this.g() << 4) + 9);
        }
    }
}
