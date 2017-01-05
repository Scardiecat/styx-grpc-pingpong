package org.scardiecat.styxgrpctest.services

import java.util.UUID

import org.scardiecat.dependency.v1.{SendVersionRequest, SendVersionResponse, ServiceVersion}
import org.scardiecat.pongservice.v1.{PongMessage, PongServiceGrpc, SendPingMessageRequest}

import scala.concurrent.Future

/**
  * Created by ralfmueller on 2016-12-10.
  */
class PingPongService extends PongServiceGrpc.PongService{
  override def sendPing(request: SendPingMessageRequest): Future[PongMessage] = {
    Future.successful(PongMessage(UUID.randomUUID().toString, "1", "pong"))
  }

  override def sendVersion(request: SendVersionRequest) = {
    val version = ServiceVersion(meta.BuildInfo.builtAtMillis, meta.BuildInfo.name, meta.BuildInfo.version, meta.BuildInfo.builtAtString)
    Future.successful(SendVersionResponse(Some(version),Nil))
  }
}
