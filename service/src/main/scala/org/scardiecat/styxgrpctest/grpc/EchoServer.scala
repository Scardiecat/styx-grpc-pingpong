package org.scardiecat.styxgrpctest.grpc

import io.grpc.internal.ServerImpl
import io.grpc.netty.NettyServerBuilder
import org.scardiecat.styxgrpctest.echoservice.v1.EchoServiceGrpc
import org.scardiecat.styxgrpctest.echoservice.v1.EchoServiceGrpc.EchoService
import org.scardiecat.styxgrpctest.pongservice.v1.PongServiceGrpc
import org.scardiecat.styxgrpctest.pongservice.v1.PongServiceGrpc.PongService

import scala.concurrent.ExecutionContext

/**
  * Created by ralfmueller on 2016-12-04.
  */
object EchoServer {
  def build(echoService: EchoService, pongService: PongService): ServerImpl = {

    val echoGrpcService = EchoServiceGrpc.bindService(echoService, ExecutionContext.global)
    val pongGrpcService = PongServiceGrpc.bindService(pongService, ExecutionContext.global)
    NettyServerBuilder
      .forPort(8443)
      .addService(echoGrpcService)
      .addService(pongGrpcService)
      .build()
  }
}
