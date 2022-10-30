# 引用

## 引用する

![引用する様子](./image/gif/quote.gif)

- 引用するには citation が閲覧権限を持つチャンネルのメッセージリンクを送信します。

!!! example

    ```
    https://discord.com/channels/988823175699771402/1031026247163183135/1031028201977282631
    ```

!!! note "citationが対応しているドメイン群"

    citationが対応しているドメインは以下のとおりです。

    - `discord.com`
    - `discordapp.com`
    - `canary.discord.com`
    - `canary.discordapp.com`
    - `ptb.discord.com`
    - `ptb.discordapp.com`

## 引用をスキップする

![引用スキップ機能](./image/gif/quote-skip.gif)

- メッセージリンクを `<>` で囲むと引用はスキップされ、展開されません。

!!! example

    ```
    <https://discord.com/channels/988823175699771402/1031026247163183135/1031028201977282631>
    ```

## 引用を削除する

![削除機能](./image/gif/quote-delete.gif)

- `🗑️` のボタンで引用を削除することができます。

!!! warning "引用を削除できる人"

    引用を削除できる人はその引用を行った人(メッセージリンクを送信した人)のみです。

    また、メッセージ管理権限または管理者権限を持つユーザーも削除することができます。

## ジャンプする

![ジャンプ機能](./image/gif/quote-jump.gif)

- `💬` のボタンを押すとメッセージへジャンプすることができます。
- `#️⃣` のボタンを押すとチャンネルへジャンプすることができます。

## 添付ファイルを引用する

![添付ファイルの引用](./image/gif/quote-attachment.gif)

- 映像、画像の添付ファイルは引用することができます。
- 画像のみ引用するとEmbedに表示されます。
- ALTテキストが添付ファイルに設定されている場合はEmbedに表示されます。

!!! note

    一度に引用できる添付ファイルは一個までです。

### スポイラーの添付ファイル

![スポイラーの挙動](./image/gif/quote-spoiler.gif)

- スポイラーが有効になった画像は引用してもEmbedに表示されず、リンクのみが表示されます。
- リンクから画像を閲覧できます。
- スポイラーされた添付ファイルはファイル名の先頭に `SPOILER.` とつきます。

!!! note 

    スポイラーについては公式ヘルプ記事を御覧ください。

    [スポイラータグ！ - Discord ヘルプセンター](https://support.discord.com/hc/ja/articles/360022320632)

!!! warning

    スポイラーされた画像のリンク先はスポイラー処理されていません。

    ネタバレにはご注意ください。

    Discordは神様じゃないので見た記憶までは消せません。

    ![チャンネルを退出することにより、再度ネタバレ処理がされますが、見た記憶まで消すことはできません。](./image/113046.png)

## NSFWについて

citation はNSFWチャンネルのコンテンツは規約違反を回避するため、引用しません。

NSFWチャンネルとして扱われるチャンネルは以下の通りです。

- テキストチャンネル
- Text Chat In Voice Channels (ボイスチャットの設定が対象になります。)
- スレッド (親元のテキストチャンネルの設定が対象になります。)
- 投稿 (親元のフォーラムチャンネルの設定が対象になります。)

## citationが引用できるもの

citation が引用できるコンテンツは以下のとおりです。

- メッセージ内容
- 添付ファイル (画像・映像)
- コードブロック
- Nitro Sticker (スタンプ)
- Guild Sticker (ギルドスタンプ)

以下のコンテンツは citation が引用できません。

- Ephemeral Message
- システムメッセージ
- GIF (URLのみが表示されます。)

!!! note "Ephemeral Messageとは"

    アプリケーションコマンド (スラッシュコマンド・コンテキストメニューなど) の Interaction API で扱われるメッセージ形態です。

    詳しくは公式ヘルプ記事を御覧ください。

    [Ephemeral Messages FAQ - Discord ヘルプセンター](https://support.discord.com/hc/ja/articles/1500000580222)

!!! warning "一部のステッカーの挙動について"

    ステッカーの種類によってはDiscord APIから画像のURLが帰ってこない(JSON方式で返ってくる)ため、citationが引用できません。
