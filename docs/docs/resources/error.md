# Dealing with Errors

### `Cannot perform action due to a lack of Permission.`

```shell
[JDA MainWS-ReadThread] ERROR net.dv8tion.jda.api.JDA - One of the EventListeners had an uncaught exception
net.dv8tion.jda.api.exceptions.InsufficientPermissionException: Cannot perform action due to a lack of Permission. Missing permission: <権限名>
```

- `<権限名>` の権限が不足しているため、citationがアクションを実行できません。
