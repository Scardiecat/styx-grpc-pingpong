package org.scardiecat.pingpong

import org.scardiecat.pingpong.grpc.PongServer
import org.scardiecat.styxgrpctest.services.PingPongService


/**
  * Created by ralfmueller on 2016-12-04.
  */
trait PongModule  {
  lazy val pongService = new PingPongService
  lazy val pongServer = PongServer.build(pongService)
}
