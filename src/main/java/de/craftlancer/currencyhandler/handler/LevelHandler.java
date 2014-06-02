package de.craftlancer.currencyhandler.handler;

import java.util.UUID;

import org.bukkit.Bukkit;
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
        Player player = convertInputHolder(holder);
        Number number = convertInputObject(amount);
        
        if (player == null)
            return false;
        
        if (number == null)
            return false;
        
        return player.getLevel() >= number.intValue();
    }
    
    @Override
    public void withdrawCurrency(Object holder, Object amount)
    {
        Player player = convertInputHolder(holder);
        Number number = convertInputObject(amount);
        
        if (player == null)
            return;
        
        if (number == null)
            return;
        
        player.setLevel(player.getLevel() - number.intValue());
    }
    
    @Override
    public void giveCurrency(Object holder, Object amount)
    {
        Player player = convertInputHolder(holder);
        Number number = convertInputObject(amount);
        
        if (player == null)
            return;
        
        if (number == null)
            return;
        
        player.setLevel(player.getLevel() + number.intValue());
    }
    
    @Override
    public void setCurrency(Object holder, Object amount)
    {
        Player player = convertInputHolder(holder);
        Number number = convertInputObject(amount);
        
        if (player == null)
            return;
        
        if (number == null)
            return;
        
        player.setLevel(number.intValue());
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
        return convertInputObject(obj) != null;
    }
    
    @Override
    public boolean checkInputHolder(Object obj)
    {
        return convertInputHolder(obj) != null;
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public Player convertInputHolder(Object obj)
    {
        if (obj instanceof Player)
            return (Player) obj;
        
        if (obj instanceof UUID)
            return Bukkit.getPlayer(((UUID) obj));
        
        return Bukkit.getPlayer(obj.toString());
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
}
