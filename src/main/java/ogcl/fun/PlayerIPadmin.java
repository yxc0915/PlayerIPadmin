package ogcl.fun;

import org.bukkit.plugin.java.JavaPlugin;

public class PlayerIPadmin extends JavaPlugin {
    private DatabaseManager dbManager;

    @Override
    public void onEnable() {
        saveDefaultConfig(); // 确保配置文件被加载
        dbManager = new DatabaseManager(this); // 初始化数据库管理器
        // 注册getip命令执行器
        this.getCommand("getip").setExecutor(new Commands(this, dbManager));
        // 注册事件监听器
        getServer().getPluginManager().registerEvents(new PlayerLoginListener(this, dbManager), this);
        getLogger().info("PlayerIPadmin 插件已启动！");
    }

    @Override
    public void onDisable() {
        getLogger().info("PlayerIPadmin 插件已关闭！");
    }
}
