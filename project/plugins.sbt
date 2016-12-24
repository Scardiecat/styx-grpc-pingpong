resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.6")

addSbtPlugin("me.lessis" % "bintray-sbt" % "0.3.0")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.6.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.8.5")

addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.2")

libraryDependencies += "com.trueaccord.scalapb" %% "compilerplugin" % "0.5.45"
//libraryDependencies += "com.github.scala-incubator.io" % "scala-io_2.10.2" % "0.4.2"