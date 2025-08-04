package de.kaktus4.autogl.core.server;

import java.util.List;
import java.util.regex.Pattern;

public class ServerRegistry {

  private final List<ServerConfiguration> serverConfigurations;

  public ServerRegistry() {
    this.serverConfigurations = List.of(
        new ServerConfigurationImpl(
          List.of("gommehd.net", "play.gommehd.net", "gommehd.de"),
          List.of(
              // Pay attention to casing or use Pattern.CASE_INSENSITIVE as 2nd parameter
              Pattern.compile("Die Runde beginnt in (\\d+) Sekunden"),
              Pattern.compile("The game starts in (\\d+) seconds")
          ),
          List.of(":")
        )
    );
  }

  /**
   * @return Eine unveränderliche Liste der konfigurierten Server.
   */
  public List<ServerConfiguration> getServerConfigurations() {
    return this.serverConfigurations;
  }
}