// -*- mode: scala -*-

import $repo.`https://jitpack.io`
import $ivy.`com.github.vic::mill-dotenv:latest`

import mill._, scalalib._

import mill.dotenv._

object hello extends ScalaModule with DotEnvModule {

  def scalaVersion = scala.util.Properties.versionNumberString

  // by default dotenv will read `$PWD/.env` file
  // unless `def dotenvSources` is overriden.

  object tests extends Tests with DotEnvModule {
    // load.env-test for test environment
    override def dotenvSources = T.sources { os.pwd / ".env-test" }

    def ivyDeps = Agg(ivy"com.lihaoyi::utest::0.7.4")
    def testFrameworks = Seq("utest.runner.Framework")
  }

}
