package de.kaktus4.autogl.core.listener;

import de.kaktus4.autogl.core.AutoGLAddon;
import de.kaktus4.autogl.core.server.ServerConfiguration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

public class ChatListener {

  private final AutoGLAddon addon;

  public ChatListener(AutoGLAddon addon) {
    this.addon = addon;
  }

  @Subscribe
  public void onChatMessageReceived(ChatReceiveEvent event) {
    if (!addon.configuration().enabled().get()) {
      return;
    }

    ServerData currentServer = Laby.labyAPI().serverController().getCurrentServerData();
    if (currentServer == null) {
      return;
    }

    String plainMessage = event.chatMessage().getOriginalPlainText();
    String currentHostAddress = currentServer.address().getHost().toLowerCase();

    if (!addon.cacheManager.qualifies(plainMessage, currentHostAddress)) {
      return;
    }

    for (ServerConfiguration serverConfig : addon.getServerRegistry().getServerConfigurations()) {
      boolean serverMatches = false;

      for (String configuredAddress : serverConfig.getServerAddresses()) {
        if (currentHostAddress.contains(configuredAddress)) {
          serverMatches = true;
          break;
        }
      }

      if (serverMatches) {
        boolean isExcludedByFilter = false;
        for (String filterTerm : serverConfig.getExclusionFilters()) {
          if (plainMessage.contains(filterTerm)) {
            isExcludedByFilter = true;
            addon.cacheManager.add(plainMessage, false, currentHostAddress);
            break;
          }
        }

        if (isExcludedByFilter) {
          continue;
        }

        for (Pattern trigger : serverConfig.getTriggerMessages()) {
          Matcher matcher = trigger.matcher(plainMessage);
          if (matcher.find()) {
            addon.trySendGoodLuckMessage(matcher.group(1));
            addon.cacheManager.add(plainMessage, true, currentHostAddress);
            return;
          }
        }

        // no match found, does not qualify
        addon.cacheManager.add(plainMessage, false, currentHostAddress);
      }
    }
  }
}