package org.scardiecat.styxgrpctest.grpc

import io.grpc.ManagedChannel
import io.grpc.netty.{NegotiationType, NettyChannelBuilder}
import org.scardiecat.styxgrpctest.echoservice.v1.EchoServiceGrpc
import org.scardiecat.styxgrpctest.pongservice.v1.PongServiceGrpc

object EchoClient {
  def buildChannel(port:Int):ManagedChannel  = {
    NettyChannelBuilder
      .forAddress("localhost", port)
      .negotiationType(NegotiationType.PLAINTEXT)
      .build()
  }
  def buildServiceStub(channel:ManagedChannel ): EchoServiceGrpc.EchoServiceStub =  {
    EchoServiceGrpc.stub(channel)
  }

  def buildPongServiceStub(channel: ManagedChannel): PongServiceGrpc.PongServiceStub = {
    PongServiceGrpc.stub(channel)
  }
}
