# Home

[![build](https://github.com/m2en/citation/actions/workflows/build.yml/badge.svg)](https://github.com/m2en/citation/actions/workflows/build.yml)
[![release](https://github.com/m2en/citation/actions/workflows/release.yml/badge.svg)](https://github.com/m2en/citation/actions/workflows/release.yml)

---

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
