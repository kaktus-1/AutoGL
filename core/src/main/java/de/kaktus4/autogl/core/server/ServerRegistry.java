package de.kaktus4.autogl.core.server;

import java.util.List;

public class ServerRegistry {

  private final List<ServerConfiguration> serverConfigurations;

  public ServerRegistry() {
    this.serverConfigurations = List.of(new ServerConfigurationImpl(
        List.of("gommehd.net", "play.gommehd.net", "gommehd.de"),
        List.of(
            "Die Runde beginnt in 10 Sekunden",
            "Die Runde beginnt in 5 Sekunden",
            "Die Runde beginnt in 4 Sekunden",
            "Die Runde beginnt in 3 Sekunden",
            "Die Runde beginnt in 2 Sekunden",

            //ENGLISH
            "The game starts in 10 seconds",
            "The game starts in 5 seconds",
            "The game starts in 4 seconds",
            "The game starts in 3 seconds",
            "The game starts in 2 seconds"
        ),
        List.of(":")
    ));
  }

  /**
   * @return Eine unveränderliche Liste der konfigurierten Server.
   */
  public List<ServerConfiguration> getServerConfigurations() {
    return this.serverConfigurations;
  }
}