package model.baked;

import java.util.List;
import java.util.Random;

import com.block.register.BlockUnbrokenGlass;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public abstract class NoEdgeBlockModel implements BakedModel {

	private final BakedModel originalModel;
	    private final TextureAtlasSprite customTexture;
	    private final TextureAtlasSprite defaultTexture;
	
	    public NoEdgeBlockModel(BakedModel originalModel, TextureAtlasSprite defaultTexture, TextureAtlasSprite customTexture) {
	        this.originalModel = originalModel;
	        this.defaultTexture = defaultTexture;
	        this.customTexture = customTexture;
	    }
	
	    /*
	    @Override
	    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand) {
	        List<BakedQuad> quads = new ArrayList<>(originalModel.getQuads(state, side, rand));
	        boolean useCustomTexture = state.getValue(BlockUnbrokenGlass.CUSTOM_TEXTURE);
	
	        for (BakedQuad quad : quads) {
	            int[] vertexData = quad.getVertices();
	            for (int i = 0; i < vertexData.length; i += 8) {
	                float u = Float.intBitsToFloat(vertexData[i + 4]);
	                float v = Float.intBitsToFloat(vertexData[i + 5]);
	
	                // Modify the UV coordinates to use the custom texture
	                TextureAtlasSprite texture = useCustomTexture ? customTexture : defaultTexture;
	                u = texture.getU(u * 16);
	                v = texture.getV(v * 16);
	
	                vertexData[i + 4] = Float.floatToIntBits(u);
	                vertexData[i + 5] = Float.floatToIntBits(v);
	            }
	        }
	        return quads;
	    }*/
	
	    // Implement other required methods by delegating to originalModel
	    @Override
	    public boolean useAmbientOcclusion() {
	        return originalModel.useAmbientOcclusion();
	    }
	
	    @Override
	    public boolean isGui3d() {
	        return originalModel.isGui3d();
	    }
	
	    @Override
	    public boolean usesBlockLight() {
	        return originalModel.usesBlockLight();
	    }
	
	    @Override
	    public boolean isCustomRenderer() {
	        return originalModel.isCustomRenderer();
	    }
	
	    @Override
	    public TextureAtlasSprite getParticleIcon() {
	        return originalModel.getParticleIcon();
	    }
	
	    @Override
	    public ItemOverrides getOverrides() {
	        return originalModel.getOverrides();
	    }



}
