package de.craftlancer.currencyhandler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CurrencyHandler extends JavaPlugin
{
    private FileConfiguration config;
    public static Logger log = Bukkit.getLogger();
    public static HashMap<String, Handler> handlerList = new HashMap<String, Handler>();
    
    @Override
    public void onEnable()
    {
        log = getLogger();
        
        if (!new File(getDataFolder().getPath() + File.separatorChar + "config.yml").exists())
            saveDefaultConfig();
        
        config = getConfig();
        
        if (config.getBoolean("money.activate", true))
            if (getServer().getPluginManager().getPlugin("Vault") != null)
            {
                RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
                if (economyProvider != null)
                    registerCurrency("money", new MoneyHandler(economyProvider.getProvider(), config.getString("money.name", economyProvider.getProvider().currencyNamePlural())));
            }
        
        if (config.getBoolean("item.activate", true))
            registerCurrency("item", new ItemHandler());
        if (config.getBoolean("food.activate", true))
            registerCurrency("food", new FoodHandler(config.getString("food.name", "Food")));
        if (config.getBoolean("health.activate", true))
            registerCurrency("health", new HealthHandler(config.getString("health.name", "Health")));
        if (config.getBoolean("enchantlevel.activate", true))
            registerCurrency("enchantlevel", new LevelHandler(config.getString("enchantlevel.name", "Level")));
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
    
    /**
     * Get a registered Handler with a given key.
     * 
     * @param key the key of the handler
     * @return the Handler object with the given key, null if there is no
     *         registered handler for this key
     */
    public static Handler getHandler(String key)
    {
        return handlerList.get(key);
    }
    
    /**
     * Check if a handler with given key is registered.
     * 
     * @param key the key of the handler
     * @return true if a handler is found, false if there is no registered
     *         handler
     */
    public static boolean hasHandler(String key)
    {
        return handlerList.containsKey(key);
    }
    
    /**
     * Withdraw all given currencies from the player
     * Uses the CurrencyHandler plugin
     * 
     * @param p the player
     * @param input the currencies
     */
    public static void withdrawCurrencies(Player p, Set<Entry<String, Object>> input)
    {
        for (Entry<String, Object> set : input)
            if (hasHandler(set.getKey()))
                if (getHandler(set.getKey()).checkInputObject(set.getValue()))
                    getHandler(set.getKey()).withdrawCurrency(p, set.getValue());
    }
    
    /**
     * Give all given currencies to the player
     * 
     * @param p the player
     * @param input the currencies
     */
    public static void giveCurrencies(Player p, Set<Entry<String, Object>> input)
    {
        for (Entry<String, Object> set : input)
            if (hasHandler(set.getKey()))
                if (getHandler(set.getKey()).checkInputObject(set.getValue()))
                    getHandler(set.getKey()).giveCurrency(p, set.getValue());
    }
    
    /**
     * Check if a player has enough currencies
     * 
     * @param p the player
     * @param s the currencies
     * @return true if the player has the currencyies, false if not
     */
    public static boolean hasCurrencies(Player p, Map<String, Object> s)
    {
        for (Entry<String, Object> set : s.entrySet())
            if (hasHandler(set.getKey()))
                if (getHandler(set.getKey()).checkInputObject(set.getValue()))
                    if (!getHandler(set.getKey()).hasCurrency(p, set.getValue()))
                        return false;
        
        return true;
    }
}
