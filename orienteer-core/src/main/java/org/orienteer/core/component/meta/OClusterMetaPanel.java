package org.orienteer.core.component.meta;

import com.orientechnologies.orient.core.conflict.ORecordConflictStrategy;
import com.orientechnologies.orient.core.db.ODatabase;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.clusterselection.OClusterSelectionStrategy;
import com.orientechnologies.orient.core.metadata.security.ORule;
import com.orientechnologies.orient.core.storage.OCluster;
import org.apache.wicket.Component;
import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.orienteer.core.component.property.DisplayMode;
import org.orienteer.core.component.widget.schema.OClustersWidget;
import ru.ydn.wicket.wicketorientdb.OrientDbWebSession;
import ru.ydn.wicket.wicketorientdb.model.SimpleNamingModel;
import ru.ydn.wicket.wicketorientdb.security.OSecurityHelper;
import ru.ydn.wicket.wicketorientdb.security.OrientPermission;

import java.util.Arrays;

/**
 * Meta panel for {@link OCluster}
 *
 * @param <V> type of a value
 */
public class OClusterMetaPanel<V> extends AbstractComplexModeMetaPanel<OCluster, DisplayMode, String, V> implements IDisplayModeAware
{
    public OClusterMetaPanel(String id, IModel<DisplayMode> modeModel, IModel<OCluster> entityModel, IModel<String> criteryModel) {
        super(id, modeModel, entityModel, criteryModel);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected V getValue(OCluster entity, String critery) {
        if(OClustersWidget.CONFLICT_STRATEGY.equals(critery))
        {
            ORecordConflictStrategy strategy = entity.getRecordConflictStrategy();
            return (V)(strategy!=null?strategy.getName():null);
        }
        else {
            return (V) PropertyResolver.getValue(critery, entity);
        }
    }

    @Override
    protected void setValue(OCluster entity, String critery, V value) {
        ODatabaseDocument db = OrientDbWebSession.get().getDatabase();
        db.commit();
        try
        {
//            entity.set(OCluster.ATTRIBUTES.valueOf(critery), value);
        } finally
        {
            db.begin();
        }
    }

    @Override
    protected Component resolveComponent(String id, DisplayMode mode, String critery) {

        if(DisplayMode.EDIT.equals(mode) && !OSecurityHelper.isAllowed(ORule.ResourceGeneric.SCHEMA, null, OrientPermission.UPDATE))
        {
            mode = DisplayMode.VIEW;
        }
        if(DisplayMode.VIEW.equals(mode))
        {
            return new Label(id, getModel());
        }
        else if(DisplayMode.EDIT.equals(mode)) {
            return new TextField<V>(id, getModel()).setType(String.class);
        }
        return null;
    }

    @Override
    protected IModel<String> newLabelModel() {
        return new SimpleNamingModel<String>("cluster", getPropertyModel());
    }
}
