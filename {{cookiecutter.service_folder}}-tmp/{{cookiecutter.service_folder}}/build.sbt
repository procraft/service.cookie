// {{cookiecutter.service_desc}}
name := "{{cookiecutter.service_folder}}"
organization := "libicraft"
version := "0.0.1"

scalaVersion in ThisBuild := "2.12.8"
scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
// uncomment this when you need to work with locally compiled chatsync-api project instead of published-to-maven version
// do not commit, this will break CI builds
  .dependsOn(ProjectRef(file("../{{cookiecutter.service_folder}}-api"), "{{cookiecutter.service_folder}}-api"))

libraryDependencies ++= {
  val circeV = "0.11.1"
  val macwireV = "2.3.3"
  val slickV = "3.3.2"
  val slickPgV = "0.17.3"
  Seq(
    //"libicraft" %% "{{cookiecutter.service_folder}}-api" % "0.0.1",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.beachape" %% "enumeratum" % "1.5.13",
    "com.github.pureconfig" %% "pureconfig" % "0.11.1",
    "com.github.tminglei" %% "slick-pg" % slickPgV,
    "com.softwaremill.macwire" %% "macros" % macwireV % "provided",
    "com.softwaremill.macwire" %% "macrosakka" % macwireV % "provided",
    "com.softwaremill.macwire" %% "util" % macwireV,
    "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2",
    "com.typesafe.slick" %% "slick-hikaricp" % slickV,
    "io.circe" %% "circe-core" % circeV,
    "io.circe" %% "circe-generic" % circeV,
    "io.circe" %% "circe-java8" % circeV,
    "io.circe" %% "circe-parser" % circeV,
    "org.postgresql" % "postgresql" % "42.2.5",
    "org.scalatest" %% "scalatest" % "3.0.8" % "test"
  )
}

resolvers += Resolver.bintrayRepo("cakesolutions", "maven")

resolvers += "Artifactory" at "https://maven.libicraft.com/artifactory/sbt/"
credentials += Credentials("Artifactory Realm", "maven.libicraft.com", "sbt", "8Dl9Ki66xKFWVwSXE")

routesGenerator := InjectedRoutesGenerator

PlayKeys.devSettings := Seq("play.server.http.port" → "9005") // TODO: Set next backend free port

// https://www.scala-sbt.org/1.0/docs/Scopes.html
fork in Runtime := true // Otherwise it won't use javaOptions.
javaOptions in Runtime += "-Dconfig.file=conf/development.conf"
javaOptions in Test += "-Dconfig.file=conf/development.conf"

// dist configuration
packageName in Universal := "libicraft-{{cookiecutter.service_folder}}"

sources in (Compile, doc) := Seq.empty
publishArtifact in (Compile, packageDoc) := false
