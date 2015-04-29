import com.typesafe.sbt.SbtStartScript

name := "Markov Chain"

version := "1.0"

scalaVersion := "2.11.1"

// Add scala test to the dependencies
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3"

// Source directories
sourceDirectories in Compile += file("src/com/circuitsofimagination/markovcahin")

seq(SbtStartScript.startScriptForClassesSettings: _*)
