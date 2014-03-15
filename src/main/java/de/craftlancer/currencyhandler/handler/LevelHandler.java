package de.craftlancer.currencyhandler.handler;

import org.bukkit.entity.Player;

import de.craftlancer.currencyhandler.Handler;

public class LevelHandler implements Handler<Player, Integer>
{
    private String name = "Level";
    
    public LevelHandler(String name)
    {
        this.name = name;
    }
    
    @Override
    public boolean hasCurrency(Player holder, Integer amount)
    {
        return holder.getLevel() >= amount;
    }
    
    @Override
    public void withdrawCurrency(Player holder, Integer amount)
    {
        holder.setLevel(holder.getLevel() - amount);
    }
    
    @Override
    public void giveCurrency(Player holder, Integer amount)
    {
        holder.setLevel(holder.getLevel() + amount);
    }
    
    @Override
    public void setCurrency(Player holder, Integer amount)
    {
        holder.setLevel(amount);
    }
    
    @Override
    public String getFormatedString(Integer value)
    {
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
