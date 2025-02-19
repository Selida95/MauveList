package net.mattlabs.mauvelist.util;

import net.mattlabs.mauvelist.MauveList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public class PlayerManager implements Runnable{

    private MauveList mauveList = MauveList.getInstance();
    private LinkedList<UUID> nonMemberUUID;
    private LinkedList<String> nonMemberName, nonMemberDate;

    public void addPlayer(Player player) {
        nonMemberName.remove(player.getName());
        nonMemberName.addFirst(player.getName());
        if (nonMemberName.size() == 11) nonMemberName.removeLast();
        nonMemberUUID.remove(player.getUniqueId());
        nonMemberUUID.addFirst(player.getUniqueId());
        if (nonMemberUUID.size() == 11) nonMemberUUID.removeLast();

        Date date = new Date(player.getLastLogin());
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        nonMemberDate.remove(dateFormat.format(date));
        nonMemberDate.addFirst(dateFormat.format(date));
        if (nonMemberDate.size() == 11) nonMemberDate.removeLast();


        Bukkit.getServer().getScheduler().runTaskAsynchronously(MauveList.getInstance(), this);
    }

    public void loadPlayerData() {
        nonMemberUUID = new LinkedList<>();
        nonMemberName = new LinkedList<>();
        nonMemberDate = new LinkedList<>();
        ArrayList<String> loadList = mauveList.getData().getNonMemberList();
        for (String string : loadList) {
            String[] parts = string.split("\\|");
            nonMemberUUID.add(UUID.fromString(parts[0]));
            nonMemberName.add(parts[1]);
            nonMemberDate.add(parts[2]);
        }
    }

    public LinkedList<UUID> getNonMemberUUID() {
        return nonMemberUUID;
    }

    public LinkedList<String> getNonMemberName() {
        return nonMemberName;
    }

    public LinkedList<String> getNonMemberDate() {
        return nonMemberDate;
    }

    // Saves data to file asynchronously
    @Override
    public void run() {
        ArrayList<String> saveList = new ArrayList<>();
        ArrayList<String> actualList = mauveList.getData().getNonMemberList();
        for (int i = 0; i < nonMemberName.size(); i++) saveList.add(nonMemberUUID.get(i) + "|" + nonMemberName.get(i) + "|" + nonMemberDate.get(i));

        actualList = saveList;
        mauveList.getConfigurateManager().save("data.conf");
    }
}
