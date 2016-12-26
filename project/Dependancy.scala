

import sbt._

case class DependedProject(projectName:String, apiVersion:String)

object Dependencies {

  object Version {
    val grpc = "1.0.2"
  }

  lazy val api = common ++ apiDep
  lazy val service = common ++ serviceDep ++ testDep

  val common = Seq(
      "ch.qos.logback" % "logback-classic" % "1.1.3",
      "io.grpc" % "grpc-netty" % Version.grpc,
      "io.grpc" % "grpc-stub" % Version.grpc,
      "org.javassist" % "javassist" % "3.21.0-GA"
  )

  val apiDep = Seq(
    "com.trueaccord.scalapb" %% "scalapb-runtime-grpc" % "0.5.45"
  )

  val serviceDep = Seq(
  )

  val testDep = Seq(
     "org.scalactic" %% "scalactic" % "3.0.1",
     "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
}


