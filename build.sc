// -*- mode: scala -*-

import mill._, scalalib._, publish._
import ammonite.ops._
import scala.util.Properties

object meta {

  val crossVersions = Seq("2.13.2", "2.12.11")

  implicit val wd: os.Path = os.pwd

  def nonEmpty(s: String): Option[String] = s.trim match {
    case v if v.isEmpty => None
    case v => Some(v)
  }

  val versionFromEnv = Properties.propOrNone("PUBLISH_VERSION")
  val gitSha = nonEmpty(%%("git", "rev-parse", "--short", "HEAD").out.trim)
  val gitTag = nonEmpty(%%("git", "tag", "-l", "-n0", "--points-at", "HEAD").out.trim)
  val publishVersion = (versionFromEnv orElse gitTag orElse gitSha).getOrElse("latest")
}

object dotenv extends Cross[Dotenv](meta.crossVersions: _*)
class Dotenv(val crossScalaVersion: String) extends CrossScalaModule with PublishModule { self =>
  def publishVersion = meta.publishVersion

  def artifactName = "mill-dotenv"

  def pomSettings = PomSettings(
    description = "mill support for twelve-factor apps. load environment variables from .env",
    organization = "com.github.vic",
    url = "https://github.com/vic/mill-dotenv",
    licenses = Seq(License.`Apache-2.0`),
    versionControl = VersionControl.github("vic", "mill-dotenv"),
    developers = Seq(
      Developer("vic", "Victor Borja", "https://github.com/vic")
    )
  )

  def compileIvyDeps = Agg(
    ivy"com.lihaoyi::mill-scalalib:latest.stable"
  )

  object tests extends Tests {
    def ivyDeps = Agg(ivy"com.lihaoyi::utest::0.7.4") ++ self.compileIvyDeps()
    def testFrameworks = Seq("utest.runner.Framework")
  }
}
