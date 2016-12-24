package org.scardiecat.echo.services

import org.scardiecat.echo.BaseSpec
import org.scardiecat.styxgrpctest.echoservice.v1.SendMessageRequest
import org.scardiecat.styxgrpctest.grpc.EchoClient

/**
  * Created by ralfmueller on 2016-12-11.
  */
class EchoServiceTest extends BaseSpec {

  val channel = EchoClient.buildChannel(8443)
  val echoServiceStub = EchoClient.buildServiceStub(channel)

  override def beforeAll(){
    echoServer.start()
  }

  override def afterAll() {
    echoServer.shutdown().awaitTermination()
  }

  describe("EchoServiceTest") {

    it("should sendEcho and receive a message") {
      val sendMessage = echoServiceStub
        .sendEcho(SendMessageRequest("hello"))
      whenReady(sendMessage) { reply =>
        reply.messageId.nonEmpty shouldBe true
        reply.content shouldEqual "hello"
      }
    }

  }
}
