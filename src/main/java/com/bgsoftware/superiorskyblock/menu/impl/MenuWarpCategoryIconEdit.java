package com.bgsoftware.superiorskyblock.menu.impl;

import com.bgsoftware.common.config.CommentedConfiguration;
import com.bgsoftware.superiorskyblock.lang.Message;
import com.bgsoftware.superiorskyblock.api.island.warps.WarpCategory;
import com.bgsoftware.superiorskyblock.api.menu.ISuperiorMenu;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.bgsoftware.superiorskyblock.menu.SuperiorMenu;
import com.bgsoftware.superiorskyblock.menu.file.MenuPatternSlots;
import com.bgsoftware.superiorskyblock.utils.FileUtils;
import com.bgsoftware.superiorskyblock.player.chat.PlayerChat;
import com.bgsoftware.superiorskyblock.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public final class MenuWarpCategoryIconEdit extends SuperiorMenu {

    private static List<Integer> typeSlots, renameSlots, loreSlots, confirmSlots, iconSlots;

    private final WarpCategory warpCategory;
    private final ItemStack itemStack;
    private String itemName = null;
    private List<String> itemLore = null;

    private MenuWarpCategoryIconEdit(SuperiorPlayer superiorPlayer, WarpCategory warpCategory) {
        super("menuWarpCategoryIconEdit", superiorPlayer);
        this.warpCategory = warpCategory;
        this.itemStack = warpCategory == null ? null : warpCategory.getRawIcon();
        if (itemStack != null) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemName = itemMeta.getDisplayName();
            itemLore = itemMeta.getLore();
        }
    }

    public static void init() {
        MenuWarpCategoryIconEdit menuWarpCategoryIconEdit = new MenuWarpCategoryIconEdit(null, null);

        File file = new File(plugin.getDataFolder(), "menus/warp-category-icon-edit.yml");

        if (!file.exists())
            FileUtils.saveResource("menus/warp-category-icon-edit.yml");

        CommentedConfiguration cfg = CommentedConfiguration.loadConfiguration(file);

        MenuPatternSlots menuPatternSlots = FileUtils.loadGUI(menuWarpCategoryIconEdit, "warp-category-icon-edit.yml", cfg);

        typeSlots = getSlots(cfg, "icon-type", menuPatternSlots);
        renameSlots = getSlots(cfg, "icon-rename", menuPatternSlots);
        loreSlots = getSlots(cfg, "icon-relore", menuPatternSlots);
        confirmSlots = getSlots(cfg, "icon-confirm", menuPatternSlots);
        iconSlots = getSlots(cfg, "icon-slots", menuPatternSlots);

        menuWarpCategoryIconEdit.markCompleted();
    }

    public static void openInventory(SuperiorPlayer superiorPlayer, ISuperiorMenu previousMenu, WarpCategory warpCategory) {
        new MenuWarpCategoryIconEdit(superiorPlayer, warpCategory).open(previousMenu);
    }

    @Override
    protected void onPlayerClick(InventoryClickEvent e) {
        if (typeSlots.contains(e.getRawSlot())) {
            previousMove = false;
            e.getWhoClicked().closeInventory();

            Message.WARP_CATEGORY_ICON_NEW_TYPE.send(e.getWhoClicked());

            PlayerChat.listen((Player) e.getWhoClicked(), message -> {
                if (!message.equalsIgnoreCase("-cancel")) {
                    String[] sections = message.split(":");
                    Material material;

                    try {
                        material = Material.valueOf(sections[0].toUpperCase());
                        if (material == Material.AIR)
                            throw new IllegalArgumentException();
                    } catch (IllegalArgumentException ex) {
                        Message.INVALID_MATERIAL.send(e.getWhoClicked(), message);
                        return true;
                    }

                    String rawMessage = sections.length == 2 ? sections[1] : "0";

                    short data;

                    try {
                        data = Short.parseShort(rawMessage);
                        if (data < 0)
                            throw new IllegalArgumentException();
                    } catch (IllegalArgumentException ex) {
                        Message.INVALID_MATERIAL_DATA.send(e.getWhoClicked(), rawMessage);
                        return true;
                    }

                    itemStack.setType(material);
                    itemStack.setDurability(data);
                }

                open(previousMenu);
                PlayerChat.remove((Player) e.getWhoClicked());

                return true;
            });
        } else if (renameSlots.contains(e.getRawSlot())) {
            previousMove = false;
            e.getWhoClicked().closeInventory();

            Message.WARP_CATEGORY_ICON_NEW_NAME.send(e.getWhoClicked());

            PlayerChat.listen((Player) e.getWhoClicked(), message -> {
                if (!message.equalsIgnoreCase("-cancel")) {
                    itemName = message;
                }

                open(previousMenu);
                PlayerChat.remove((Player) e.getWhoClicked());

                return true;
            });
        } else if (loreSlots.contains(e.getRawSlot())) {
            previousMove = false;
            e.getWhoClicked().closeInventory();

            Message.WARP_CATEGORY_ICON_NEW_LORE.send(e.getWhoClicked());

            PlayerChat.listen((Player) e.getWhoClicked(), message -> {
                if (!message.equalsIgnoreCase("-cancel")) {
                    itemLore = Arrays.asList(message.split("\\\\n"));
                }

                open(previousMenu);
                PlayerChat.remove((Player) e.getWhoClicked());

                return true;
            });
        } else if (confirmSlots.contains(e.getRawSlot())) {
            e.getWhoClicked().closeInventory();

            Message.WARP_CATEGORY_ICON_UPDATED.send(e.getWhoClicked());

            warpCategory.setIcon(new ItemBuilder(itemStack).withName(itemName).withLore(itemLore).build());
        }
    }

    @Override
    public void cloneAndOpen(ISuperiorMenu previousMenu) {
        openInventory(superiorPlayer, previousMenu, warpCategory);
    }

    @Override
    protected Inventory buildInventory(Function<String, String> titleReplacer) {
        Inventory inventory = super.buildInventory(title -> title.replace("{0}", warpCategory.getName()));

        iconSlots.forEach(slot -> inventory.setItem(slot, new ItemBuilder(itemStack)
                .withName(itemName).withLore(itemLore).build()));

        return inventory;
    }

}