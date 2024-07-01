package network;

import java.util.function.BiConsumer;
import java.util.function.Function;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;
import network.client.CRocketStart;
import network.server.SRocketStart;
import net.minecraft.resources.ResourceLocation;

public class NetworkHandler {

	  private static final int PTC_VERSION = 1;

	  private static final String MODID = "maring";

	  public static SimpleChannel INSTANCE;

	  public static void register() {

	    INSTANCE = ChannelBuilder.named(new ResourceLocation(MODID, "main"))
	        .networkProtocolVersion(PTC_VERSION)
	        .clientAcceptedVersions(Channel.VersionTest.exact(PTC_VERSION))
	        .serverAcceptedVersions(Channel.VersionTest.exact(PTC_VERSION)).simpleChannel();

	    //Client Packets
	    register(CRocketStart.class, CRocketStart::encode, CRocketStart::decode,
	    		CRocketStart::handle);

	    // Server Packets
	    register(SRocketStart.class, SRocketStart::encode, SRocketStart::decode,
	    		SRocketStart::handle);//没用到
	   
	  }

	  private static <M> void register(Class<M> messageType, BiConsumer<M, FriendlyByteBuf> encoder,
	                                   Function<FriendlyByteBuf, M> decoder,
	                                   BiConsumer<M, CustomPayloadEvent.Context> messageConsumer) {
	    INSTANCE.messageBuilder(messageType)
	        .decoder(decoder)
	        .encoder(encoder)
	        .consumerNetworkThread(messageConsumer)
	        .add();
	  }
}
