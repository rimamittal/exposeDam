package dampoc.core.impl;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.commons.util.DamUtil;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import dampoc.core.FetchDamAssets;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.Session;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Rima
 * Date: 5/29/15
 * Time: 9:59 AM
 * To change this template use File | Settings | File Templates.
 */
@Component(immediate = true)
@Service
@org.apache.felix.scr.annotations.Properties({
        @Property(name = "service.vendor", value = "Dam POC"),
        @Property(name = "service.description", value = "Component to fetch Dam assets as JSON")
})
public class FetchDamAssetsImpl implements FetchDamAssets{
    @Override
    public List<Asset> getDamAssetList(ResourceResolver resourceResolver, String damPath) {

        List<Asset> damAssetList = new ArrayList<Asset>();

        Session session = resourceResolver.adaptTo(Session.class);
        QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);

        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("path", damPath);
        queryMap.put("type", "dam:Asset");
        Query query = queryBuilder.createQuery(PredicateGroup.create(queryMap), session);
        query.setStart(0);
        query.setHitsPerPage(500);
        Iterator<Resource> damResources = query.getResult().getResources();

        while(damResources.hasNext()){
            Asset damAsset = DamUtil.resolveToAsset(damResources.next());
            damAssetList.add(damAsset);
        }

        return damAssetList;
    }
}
