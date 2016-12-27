package org.scardiecat.pingpong

import org.http4s.HttpService
import org.http4s._
import org.http4s.dsl._
import org.http4s.server.blaze.BlazeBuilder

/**
  * Created by ralfmueller on 2016-12-04.
  */

object Server extends App with PongModule {
  val healthService = HttpService {
    case GET -> Root / "version" =>
      Ok(meta.BuildInfo.toJson)
    case GET -> Root / "health" =>
      Ok()
    case GET -> Root / "ready" =>
      Ok()
  }
  val builder = BlazeBuilder.bindHttp(8080, "0.0.0.0").mountService(healthService, "/")
  builder.run
  pongServer.start().awaitTermination()
}