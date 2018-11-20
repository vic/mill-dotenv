// -*- mode: scala -*-

import mill._, scalalib._, publish._

object dotenv extends ScalaModule with PublishModule {

  def publishVersion = "0.0.3"

  def scalaVersion = scala.util.Properties.versionNumberString

  def artifactName = "mill-dotenv"

  def m2 = T {
    val pa = publishArtifacts()
    val wd = T.ctx().dest
    val ad = pa.meta.group.split("\\.").foldLeft(wd)((a, b) => a / b) / pa.meta.id / pa.meta.version
    os.makeDir(ad)
    pa.payload.map { case (f,n) => os.copy(f.path, ad/n) }
  }

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

  def ivyDeps = Agg(
    ivy"com.lihaoyi::mill-scalalib:${System.getProperty("MILL_VERSION")}"
  )

  object tests extends Tests {
    def ivyDeps = Agg(ivy"com.lihaoyi::utest::0.6.3")
    def testFrameworks = Seq("utest.runner.Framework")
  }

}
