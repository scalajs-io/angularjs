import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys.{libraryDependencies, _}
import sbt.Project.projectToRef
import sbt._

import scala.language.postfixOps

val apiVersion = "0.3.0.6"
val scalaJsIOVersion = "0.3.0.6"
val scalaJsVersion = "2.12.1"

homepage := Some(url("https://github.com/scalajs.io/angularjs"))

val commonSettings = Seq(
  version := apiVersion,
  scalaVersion := scalaJsVersion,
  scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:implicitConversions", "-Xlint"),
  scalacOptions in(Compile, doc) ++= Seq("-no-link-warnings"),
  autoCompilerPlugins := true,
  scalaJSModuleKind := ModuleKind.CommonJSModule,
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaJsVersion,
    "org.scalatest" %%% "scalatest" % "3.0.1" % "test",
    "io.scalajs" %%% "dom-html" % scalaJsIOVersion,
    "io.scalajs" %%% "jquery" % "3.1.1-1"
  ))

lazy val root = (project in file(".")).
  enablePlugins(ScalaJSPlugin).
  aggregate(
    angular_core, angular_anchorScroll, angular_animate, angular_cookies, angular_facebook, angular_md5,
    angular_nervgh_fileupload, angular_nvd3, angular_sanitize, angular_toaster, angular_ui_bootstrap, angular_ui_router).
  dependsOn(
    angular_core, angular_anchorScroll, angular_animate, angular_cookies, angular_facebook, angular_md5,
    angular_nervgh_fileupload, angular_nvd3, angular_sanitize, angular_toaster, angular_ui_bootstrap, angular_ui_router).
  settings(
    name := "angularjs-bundle",
    organization := "io.scalajs",
    description := "AngularJS API bindings for Scala.js",
    version := apiVersion,
    scalaVersion := scalaJsVersion,
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:implicitConversions", "-Xlint"),
    scalacOptions in(Compile, doc) ++= Seq("-no-link-warnings"),
    autoCompilerPlugins := true,
    scalaJSModuleKind := ModuleKind.CommonJSModule,
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaJsVersion,
      "org.scalatest" %%% "scalatest" % "3.0.1" % "test",
      "io.scalajs" %%% "dom-html" % scalaJsIOVersion
    ))

/////////////////////////////////////////////////////////////////////////////////
//      AngularJS sub-projects
/////////////////////////////////////////////////////////////////////////////////

lazy val angular_core = (project in file("angularjs/core")).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(
    name := "angularjs-core",
    organization := "io.scalajs",
    description := "AngularJS/core binding for Scala.js"
  )

lazy val angular_anchorScroll = (project in file("angularjs/anchor-scroll")).
  dependsOn(angular_core).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(
    name := "angularjs-anchor-scroll",
    organization := "io.scalajs",
    description := "AngularJS/anchorScroll binding for Scala.js"
  )

lazy val angular_animate = (project in file("angularjs/animate")).
  dependsOn(angular_core).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(
    name := "angularjs-animate",
    organization := "io.scalajs",
    description := "AngularJS/animate binding for Scala.js"
  )

lazy val angular_cookies = (project in file("angularjs/cookies")).
  dependsOn(angular_core).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(
    name := "angularjs-cookies",
    organization := "io.scalajs",
    description := "AngularJS/cookies binding for Scala.js"
  )

lazy val angular_facebook = (project in file("angularjs/facebook")).
  dependsOn(angular_core).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(
    name := "angularjs-facebook",
    organization := "io.scalajs",
    description := "AngularJS/facebook binding for Scala.js",
    libraryDependencies ++= Seq(
      "io.scalajs" %%% "facebook-api" % scalaJsIOVersion
    ))

lazy val angular_md5 = (project in file("angularjs/md5")).
  dependsOn(angular_core).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(
    name := "angularjs-md5",
    organization := "io.scalajs",
    description := "AngularJS/md5 binding for Scala.js"
  )

lazy val angular_nervgh_fileupload = (project in file("angularjs/nervgh-fileupload")).
  dependsOn(angular_core).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(
    name := "angularjs-nervgh-fileupload",
    organization := "io.scalajs",
    description := "AngularJS/nervgh-fileupload binding for Scala.js"
  )

lazy val angular_nvd3 = (project in file("angularjs/nvd3")).
  dependsOn(angular_core).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(
    name := "angularjs-nvd3",
    organization := "io.scalajs",
    description := "AngularJS/nvd3 binding for Scala.js"
  )

lazy val angular_sanitize = (project in file("angularjs/sanitize")).
  dependsOn(angular_core).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(
    name := "angularjs-sanitize",
    organization := "io.scalajs",
    description := "AngularJS/sanitize binding for Scala.js"
  )

lazy val angular_toaster = (project in file("angularjs/toaster")).
  dependsOn(angular_core).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(
    name := "angularjs-toaster",
    organization := "io.scalajs",
    description := "AngularJS/toaster binding for Scala.js"
  )

lazy val angular_ui_bootstrap = (project in file("angularjs/ui-bootstrap")).
  dependsOn(angular_core).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(
    name := "angularjs-ui-bootstrap",
    organization := "io.scalajs",
    description := "AngularJS/ui-bootstrap binding for Scala.js"
  )

lazy val angular_ui_router = (project in file("angularjs/ui-router")).
  dependsOn(angular_core).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(
    name := "angularjs-ui-router",
    organization := "io.scalajs",
    description := "AngularJS/ui-router binding for Scala.js"
  )

/////////////////////////////////////////////////////////////////////////////////
//      Publishing
/////////////////////////////////////////////////////////////////////////////////

lazy val publishingSettings = Seq(
  sonatypeProfileName := "org.xerial",
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  pomExtra :=
    <url>https://github.com/ldaniels528/scalajs-node-npm</url>
      <licenses>
        <license>
          <name>MIT License</name>
          <url>http://www.opensource.org/licenses/mit-license.php</url>
        </license>
      </licenses>
      <scm>
        <connection>scm:git:github.com/scalajs-io/angularjs.git</connection>
        <developerConnection>scm:git:git@github.com:scalajs-io/angularjs.git</developerConnection>
        <url>github.com/scalajs-io/angularjs.git</url>
      </scm>
      <developers>
        <developer>
          <id>ldaniels528</id>
          <name>Lawrence Daniels</name>
          <email>lawrence.daniels@gmail.com</email>
          <organization>io.scalajs</organization>
          <organizationUrl>https://github.com/scalajs-io</organizationUrl>
          <roles>
            <role>Project-Administrator</role>
            <role>Developer</role>
          </roles>
          <timezone>+7</timezone>
        </developer>
      </developers>
)

// loads the Scalajs-io root project at sbt startup
onLoad in Global := (Command.process("project root", _: State)) compose (onLoad in Global).value
