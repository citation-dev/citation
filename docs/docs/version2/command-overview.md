# Command Overview

## /help

- ヘルプ埋め込みがEphemeral Messageで表示されます。

## /docs

[ドキュメントページ](search.md)

| parameter | type     | description | autocomplete |
|-----------|----------|-------------|--------------|
| `query`   | `STRING` | 検索クエリ       | true         |

- 検索クエリで指定したページのドキュメントのURLを返します。

## /github

[ドキュメントページ](search.md)

| parameter | type     | description | autocomplete |
|-----------|----------|-------------|--------------|
| `query`   | `STRING` | 検索クエリ       | true         |

- 検索クエリで指定したページのGitHubのURLを返します。

## /shutdown

[ドキュメントページ](shutdown.md)

| parameter | type      | description | autocomplete |
|-----------|-----------|-------------|--------------|
| `force`   | `BOOLEAN` | 強制終了するかどうか  | ----         |

- アクティブなプロセスを終了します
