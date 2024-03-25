package ogcl.fun;

import org.bukkit.plugin.java.JavaPlugin;

public class PlayerIPadmin extends JavaPlugin {
    private DatabaseManager dbManager;
    private Commands commands;
    private PlayerIPadminAPI api;

    @Override
    public void onEnable() {
        saveDefaultConfig(); // 确保加载默认配置
        dbManager = new DatabaseManager(this); // 初始化数据库管理器
        commands = new Commands(this, dbManager); // 初始化命令处理器
        getCommand("getip").setExecutor(commands); // 注册/getip命令

        api = new PlayerIPadminAPI(this); // 初始化API

        // 注册事件监听器
        getServer().getPluginManager().registerEvents(new PlayerLoginListener(this, dbManager), this);

        getLogger().info("PlayerIPadmin 插件已启动！");
    }

    @Override
    public void onDisable() {
        getLogger().info("PlayerIPadmin 插件已关闭！");
    }

    public PlayerIPadminAPI getApi() {
        return api;
    }

    // 如果其他地方需要访问Commands实例，可以提供getter方法
    public Commands getCommands() {
        return commands;
    }
}
