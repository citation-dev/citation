# コマンドの削除

citationが使っているDiscord API Wrapper **Kord** にはApplication Commandを削除する機能がありません。

Application Commandを削除するには以下の手順を踏んでください。

## 全て削除する (推奨)

`!register` で登録したコマンドは全てギルドコマンドとして登録されます。

ギルドコマンドして登録したコマンドはそのギルド内でしか使えないので、そのBotがギルドからKickされたらギルドコマンドは削除されます。

1. citationを右クリックする
2. 右クリックメニューから `citationをキック` をクリックする

## 個別に削除する

個別に削除する場合はDiscord APIを叩いて自ら削除する必要があります。

[Delete Guild Application Command - Discord Developer Documents](https://discord.com/developers/docs/interactions/application-commands#delete-guild-application-command)

Discord API `https://discord.com/api` に対し、以下のリクエストを投げます。

```
DELETE /applications/{application.id}/guilds/{guild.id}/commands/{command.id}
```

Example `https://discord.com/api/applications/586824421470109716/guilds/683939861539192860/commands/794211975373520906`

| Query              | Description                          |
|--------------------|--------------------------------------|
| `{application.id}` | そのBotのアプリケーションID                     |
| `{guild.id}`       | ギルドID                                |
| `{command.id}`     | コマンドID (コマンドIDはギルド内連携サービスからコピーできます。) |