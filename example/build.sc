// -*- mode: scala -*-

import $ivy.`io.get-coursier:interface:0.0.21`

// add mill-scalaxb artifact repo
import mill._
interp.repositories() =
  interp.repositories() ++ Seq(coursierapi.MavenRepository.of("https://jitpack.io"))

@

import mill._, scalalib._

import $ivy.`com.github.vic::mill-dotenv:0.5.0`, mill.dotenv._

object hello extends ScalaModule with DotEnvModule {

  // by default dotenv will read `$PWD/.env` file
  // unless dotenvSources is overriden.

  object tests extends Tests with DotEnvModule {
    // load.env-test for test environment
    override def dotenvSources = T.sources { os.pwd / ".env-test" }

    def ivyDeps = Agg(ivy"com.lihaoyi::utest::0.7.4")
    def testFrameworks = Seq("utest.runner.Framework")
  }

  def scalaVersion = scala.util.Properties.versionNumberString

}
