val scala3Version = "3.7.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "sWeather",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "com.squareup.okhttp3" % "okhttp" % "4.12.0", 
      "com.google.code.gson" % "gson" % "2.13.1"
    )
)