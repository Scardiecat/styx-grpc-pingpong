
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



//Tests

concurrentRestrictions in Global += Tags.limit(Tags.Test, 1)
