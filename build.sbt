val finchVersion = "0.31.0"
val circeVersion = "0.12.3"
val scalatestVersion = "3.0.5"

lazy val root = (project in file("."))
  .settings(
    organization := "io.github.viniarck",
    name := "finapi",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.10",
    libraryDependencies ++= Seq(
      "com.github.finagle" %% "finchx-core"  % finchVersion,
      "com.github.finagle" %% "finchx-circe"  % finchVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "org.postgresql" % "postgresql" % "42.2.8",
      "io.getquill" %% "quill-jdbc" % "3.1.0",
      "io.getquill" %% "quill-finagle-postgres" % "3.5.0",
      "org.scalatest"      %% "scalatest"    % scalatestVersion % "test"
    )
  )
