## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## 購買注文システム

このプログラムは、TCP通信を使用してクライアントとサーバー間で購買注文をやり取りするシステムです。

## ファイル構成
- `OrderRequest.java`: 注文リクエストを表すクラス。
- `OrderResponse.java`: 注文レスポンスを表すクラス。
- `OrderServer.java`: サーバー側のプログラム。
- `OrderClient.java`: クライアント側のプログラム。

## 使用方法

### サーバーの起動方法
1. ターミナルまたはコマンドプロンプトを開く。
2. `OrderServer.java` をコンパイルして実行する。
    ```sh
    javac OrderServer.java
    java OrderServer
    ```
3. ポート番号を入力するように求められるので、使用したいポート番号を入力する（例: `5000`）。

### クライアントの起動方法
1. 別のターミナルまたはコマンドプロンプトを開く。
2. `OrderClient.java` をコンパイルして実行する。
    ```sh
    javac OrderClient.java
    java OrderClient
    ```
3. ポート番号を入力するように求められるので、サーバーで使用したポート番号と同じものを入力する（例: `5000`）。

### プログラムの実行
1. クライアント側で商品名と数量を入力する。
2. サーバーは受信した注文を処理し、確認メッセージを返信する。
3. クライアントはサーバーからの確認メッセージを受け取り、結果を表示する。
4. クライアントで `quit` または `exit` を入力するとプログラムを終了できる。