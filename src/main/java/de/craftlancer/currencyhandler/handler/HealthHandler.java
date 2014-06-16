package de.craftlancer.currencyhandler.handler;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;

import de.craftlancer.currencyhandler.Handler;

public class HealthHandler implements Handler
{
    private String name = "Health";
    
    public HealthHandler(String name)
    {
        this.name = name;
    }
    
    @Override
    public boolean hasCurrency(Object holder, Object amount)
    {
        Damageable entity = convertInputHolder(holder);
        Number number = convertInputObject(amount);
        
        if (entity == null)
            return false;
        
        if (number == null)
            return false;
        
        return entity.getHealth() >= number.doubleValue();
    }
    
    @Override
    public void withdrawCurrency(Object holder, Object amount)
    {
        Damageable entity = convertInputHolder(holder);
        Number number = convertInputObject(amount);
        
        if (entity == null)
            return;
        
        if (number == null)
            return;
        
        entity.setHealth(entity.getHealth() - number.doubleValue());
    }
    
    @Override
    public void giveCurrency(Object holder, Object amount)
    {
        Damageable entity = convertInputHolder(holder);
        Number number = convertInputObject(amount);
        
        if (entity == null)
            return;
        
        if (number == null)
            return;
        
        entity.setHealth(entity.getHealth() + number.doubleValue());
    }
    
    @Override
    public void setCurrency(Object holder, Object amount)
    {
        Damageable entity = convertInputHolder(holder);
        Number number = convertInputObject(amount);
        
        if (entity == null)
            return;
        
        if (number == null)
            return;
        
        entity.setHealth(number.doubleValue());
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
    public Damageable convertInputHolder(Object obj)
    {
        if (obj instanceof Damageable)
            return (Damageable) obj;
        
        if (obj instanceof UUID)
            return Bukkit.getPlayer(((UUID) obj));
        
        try
        {
            return Bukkit.getPlayer(UUID.fromString(obj.toString()));
        }
        catch (IllegalArgumentException e)
        {
            return Bukkit.getPlayer(obj.toString());
        }
    }

    @Override
    public Number convertInputObject(Object obj)
    {
        if (obj instanceof Number)
            return (Number) obj;
        
        try
        {
            return Double.parseDouble(obj.toString());
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }
}
