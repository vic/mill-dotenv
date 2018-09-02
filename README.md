# mill-dotenv

[![Build Status](https://travis-ci.org/vic/mill-dotenv.svg?branch=master)](https://travis-ci.org/vic/mill-dotenv)


A [Mill][mill] module for loading environment variables from a local file.

Storing configuration in the environment is one of the tenets of a twelve-factor app. Anything that is likely to change between deployment environments–such as resource handles for databases or credentials for external services–should be extracted from the code into environment variables.

But it is not always practical to set environment variables on development machines or continuous integration servers where multiple projects are run. mill-dotenv loads variables from a .env file into ENV when the environment is bootstrapped.

mill-dotenv is intended to be used in development.

## Usage

See the example project buildfile: [`example/build.sc`][example].


### Credits

The regular expression for parsing the env file has been borrowed from [sbt-dotenv] to support exactly the same format.

[mill]: https://www.lihaoyi.com/mill
[sbt-dotenv]: https://github.com/mefellows/sbt-dotenv
[example]: https://github.com/vic/mill-dotenv/blob/master/example/build.sc
