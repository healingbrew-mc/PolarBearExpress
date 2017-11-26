package dyn.healingbrew.polarbearexpress;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

@SuppressWarnings({"WeakerAccess"})
public class Config {
    public static boolean LOADED = false;

    public static boolean AllowChildTaming = false;
    public static boolean AllowSharing = false;
    public static ResourceLocation TamingItem = new ResourceLocation("minecraft", "fish");
    public static int MinimumAttempts = 0;
    public static float ChancePerAttempt = 0.2f;
    public static float IncreaseOddsWithAttempt = 0.0f;

    public static Configuration CONFIG = null;

    public static void Load(Configuration config) throws Exception {
        CONFIG = config;

        int version = config.getInt("version", "healingbrew", 1, 0, Integer.MAX_VALUE, "Don't edit this, used to keep track of config versions");

        if(version > 1) {
            throw new Exception("Malformed configuration file, cannot load version " + version);
        }

        if(version >= 1) {
            AllowChildTaming = config.getBoolean("allow_child_taming", "taming_behavior", Config.AllowChildTaming, "Allow child polar bears to be tamed");
            AllowSharing = config.getBoolean("allow_sharing", "taming_behavior", Config.AllowSharing, "Allow other people to mount bears you've tamed");

            TamingItem = new ResourceLocation(config.getString("taming_item", "taming_behavior", TamingItem.toString(), "Item to tame polar bears with"));

            MinimumAttempts = config.getInt("minimum_attempts", "taming_behavior", Config.MinimumAttempts, 0, Integer.MAX_VALUE, "Minimum number of attempts to tame a polar bear before taming a polar bear");
            ChancePerAttempt = config.getFloat("chance", "taming_behavior", Config.ChancePerAttempt, 0.1f, 1.0f, "Allow child polar bears to be tamed");
            IncreaseOddsWithAttempt = config.getFloat("feeling_lucky", "taming_behavior", Config.IncreaseOddsWithAttempt, 0.0f, 1.0f, "Increase the odds of taming a polar bear with each attempt by this amount");
        } else {
            throw new Exception("Cannot resolve config loaders for version " + version);
        }

        LOADED = true;
        config.save();
    }
}
