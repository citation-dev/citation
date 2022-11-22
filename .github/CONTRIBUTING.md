# Contributing Guide

[日本語](CONTRIBUTING.md) / [英語版](CONTRIBUTING_EN.md)

citation Contributing Guide

## Contribution Flow

GitHub FlowとGit Flowを組み合わせたようなものを採用しています。

1. `develop` ブランチから新しいブランチをチェックアウトする
   1. ブランチ命名については下記参照
2. 作業を行う
   1. コミットメッセージの規則についても下記参照
3. `develop` ブランチに対してPull Requestを作成する
   1. `main` ブランチに対しては作成しない
4. 貢献成功

## Branch Naming Rule

ブランチ命名の主な形は以下を指します。

```shell
<type>/<description>
```

- `<type>` はブランチの種類の型を指定します。
  - 型はコミットメッセージの規則で従っている [Conventional Commits](https://www.conventionalcommits.org/ja/v1.0.0/) に準拠します。
  - 例:
    - `feat`: 新機能
    - `fix`: バグ修正
    - `docs`: ドキュメント
- `<description>` はブランチの説明を指定します。
  - これと言って決まったルールはありません。
  - issue潰しが目的の場合はIssue番号でも構いません。
- 例:
  - [m2en/citation #26](https://github.com/m2en/citation/issues/26) をやる場合は `fix/#26`
  - 新機能 **BAN機能** を追加する場合は `feat/guild-member-ban` 

## Commit Message Rule

コミットメッセージ規則は [Conventional Commits](https://www.conventionalcommits.org/ja/v1.0.0/) に準拠します。

詳しくは [Conventional Commits](https://www.conventionalcommits.org/ja/v1.0.0/) を参照してください。

## SemVer

citationは [Semantic Versioning 2.0.0](https://semver.org/lang/ja/) に準拠し、バージョンを管理します。

```shell
vX.Y.Z
```

- `X` はメジャーアップデートを表します。
  - 互換性のない変更が含まれます。
  - この値が変更された場合はその前のバージョンとは互換性がなくなるため、 [Security Policy](../SECURITY.md) などを変更する必要があります。これらのアップデートについては @m2en が決めるため、一般のコントリビューターが独断で決めることはできませんし、させません。
- `Y` はマイナーアップデートを表します。
  - 互換性のある変更が含まれます。
  - この値が変更された場合はその前のバージョンとは互換性があります。
- `Z` はパッチアップデートを表します。
  - 互換性のあるバグ修正が含まれます。
  - この値が変更された場合はその前のバージョンとは互換性があります。

詳しい詳細については [Semantic Versioning 2.0.0](https://semver.org/lang/ja/) を参照してください。

## Release Flow

citationのリリースフローは以下のようになっています。

1. `develop` ブランチの変更量が一定に達する
2. ドラフト状態のプルリクエストをレビュー状態にし、CIを走らせる
3. CIが成功したら `main` ブランチにマージする
4. `main` ブランチに対してリリースCIを実行する
5. ghcr.ioに最新イメージがプッシュされ、 @m2en によりパッチノートが作成され無事新バージョンがリリースされます。

## Pull Request Rule

Pull Requestの主なルールは以下のようになっています。

1. Pull Request のタイトルは Conventional Commit の型を使用して作成してください。

## Coating Terms and Conditions

コーディング規約は以下のとおりです。

1. 各ファイル名は最初を大文字とします。
2. 各変数名などはキャラメルケース(`camelCase`)とします。
