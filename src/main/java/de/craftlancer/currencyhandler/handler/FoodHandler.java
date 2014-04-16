package de.craftlancer.currencyhandler.handler;

import org.bukkit.entity.Player;

import de.craftlancer.currencyhandler.Handler;

public class FoodHandler implements Handler
{
    private String name = "Food";
    
    public FoodHandler(String name)
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
        
        return ((Player) holder).getFoodLevel() >= ((Number) amount).intValue();
    }
    
    @Override
    public void withdrawCurrency(Object holder, Object amount)
    {
        if (!checkInputHolder(holder))
            return;
        
        if (!checkInputObject(amount))
            return;
        
        Player player = (Player) holder;
        player.setFoodLevel(player.getFoodLevel() - ((Number) amount).intValue());
    }
    
    @Override
    public void giveCurrency(Object holder, Object amount)
    {
        if (!checkInputHolder(holder))
            return;
        
        if (!checkInputObject(amount))
            return;
        
        Player player = (Player) holder;
        player.setFoodLevel(player.getFoodLevel() + ((Number) amount).intValue());
    }
    
    @Override
    public void setCurrency(Object holder, Object amount)
    {
        if (!checkInputHolder(holder))
            return;
        
        if (!checkInputObject(amount))
            return;
        
        ((Player) holder).setFoodLevel(((Number) amount).intValue());
    }
    
    @Override
    public String getFormatedString(Object value)
    {
        if (!checkInputObject(value))
            return "INVALID INPUT PARAMETER!";
        
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
        return obj instanceof Number;
    }
    
    @Override
    public boolean checkInputHolder(Object obj)
    {
        return obj instanceof Player;
    }
}
