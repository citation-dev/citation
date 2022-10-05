!!! warning

    GitHub の `SECURITY.md` ファイルとcitation docsに差異がある場合は、GitHub の `SECURITY.md` ファイルに記載されているものが優先されます。

    - [citation Security Policy](https://github.com/m2en/citation/security/policy)

# Security Policy

citation のセキュリティポリシーについてです。

## Supported Versions

citation は、GitHub Packages Registry(ghcr.io)にて、ビルド済みイメージを公開しており、利用者自身が利用したいバージョンのイメージを自由に使用することが可能です。

以下の表は現在コントリビューターがサポートしている citation のバージョン一覧となります。

| Version                     | Status     | Support Start Date                                                 | Support End Date                                                       |
| --------------------------- | ---------- | ------------------------------------------------------------------ | ---------------------------------------------------------------------- |
| v1 (`v1.0.0`～)             | `Active`   | [2022/08/21](https://github.com/m2en/citation/releases/tag/v1.0.0) | 現在サポート中                                                         |
| v0 (`v0.1.0`～`v1.0.0-rc1`) | `Inactive` | [2022/08/15](https://github.com/m2en/citation/releases/tag/v0.1.0) | [2022/08/19](https://github.com/m2en/citation/releases/tag/v1.0.0-rc1) |

`Status` が `Inactive` となっているバージョンは、サポート対象外となります。 セキュリティパッチは例外を除いて配信されません。

Dockerfile などで利用する際のバージョン指定の際はなるべく `Active` になっているものを選ぶことを推奨します。

```yml
# Docker Compose
services:
  citation:
    image: ghcr.io/m2en/citation:latest
```

```shell
# Docker Pull
docker pull ghcr.io/m2en/citation:latest
```

```dockerfile
# Dockerfile
FROM ghcr.io/m2en/citation:latest
```

## Reporting a Vulnerability

citation に関する脆弱性を発見した際は Discord や Twitter、Issue、Discussions では報告せず、 [me@m2en.dev](mailto:me@m2en.dev) まで **PGP 鍵での暗号化を行って** メールで報告してください。

暗号化を行う際は、以下の公開鍵を利用してください。

鍵指紋: `6E23 C654 C587 E55D FAFA 8D47 15DB 72F0 6F2A CC5C`

[pgp_keys.asc](https://keybase.io/merunno/pgp_keys.asc?fingerprint=6e23c654c587e55dfafa8d4715db72f06f2acc5c)

---

また報告する際は以下の情報をできる限り多く提供してください。

- 脆弱性の種類
  - (例: SQL インジェクション、コード・インジェクション、リソース管理の問題、設計上の問題、認可・権限・アクセス制御)
- 脆弱性に関連するソースファイルのフルパス
- 脆弱性に関連するソースコードのタグ、コミットハッシュ、permlink など
- 脆弱性を再現するために必要な特別な設定
- 問題を再現するための手順
- PoC(概念実証)(可能であれば)
- 脆弱性の影響
