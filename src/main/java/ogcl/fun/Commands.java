package ogcl.fun;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Commands implements CommandExecutor {
    private final PlayerIPadmin plugin;
    private final DatabaseManager dbManager;

    public Commands(PlayerIPadmin plugin, DatabaseManager dbManager) {
        this.plugin = plugin;
        this.dbManager = dbManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("getip") && args.length == 1) {
            if (!sender.hasPermission("playeripadmin.getip")) {
                sender.sendMessage(ChatColor.RED + "你没有权限使用这个命令。");
                return true;
            }

            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "玩家 " + args[0] + " 不在线或不存在。");
                return true;
            }

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                String ip = target.getAddress().getAddress().getHostAddress();
                String locationInfo = getLocationInfo(ip);
                Bukkit.getScheduler().runTask(plugin, () -> {
                    // 在地理位置信息前添加换行符以换行显示
                    sender.sendMessage(ChatColor.GREEN + "玩家 " + target.getName() + " 的IP: " + ip + "\n地理位置: \n" + locationInfo);
                });
            });
            return true;
        }
        return false;
    }

    private String getLocationInfo(String ip) {
        String apiUrl = "https://www.ip.cn/api/index?ip=" + ip + "&type=1";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            if (jsonResponse.get("code").getAsInt() == 0) {
                return jsonResponse.get("address").getAsString();
            }
        } catch (Exception e) {
            Bukkit.getLogger().warning("[PlayerIPadmin] Failed to fetch location for IP: " + ip + " - " + e.getMessage());
        }
        return "未知";
    }
}
