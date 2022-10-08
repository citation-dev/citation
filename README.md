# citation

[![Build](https://github.com/m2en/citation/actions/workflows/build.yml/badge.svg)](https://github.com/m2en/citation/actions/workflows/build.yml)
[![release](https://github.com/m2en/citation/actions/workflows/release.yml/badge.svg)](https://github.com/m2en/citation/actions/workflows/release.yml)

----

- [Documents](https://citation.m2en.dev)
- [Security Policy](https://citation.m2en.dev/resources/security-policy/)

メッセージリンクのプレビューをEmbedで表示するDiscord Botです。

## Usage

citationはDockerをサポートしています。次のコマンドを使用することでビルド済みイメージを利用することができます。

```shell
# 最新版
docker pull ghcr.io/m2en/citation:latest

# バージョン指定
docker pull ghcr.io/m2en/citation:<任意のバージョン>
```

利用可能なイメージは[ここから](https://github.com/m2en/citation/pkgs/container/citation/versions)確認することが出来ます。 (できるかぎり、最新のイメージを使用してください。)

詳しいセットアップ方法はcitation docsで確認してください。

[Getting started - citation docs](https://citation.m2en.dev/resources/getting-started/)

## Environment variables

| value                | description                                                                                                                               |
|----------------------|-------------------------------------------------------------------------------------------------------------------------------------------|
| `CITATION_BOT_TOKEN` | citationが接続するDiscord Botのトークン ([発行方法](https://github.com/m2en/citation/blob/main/docs/getting-started.md#bot%E3%81%AE%E7%99%BB%E9%8C%B2)) |
| `GUILD_ID`           | Commandを登録したいサーバーのギルドID                                                                                                                   |
