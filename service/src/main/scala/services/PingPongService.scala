package org.scardiecat.styxgrpctest.services

import java.util.UUID

import org.scardiecat.pongservice.v1.{PongMessage, PongServiceGrpc, SendPingMessageRequest}

import scala.concurrent.Future

/**
  * Created by ralfmueller on 2016-12-10.
  */
class PingPongService extends PongServiceGrpc.PongService{
  override def sendPing(request: SendPingMessageRequest): Future[PongMessage] = {
    Future.successful(PongMessage(UUID.randomUUID().toString, "1", "pong"))
  }
}
