# エラー対処

## citation関連

### Interaction API (Application Command)

#### このインタラクションはcitationで本来想定されていません

citationで利用できるインタラクションではないApplication Commandがcitationに対して、応答を求めてきた際に発生します。

**対処方法:** [コマンドの削除](./commands/remove.md) を参照して、コマンドを削除し `!register` で再登録してください。

#### ギルド以外から発行されたインタラクションには応答できません

citationと接続したクライアントユーザーとのDMチャンネル内でApplication Commandを実行した際に発生します。

本来citationはグローバルコマンド(どこのギルドでも利用できるようにする)での登録は想定されていないため、利用できません。

#### 権限が足りません。このコマンドを実行するには **サーバーの管理権限(`ManageGuild`) または 管理者権限(`Administrator`)** が必要です。

`!register` コマンドを実行するには `ManageGuild` または `Administrator` が必要です。

**対処方法**: [権限をセットアップするには？ - Discordヘルプセンター](https://support.discord.com/hc/ja/articles/206029707) を参照して権限を付与してください。

#### Application Commandの登録に失敗しました。詳細はログを参照してください。

Application Commandの登録に失敗した際のログです。

主な対処方法は以下のとおりです。

##### dotenv.get("GUILD_ID") must not be null

`.env` または 環境変数にて `GUILD_ID`(ギルドID) が指定されていません。

```shell
[DefaultDispatcher-worker-7] ERROR dev.kord.core.Kord - Application Commandの登録に失敗しました
java.lang.NullPointerException: dotenv.get("GUILD_ID") must not be null
```

### Quote

#### 警告: 引用をスキップしました: ギルドが一致しません。

メッセージリンクで指定されたギルドと、送信元のギルドが一致しない際に発生します。

#### エラー: 引用に失敗しました: チャンネルが見つかりません。

チャンネルを見つけることができなかった際に発生します。

テキストチャットではないチャンネルが見つかった際にも発動します。

#### エラー: 引用に失敗しました: NSFWとして指定されているチャンネルのメッセージです。

NSFWとして指定されたチャンネルのメッセージを引用しようとした際に発生します。

#### エラー: 引用に失敗しました: メッセージが見つかりません。

メッセージリンクのメッセージIDからメッセージを発見することができなかった際に発生します。

#### 警告: 通常メッセージではないため、引用をキャンセルしました。

システムメッセージなどを引用しようとした際に発生します。citationが引用できるメッセージの種類については [引用 - 引用を行う](./function/quote.md) を確認してください。

#### 警告: メッセージ内容が空で、Embedのみだったため引用をキャンセルしました。

メッセージ内容(`MessageContent`) が空文字列で、Embedのみのメッセージだった場合は引用されません。

