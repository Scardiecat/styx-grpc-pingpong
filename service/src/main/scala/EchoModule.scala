package org.scardiecat.styxgrpctest

import org.scardiecat.styxgrpctest.grpc.EchoServer
import org.scardiecat.styxgrpctest.services.{EchoService, PingPongService}


/**
  * Created by ralfmueller on 2016-12-04.
  */
trait EchoModule  {
  lazy val echoService = new EchoService
  lazy val pongService = new PingPongService
  lazy val echoServer = EchoServer.build(echoService, pongService)
}
