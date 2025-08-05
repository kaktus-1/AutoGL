package de.kaktus4.autogl.core.server;

import java.util.List;
import java.util.regex.Pattern;

public interface ServerConfiguration {

    /**
     * @return Eine Liste aller verfügbaren Serveradressen für einen Server
     */
    List<String> getServerAddresses();

    /**
     * @return Eine Liste aller Nachrichtenmuster, bei deren Erkennung die GL-Nachricht gesendet werden soll.
     */
    List<Pattern> getTriggerMessages();

    /**
     * @return Eine Liste von Zeichenfolgen, bei deren Vorhandensein in einer Nachricht die GL-Nachricht nicht gesendet werden soll
     */
    List<String> getExclusionFilters();
}