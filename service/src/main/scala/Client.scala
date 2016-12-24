package org.scardiecat.styxgrpctest

import java.util.concurrent.{CountDownLatch, TimeUnit}

import scala.concurrent.ExecutionContext.Implicits.global
import org.scardiecat.styxgrpctest.echoservice.v1.{Message, SendMessageRequest}
import org.scardiecat.styxgrpctest.grpc.EchoClient
import org.scardiecat.styxgrpctest.pongservice.v1.{PongMessage, SendPingMessageRequest}

import scala.util.{Failure, Success}

object Client extends App{
  def time(f: => Unit)={
    val s = System.currentTimeMillis
    System.currentTimeMillis - s
  }

  var count = 500
  val latch:CountDownLatch = new CountDownLatch(count*2)
  val channel = EchoClient.buildChannel(8443)
  val echoServiceStub = EchoClient.buildServiceStub(channel)
  val pongServiceStub = EchoClient.buildPongServiceStub(channel)

  var x = 0
  var outp = 0
 println (time {
   for (x <- 1 to count) {
     if (x - outp > 20000) {
       Thread.sleep(1)
     }
     val sendMessage = echoServiceStub
       .sendEcho(SendMessageRequest("hello"))
     sendMessage.onComplete {
       case Success(value: Message) => {
         //println(s"Message received: $value")
         outp = outp + 1
         latch.countDown()
         if (outp % 50000 == 0) {
           println(s"$outp")
         }
       }
       case Failure(e) => {
         e.printStackTrace
         latch.countDown()
       }
     }
     val sendPongMessage = pongServiceStub
       .sendPing(SendPingMessageRequest("Ho"))
     sendPongMessage.onComplete {
       case Success(value: PongMessage) => {
         //println(s"Message received: $value")
         outp = outp + 1
         latch.countDown()
         if (outp % 50000 == 0) {
           println(s"$outp")
         }
       }
       case Failure(e) => {
         e.printStackTrace
         latch.countDown()
       }
     }
   }
   println("sent")
   latch.await(5, TimeUnit.MINUTES)
 })
  channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  println("shutdown")
}