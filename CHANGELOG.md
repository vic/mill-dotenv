# Changelog

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

When creating a release always specify the `mill` and `scala` versions that were
used to build the project. Since `mill` libs currently have no binary backwards compatibility.

## 0.2.1 - [Unreleased]

## [0.2.0] - 2019-09-05

### Changed
- Upgrade to Mill 0.5.1 and Scala 2.12.9

## [0.1.0] - 2019-06-27

### Changed

- Upgrade to Mill 0.4.1 and Scala 2.12.8

## [0.0.3] - 2018-11-20

### Added

- This CHANGELOG file

### Changed

- Upgrade to Mill 0.3.5
- Use `os-lib` instead of `ammonite.ops._`
- Make build time versions easier to manage.
  Scala and mill versions are managed from `.tool-versions` file.
  The VERSION specifies this project's version number.

[Unreleased]: https://github.com/vic/mill-docker/compare/0.2.0...HEAD
[0.2.0]: https://github.com/vic/mill-docker/compare/0.1.0...0.2.0
[0.1.0]: https://github.com/vic/mill-docker/compare/0.0.3...0.1.0
[0.0.3]: https://github.com/vic/mill-docker/compare/0.0.2...0.0.3
