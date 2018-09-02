// -*- mode: scala -*-

// add mill-scalaxb artifact repo
import mill._
interp.repositories() =
  interp.repositories() ++ Seq(coursier.MavenRepository("https://jitpack.io"))

@

import mill._, scalalib._
import ammonite.ops._
import ImplicitWd._

import $ivy.`com.github.vic::mill-dotenv:0.0.1`, mill.dotenv._

object hello extends ScalaModule with DotEnvModule {

  def scalaVersion = "2.12.6"

  // by default dotenv will read `$PWD/.env` file
  // unless dotenvSources is overriden.

  object tests extends Tests with DotEnvModule {
    // load.env-test for test environment
    override def dotenvSources = T.sources { pwd / ".env-test" }

    def ivyDeps = Agg(ivy"com.lihaoyi::utest::0.6.3")
    def testFrameworks = Seq("utest.runner.Framework")
  }

}
