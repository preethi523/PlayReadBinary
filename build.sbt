name := """PlayReadBinary"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala).dependsOn(backend)


lazy val backend = (project in file("backend"))




scalaVersion := "2.13.8"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies ++= Seq(

  "org.apache.poi" % "poi" % "5.2.2",

  "org.apache.poi" % "poi-ooxml" % "5.2.2",

  "org.apache.poi" % "poi-ooxml-lite" % "5.2.2"

)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
