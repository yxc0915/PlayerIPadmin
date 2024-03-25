# PlayerIPadmin

PlayerIPadmin 是一个Minecraft服务器插件，专为服务器管理员提供玩家IP地址和地理位置信息的查询功能。当玩家加入服务器时，自动向玩家和全服广播显示他们的地理位置信息。此外，插件还提供了一个API，允许其他插件开发者轻松获取玩家的IP地址和地理位置信息。

## 支持版本
Java1.20.4（已测试）


## 功能

- 自动在玩家加入时显示和广播玩家的地理位置信息。
- 提供`/getip`命令，使管理员能够查询玩家的IP地址和详细地理位置信息（国家、省份、地级市）。
- 提供API，允许其他插件获取玩家的IP和地理位置信息。
- - ![I({1(1QOAP$B6RUQFMSP$@A](https://github.com/yxc0915/PlayerIPadmin/assets/62410385/e3b13c37-56de-4c64-9bb6-ab6da85ff468)
- ![(TCM$XL7PHH9OS HS8F~H{6](https://github.com/yxc0915/PlayerIPadmin/assets/62410385/44ac5433-bb13-4703-8623-f364aed0ad49)


## 安装

1. 确保您的服务器运行Spigot、Paper或兼容的Minecraft服务器软件。
2. 从[插件发布页面](https://github.com/yxc0915/PlayerIPadmin/releases/)下载`PlayerIPadmin.jar`文件。
3. 将`PlayerIPadmin.jar`文件复制到您的服务器的`plugins`目录中。
4. 重启服务器。

## 使用方法

### 对于服务器管理员

- 使用`/getip <玩家名>`命令查询指定玩家的IP和地理位置信息。

### 对于插件开发者

- 可以通过`PlayerIPadmin`提供的API获取玩家的IP地址和地理位置信息。示例用法如下：

```java
PlayerIPadmin plugin = (PlayerIPadmin) Bukkit.getServer().getPluginManager().getPlugin("PlayerIPadmin");
if (plugin != null) {
    PlayerIPadminAPI api = plugin.getApi();
    String ip = api.getPlayerIP(player); // 获取玩家IP
    api.getPlayerLocationAsync(ip, locationInfo -> {
        // 处理地理位置信息
    });
}
```
## 权限
- playeripadmin.getip：允许使用/getip命令。

## 构建

如果你想从源代码构建PlayerIPadmin插件，需要以下步骤：

1. 克隆仓库到本地。
2. 使用Maven进行构建：`mvn clean package`。
3. 构建成功后，在`target`目录下找到`PlayerIPadmin.jar`。

## 贡献

欢迎任何形式的贡献，无论是功能请求、bug报告还是拉取请求。

## 许可证

[MIT](LICENSE) © [yxc0915]

