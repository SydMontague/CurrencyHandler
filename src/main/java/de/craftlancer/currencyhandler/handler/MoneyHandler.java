package de.craftlancer.currencyhandler.handler;

import java.util.UUID;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import de.craftlancer.currencyhandler.Handler;

public class MoneyHandler implements Handler
{
    private Economy economy;
    private String currency;
    
    public MoneyHandler(Economy economy, String currency)
    {
        this.economy = economy;
        this.currency = currency;
    }
    
    @Override
    public boolean hasCurrency(Object holder, Object amount)
    {
        Number number = convertInputObject(amount);
        OfflinePlayer player = convertInputHolder(holder);
        
        if (number == null)
            return false;
        
        if (player == null)
            return false;
        
        return economy.has(player, number.doubleValue());
    }
    
    @Override
    public void withdrawCurrency(Object holder, Object amount)
    {
        Number number = convertInputObject(amount);
        OfflinePlayer player = convertInputHolder(holder);
        
        if (number == null)
            return;
        
        if (player == null)
            return;
        
        economy.withdrawPlayer(player, number.doubleValue());
    }
    
    @Override
    public void giveCurrency(Object holder, Object amount)
    {
        Number number = convertInputObject(amount);
        OfflinePlayer player = convertInputHolder(holder);
        
        if (number == null)
            return;
        
        if (player == null)
            return;
        
        economy.depositPlayer(player, number.doubleValue());
    }
    
    @Override
    public void setCurrency(Object holder, Object amount)
    {
        Number number = convertInputObject(amount);
        OfflinePlayer player = convertInputHolder(holder);
        
        if (number == null)
            return;
        
        if (player == null)
            return;
        
        double diff = number.doubleValue() - economy.getBalance(player);
        if (diff < 0)
            economy.withdrawPlayer(player, -diff);
        else
            economy.depositPlayer(player, diff);
    }
    
    @Override
    public String getFormatedString(Object value)
    {
        if (!checkInputObject(value))
            return "INVALID INPUT OBJECT";
        
        return value.toString() + " " + getCurrencyName();
    }
    
    @Override
    public String getCurrencyName()
    {
        return currency;
    }
    
    @Override
    public boolean checkInputObject(Object obj)
    {
        return convertInputObject(obj) != null;
    }
    
    @Override
    public boolean checkInputHolder(Object obj)
    {
        return convertInputObject(obj) != null;
    }
    
    @Override
    public Number convertInputObject(Object obj)
    {
        if (obj instanceof Number)
            return (Number) obj;
        
        try
        {
            return Integer.parseInt(obj.toString());
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public OfflinePlayer convertInputHolder(Object obj)
    {
        if (obj instanceof OfflinePlayer)
            return (OfflinePlayer) obj;
        
        if (obj instanceof UUID)
            return Bukkit.getOfflinePlayer(((UUID) obj));
        
        try
        {
            return Bukkit.getOfflinePlayer(UUID.fromString(obj.toString()));
        }
        catch (IllegalArgumentException e)
        {
            return Bukkit.getOfflinePlayer(obj.toString());
        }
    }
}
