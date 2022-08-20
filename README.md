# citation

メッセージリンクのプレビューをEmbedで表示するDiscord Botです。

## Usage

citationはDockerをサポートしています。次のコマンドを使用することでビルド済みイメージを利用することができます。

```shell
# 最新版
docker pull ghcr.io/m2en/citation:latest

# バージョン指定
docker pull ghcr.io/m2en/citation:<任意のバージョン>
```

利用可能なイメージは [packages](https://github.com/m2en/citation/pkgs/container/citation) ページで確認することが出来ます。 (できるかぎり、最新のイメージを使用してください。)

- [利用可能なイメージ(バージョン一覧)](https://github.com/m2en/citation/pkgs/container/citation/versions)

## Register Commands

citationは[Application Commands](https://discord.com/developers/docs/interactions/application-commands)をサポートしています。

`/debug`などのコマンド類を使用するには登録が必要です。

登録方法については [citation docs](https://github.com/m2en/citation/blob/main/docs/README.md) を参照してください。

## Environment variables

| value                | description                                                                                                                               |
|----------------------|-------------------------------------------------------------------------------------------------------------------------------------------|
| `CITATION_BOT_TOKEN` | citationが接続するDiscord Botのトークン ([発行方法](https://github.com/m2en/citation/blob/main/docs/getting-started.md#bot%E3%81%AE%E7%99%BB%E9%8C%B2)) |
| `GUILD_ID`           | Commandを登録したいサーバーのギルドID                                                                                                                   |
