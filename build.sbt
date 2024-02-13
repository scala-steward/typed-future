import Dependencies._
import BuildHelper._

ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / organization := "dev.ragz"

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias("check", "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck")

lazy val root =
  project
    .in(file("."))
    .settings(publish / skip := true)
    .aggregate(result, benchmark)

lazy val result = module("result", "result")
  .enablePlugins(BuildInfoPlugin)
  .settings(buildInfoSettings("dev.ragz"))
  .settings(libraryDependencies += munit % Test)
  .settings(stdSettings("result"))

lazy val benchmark = module("scala-result-benchmark", "benchmark")
  .enablePlugins(BuildInfoPlugin, JmhPlugin)
  .settings(buildInfoSettings("dev.ragz"))
  .settings(libraryDependencies += munit % Test)
  .settings(stdSettings("scala-result-benchmark"))
  .dependsOn(result)

def module(moduleName: String, fileName: String): Project =
  Project(moduleName, file(fileName))
    .settings(stdSettings(moduleName))

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
