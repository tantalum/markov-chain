import com.typesafe.sbt.SbtStartScript

name := "Markov Chain"

version := "1.0"

scalaVersion := "2.9.1"

// Add scala test to the dependencies
libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

// Source directories
sourceDirectories in Compile += file("src/com/circuitsofimagination/markovcahin")

seq(SbtStartScript.startScriptForClassesSettings: _*)
