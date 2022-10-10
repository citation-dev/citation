# Error Handling

citationのエラー対処

## Could not find /.env on the classpath

```shell
Exception in thread "main" io.github.cdimascio.dotenv.DotenvException: Could not find /.env on the classpath
```

- citationが起動する際に使用する環境変数が定義されたファイル `.env` がプロジェクトのディレクトリ上に存在しません。
- [.env ファイルを作成してください。](getting-started.md)

## Application Commandの登録に失敗しました。

```shell
[ERROR] Application Commandの登録に失敗しました
// Exception Log
```

- Application Commandの登録に失敗しました。
- 考えられる可能性は以下の通りです。
> 1. Discord API 関連に障害が起きている ( [Discord API Status](https://discordstatus.com/) を参照してください。)
- 登録リクエストを送信し続けるとレート制限に近い現象が起き、時間がかかる場合があります。
