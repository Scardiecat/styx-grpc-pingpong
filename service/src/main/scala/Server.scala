package org.scardiecat.styxgrpctest

import org.scardiecat.pingpong.PongModule

/**
  * Created by ralfmueller on 2016-12-04.
  */

object Server extends App with PongModule {
   pongServer.start().awaitTermination()
}