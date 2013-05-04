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
    public String getCurrencyName()
    {
        return currency;
    }
    
    @Override
    public boolean checkInputClass(Object obj)
    {
        return obj instanceof Number;
    }
    
    @Override
    public String getFormatedString(Object value)
    {
        return ((Number) value).toString() + " " + getCurrencyName();
    }
}
