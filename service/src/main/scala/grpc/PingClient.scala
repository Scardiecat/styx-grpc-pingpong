package org.scardiecat.pingpong.grpc

import io.grpc.ManagedChannel
import io.grpc.netty.{NegotiationType, NettyChannelBuilder}
import org.scardiecat.pongservice.v1.PongServiceGrpc

object PingClient {
  def buildChannel(port:Int):ManagedChannel  = {
    NettyChannelBuilder
      .forAddress("localhost", port)
      .negotiationType(NegotiationType.PLAINTEXT)
      .build()
  }

  def buildPongServiceStub(channel: ManagedChannel): PongServiceGrpc.PongServiceStub = {
    PongServiceGrpc.stub(channel)
  }
}
