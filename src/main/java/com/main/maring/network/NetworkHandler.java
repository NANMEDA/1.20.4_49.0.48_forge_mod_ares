package com.main.maring.network;

import java.util.Optional;

import com.main.maring.Maring;
import com.main.maring.network.client.*;
import com.main.maring.network.server.SRocketStart;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {

	  private static final String PTC_VERSION = "1.0.0";

	  public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Maring.MODID, "main"), () -> PTC_VERSION, it -> it.equals(PTC_VERSION), it -> it.equals(PTC_VERSION));;

	  
	  /***
	   * 实现 C 和 S 互相传包
	   * 比如 按下火箭发射键后 要把这个信息传到 Server
	   * ***/
	  public static void init() {
	    //Client 2 Server
		  INSTANCE.registerMessage(0, CRocketStart.class, CRocketStart::encode, CRocketStart::decode,
				  CRocketStart::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		  INSTANCE.registerMessage(1, CResearchTableUpdate.class, CResearchTableUpdate::encode, CResearchTableUpdate::decode,
				  CResearchTableUpdate::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		  INSTANCE.registerMessage(2, CDomeControl.class, CDomeControl::encode, CDomeControl::decode,
				  CDomeControl::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		  INSTANCE.registerMessage(3, CTechTreeUpdate.class, CTechTreeUpdate::encode, CTechTreeUpdate::decode,
				  CTechTreeUpdate::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		  INSTANCE.registerMessage(4, CStartTech.class, CStartTech::encode, CStartTech::decode,
				  CStartTech::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		  INSTANCE.registerMessage(5, COpenRocketInv.class, COpenRocketInv::encode, COpenRocketInv::decode,
				  COpenRocketInv::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));

		  // Server 2 Client
		  INSTANCE.registerMessage(6, SRocketStart.class, SRocketStart::encode, SRocketStart::decode,
				  SRocketStart::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));//没用到
	  }

}
