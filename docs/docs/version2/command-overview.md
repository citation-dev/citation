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

## /ping

- citationが接続しているアクティブなセッションの各Rest request、GatewayAPIのレスポンスタイムを返します。
- このコマンドで表示されるレスポンスタイムはどれも大まかな値となり、正確な値ではないことに注意してください。

!!! note "GatewayAPIのレスポンスタイム"
    Discordが最後のハートビートに応答するのにかかった時間(ミリ秒)

    この `/ping` が実行された時点のcitationのセッションに対するWebSocket Pingをおおよそ表しています。
!!! note "Rest requestのレスポンスタイム"
    DiscordがRest requestに応答するのにかかった時間(ミリ秒)

    これはDiscord APIから現在citationが接続しているクライアントユーザーをリクエストし、それに対するレスポンスにかかった時間になっています。
!!! warning "citationのレスポンスが悪いと感じたら"

    [Discord Status](https://discordstatus.com/) の **API Response Time** を確認してください。

    値が高い場合はcitationのレスポンスが悪いということではなく、Discord APIサーバーのレスポンスが悪い可能性があります。最新情報を確認してください。
!!! warning "GatewayAPIとRest requestのレスポンスタイムについて"

    RestActionのレスポンスタイムはこのレスポンスタイムとは相関がありません。
