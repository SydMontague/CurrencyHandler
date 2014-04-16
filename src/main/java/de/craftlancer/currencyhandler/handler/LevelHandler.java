package de.craftlancer.currencyhandler.handler;

import org.bukkit.entity.Player;

import de.craftlancer.currencyhandler.Handler;

public class LevelHandler implements Handler
{
    private String name = "Level";
    
    public LevelHandler(String name)
    {
        this.name = name;
    }
    
    @Override
    public boolean hasCurrency(Object holder, Object amount)
    {
        if (!checkInputHolder(holder))
            return false;
        
        if (!checkInputObject(amount))
            return false;
        
        return ((Player) holder).getLevel() >= (Integer) amount;
    }
    
    @Override
    public void withdrawCurrency(Object holder, Object amount)
    {
        if (!checkInputHolder(holder))
            return;
        
        if (!checkInputObject(amount))
            return;
        
        Player player = (Player) holder;
        player.setLevel(player.getLevel() - (Integer) amount);
    }
    
    @Override
    public void giveCurrency(Object holder, Object amount)
    {
        if (!checkInputHolder(holder))
            return;
        
        if (!checkInputObject(amount))
            return;
        
        Player player = (Player) holder;
        player.setLevel(player.getLevel() + (Integer) amount);
    }
    
    @Override
    public void setCurrency(Object holder, Object amount)
    {
        if (!checkInputHolder(holder))
            return;
        
        if (!checkInputObject(amount))
            return;
        
        ((Player) holder).setLevel((Integer) amount);
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
        return name;
    }
    
    @Override
    public boolean checkInputObject(Object obj)
    {
        return obj instanceof Integer;
    }
    
    @Override
    public boolean checkInputHolder(Object obj)
    {
        return obj instanceof Player;
    }
}
