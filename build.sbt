lazy val commonSettings = Seq(
  organization := "io.github.pityka",
  scalaVersion := "2.12.4",
  crossScalaVersions := Seq("2.12.4"),
  version := "0.0.1-SNAPSHOT",
  licenses += ("MIT", url("https://opensource.org/licenses/MIT")),
  publishTo := sonatypePublishTo.value,
  pomExtra in Global := {
    <url>https://pityka.github.io/sampling/</url>
      <scm>
        <connection>scm:git:github.com/pityka/sampling</connection>
        <developerConnection>scm:git:git@github.com:pityka/sampling</developerConnection>
        <url>github.com/pityka/flatjoin</url>
      </scm>
      <developers>
        <developer>
          <id>pityka</id>
          <name>Istvan Bartha</name>
          <url>https://pityka.github.io/sampling/</url>
        </developer>
      </developers>
  }
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "sampling",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.0" % "test"
    )
  )

scalafmtOnCompile in ThisBuild := true
