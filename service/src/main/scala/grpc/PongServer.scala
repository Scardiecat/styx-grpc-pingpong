package org.scardiecat.pingpong.grpc

import io.grpc.internal.ServerImpl
import io.grpc.netty.NettyServerBuilder
import org.scardiecat.pongservice.v1.PongServiceGrpc
import org.scardiecat.pongservice.v1.PongServiceGrpc.PongService

import scala.concurrent.ExecutionContext

/**
  * Created by ralfmueller on 2016-12-04.
  */
object PongServer {
  def build(pongService: PongService): ServerImpl = {

    val pongGrpcService = PongServiceGrpc.bindService(pongService, ExecutionContext.global)
    NettyServerBuilder
      .forPort(8443)
      .addService(pongGrpcService)
      .build()
  }
}
