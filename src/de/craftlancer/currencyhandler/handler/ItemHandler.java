package de.craftlancer.currencyhandler.handler;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.craftlancer.currencyhandler.Handler;

public class ItemHandler implements Handler<List<?>>
{
    private String name = "Item";
    
    @Override
    public boolean hasCurrency(Player p, List<?> amount)
    {
        for (Object s : amount)
        {
            ItemStack item = getItemStack(s);
            if (!p.getInventory().containsAtLeast(item, item.getAmount()))
                return false;
        }
        return true;
    }
    
    @Override
    public void withdrawCurrency(Player p, List<?> amount)
    {
        for (Object s : amount)
            p.getInventory().removeItem(getItemStack(s));
    }
    
    @Override
    public void giveCurrency(Player p, List<?> amount)
    {
        for (Object s : amount)
            p.getInventory().addItem(getItemStack(s));
    }
    
    @Override
    public void setCurrency(Player p, List<?> amount)
    {
        throw new UnsupportedOperationException("ItemHandler does not support setCurrency()!");
    }
    
    @Override
    public String getFormatedString(List<?> value)
    {
        String output = "";
        
        for (Object s : value)
            output += getItemString(s.toString()) + " ";
        
        return output;
    }
    
    @Override
    public String getCurrencyName()
    {
        return name;
    }
    
    @Override
    public boolean checkInputObject(Object obj)
    {
        if (!(obj instanceof List<?>))
            return false;
        
        for (Object s : (List<?>) obj)
            if (isItemStack(s))
                return false;
        
        return true;
    }
    
    private static String getItemString(Object obj)
    {
        if (obj instanceof ItemStack)
        {
            ItemStack item = (ItemStack) obj;
            return item.getAmount() + " " + item.getType().name() + (item.getDurability() >= 0 ? ":" + item.getDurability() : "");
        }
        
        String str = obj.toString();
        int id;
        int data = -1;
        int amount;
        
        String value[] = str.split(" ");
        
        if (value[0].contains(":"))
        {
            id = Integer.parseInt(value[0].split(":")[0]);
            data = Integer.parseInt(value[0].split(":")[1]);
        }
        else
            id = Integer.parseInt(value[0]);
        
        amount = Integer.parseInt(value[1]);
        
        return amount + " " + Material.getMaterial(id).name() + (data >= 0 ? ":" + data : "");
    }
    
    private static ItemStack getItemStack(Object obj)
    {
        if (obj instanceof ItemStack)
            return (ItemStack) obj;
        
        String str = obj.toString();
        int id;
        short data = -1;
        int amount;
        
        String value[] = str.split(" ");
        
        if (value[0].contains(":"))
        {
            id = Integer.parseInt(value[0].split(":")[0]);
            data = Short.parseShort(value[0].split(":")[1]);
        }
        else
            id = Integer.parseInt(value[0]);
        
        amount = Integer.parseInt(value[1]);
        
        return data == -1 ? new ItemStack(id, amount) : new ItemStack(id, amount, data);
    }
    
    private static boolean isItemStack(Object obj)
    {
        if(obj instanceof ItemStack)
            return true;
        
        String str = obj.toString();
        
        if (str == null)
            return false;
        
        String value[] = str.split(" ");
        
        if (value.length != 2)
            return false;
        
        if (!(value[0].contains(":") && value[0].split(":")[0].matches("[0-9]") && value[0].split(":")[1].matches("[0-9]")))
            return false;
        else if (!value[0].matches("[0-9]"))
            return false;
        
        if (!value[1].matches("[0-9]"))
            return false;
        
        return true;
    }
}
