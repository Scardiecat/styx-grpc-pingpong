enablePlugins(GitVersioning)

val commonSettings = Seq(
  organization := "org.scardiecat",
  git.baseVersion := "0.0.1",
  git.gitTagToVersionNumber := { tag: String =>
    if(tag matches "[0-9]+\\..*") Some(tag)
    else None
  },
  git.useGitDescribe := true,
  scalaVersion := "2.12.1",
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:existentials", "-language:higherKinds"),


  // build info
  buildInfoPackage := "meta",
  buildInfoOptions += BuildInfoOption.ToJson,
  buildInfoOptions += BuildInfoOption.BuildTime,
  buildInfoKeys := Seq[BuildInfoKey](
    name, version, scalaVersion
  ),
  publishMavenStyle := true,
  bintrayReleaseOnPublish in ThisBuild := true,
  bintrayPackageLabels := Seq("styx", "scala", "grpc"),
  licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
)

val commonDockerSettings = Seq(
  dockerBaseImage := "anapsix/alpine-java:8_server-jre_unlimited",
  dockerExposedPorts := Seq(8443),
  maintainer in Docker := "Ralf Mueller <docker@scardiecat.org>",
  dockerRepository := Some("quay.io/scardiecat")
)

val protobuf = Seq(
  managedSourceDirectories in Compile += (target.value / "protobuf-generated"),
  PB.targets in Compile := Seq(
    scalapb.gen(grpc = true, flatPackage = true) -> (target.value / "protobuf-generated")
  )
)
lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin,JavaAppPackaging,DockerPlugin, GitVersioning)
  .settings(
    name := """grpc-pingpong""",
    commonSettings
  ).aggregate(api,service)
//
// API
//
lazy val api = (project in file("api"))
  .enablePlugins(BuildInfoPlugin,JavaAppPackaging,DockerPlugin, GitVersioning)
  .settings(
    name := "styx-grpc-pingpong-api",
    libraryDependencies ++= Dependencies.api,
    commonSettings,
    protobuf
  )

//
// PingPong
//
lazy val service = (project in file("service"))
  .enablePlugins(BuildInfoPlugin,JavaAppPackaging,DockerPlugin, GitVersioning)
  .settings(
    name := "styx-grpc-pingpong",
    libraryDependencies ++= Dependencies.service,
    commonSettings,
    commonDockerSettings,
    mainClass in (Compile)  := Some("Server")
  ).dependsOn(api)

//Tests

concurrentRestrictions in Global += Tags.limit(Tags.Test, 1)
logBuffered in Test := false
