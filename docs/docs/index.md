# Home

<p align="center">
  <img src="https://github.com/m2en/citation/actions/workflows/build.yml/badge.svg" />
  <img src="https://github.com/m2en/citation/actions/workflows/release.yml/badge.svg" />
  <a href="https://github.com/m2en/citation/pkgs/container/citation">
    <img alt="downloads" src="https://img.shields.io/badge/ghcr.io-citation-blue.svg" target="_blank" />
  </a>
  <a href="https://github.com/m2en/citation/blob/main/LICENSE">
    <img alt="License: MIT" src="https://img.shields.io/badge/license-Apache_2.0-green.svg" target="_blank" />
  </a>
  <a href="https://github.com/sponsors/m2en">
    <img alt="License: MIT" src="https://img.shields.io/badge/GitHub_Sponsor-m2en-pink.svg" target="_blank" />
  </a>
</p>

!!! danger

    citation に メジャーアップデートとなる`v2`(バージョン2) がリリースされました。

    以前のバージョン `v1`(バージョン1) は非推奨になるまで利用できますが、セキュリティアップデートは配信されません。

    早急に`v2`への移行をお願いします。

    移行に関するガイドは [Migration v1 to v2]() を参照してください。

citation は Kotlin で書かれたメッセージリンクのプレビュー表示を行う Discord Bot です。

ギルドに送信されたメッセージリンクからメッセージを取得し、埋め込みメッセージとして表示します。

- [Getting Started](./resources/getting-started.md)
- [Security Policy](./resources/security-policy.md)
- [Contributing Guide](./resources/contributing.md)

## Installation

citation を使用するには Docker が必要です。

詳しいセットアップ方法については [Getting Started](./resources/getting-started.md) を参照してください。

!!! note

    Dockerを使用しないセットアップも可能です。それらの方法も [Getting Started](./resources/getting-started.md) に記載しています。

```shell
docker pull ghcr.io/m2en/citation:latest

docker run --env-file .env ghcr.io/m2en/citation:latest
```
