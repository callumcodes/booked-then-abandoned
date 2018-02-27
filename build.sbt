val Http4sVersion = "0.18.0"
val Specs2Version = "4.0.2"
val LogbackVersion = "1.2.3"
val circeVersion = "0.9.1"

lazy val root = (project in file("."))
  .settings(
    organization := "uk.gov.hmrc",
    name := "booked-then-abandoned",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.4",
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "org.specs2"     %% "specs2-core"          % Specs2Version % "test",
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,
      "io.circe"        %% "circe-generic"       % circeVersion,
      "io.circe"        %% "circe-java8" % circeVersion,
      "org.http4s" %% "http4s-json4s-native" % Http4sVersion,
      "org.http4s" %% "http4s-json4s-jackson" % Http4sVersion
    )
  )

enablePlugins(JavaAppPackaging)