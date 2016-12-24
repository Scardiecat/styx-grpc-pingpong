package org.scardiecat.styxgrpctest.services

import org.scardiecat.echo.BaseSpec
import org.scardiecat.pingpong.grpc.PingClient
import org.scardiecat.pongservice.v1.SendPingMessageRequest

/**
  * Created by ralfmueller on 2016-12-11.
  */
class PingPongServiceTest extends BaseSpec {
  val channel = PingClient.buildChannel(8443)
  val pongServiceStub = PingClient.buildPongServiceStub(channel)

  override def beforeAll(){
    pongServer.start()
  }

  override def afterAll() {
    pongServer.shutdown().awaitTermination()
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
