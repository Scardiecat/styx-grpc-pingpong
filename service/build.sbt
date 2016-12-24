import java.nio.file.{Files, Path, Paths, StandardCopyOption}

enablePlugins(GitVersioning)
enablePlugins(JavaAppPackaging)
enablePlugins(BuildInfoPlugin)
enablePlugins(GitVersioning)


organization := "org.scardiecat"
git.baseVersion := "0.0.1"
git.gitTagToVersionNumber := { tag: String =>
  if(tag matches "[0-9]+\\..*") Some(tag)
    else None
}
git.useGitDescribe := true
scalaVersion := "2.11.8"
scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:existentials", "-language:higherKinds")


// build info
buildInfoPackage := "meta"
buildInfoOptions += BuildInfoOption.ToJson
buildInfoOptions += BuildInfoOption.BuildTime
buildInfoKeys := Seq[BuildInfoKey](
  name, version, scalaVersion
)

publishMavenStyle := true
bintrayReleaseOnPublish in ThisBuild := true
bintrayPackageLabels := Seq("styx", "scala", "grpc")
licenses += ("MIT", url("http://opensource.org/licenses/MIT"))


val commonDockerSettings = Seq(
  dockerBaseImage := "frolvlad/alpine-oraclejdk8",
  dockerExposedPorts := Seq(2551),
  maintainer in Docker := "Ralf Mueller <docker@scardiecat.org>",
  dockerRepository := Some("magicmoose-docker-registry.bintray.io/scardiecat")
)


name := """styx-grpc-test-app"""
//
// API
//
libraryDependencies ++= Seq(
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "io.grpc" % "grpc-netty" % "1.0.1",
    "io.grpc" % "grpc-stub" % "1.0.1",
    "org.javassist" % "javassist" % "3.21.0-GA",
    "com.trueaccord.scalapb" %% "scalapb-runtime-grpc" % "0.5.45"
  )
  
managedSourceDirectories in Compile += (target.value / "protobuf-generated")

PB.targets in Compile := Seq(
  scalapb.gen(grpc = true, flatPackage = true) -> (target.value / "protobuf-generated")
)

mainClass in (Compile)  := Some("Server")


// Tests
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"


//
// Service dependencies
//
lazy val dependedProjects = SettingKey[Seq[DependedProject]]("Dependent projects and api version")

dependedProjects := Seq(
  DependedProject("pingpong","v1"),
  DependedProject("echo","v1")
)

//
// Tooling
//

// Helpers
def copyDir(source: Path, dest: Path) {
  Files.copy(source, dest, StandardCopyOption.COPY_ATTRIBUTES)
  if (source.toFile.isDirectory) {
    val dir = source.toFile
    dir.listFiles.foreach(file =>
      copyDir(file.toPath, dest.resolve(file.getName)))
  }
}

def createDir(dir:Path) {
  if (!Files.exists(dir)) {
    try {
      Files.createDirectories(dir);
    } catch {
      case e:Exception => println(e)
    }
  }
}


//
// Init Api Task
//
lazy val initApi = TaskKey[Unit]("initApi")

initApi := {
  if( ("sh deleteAPICopy.sh" !) == 0) {
  }
  print("checkout api")
  if( ("sh getApi.sh" !) == 0) {
  }
  println("copying api")
  println(dependedProjects.value)
  val target= "app/src/main/protobuf/"
  for (dep <- dependedProjects.value) {
    createDir(Paths.get(target, dep.projectName))
    copyDir(Paths.get("styx-grpc-test-api", dep.projectName, dep.apiVersion), Paths.get(target, dep.projectName, dep.apiVersion))
  }

  print("remove api")
  if( ("sh removeApi.sh" !) == 0) {
  }
}
