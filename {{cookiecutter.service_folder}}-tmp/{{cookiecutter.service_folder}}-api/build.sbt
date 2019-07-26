// {{cookiecutter.service_desc}}
name := "{{cookiecutter.service_folder}}-api"
organization := "libicraft"
version := "0.0.1"

scalaVersion in ThisBuild := "2.12.8"
scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

libraryDependencies ++= {
  Seq(
    "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
    "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
    "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"
  )
}

publishTo := Some(
  "Artifactory Realm" at "https://maven.libicraft.com/artifactory/sbt;build.timestamp=" + new java.util.Date().getTime
)
credentials += Credentials("Artifactory Realm", "maven.libicraft.com", "sbt", "8Dl9Ki66xKFWVwSXE")

sources in doc := Seq.empty
publishArtifact in packageDoc := false
