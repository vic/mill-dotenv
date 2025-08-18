//| mill-version: 1.0.3

// -*- mode: scala -*-
package build

import mill.*, os.*, scalalib.*, publish.*
import mill.util.BuildInfo.{millVersion, millBinPlatform}
import scala.util.Properties

object meta {

  def nonEmpty(s: String): Option[String] = s.trim match {
    case v if v.isEmpty => None
    case v              => Some(v)
  }

  val versionFromEnv = Properties.propOrNone("PUBLISH_VERSION")
  val gitSha = nonEmpty(
    os.call(Seq("git", "rev-parse", "--short", "HEAD")).out.text().trim()
  )
  val gitTag = nonEmpty(
    os.call(Seq("git", "tag", "-l", "-n0", "--points-at", "HEAD"))
      .out
      .text()
      .trim()
  )
  val publishVersion =
    (versionFromEnv orElse gitTag orElse gitSha).getOrElse("latest")
}

object dotenv extends ScalaModule with PublishModule {

  override def scalaVersion = "3.7.1"

  override def publishVersion = meta.publishVersion

  override def artifactName = "mill-dotenv"

  override def pomSettings = PomSettings(
    description =
      "mill support for twelve-factor apps. load environment variables from .env",
    organization = "com.github.vic",
    url = "https://github.com/vic/mill-dotenv",
    licenses = Seq(License.`Apache-2.0`),
    versionControl = VersionControl.github("vic", "mill-dotenv"),
    developers = Seq(
      Developer("vic", "Victor Borja", "https://github.com/vic")
    )
  )

  override def mvnDeps = Seq(mvn"com.lihaoyi::mill-libs:$millVersion")

  object tests extends ScalaTests, TestModule.Utest {
    override def mvnDeps = Seq(mvn"com.lihaoyi::mill-testkit:$millVersion")
  }
}
