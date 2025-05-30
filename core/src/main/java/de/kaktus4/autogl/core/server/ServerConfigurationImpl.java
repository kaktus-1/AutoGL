package de.kaktus4.autogl.core.server;

import java.util.List;
import java.util.stream.Collectors;

public class ServerConfigurationImpl implements ServerConfiguration {

    private final List<String> serverAddresses;
    private final List<String> triggerMessages;
    private final List<String> exclusionFilters;

    public ServerConfigurationImpl(List<String> addresses, List<String> formats, List<String> filters) {
        this.serverAddresses = List.copyOf(
            addresses.stream()
                     .map(String::toLowerCase)
                     .collect(Collectors.toList())
        );
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