# 開発: 便利クラス

!!! caution 

    ここから先は開発者向けのページです。

これらは開発協力する際に使える便利クラスです。

## Utils

### 環境変数を取得する - `getKey()`

```kotlin
fun getEnv(key: String): String {
    return dotenv.get(key) ?: throw Error("次の環境変数が指定されていません: $key")
}
```

- 第一引数のKeyを持つ環境変数の値を返します。
- ない場合は例外を返します。

### ランダムなHSBカラーを作成する - `randomColor()`

!!! danger "非推奨"

    v2.0.0から導入されましたが不具合により、現在は使用されていません。今後不具合が修正されない場合は削除されます。

```kotlin
fun randomColor(): Color {
    val h = (0..0.999.toInt()).random().toFloat()
    val s = (0..1).random().toFloat()
    val b = (0..1).random().toFloat()

    return Color.getHSBColor(h, s, b)
}
```

### 文字数の確認を行う - `checkLimit()`

```kotlin
fun checkLimit(target: String, limit: Int): Boolean {
    if (target.length <= limit) {
        return false
    }
    return true
}
```

- 指定文字列の文字数を確認します。
    - 超えている場合は `false`
    - 超えていない場合は `true`
- Embedの各オブジェクトの値最大値を超えないように確認を取るときに使えます。
