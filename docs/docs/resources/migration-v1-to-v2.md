# Migration v1 to v2
> v1 から v2 への移行

## はじめに

このページは citation v1 から v2 への移行に関するガイドです。

## v2のインストール

v2 以降からはDockerでのセットアップのみを推奨しています。

以下のコマンドを実行して v2 をインストールしてください。

!!! note

    v2からはメジャーバージョン, マイナーバージョン, パッチバージョンそれぞれ、ghcr.ioにプッシュされるようになりました。

    つまり、利用者自身が利用したいバージョンを選択できるようになっています。

```shell
# latest タグからプルする
docker pull ghcr.io/m2en/citation:latest

# v2 タグからプルする
docker pull ghcr.io/m2en/citation:v2

# v2.0.0 タグからプルする
docker pull ghcr.io/m2en/citation:v2.0.0
```

詳しいセットアップ方法については [Getting Started](./getting-started.md) を参照してください。

## コマンドの再登録

v2 では同じコマンドを利用できるようになっていますが、互換性保持のため、コマンドの再登録を推奨しています。

### 1. コマンドを削除する

citation v1 で登録されているコマンドを削除します。

以下のエンドポイントに対して `DELETE` リクエストを送信します。

```https
DELETE /applications/{application.id}/guilds/{guild.id}/commands/{command.id}
```

| value | description                                                  |
| ---- |--------------------------------------------------------------|
| `application.id` | アプリケーション ID (Applicationsページ, "General Information" にてコピー可能) |
| `guild.id` | サーバー ID (Discordクライアントからコピー可能)                               |
| `command.id` | コマンド ID (Discordクライアントからコピー可能。)                     |

![コマンドIDのコピー方法](./images/002051.png)

!!! example
    ```shell
    curl -X DELETE \
        -H "Authorization: Bot <token>" \
        https://discord.com/api/v10/applications/1234567890/guilds/1234567890/commands/1234567890
    ```

### 2. コマンドの再登録

v2 で推奨されているコマンドを再登録します。

#### 起動時に登録する

設定ファイル `config.yml` の `register-ready` を `true` に設定します。

この設定の値が `true` になっている場合、起動時にコマンドが自動的に登録されます。

```yaml
register-ready: true
```

!!! warning

    複数回再起動されるような環境でcitationを利用する場合、コマンドの再登録が複数回行われる可能性があります。
    
    複数回連続したリクエストがDiscord APIに送られる場合レート制限に引っかかる可能性があります。

    そのような場合は `register-ready` を `false` で利用することを推奨します。

#### 起動中に登録する

`!register` コマンドを実行することで、起動中にコマンドを登録することができます。(v1と同様)

!!! warning
    
    `!register` コマンドは `register-ready` が `true` の場合は利用できません。

!!! note
    
    `!register` を実行できるメンバーは以下の権限を取得しておく必要があります。

    - `ManageServer` (サーバーの管理権限)

    または

    - `Administrator` (管理権限)

    サーバー所有者であっても、 上記権限を持っていないと実行できません。
