# Security Policy

[日本語](SECURITY.md) / [English](SECURITY_EN.md)

citationのセキュリティポリシーについてです。

## Supported Versions

citationは、GitHub Packages Registry(ghcr.io)にて、ビルド済みイメージを公開しており、利用者自身が利用したいバージョンのイメージを自由に使用することが可能です。

以下の表は現在コントリビューターがサポートしているcitationのバージョン一覧となります。

| Version                                | Status                  | Support Start Date                                                         | Support End Date                                                               |
|----------------------------------------|-------------------------|----------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| v2 (`v2.2.1`～)                         | `Active`                | [2022/10/16](https://github.com/citation-dev/citation/releases/tag/v2.2.1) | 現在サポート中                                                                        |
| v2 -`m2en/citation`(`v2.0.0`～`v2.2.1`) | `Active(not available)` | [2022/10/16](https://github.com/citation-dev/citation/releases/tag/v2.0.0) | 現在サポート中                                                                        |
| v1 (`v1.0.0`～)                         | `Inactive`              | [2022/08/21](https://github.com/citation-dev/citation/releases/tag/v1.0.0) | [2022/10/16](https://github.com/citation-dev/citation/releases/tag/v1.4.0)     |
| v0 (`v0.1.0`～`v1.0.0-rc1`)             | `Inactive`              | [2022/08/15](https://github.com/citation-dev/citation/releases/tag/v0.1.0) | [2022/08/19](https://github.com/citation-dev/citation/releases/tag/v1.0.0-rc1) |

`Status` が `Inactive` となっているバージョンは、サポート対象外となります。 セキュリティパッチは例外を除いて配信されません。

`not available` は何らかの理由で利用できないバージョンを表します。

Dockerfileなどで利用する際のバージョン指定の際はなるべく `Active` になっているものを選ぶことを推奨します。

```yml
# Docker Compose
services:
  citation:
    image: ghcr.io/citation-dev/citation:latest
```

```shell
# Docker Pull
docker pull ghcr.io/citation-dev/citation:latest
```

```dockerfile
# Dockerfile
FROM ghcr.io/citation-dev/citation:latest
```

## Reporting a Vulnerability

citationに関する脆弱性を発見した際はDiscordやTwitter、Issue、Discussionsでは報告せず、 [citation@m2en.dev](mailto:citation@m2en.dev) まで **PGP鍵での暗号化を行って** メールで報告してください。

暗号化を行う際は、以下の公開鍵を利用してください。

鍵指紋: `78E4 CFE0 B3B2 0C4C 7BAA A3CA 6554 A829 D251 53F9`

[pgp_keys.asc](https://keybase.io/m2en/pgp_keys.asc?fingerprint=78e4cfe0b3b20c4c7baaa3ca6554a829d25153f9)

<details>
<summary>GitHubから報告する(Beta)</summary>

----
  
 
GPG鍵署名でのメール報告の他に citation-dev/citation の [**Security Tab**](https://github.com/citation-dev/citation/security) からでも報告することができます。
  
![image](https://user-images.githubusercontent.com/82575685/208250469-06d9e9c5-7804-4806-8122-e4b0628d7b16.png)

詳細は [Privately reporting a security vulnerability - GitHub Docs](https://docs.github.com/en/code-security/security-advisories/guidance-on-reporting-and-writing/privately-reporting-a-security-vulnerability) を確認してください。
  
</details>

----

また報告する際は以下の情報をできる限り多く提供してください。

- 脆弱性の種類
  - (例: SQL インジェクション、コード・インジェクション、リソース管理の問題、設計上の問題、認可・権限・アクセス制御)
- 脆弱性に関連するソースファイルのフルパス
- 脆弱性に関連するソースコードのタグ、コミットハッシュ、permlink など
- 脆弱性を再現するために必要な特別な設定
- 問題を再現するための手順
- PoC(概念実証)(可能であれば)
- 脆弱性の影響
