package de.craftlancer.currencyhandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.craftlancer.currencyhandler.handler.FoodHandler;
import de.craftlancer.currencyhandler.handler.HealthHandler;
import de.craftlancer.currencyhandler.handler.ItemHandler;
import de.craftlancer.currencyhandler.handler.LevelHandler;
import de.craftlancer.currencyhandler.handler.MoneyHandler;

@SuppressWarnings("rawtypes")
public class CurrencyHandler extends JavaPlugin
{
    private FileConfiguration config;
    public static Logger log = Bukkit.getLogger();
    public static HashMap<String, Handler> handlerList = new HashMap<String, Handler>();
    
    @Override
    public void onEnable()
    {
        log = getLogger();
        config = getConfig();
        
        if (config.getBoolean("general.handler.economy.activate", true))
            if (getServer().getPluginManager().getPlugin("Vault") != null)
            {
                RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
                if (economyProvider != null)
                    registerCurrency("money", new MoneyHandler(economyProvider.getProvider(), config.getString("general.handler.money.name", "Dollar")));
            }
        
        if (config.getBoolean("item.activate", true))
            registerCurrency("item", new ItemHandler());
        if (config.getBoolean("food.activate", true))
            registerCurrency("food", new FoodHandler(config.getString("food.name", "Food")));
        if (config.getBoolean("health.activate", true))
            registerCurrency("health", new HealthHandler(config.getString("health.name", "Health")));
        if (config.getBoolean("level.activate", true))
            registerCurrency("level", new LevelHandler(config.getString("level.name", "Level")));
        if (config.getBoolean("xp.activate", true))
            registerCurrency("xp", new LevelHandler(config.getString("xp.name", "XP")));
    }
    
    /**
     * Register a new currency to be used with this plugin
     * 
     * @param key the key, used in config for the "currency"
     * @param handler the handler object
     * @return false when something went wrong, true otherwise
     */
    public static boolean registerCurrency(String key, Handler handler)
    {
        if (handler == null)
            return false;
        
        if (handlerList.containsKey(key))
        {
            log.info("Conflict with key: " + key);
            return false;
        }
        
        handlerList.put(key, handler);
        log.info("Registering handler for key: " + key);
        return true;
    }
    
    public static Handler getHandler(String key)
    {
        return handlerList.get(key);
    }
    
    public static boolean hasHandler(String key)
    {
        return handlerList.containsKey(key);
    }
    
    @SuppressWarnings("unchecked")
    public static boolean hasCurrency(Player p, Map<String, Object> s)
    {
        for (Entry<String, Object> set : s.entrySet())
            if (hasHandler(set.getKey()))
                if (getHandler(set.getKey()).checkInputClass(set.getValue()))
                    if (!getHandler(set.getKey()).hasCurrency(p, set.getValue()))
                        return false;
        
        return true;
    }
}
