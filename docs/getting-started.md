# Getting Started

- [ビルド済みイメージの取得](#ビルド済みイメージの取得)
- [Botの登録](#Botの登録)
- [コマンドの登録](#コマンドの登録)
- [コマンドの削除](#コマンドの削除)
- [コマンドのアップデート](#コマンドのアップデート)

## ビルド済みイメージの取得

次のコマンドを実行し、ビルド済みイメージを取得してください。

```shell
# 最新版
docker pull ghcr.io/m2en/citation:latest

# バージョン指定
docker pull ghcr.io/m2en/citation:<任意のバージョン>
```

利用可能なイメージは [packages](https://github.com/m2en/citation/pkgs/container/citation) ページで確認することが出来ます。 (できるかぎり、最新のイメージを使用してください。)

## Botの登録

1. [Developer Portal](https://discord.com/developers/applications) にアクセスする
2. `New Application`をクリックする
3. `name` にBotの名前を入力する
4. 次に `Bots` タブへ移動し、 `New bot` をクリックしBotを作成する
5. ~~`PRESENCE INTENT`, `SERVER MEMBERS INTENT`~~ `MESSAGE CONTENT INTENT` をそれぞれONにする
6. `Reset token` からトークンを取得する
7. `OAuth2` タブに移動し、 `URL Generator` をクリックする
8. `SCOPES` から以下のScopeを選択する
    - `bot`
    - `applications.commands`
9. `BOT PERMISSIONS` から以下の権限を選択する
    - `Send Messages`
    - `Send Messages in Threads`
    - `Read Messages / View Channels`
10. 生成されたURLからBotを招待する

## `.env` を用意する

```shell
# 設定例(.env.example)をコピーする (ソースをクローンして、自前でビルドする場合)
cp .env.example .env
```

```shell
# nano
nano .env

# vimmerのお前
vim .emv
```

以下の値を設定してください。(それぞれの値については[こちらから詳細](https://github.com/m2en/citation#environment-variables)を確認してください。)

```dotenv
CITATION_BOT_TOKEN=
CLIENT_ID=
GUILD_ID=
```

## Botの起動

以下のコマンドを実行します。

```shell
docker run --env-file .env citation
```

`citation is ready!` と表示されたら起動に成功しています、お疲れ様でした。

## コマンドの登録

次にApplicationCommandsを登録します。

次の環境変数を登録し、 `!register` を実行します。

- `CLIENT_ID`
- `GUILD_ID`
