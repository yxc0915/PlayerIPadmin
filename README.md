# PlayerIPadmin

PlayerIPadmin是一个为Minecraft服务器设计的插件，它提供了详细的玩家IP和地理位置信息查询功能。当玩家加入服务器时，插件会显示他们的地理位置（国家+省份+地级市），并且允许管理员通过`/getip`命令查询玩家的IP地址和地理位置信息。

## 功能

- **玩家加入提示**：当玩家加入游戏时，自动向玩家显示当前和上次登录的地理位置信息；同时向全服广播玩家的登录地点。
- **IP和地理位置查询**：允许管理员使用`/getip <玩家名>`命令查询指定玩家的IP地址和详细地理位置信息。
- ![I({1(1QOAP$B6RUQFMSP$@A](https://github.com/yxc0915/PlayerIPadmin/assets/62410385/e3b13c37-56de-4c64-9bb6-ab6da85ff468)
- ![(TCM$XL7PHH9OS HS8F~H{6](https://github.com/yxc0915/PlayerIPadmin/assets/62410385/44ac5433-bb13-4703-8623-f364aed0ad49)


## 安装

1. 确保你的Minecraft服务器运行的是支持插件的版本，如Spigot或Paper。
2. 下载`PlayerIPadmin.jar`文件。
3. 将下载的`PlayerIPadmin.jar`文件复制到你的服务器的`plugins`目录下。
4. 重启服务器，插件将自动加载。

## 使用

- **查看玩家IP和地理位置**：使用`/getip <玩家名>`命令。需要管理员权限。

## 配置

插件提供了一些可配置选项，这些选项可以在`plugins/PlayerIPadmin/config.yml`文件中找到和修改。当前可配置的选项包括：

- `messages.personalLoginMessage`: 定制发送给玩家的登录地理位置信息。
- `messages.broadcastLoginMessage`: 定制广播给全服的玩家登录地理位置信息。

## 权限

- `playeripadmin.getip`: 允许使用`/getip`命令查询玩家IP和地理位置。

## 构建

如果你想从源代码构建PlayerIPadmin插件，需要以下步骤：

1. 克隆仓库到本地。
2. 使用Maven进行构建：`mvn clean package`。
3. 构建成功后，在`target`目录下找到`PlayerIPadmin.jar`。

## 贡献

欢迎任何形式的贡献，无论是功能请求、bug报告还是拉取请求。

## 许可证

[MIT](LICENSE) © [yxc0915]

