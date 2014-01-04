package de.craftlancer.currencyhandler.handler;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Player;

import de.craftlancer.currencyhandler.Handler;

public class MoneyHandler implements Handler<Number>
{
    Economy economy;
    String currency;
    
    public MoneyHandler(Economy economy, String currency)
    {
        this.economy = economy;
        this.currency = currency;
    }
    
    @Override
    public boolean hasCurrency(Player p, Number amount)
    {
        return economy.has(p.getName(), amount.doubleValue());
    }
    
    @Override
    public void withdrawCurrency(Player p, Number amount)
    {
        economy.withdrawPlayer(p.getName(), amount.doubleValue());
    }
    
    @Override
    public void giveCurrency(Player p, Number amount)
    {
        economy.depositPlayer(p.getName(), amount.doubleValue());
    }
    
    @Override
    public void setCurrency(Player p, Number amount)
    {
        double diff = amount.doubleValue() - economy.getBalance(p.getName());
        if (diff < 0)
            economy.withdrawPlayer(p.getName(), amount.doubleValue());
        else
            economy.depositPlayer(p.getName(), amount.doubleValue());
    }
    
    @Override
    public String getFormatedString(Number value)
    {
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
        return obj instanceof Number;
    }
}