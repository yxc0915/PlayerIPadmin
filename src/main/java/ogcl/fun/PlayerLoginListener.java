package ogcl.fun;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PlayerLoginListener implements Listener {
    private final PlayerIPadmin plugin;
    private final DatabaseManager dbManager;

    public PlayerLoginListener(PlayerIPadmin plugin, DatabaseManager dbManager) {
        this.plugin = plugin;
        this.dbManager = dbManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CompletableFuture.runAsync(() -> {
            String ip = player.getAddress() != null ? player.getAddress().getAddress().getHostAddress() : "未知";
            if (!"未知".equals(ip)) {
                String locationInfo = getLocationInfo(ip);
                String city = parseCity(locationInfo);

                Bukkit.getScheduler().runTask(plugin, () -> {
                    String lastLocation = dbManager.getLastLoginLocation(player.getUniqueId().toString());
                    lastLocation = lastLocation == null ? "未知" : parseCity(lastLocation);

                    player.sendMessage(ChatColor.GREEN + "感谢选择樨苑云烟服务器！祝你玩的开心！\n你当前的登录位置是" + city + "，上一次登录位置" + lastLocation + "，请注意核实！");
                    Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "玩家" + player.getName() + "在" + city + "登录服务器！");

                    dbManager.updateLoginLocation(player.getUniqueId().toString(), locationInfo);
                });
            }
        });
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

    private String parseCity(String fullAddress) {
        // Assuming the address format is "Country Region City" and extracts the city part.
        String[] parts = fullAddress.split(" ");
        return parts.length > 2 ? parts[2] : fullAddress; // Returning the full address if parsing fails
    }
}
