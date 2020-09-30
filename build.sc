// -*- mode: scala -*-

import $ivy.`io.get-coursier:interface:0.0.21`

// Dont use sonatype's maven-central as it timeouts in travis.
interp.repositories() =
  List(coursierapi.MavenRepository.of("https://jcenter.bintray.com"))

@

import mill._, scalalib._, publish._

object meta {
  val crossVersions = Seq("2.13.2", "2.12.11")

  implicit val wd: os.Path = os.pwd

  val gitSha = %%("git", "rev-parse", "--short", "HEAD").out.trim
  val gitTag = %%("git", "tag", "-l", "-n0", "--points-at", "HEAD").out.trim

  val publishVersion = {
    gitTag match {
      case "" => gitSha
      case _  => gitTag
    }
  }
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
