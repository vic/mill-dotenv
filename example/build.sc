//| mvnDeps: ["com.github.vic::mill-dotenv:latest"]
//| mill-version: 1.0.3

// -*- mode: scala -*-
package build

import mill._, scalalib._, mill.dotenv._
import mill.api.BuildCtx

object hello extends ScalaModule with DotEnvModule {
  override def scalaVersion = scala.util.Properties.versionNumberString
  override def finalMainClass = Task("hello.Main")

  // by default dotenv will read `$PWD/.env` file
  // unless `def dotenvSources` is overriden.

  object tests extends ScalaTests with DotEnvModule with TestModule.Utest {
    // load.env-test for test environment
    override def dotenvSources = Task.Sources {
      BuildCtx.workspaceRoot / ".env-test"
    }
    override def mvnDeps = Seq(mvn"com.lihaoyi::utest:0.9.0")
  }
}
