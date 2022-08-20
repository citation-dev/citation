# コマンドの詳細

| Command                        | Description               | Types       |
|--------------------------------|---------------------------|-------------|
| `!interaction`                 | ApplicationCommands を登録する | Message     |
| `/debug <messageId> [channel]` | メッセージをデバッグする              | Interaction |
| `/help`                        | ヘルプを表示する                  | Interaction |

## `!register`

Application Commandsを登録します。

サーバーの管理権限を持ったユーザーのみが実行できます。

## `/debug`

| Command  | Parameter                                |
|----------|------------------------------------------|
| `/debug` | `<messageId: String> [channel: Channel]` |

第1引数で指定したメッセージIDのメッセージをコードブロックとして表示します。

実行したチャンネル、または第2引数で指定されたチャンネルのメッセージのみを検索することができます。

## `/help`

ヘルプを表示します。
