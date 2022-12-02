# Security Policy

[日本語](SECURITY.md) / [English](SECURITY_EN.md)

Security policy of CITATION.

## Supported Versions

citation publishes pre-built images on the GitHub Packages Registry (ghcr.io), and users are free to use any version of the images they wish.

The following table lists the citation versions currently supported by contributors.

| Version                                 | Status                  | Support Start Date                                                         | Support End Date                                                               |
|-----------------------------------------|-------------------------|----------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| v2 (`v2.2.1`～)                          | `Active`                | [2022/10/16](https://github.com/citation-dev/citation/releases/tag/v2.2.1) | Currently supported                                                            |
| v2 -`m2en/citation` (`v2.0.0`~`v2.2.1`) | `Active(not available)` | [2022/10/16](https://github.com/citation-dev/citation/releases/tag/v2.0.0) | Currently supported                                                            |
| v1 (`v1.0.0`~)                          | `Inactive`              | [2022/08/21](https://github.com/citation-dev/citation/releases/tag/v1.0.0) | [2022/10/16](https://github.com/citation-dev/citation/releases/tag/v1.4.0)     |
| v0 (`v0.1.0`~ `v1.0.0-rc1`)             | `Inactive`              | [2022/08/15](https://github.com/citation-dev/citation/releases/tag/v0.1.0) | [2022/08/19](https://github.com/citation-dev/citation/releases/tag/v1.0.0-rc1) |

Versions with `Status` set to `Inactive` are not supported. Security patches will not be delivered with exceptions.

`not available` indicates that the version is not available for some reason.

It is recommended that you choose `Active` when specifying the version for use in Dockerfiles and so on.

```yml
# Docker Compose
services:
  citation:
    image: ghcr.io/citation-dev/citation:latest
```

```shell
# Docker Pull
docker pull ghcr.io/citation-dev/citation:latest
```

```dockerfile
# Dockerfile
FROM ghcr.io/citation-dev/citation:latest
```

## Reporting a Vulnerability

If you find a vulnerability in citation, please do not report it on Discord, Twitter, Issues, or Discussions, but send an email to [citation@m2en.dev](mailto:citation@m2en.dev) **encrypted with PGP key**. Please encrypt your report with a PGP key and email it to []().

Please use the following public key for encryption.

Key fingerprint: `78E4 CFE0 B3B2 0C4C 7BAA A3CA 6554 A829 D251 53F9`

[pgp_keys.asc](https://keybase.io/m2en/pgp_keys.asc?fingerprint=78e4cfe0b3b20c4c7baaa3ca6554a829d25153f9)

----

When reporting, please also provide as much of the following information as possible

- Type of vulnerability
  - (e.g., SQL injection, code injection, resource management issues, design issues, authorization/permission/access control)
- Full paths to the source files associated with the vulnerability
- Tags, commit hashes, permlinks, etc. of the source code related to the vulnerability
- Special settings needed to reproduce the vulnerability
- Procedures for reproducing the issue
- PoC (proof of concept) (if possible)
- Impact of the vulnerability
