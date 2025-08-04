package de.kaktus4.autogl.core;

import de.kaktus4.autogl.core.listener.ChatListener;
import de.kaktus4.autogl.core.server.ServerRegistry;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

@AddonMain
public class AutoGLAddon extends LabyAddon<AutoGLConfiguration> {

  private ServerRegistry serverRegistry;
  private final AtomicBoolean recentlySentMessage = new AtomicBoolean(false);
  private Timer messageScheduler;

  @Override
  protected void enable() {
    this.registerSettingCategory();
    this.serverRegistry = new ServerRegistry();
    this.registerListener(new ChatListener(this));

    this.messageScheduler = new Timer("AutoGL-Scheduler", true);

    this.logger().info("AutoGG Addon enabled.");
  }

  public void trySendGoodLuckMessage(String seconds) {
    this.logger().info("[CHATLISTENER] Trigger Seconds: " + seconds);

    if (!configuration().enabled().get() || recentlySentMessage.get()) {
      this.logger().info("[CHATLISTENER] AutoGG Addon is disabled or a message was recently sent");
      return;
    }

    final Random random = new Random();

    final String[] configuredMessages = configuration().getCustomMessage().getOrDefault("gl").split(",");
    this.logger().info("[CHATLISTENER] configured messages are: " + Arrays.toString(configuredMessages));

    final String messageToSend = configuredMessages[random.nextInt(configuredMessages.length)].trim();
    this.logger().info("[CHATLISTENER] Sending " + messageToSend);

    final Integer messageSecondsTrigger = configuration().getMessageSecondsTrigger().getOrDefault(5);
    final long cooldownMillis = 15000L;


    try {
      int secondsInt = Integer.parseInt(seconds);
      if (secondsInt <= messageSecondsTrigger) {
        this.logger().info("[CHATLISTENER] Sending message");
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
      this.logger().warn("[CHATLISTENER] Could not parse seconds: " + seconds);
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