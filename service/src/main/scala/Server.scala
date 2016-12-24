package org.scardiecat.styxgrpctest

/**
  * Created by ralfmueller on 2016-12-04.
  */

object Server extends App with EchoModule {
   echoServer.start().awaitTermination()
}