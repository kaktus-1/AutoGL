package de.kaktus4.autogl.core;

import de.kaktus4.autogl.core.cache.CacheManager;
import de.kaktus4.autogl.core.listener.ChatListener;
import de.kaktus4.autogl.core.server.ServerRegistry;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class AutoGLAddon extends LabyAddon<AutoGLConfiguration> {

  private ServerRegistry serverRegistry;
  public CacheManager cacheManager;

  private final AtomicBoolean recentlySentMessage = new AtomicBoolean(false);
  private Timer messageScheduler;

  @Override
  protected void enable() {
    this.registerSettingCategory();
    this.serverRegistry = new ServerRegistry();
    this.registerListener(new ChatListener(this));
    this.cacheManager = new CacheManager();

    this.messageScheduler = new Timer("AutoGL-Scheduler", true);

    this.logger().info("AutoGG Addon enabled.");
  }

  public void trySendGoodLuckMessage(String seconds) {
    if (!configuration().enabled().get() || recentlySentMessage.get()) {
      return;
    }

    final Random random = new Random();
    final String[] configuredMessages = configuration().getCustomMessage().getOrDefault("gl").split(",");
    final String messageToSend = configuredMessages[random.nextInt(configuredMessages.length)].trim();

    final Integer messageSecondsTrigger = configuration().getMessageSecondsTrigger().getOrDefault(5);
    final long cooldownMillis = 15000L;


    try {
      int secondsInt = Integer.parseInt(seconds);
      if (secondsInt <= messageSecondsTrigger) {
        Laby.references().chatExecutor().chat(messageToSend, false);

        recentlySentMessage.set(true);
        messageScheduler.schedule(new TimerTask() {
          @Override
          public void run() {
            recentlySentMessage.set(false);
          }
        }, cooldownMillis);
      }
    } catch (NumberFormatException e) {
      this.logger().warn("Could not parse seconds: " + seconds);
    }
  }

  public ServerRegistry getServerRegistry() {
    return serverRegistry;
  }

  @Override
  protected Class<? extends AutoGLConfiguration> configurationClass() {
    return AutoGLConfiguration.class;
  }
}