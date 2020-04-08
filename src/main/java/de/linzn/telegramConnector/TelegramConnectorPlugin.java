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

package de.linzn.telegramConnector;



import de.linzn.telegramConnector.data.TelegramProfile;
import de.stem.stemSystem.STEMSystemApp;
import de.stem.stemSystem.modules.pluginModule.STEMPlugin;


public class TelegramConnectorPlugin extends STEMPlugin {

    public static TelegramConnectorPlugin telegramConnectorPlugin;


    public TelegramConnectorPlugin() {
        telegramConnectorPlugin = this;
    }

    @Override
    public void onEnable() {
        STEMSystemApp.getInstance().getNotificationModule().registerNotificationProfile(new TelegramProfile());
    }

    @Override
    public void onDisable() {
    }
}
