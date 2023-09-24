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


import de.linzn.telegramConnector.TelegramConnectorPlugin;
import de.linzn.telegramapi.TelegramAPI;
import de.stem.stemSystem.STEMSystemApp;
import de.stem.stemSystem.modules.eventModule.handler.StemEventHandler;
import de.stem.stemSystem.modules.informationModule.InformationBlock;
import de.stem.stemSystem.modules.informationModule.InformationIntent;
import de.stem.stemSystem.modules.informationModule.events.InformationEvent;
import de.stem.stemSystem.modules.notificationModule.NotificationPriority;
import de.stem.stemSystem.modules.notificationModule.events.NotificationEvent;

public class TelegramNotificationListener {
    private String chatID;
    private String token;

    public TelegramNotificationListener() {
        this.chatID = TelegramConnectorPlugin.telegramConnectorPlugin.getDefaultConfig().getString("defaultChatID", "-100000");
        this.token = TelegramConnectorPlugin.telegramConnectorPlugin.getDefaultConfig().getString("token", "xxxxxxxx:xxxxxxxxxxxxxxxxx");
        TelegramConnectorPlugin.telegramConnectorPlugin.getDefaultConfig().save();
    }

    @StemEventHandler()
    public void onNotification(NotificationEvent notificationEvent) {
        if (notificationEvent.getNotificationPriority().hasPriority(NotificationPriority.DEFAULT)) {
            STEMSystemApp.LOGGER.INFO("Send telegram info with chatId: " + chatID);
            TelegramAPI telegramAPI = new TelegramAPI(token);
            telegramAPI.sendMessage(chatID, notificationEvent.getNotification()).getResponse();
        }
    }

    @StemEventHandler()
    public void inInformationEvent(InformationEvent informationEvent) {
        InformationBlock informationBlock = informationEvent.getInformationBlock();
        if(informationBlock.hasIntent(InformationIntent.NOTIFY_USER)){
            sendInformationBlock(informationBlock);
        } else {
            if (informationBlock.getSourcePlugin().getPluginName().equalsIgnoreCase("system-chain")) {
                sendInformationBlock(informationBlock);
            } else if (informationBlock.getSourcePlugin().getPluginName().equalsIgnoreCase("home-devices")) {
                sendInformationBlock(informationBlock);
            }
        }
    }

    private void sendInformationBlock(InformationBlock informationBlock) {
        STEMSystemApp.LOGGER.DEBUG("Listen to InformationEvent and forward to telegram: " + chatID);
        TelegramAPI telegramAPI = new TelegramAPI(token);
        telegramAPI.sendMessage(chatID, informationBlock.getLongDescription());
    }
}
