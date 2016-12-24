package org.scardiecat.echo

import org.scalatest.{BeforeAndAfterAll, FunSpec, Matchers}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

/**
  * Created by ralfmueller on 2016-12-11.
  */
class BaseSpec
  extends FunSpec
    with TestModule
    with Matchers
    with ScalaFutures
    with BeforeAndAfterAll {

  implicit override val patienceConfig =
    PatienceConfig(timeout = Span(5, Seconds), interval = Span(50, Millis))
}
