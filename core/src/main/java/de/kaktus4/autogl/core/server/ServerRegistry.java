package de.kaktus4.autogl.core.server;

import java.util.ArrayList;
import java.util.Collections;
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
            "Die Runde beginnt in 1 Sekunde"
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