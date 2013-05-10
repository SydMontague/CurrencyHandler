package de.craftlancer.currencyhandler.handler;

import org.bukkit.entity.Player;

import de.craftlancer.currencyhandler.Handler;

public class LevelHandler implements Handler<Integer>
{
    String name = "Level";
    
    public LevelHandler(String name)
    {
        this.name = name;
    }
    
    @Override
    public boolean hasCurrency(Player p, Integer amount)
    {
        return p.getLevel() >= amount;
    }
    
    @Override
    public void withdrawCurrency(Player p, Integer amount)
    {
        p.setLevel(p.getLevel() - amount);
    }
    
    @Override
    public void giveCurrency(Player p, Integer amount)
    {
        p.setLevel(p.getLevel() + amount);
    }
    
    @Override
    public String getCurrencyName()
    {
        return name;
    }
    
    @Override
    public boolean checkInputObject(Object obj)
    {
        return (obj instanceof Integer);
    }
    
    @Override
    public String getFormatedString(Object value)
    {
        return value.toString() + " " + getCurrencyName();
    }
}
