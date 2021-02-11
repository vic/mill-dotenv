// -*- mode: scala -*-

import $ivy.`com.github.vic::mill-dotenv:latest` // Change to fixed release

import mill._, scalalib._, mill.dotenv._

object hello extends ScalaModule with DotEnvModule {

  override def scalaVersion = scala.util.Properties.versionNumberString
  override def finalMainClass = T("hello.Main")

  // by default dotenv will read `$PWD/.env` file
  // unless `def dotenvSources` is overriden.

  object tests extends Tests with DotEnvModule {
    // load.env-test for test environment
    override def dotenvSources = T.sources { os.pwd / ".env-test" }

    def ivyDeps = Agg(ivy"com.lihaoyi::utest::0.7.4")
    def testFrameworks = Seq("utest.runner.Framework")
  }

}
