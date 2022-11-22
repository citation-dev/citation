# Contributing Guide

[日本語](CONTRIBUTING.md) / [English](CONTRIBUTING_EN.md)

Contributing Guide

## Contribution Flow

We use a combination of GitHub Flow and Git Flow.

1. check out a new branch from the `develop` branch
    1. see below for branch naming
2. do the work
    1. also see below for commit message rules
       Create a Pull Request on your `develop` branch 1.
       Do not create a Pull Request on the `main` branch. 4.
       Contribute successfully.

## Branch Naming Rules

The main forms of branch naming refer to the following.

```shell
<type>/<description>
```

- `<type>` specifies the type of branch type.
  - The type conforms to the [Conventional Commits](https://www.conventionalcommits.org/ja/v1.0.0/) rules for commit messages.
  - Example:
    - `feat`: new feature
    - `fix`: bug fixes
    - `docs`: documentation
- `<description>` specifies a description of the branch.
  - There is no fixed rule for this.
  - Issue numbers are also acceptable if the purpose of the issue is to kill an issue.
- Example:
  - For example, to do [m2en/citation #26](https://github.com/m2en/citation/issues/26), use `fix/#26`.
  - If you want to add a new feature **BAN feature**, please add `feat/guild-member-ban`. 

## Commit Message Rule

Commit message rules conform to [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/).

See [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) for details.

## SemVer

citation conforms to [Semantic Versioning 2.0.0](https://semver.org/) and manages versions.

```shell
vX.Y.Z
```

- ``X`` denotes a major update.
    - It contains incompatible changes.
    - If this value is changed, it is no longer compatible with the previous version, so [Security Policy](../SECURITY.md), etc. These updates are determined by @m2en and cannot and will not be made at the sole discretion of the general contributors.
- `Y` denotes minor updates.
    - Compatibility changes are included.
    - If this value is changed, it is compatible with the previous version.
- `Z` represents a patch update.
    - Contains compatibility bug fixes.
    - If this value is changed, it is compatible with the previous version.

See [Semantic Versioning 2.0.0](https://semver.org) for more details.

## Release Flow

The release flow of citation is as follows: 1.

1. the amount of changes in the `develop` branch reaches a certain level
2. put the draft pull request into review state and run CI. 3.
   If CI succeeds, merge it into the `main` branch. 4.
3. run release CI on the `main` branch 5.
   The latest image is pushed to ghcr.io, and @m2en creates a patch note and releases the new version successfully.

## Pull Request Rules

The main rules of a pull request are as follows: 1.

The title of the Pull Request should be created using the Conventional Commit type.

## Coating Terms and Conditions

1. The coding terms and conditions are as follows
2. Each file name must be capitalized at the beginning Variable names, etc. should be in `camelCase`.
