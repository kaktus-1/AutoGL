package de.kaktus4.autogl.core.server;

import java.util.List;

public class ServerConfigurationImpl implements ServerConfiguration {

  private final List<String> serverAddresses;
  private final List<String> triggerMessages;
  private final List<String> exclusionFilters;

  public ServerConfigurationImpl(List<String> addresses, List<String> formats,
      List<String> filters) {
    this.serverAddresses = List.copyOf(
        addresses); // Should be normally lower-case because of hardcoded implementation
    this.triggerMessages = List.copyOf(formats);
    this.exclusionFilters = List.copyOf(filters);
  }

  @Override
  public List<String> getServerAddresses() {
    return serverAddresses;
  }

  @Override
  public List<String> getTriggerMessages() {
    return triggerMessages;
  }

  @Override
  public List<String> getExclusionFilters() {
    return exclusionFilters;
  }
}