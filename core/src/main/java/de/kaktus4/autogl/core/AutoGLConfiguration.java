package de.kaktus4.autogl.core;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class AutoGLConfiguration extends AddonConfig {

  @SwitchWidget.SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @TextFieldWidget.TextFieldSetting
  private final ConfigProperty<String> customMessage = new ConfigProperty<>("gl");

  @SliderWidget.SliderSetting(min = 0.0F, max = 10.0F, steps = 0.1F)
  private final ConfigProperty<Double> messageDelaySeconds = new ConfigProperty<>(1.0);

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ConfigProperty<String> getCustomMessage() {
    return customMessage;
  }

  public ConfigProperty<Double> getMessageDelaySeconds() {
    return messageDelaySeconds;
  }
}