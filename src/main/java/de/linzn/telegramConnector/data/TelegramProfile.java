/*
 * Copyright (C) 2020. Niklas Linz - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.telegramConnector.data;


import de.azcore.azcoreRuntime.AppLogger;
import de.azcore.azcoreRuntime.modules.notificationModule.INotificationProfile;
import de.azcore.azcoreRuntime.modules.notificationModule.NotificationContainer;
import de.azcore.azcoreRuntime.modules.notificationModule.NotificationPriority;
import de.linzn.telegramConnector.TelegramConnectorPlugin;
import de.linzn.telegramapi.TelegramAPI;

public class TelegramProfile implements INotificationProfile {
    private String chatID;
    private String token;

    public TelegramProfile() {
        this.chatID = TelegramConnectorPlugin.telegramConnectorPlugin.getDefaultConfig().getString("defaultChatID", "-100000");
        this.token = TelegramConnectorPlugin.telegramConnectorPlugin.getDefaultConfig().getString("token", "xxxxxxxx:xxxxxxxxxxxxxxxxx");
        TelegramConnectorPlugin.telegramConnectorPlugin.getDefaultConfig().save();
    }

    @Override
    public void push(NotificationContainer notificationContainer) {
        if (notificationContainer.notificationPriority.hasPriority(NotificationPriority.DEFAULT)) {
            AppLogger.debug("Telegram to chatId: " + chatID);
            TelegramAPI telegramAPI = new TelegramAPI(token);
            System.out.println(telegramAPI.sendMessage(chatID, notificationContainer.notification).getResponse());
        }
    }
}
