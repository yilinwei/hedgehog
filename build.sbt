import _root_.sbt.Keys._
import android.Keys._

scalaVersion := "2.11.6"

val commonSettings = androidBuild ++ Seq(
  platformTarget in Android := "android-22",

  organization := "com.ithaca.hedgehog",
  version := "1.0.0",

  scalaVersion := "2.11.6",
  crossScalaVersions := Seq("2.11.6"),
  javacOptions ++= Seq("-source", "1.7", "-target", "1.7"),
  scalacOptions ++= Seq("-feature", "-deprecation", "-target:jvm-1.7"),

  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    "jcenter" at "http://jcenter.bintray.com"
  ),

  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "2.1.5" % "test"
  ),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full)
)

val paradiseSettings = Seq(
  libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _)
)

lazy val macros = (project in file("macros")) settings commonSettings ++ paradiseSettings

lazy val core = (project in file("core")) settings commonSettings ++ paradiseSettings dependsOn macros

lazy val root = (project in file(".")) aggregate(macros, core)