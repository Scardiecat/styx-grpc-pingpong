package org.scardiecat.styxgrpctest.services

import org.scardiecat.echo.BaseSpec
import org.scardiecat.styxgrpctest.grpc.EchoClient
import org.scardiecat.styxgrpctest.pongservice.v1.SendPingMessageRequest

/**
  * Created by ralfmueller on 2016-12-11.
  */
class PingPongServiceTest extends BaseSpec {
  val channel = EchoClient.buildChannel(8443)
  val pongServiceStub = EchoClient.buildPongServiceStub(channel)

  override def beforeAll(){
    echoServer.start()
  }

  override def afterAll() {
    echoServer.shutdown().awaitTermination()
  }

  describe("testSendPing") {
    it("should sendPing and receive a message") {
      val sendMessage = pongServiceStub
        .sendPing(SendPingMessageRequest("hello"))
      whenReady(sendMessage) { reply =>
        reply.messageId.nonEmpty shouldBe true
        reply.content shouldEqual "pong"
      }
    }
  }

}
