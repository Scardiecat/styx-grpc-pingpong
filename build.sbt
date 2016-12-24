import java.nio.file._




lazy val root = (project in file("."))
  .aggregate(api,service)

//
// App
//
lazy val api = (project in file("api"))

//
// PingPong
//
lazy val service = (project in file("service")).dependsOn(api)



// For reference
//lazy val buildFrontend = taskKey[Unit]("Execute frontend scripts")
//
//buildFrontend := {
//  val s: TaskStreams = streams.value
//  val shell: Seq[String] = if (sys.props("os.name").contains("Windows")) Seq("cmd", "/c") else Seq("bash", "-c")
//  val npmInstall: Seq[String] = shell :+ "npm install"
//  val npmTest: Seq[String] = shell :+    "npm run test"
//  val npmLint: Seq[String] = shell :+    "npm run lint"
//  val npmBuild: Seq[String] = shell :+   "npm run build"
//  s.log.info("building frontend...")
//  if((npmInstall #&& npmTest #&& npmLint #&& npmBuild !) == 0) {
//    s.log.success("frontend build successful!")
//  } else {
//    throw new IllegalStateException("frontend build failed!")
//  }
//}

//Tests

concurrentRestrictions in Global += Tags.limit(Tags.Test, 1)
