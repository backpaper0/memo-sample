# ｃ⌒っﾟдﾟ)っφ　ﾒﾓﾒﾓ...

[フロントエンド初心者勉強会 in 大阪](https://kanjava.connpass.com/event/49104/)
に向けて作ったメモアプリケーション。

## 材料

* [Vue.js](https://jp.vuejs.org/)
* [Bulma](http://bulma.io/)
* [marked](https://github.com/chjj/marked)
* [Spring Boot](https://projects.spring.io/spring-boot/)
* [Doma](https://doma.readthedocs.io/ja/stable/)
* [H2](http://www.h2database.com/html/main.html)

## 起動方法

```console
gradlew bootRun
```

[http://localhost:8080](http://localhost:8080)
をブラウザで開く。

[http://localhost:8080/h2-console](http://localhost:8080/h2-console)
でH2のコンソールも見れる。

* JDBC URL: `jdbc:h2:file:./build/memo-db`
* JDBC Username: `sa`
* JDBC Password: `secret`

## License

Licensed under [The MIT License](https://opensource.org/licenses/MIT)

