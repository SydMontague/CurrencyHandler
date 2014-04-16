package de.craftlancer.currencyhandler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.mcstats.Metrics;
import org.mcstats.Metrics.Graph;

import de.craftlancer.currencyhandler.handler.FoodHandler;
import de.craftlancer.currencyhandler.handler.HealthHandler;
import de.craftlancer.currencyhandler.handler.ItemHandler;
import de.craftlancer.currencyhandler.handler.LevelHandler;
import de.craftlancer.currencyhandler.handler.MoneyHandler;

public class CurrencyHandler extends JavaPlugin
{
    private static CurrencyHandler instance;
    private FileConfiguration config;
    private static Logger log = Bukkit.getLogger();
    private HashMap<String, Handler> handlerList = new HashMap<String, Handler>();
    
    @Override
    public void onEnable()
    {
        instance = this;
        log = getLogger();
        
        if (!new File(getDataFolder(), "config.yml").exists())
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
        
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Metrics metrics = new Metrics(getInstance());
                    metrics.start();
                    
                    Graph currencies = metrics.createGraph("Currencies");
                    for (String e : getHandlerList().keySet())
                        currencies.addPlotter(new Metrics.Plotter(e)
                        {
                            @Override
                            public int getValue()
                            {
                                return 1;
                            }
                        });
                }
                catch (IOException e)
                {
                }
            }
        }.runTaskLater(this, 5L);
    }
    
    public static CurrencyHandler getInstance()
    {
        return instance;
    }
    
    public HashMap<String, Handler> getHandlerList()
    {
        return handlerList;
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
        
        if (hasHandler(key))
        {
            log.warning("Conflict with key: " + key);
            return false;
        }
        
        getInstance().getHandlerList().put(key, handler);
        log.info("Registering handler for key: " + key);
        return true;
    }
    
    /**
     * Get a registered Handler with a given key.
     * 
     * @param key the key of the handler
     * @return the Handler object with the given key, null if there is no registered handler for this key
     */
    public static Handler getHandler(String key)
    {
        return getInstance().getHandlerList().get(key);
    }
    
    /**
     * Check if a handler with given key is registered.
     * 
     * @param key the key of the handler
     * @return true if a handler is found, false if there is no registered handler
     */
    public static boolean hasHandler(String key)
    {
        return getInstance().getHandlerList().containsKey(key);
    }
    
    /**
     * Withdraw all given currencies from the holder.
     * If a currency is not compatible with the provided holder type, it will be ignored!
     * 
     * @param holder the holder of the currencies
     * @param input the currencies
     */
    public static void withdrawCurrencies(Object holder, Map<String, Object> input)
    {
        for (Entry<String, Object> set : input.entrySet())
            if (hasHandler(set.getKey()))
                getHandler(set.getKey()).withdrawCurrency(holder, set.getValue());
    }
    
    /**
     * Give all given currencies to the holder.
     * If a currency is not compatible with the provided holder type, it will be ignored!
     * 
     * @param holder the holder of the currencies
     * @param input the currencies
     */
    public static void giveCurrencies(Object holder, Map<String, Object> input)
    {
        for (Entry<String, Object> set : input.entrySet())
            if (hasHandler(set.getKey()))
                   getHandler(set.getKey()).giveCurrency(holder, set.getValue());
    }
    
    /**
     * Check if a holder has enough currencies.
     * 
     * @param holder the holder of the currencies
     * @param input the currencies
     * @return true if the player has the currencies, false if not or if one input value is not useable by the handler
     */
    public static boolean hasCurrencies(Object holder, Map<String, Object> input)
    {
        for (Entry<String, Object> set : input.entrySet())
            if (hasHandler(set.getKey()))
                    if (!getHandler(set.getKey()).hasCurrency(holder, set.getValue()))
                        return false;
        
        return true;
    }
    
    /**
     * Check if the given parameters are applicable to the handlers
     * 
     * @param holder the holder of the currencies
     * @param input the currencies
     * @return true of the input can be handled by every requested handler
     */
    public static boolean isApplicable(Object holder, Map<String, Object> input)
    {
        for (Entry<String, Object> set : input.entrySet())
            if (hasHandler(set.getKey()))
                if (!getHandler(set.getKey()).checkInputHolder(holder) || !getHandler(set.getKey()).checkInputObject(input))
                    return false;
        
        return true;
    }
}
