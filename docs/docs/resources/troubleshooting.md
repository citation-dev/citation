# Troubleshooting

## コマンドが登録できない

- `GUILD_ID` 環境変数を登録しているかどうか確認してください。

## 引用されない

- 引用したいメッセージがあるチャンネルに citation への閲覧権限がついているかどうかを確認してください。
- 引用したいメッセージがあるチャンネルがNSFWになっていないかどうか確認してください。
- メッセージリンクを送信したチャンネルに citation への閲覧権限とメッセージ送信権限がついているかどうかを確認してください。

!!! note

    バージョン2からメッセージリンクを送信したチャンネルがNSFWであっても、NSFWチャンネルのメッセージは引用できなくなりました。


## 起動しない

### Privileged Intent Error

```shell
[JDA MainWS-WriteThread] ERROR net.dv8tion.jda.internal.requests.WebSocketClient - WebSocket connection was closed and cannot be recovered due to identification issues
CloseCode(4014 / Disallowed intents. Your bot might not be eligible to request a privileged intent such as GUILD_PRESENCES, MESSAGE_CONTENT, or GUILD_MEMBERS.)
```

- [Discord Developer Portal](https://discord.com/developers/applications) の `Privileged Gateway Intents` が無効になっています。
- citationは少なくとも以下のIntentsを要求します。有効にしてください。

1. `MESSAGE CONTENT INTENT`
2. `SERVER MEMBERS INTENT`
3. `PRESENCE INTENT`

![Privileged Gateway Intents](./images/103852.png)

### トークンが正しくない

- `.env` の `CITATION_BOT_TOKEN` に正しいトークンが指定されているかどうか確認してください。

## 起動が遅い

- `v2.0.0` からコマンドは起動時に登録されるようになりました。起動を繰り返すとその分のリクエストが送信されるため、レート制限のような処置がDiscord API側からどうしてもかかってしまいます。
- 現時点での対策方法はありません、

## 検索コマンドの補完が効かない

- 検索コマンドの補完が効かない問題が発生した場合は大抵Discord クライアントに問題があります。
- 再起動を行ってください。
