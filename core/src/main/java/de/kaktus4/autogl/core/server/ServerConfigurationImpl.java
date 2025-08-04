package de.kaktus4.autogl.core.server;

import java.util.List;
import java.util.regex.Pattern;

public class ServerConfigurationImpl implements ServerConfiguration {

  private final List<String> serverAddresses;
  private final List<Pattern> triggerMessages;
  private final List<String> exclusionFilters;

  public ServerConfigurationImpl(List<String> addresses, List<Pattern> formats, List<String> filters) {
    this.serverAddresses = List.copyOf(addresses);
    this.triggerMessages = List.copyOf(formats);
    this.exclusionFilters = List.copyOf(filters);
  }

  @Override
  public List<String> getServerAddresses() {
    return serverAddresses;
  }

  @Override
  public List<Pattern> getTriggerMessages() {
    return triggerMessages;
  }

  @Override
  public List<String> getExclusionFilters() {
    return exclusionFilters;
  }
}