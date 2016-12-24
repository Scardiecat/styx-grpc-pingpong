package org.scardiecat.styxgrpctest.services

import java.util.UUID

import io.grpc.Status
import org.scardiecat.styxgrpctest.echoservice.v1.{EchoServiceGrpc, Message, SendMessageRequest}

import scala.concurrent.Future

/**
  * Created by ralfmueller on 2016-12-04.
  */
class EchoService extends EchoServiceGrpc.EchoService {
  override def sendEcho(request: SendMessageRequest): Future[Message] = {
    Future.successful(Message(UUID.randomUUID().toString, "1", request.content))
  }
}
