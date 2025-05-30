package de.kaktus4.autogl.core;

import de.kaktus4.autogl.core.listener.ChatListener;
import de.kaktus4.autogl.core.server.ServerRegistry;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
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

  public void trySendGoodLuckMessage() {
    if (!configuration().enabled().get() || recentlySentMessage.getAndSet(true)) {
      return;
    }

    final String messageToSend = configuration().getCustomMessage().getOrDefault("gl");
    final long delayMillis = (long) (configuration().getMessageDelaySeconds().getOrDefault(1.0) * 1000.0);
    final long cooldownMillis = 15000L;

    messageScheduler.schedule(new TimerTask() {
      @Override
      public void run() {
        if (configuration().enabled().get()) {
          Laby.references().chatExecutor().chat(messageToSend, false);
        }
      }
    }, delayMillis);

    messageScheduler.schedule(new TimerTask() {
      @Override
      public void run() {
        recentlySentMessage.set(false);
      }
    }, cooldownMillis);
  }

  public ServerRegistry getServerRegistry() {
    return serverRegistry;
  }

  @Override
  protected Class<? extends AutoGLConfiguration> configurationClass() {
    return AutoGLConfiguration.class;
  }
}