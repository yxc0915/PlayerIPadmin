package ogcl.fun;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PlayerIPadminAPI {
    private final PlayerIPadmin plugin;

    public PlayerIPadminAPI(PlayerIPadmin plugin) {
        this.plugin = plugin;
    }

    /**
     * 获取指定玩家的IP地址。
     *
     * @param player 目标玩家
     * @return 返回玩家的IP地址，如果无法获取则返回"未知"。
     */
    public String getPlayerIP(Player player) {
        if (player.getAddress() != null) {
            return player.getAddress().getAddress().getHostAddress();
        }
        return "未知";
    }

    /**
     * 异步方式获取指定IP的地理位置信息。
     *
     * @param ip       目标IP地址
     * @param callback 当获取到地理位置信息后将通过这个回调返回
     */
    public void getPlayerLocationAsync(String ip, LocationInfoCallback callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String locationInfo = getLocationInfo(ip);
            // 确保回调在主线程执行
            Bukkit.getScheduler().runTask(plugin, () -> callback.onLocationInfoReceived(locationInfo));
        });
    }

    /**
     * 从外部API获取IP地址对应的地理位置信息。
     *
     * @param ip 目标IP地址
     * @return 返回地理位置信息，如果失败则返回"未知"。
     */
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

    /**
     * 地理位置信息获取完成后的回调接口。
     */
    public interface LocationInfoCallback {
        void onLocationInfoReceived(String locationInfo);
    }
}
