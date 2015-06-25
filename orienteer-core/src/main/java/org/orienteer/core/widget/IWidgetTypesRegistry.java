package org.orienteer.core.widget;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * Widget registry is a central place for manipulation of system's widget types
 */
@ImplementedBy(DefaultWidgetTypesRegistry.class)
public interface IWidgetTypesRegistry {
	public List<IWidgetType<?>> listWidgetTypes();
	public IWidgetType<?> lookupByTypeId(String typeId);
	public <T> List<IWidgetType<T>> lookupByDefaultDomain(String domain);
	public <T> List<IWidgetType<T>> lookupByDefaultDomainAndTab(String domain, String tab);
	public <T> List<IWidgetType<T>> lookupByType(Class<T> typeClass);
	public IWidgetType<?> lookupByWidgetClass(Class<? extends AbstractWidget<?>> widgetClass);
	public IWidgetTypesRegistry register(IWidgetType<?> description);
	public <T> IWidgetTypesRegistry register(Class<? extends AbstractWidget<T>> widgetClass);
	public IWidgetTypesRegistry register(String packageName);
}