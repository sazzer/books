import sbt._
import sbt.Keys._

object Settings {
    val scalaCompilerVersion = "2.10.2"

    lazy val coreSettings = Seq(
        organization := "uk.co.grahamcox.books",
        version := "0.1-SNAPSHOT",
        scalaVersion := scalaCompilerVersion
    )

    lazy val defaultSettings = Defaults.defaultSettings ++ 
        org.scalastyle.sbt.ScalastylePlugin.Settings ++ 
        com.typesafe.sbt.SbtSite.site.settings ++
        com.typesafe.sbt.SbtSite.site.includeScaladoc() ++
        coreSettings ++ Seq(
            scalacOptions in Compile ++= Seq(
                "-encoding", "UTF-8",
                "-target:jvm-1.7",
                "-deprecation",
                "-feature",
                "-unchecked"
            )
        ) ++
        net.virtualvoid.sbt.graph.Plugin.graphSettings
}

object Dependencies {
    val Slf4jVersion = "1.7.1"
    val LogbackVersion = "1.0.1"
    val ScalatraVersion = "2.2.1"

    val Specs = Seq(
        "org.specs2" %% "specs2" % "2.2.2" % "test",
        "org.scalacheck" %% "scalacheck" % "1.10.0" % "test",
        "org.mockito" % "mockito-all" % "1.9.0" % "test",
        "org.hamcrest" % "hamcrest-all" % "1.3" % "test"
    )

    val Logging = Seq(
        "org.clapper" %% "grizzled-slf4j" % "1.0.1"
    )

    def LoggingImpl(scope: String = "runtime") = Seq(
        "org.slf4j" % "jcl-over-slf4j" % Slf4jVersion % scope,
        "org.slf4j" % "jul-to-slf4j" % Slf4jVersion % scope,
        "ch.qos.logback" % "logback-classic" % LogbackVersion % scope,
        "ch.qos.logback" % "logback-core" % LogbackVersion % scope
    )

    val ScalaCore = Seq(
      "org.scala-lang" % "scala-reflect" % Settings.scalaCompilerVersion
    )

    val Jetty = Seq(
      "org.eclipse.jetty" % "jetty-webapp" % "8.1.2.v20120308" % "container"
    )

    val Scalatra = Seq(
      "org.scalatra" %% "scalatra" % ScalatraVersion,
      "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
      "org.scalatra" %% "scalatra-json" % ScalatraVersion,
      "org.json4s" %% "json4s-jackson" % "3.2.4"
    )

    val Servlet = Seq(
      "javax.servlet" % "servlet-api" % "2.5" % "provided"
    )

    val Core = ScalaCore ++ Logging ++ Specs ++ Seq(
      "com.github.nscala-time" %% "nscala-time" % "0.6.0"
    )
}

object BooksBuild extends Build {
    lazy val root = Project(
        id = "books",
        base = file("."),
        aggregate = Seq(webapp),
        settings = Settings.defaultSettings ++ 
          ScctPlugin.mergeReportSettings ++
          com.github.theon.coveralls.CoverallsPlugin.coverallsSettings ++ Seq(
            com.github.theon.coveralls.CoverallsPlugin.CoverallsKeys.coverallsToken := Some("SyWZl799F2VHaLTbE6bPwCpOWxNfFQVN3")
          )
    )

    lazy val webapp = Project(
        id = "webapp",
        base = file("webapp"),
        settings = Settings.defaultSettings ++ 
            com.earldouglas.xsbtwebplugin.WebPlugin.webSettings ++ 
            sbtbuildinfo.Plugin.buildInfoSettings ++
            ScctPlugin.instrumentSettings ++
            Seq(
              sourceGenerators in Compile <+= sbtbuildinfo.Plugin.buildInfo,
              sbtbuildinfo.Plugin.buildInfoKeys := Seq[sbtbuildinfo.Plugin.BuildInfoKey](name, version, scalaVersion),
              sbtbuildinfo.Plugin.buildInfoPackage := "uk.co.grahamcox.books.buildinfo"
            ) ++
            Seq(
              libraryDependencies ++= Dependencies.Core ++ 
                  Dependencies.LoggingImpl("runtime") ++
                  Dependencies.Jetty ++
                  Dependencies.Servlet ++ 
                  Dependencies.Scalatra
            )
    )
}
